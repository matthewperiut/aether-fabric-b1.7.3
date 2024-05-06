package com.matthewperiut.aether.entity.special;

import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.AxixAlignedBoundingBox;
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
        this.setSize(1.0F, 1.0F);
    }

    public EntityCloudParachute(World world, double d, double d1, double d2) {
        this(world);
        this.method_1338(d, d1, d2, this.yaw, this.pitch);
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

            this.world.playSound(this.entityUsing, "cloud", 1.0F, 1.0F / (this.rand.nextFloat() * 0.1F + 0.95F));
        }

        this.entityUsing = null;
        this.removed = true;
    }

    public static void doCloudSmoke(World world, LivingEntity entityliving) {
        double x = entityliving.x + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        double y = entityliving.boundingBox.minY - 0.5 + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        double z = entityliving.z + ((EntityAccessor) entityliving).getRand().nextDouble() * 0.75 * 2.0 - 0.75;
        world.addParticle("white_cloud_smoke", x, y, z, 0.0, 0.0, 0.0);
    }

    public static boolean entityHasRoomForCloud(World world, LivingEntity entityliving) {
        AxixAlignedBoundingBox boundingBox = AxixAlignedBoundingBox.create(entityliving.x - 0.5, entityliving.boundingBox.minY - 1.0, entityliving.z - 0.5, entityliving.x + 0.5, entityliving.boundingBox.minY, entityliving.z + 0.5);
        return world.method_190(entityliving, boundingBox).size() == 0 && !world.method_207(boundingBox, Material.WATER);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRenderAtDistance(double d) {
        return this.entityUsing != null ? this.entityUsing.shouldRenderAtDistance(d) : super.shouldRenderAtDistance(d);
    }

    public boolean method_1356() {
        return true;
    }

    public AxixAlignedBoundingBox method_1381() {
        return this.boundingBox;
    }

    public void tick() {
        if (!this.removed) {
            if (this.entityUsing == null) {
                if (this.world.isClient && !this.justServerSpawned) {
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

            if (this.entityUsing.yVelocity < -0.25) {
                this.entityUsing.yVelocity = -0.25;
            }

            ((EntityAccessor) entityUsing).setFallDistance(0.0F);
            doCloudSmoke(this.world, this.entityUsing);
            this.moveToEntityUsing();
        }
    }

    private LivingEntity findUser() {
        List entities = this.world.getEntities(LivingEntity.class, this.boundingBox.method_92().addPos(0.0, 1.0, 0.0));
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
        this.method_1338(this.entityUsing.x, this.entityUsing.boundingBox.minY - (double) (this.height / 2.0F), this.entityUsing.z, this.entityUsing.yaw, this.entityUsing.pitch);
        this.xVelocity = this.entityUsing.xVelocity;
        this.yVelocity = this.entityUsing.yVelocity;
        this.zVelocity = this.entityUsing.zVelocity;
        this.yaw = this.entityUsing.yaw;
        if (this.isCollided()) {
            this.die();
        }

    }

    private boolean isCollided() {
        return this.world.method_190(this, this.boundingBox).size() > 0 || this.world.method_207(this.boundingBox, Material.WATER);
    }

    public void onPlayerCollision(PlayerEntity entityplayer) {
    }

    protected void readAdditional(CompoundTag nbttagcompound) {
    }

    protected void writeAdditional(CompoundTag nbttagcompound) {
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("CloudParachute");
    }
}
