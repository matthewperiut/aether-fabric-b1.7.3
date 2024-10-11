package com.matthewperiut.aether.entity.special;

import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityCloudParachute extends Entity implements EntitySpawnDataProvider {
    private LivingEntity entityUsing;
    private boolean justServerSpawned;
    private static Map<LivingEntity, EntityCloudParachute> cloudMap = new HashMap();
    private static final float MAX_FALL_SPEED = 0.25F;
    private static final double ANIM_RADIUS = 0.75;
    public boolean gold;

    public EntityCloudParachute(World world) {
        super(world);
        this.justServerSpawned = false;
        this.setBoundingBoxSpacing(1.0F, 1.0F);
    }

    public EntityCloudParachute(World world, double d, double d1, double d2) {
        this(world);
        this.setPositionAndAngles(d, d1, d2, this.yaw, this.pitch);
        this.justServerSpawned = true;
    }

    public EntityCloudParachute(World world, LivingEntity entityliving, boolean bool) {
        this(world);
        if (entityliving == null) {
            throw new IllegalArgumentException("entityliving cannot be null.");
        } else {
            this.entityUsing = entityliving;
            cloudMap.put(entityliving, this);
            this.prevX = this.x;
            this.prevY = this.y;
            this.prevZ = this.z;
            this.moveToEntityUsing();
            this.gold = bool;
        }
    }

    public static EntityCloudParachute getCloudBelongingToEntity(LivingEntity entity) {
        return (EntityCloudParachute) cloudMap.get(entity);
    }

    public World getWorld() {
        return this.world;
    }

    public void die() {
        if (this.entityUsing != null) {
            if (cloudMap.containsKey(this.entityUsing)) {
                cloudMap.remove(this.entityUsing);
            }

            for (int i = 0; i < 32; ++i) {
                doCloudSmoke(this.world, this.entityUsing);
            }

            this.world.playSound(this.entityUsing, "cloud", 1.0F, 1.0F / (this.random.nextFloat() * 0.1F + 0.95F));
        }

        this.entityUsing = null;
        this.dead = true;
    }

    public static void doCloudSmoke(World world, LivingEntity entityliving) {
        double x = entityliving.x + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        double y = entityliving.boundingBox.minY - 0.5 + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        double z = entityliving.z + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        world.addParticle("white_cloud_smoke", x, y, z, 0.0, 0.0, 0.0);
    }

    public static boolean entityHasRoomForCloud(World world, LivingEntity entityliving) {
        Box boundingBox = Box.create(entityliving.x - 0.5, entityliving.boundingBox.minY - 1.0, entityliving.z - 0.5, entityliving.x + 0.5, entityliving.boundingBox.minY, entityliving.z + 0.5);
        return world.getEntityCollisions(entityliving, boundingBox).size() == 0 && !world.isFluidInBox(boundingBox, Material.WATER);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRender(double d) {
        return this.entityUsing != null ? this.entityUsing.shouldRender(d) : super.shouldRender(d);
    }

    public boolean isCollidable() {
        return true;
    }

    public Box getBoundingBox() {
        return this.boundingBox;
    }

    public void tick() {
        if (!this.dead) {
            if (this.entityUsing == null) {
                if (this.world.isRemote && !this.justServerSpawned) {
                    this.die();
                    return;
                }

                this.justServerSpawned = false;
                this.entityUsing = this.findUser();
                if (this.entityUsing == null) {
                    this.die();
                    return;
                }

                cloudMap.put(this.entityUsing, this);
            }

            if (this.entityUsing.velocityY < -0.25) {
                this.entityUsing.velocityY = -0.25;
            }

            ((EntityAccessor) entityUsing).setFallDistance(0.0F);
            doCloudSmoke(this.world, this.entityUsing);
            this.moveToEntityUsing();
        }
    }

    private LivingEntity findUser() {
        List entities = this.world.collectEntitiesByClass(LivingEntity.class, this.boundingBox.copy().translate(0.0, 1.0, 0.0));
        double minDeltaSquared = -1.0;
        LivingEntity entityliving = null;

        for (int i = 0; i < entities.size(); ++i) {
            LivingEntity entitylivingtemp = (LivingEntity) entities.get(i);
            if (entitylivingtemp.isAlive()) {
                double xDelta = this.x - entitylivingtemp.x;
                double yDelta = this.boundingBox.maxY - entitylivingtemp.boundingBox.minY;
                double zDelta = this.z - entitylivingtemp.z;
                double deltaSquared = xDelta * xDelta + yDelta * yDelta + zDelta * zDelta;
                if (minDeltaSquared == -1.0 || deltaSquared < minDeltaSquared) {
                    minDeltaSquared = deltaSquared;
                    entityliving = entitylivingtemp;
                }
            }
        }

        return entityliving;
    }

    private void moveToEntityUsing() {
        this.setPositionAndAngles(this.entityUsing.x, this.entityUsing.boundingBox.minY - (double) (this.height / 2.0F), this.entityUsing.z, this.entityUsing.yaw, this.entityUsing.pitch);
        this.velocityX = this.entityUsing.velocityX;
        this.velocityY = this.entityUsing.velocityY;
        this.velocityZ = this.entityUsing.velocityZ;
        this.yaw = this.entityUsing.yaw;
        if (this.isCollided()) {
            this.die();
        }

    }

    private boolean isCollided() {
        return this.world.getEntityCollisions(this, this.boundingBox).size() > 0 || this.world.isFluidInBox(this.boundingBox, Material.WATER);
    }

    public void onPlayerInteraction(PlayerEntity entityplayer) {
    }

    protected void readNbt(NbtCompound nbttagcompound) {
    }

    protected void writeNbt(NbtCompound nbttagcompound) {
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("CloudParachute");
    }
}
