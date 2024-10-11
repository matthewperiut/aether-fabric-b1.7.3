package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.entity.projectile.EntityZephyrSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityZephyr extends FlyingEntity implements Monster, MobSpawnDataProvider {
    private long checkTime;
    private final long checkTimeInterval = 3000L;
    private double checkX;
    private double checkY;
    private double checkZ;
    private final double minTraversalDist = 3.0;
    private boolean isStuckWarning;
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;

    public EntityZephyr(final World world) {
        super(world);
        this.checkTime = 0L;
        this.checkX = 0.0;
        this.checkY = 0.0;
        this.checkZ = 0.0;
        this.isStuckWarning = false;
        this.courseChangeCooldown = 0;
        this.targetedEntity = null;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.texture = "aether:stationapi/textures/mobs/Zephyr.png";
        this.setBoundingBoxSpacing(4.0f, 4.0f);
    }

    @Override
    protected void tickLiving() {
        if (this.y < -2.0 || this.y > 130.0) {
            this.tryDespawn();
        }
        this.prevAttackCounter = this.attackCounter;
        final double d = this.waypointX - this.x;
        final double d2 = this.waypointY - this.y;
        final double d3 = this.waypointZ - this.z;
        final double d4 = MathHelper.sqrt(d * d + d2 * d2 + d3 * d3);
        if (d4 < 1.0 || d4 > 60.0) {
            this.waypointX = this.x + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.waypointY = this.y + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
            this.waypointZ = this.z + (this.random.nextFloat() * 2.0f - 1.0f) * 16.0f;
        }
        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.random.nextInt(5) + 2;
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d4)) {
                this.velocityX += d / d4 * 0.1;
                this.velocityY += d2 / d4 * 0.1;
                this.velocityZ += d3 / d4 * 0.1;
            } else {
                this.waypointX = this.x;
                this.waypointY = this.y;
                this.waypointZ = this.z;
            }
        }
        if (this.targetedEntity != null && this.targetedEntity.dead) {
            this.targetedEntity = null;
        }
        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.world.getClosestPlayer(this, 100.0);
            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }
        final double d5 = 64.0;
        if (this.targetedEntity != null && this.targetedEntity.getSquaredDistance(this) < d5 * d5) {
            final double d6 = this.targetedEntity.x - this.x;
            final double d7 = this.targetedEntity.boundingBox.minY + this.targetedEntity.height / 2.0f - (this.y + this.height / 2.0f);
            final double d8 = this.targetedEntity.z - this.z;
            final float n = -(float) Math.atan2(d6, d8) * 180.0f / 3.141593f;
            this.yaw = n;
            this.bodyYaw = n;
            if (this.canSee(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    this.world.playSound(this, "aether:mobs.zephyr.zephyrcall", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                }
                ++this.attackCounter;
                if (this.attackCounter == 20) {
                    this.world.playSound(this, "aether:mobs.zephyr.zephyrshoot", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
                    final EntityZephyrSnowball entitysnowball = new EntityZephyrSnowball(this.world, this, d6, d7, d8);
                    final double d9 = 4.0;
                    Vec3d vec3d = this.getLookVector(1.0F);
                    entitysnowball.x = this.x + vec3d.x * d8;
                    entitysnowball.y = this.y + (double) (this.height / 2.0F) + 0.5;
                    entitysnowball.z = this.z + vec3d.z * d8;
                    this.world.spawnEntity(entitysnowball);
                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.bodyYaw = this.yaw = -((float) Math.atan2(this.velocityX, this.velocityZ)) * 180.0F / 3.141593F;
            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }
        this.texture = "aether:stationapi/textures/mobs/Zephyr.png";
        //if (!this.world.isServerSide && this.world.difficulty == 0) {
        //this.remove();
        //}
        this.checkForBeingStuck();
    }

    private boolean isCourseTraversable(double d, double d1, double d2, double d3) {
        double d4 = (this.waypointX - this.x) / d3;
        double d5 = (this.waypointY - this.y) / d3;
        double d6 = (this.waypointZ - this.z) / d3;
        Box axisalignedbb = this.boundingBox.copy();

        for (int i = 1; (double) i < d3; ++i) {
            axisalignedbb.translate(d4, d5, d6);
            if (this.world.getEntityCollisions(this, axisalignedbb).size() > 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected String getRandomSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    @Override
    protected String getHurtSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    @Override
    protected String getDeathSound() {
        return "aether:mobs.zephyr.zephyrcall";
    }

    @Override
    protected int getDroppedItemId() {
        return AetherBlocks.Aercloud.id;
    }

    public boolean method_940() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 3.0f;
    }

    private void checkForBeingStuck() {
        final long curtime = System.currentTimeMillis();
        if (curtime > this.checkTime + 3000L) {
            final double diffx = this.x - this.checkX;
            final double diffy = this.y - this.checkY;
            final double diffz = this.z - this.checkZ;
            final double distanceTravelled = Math.sqrt(diffx * diffx + diffy * diffy + diffz * diffz);
            if (distanceTravelled < 3.0) {
                if (!this.isStuckWarning) {
                    this.isStuckWarning = true;
                } else {
                    this.markDead();
                }
            }
            this.checkX = this.x;
            this.checkY = this.y;
            this.checkZ = this.z;
            this.checkTime = curtime;
        }
    }

    @Override
    public boolean canSpawn() {
        final int i = MathHelper.floor(this.x);
        final int j = MathHelper.floor(this.boundingBox.minY);
        final int k = MathHelper.floor(this.z);
        boolean flag = this.random.nextInt(85) == 0 && this.world.canSpawnEntity(this.boundingBox) && this.world.getEntityCollisions(this, this.boundingBox).size() == 0
                && !this.world.isBoxSubmergedInFluid(this.boundingBox) && this.world.getBlockId(i, j - 1, k) != AetherBlocks.DungeonStone.id &&
                this.world.getBlockId(i, j - 1, k) != AetherBlocks.LightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedDungeonStone.id &&
                this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.Holystone.id &&
                this.world.difficulty > 0;
        return flag;
    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Zephyr");
    }
}
