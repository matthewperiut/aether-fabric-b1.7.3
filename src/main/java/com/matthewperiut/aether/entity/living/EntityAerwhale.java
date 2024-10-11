package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityAerwhale extends FlyingEntity implements Monster, MobSpawnDataProvider {
    private final long checkTimeInterval = 3000L;
    private final double minTraversalDist = 3.0;
    public int courseChangeCooldown = 0;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    public int prevAttackCounter;
    public int attackCounter;
    public double motionYaw;
    public double motionPitch;
    private long checkTime = 0L;
    private double checkX = 0.0;
    private double checkY = 0.0;
    private double checkZ = 0.0;
    private boolean isStuckWarning = false;
    private Entity targetedEntity = null;
    private int aggroCooldown;

    public EntityAerwhale(World world) {
        super(world);
        this.fireImmune = true;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.texture = "aether:stationapi/textures/mobs/Mob_Aerwhale.png";
        this.setBoundingBoxSpacing(4.0F, 4.0F);
        this.movementSpeed = 0.5F;
        this.health = 20;
        this.yaw = 360.0F * this.random.nextFloat();
        this.pitch = 90.0F * this.random.nextFloat() - 45.0F;
        this.ignoreFrustumCull = true;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
    }

    public void tickMovement() {
    }

    public void tick() {
        byte byte0 = this.dataTracker.getByte(16);
        this.texture = byte0 != 1 ? "aether:stationapi/textures/mobs/Mob_Aerwhale.png" : "aether:stationapi/textures/mobs/Mob_Aerwhale.png";
        double[] distances = new double[]{this.openSpace(0.0F, 0.0F), this.openSpace(45.0F, 0.0F), this.openSpace(0.0F, 45.0F), this.openSpace(-45.0F, 0.0F), this.openSpace(0.0F, -45.0F)};
        int longest = 0;

        int i;
        for (i = 1; i < 5; ++i) {
            if (distances[i] > distances[longest]) {
                longest = i;
            }
        }

        switch (longest) {
            case 0:
                if (distances[0] == 50.0) {
                    this.motionYaw *= 0.8999999761581421;
                    this.motionPitch *= 0.8999999761581421;
                    if (this.y > 100.0) {
                        this.motionPitch -= 2.0;
                    }

                    if (this.y < 20.0) {
                        this.motionPitch += 2.0;
                    }
                } else {
                    this.pitch = -this.pitch;
                    this.yaw = -this.yaw;
                }
                break;
            case 1:
                this.motionYaw += 5.0;
                break;
            case 2:
                this.motionPitch -= 5.0;
                break;
            case 3:
                this.motionYaw -= 5.0;
                break;
            case 4:
                this.motionPitch += 5.0;
        }

        this.motionYaw += (double) (2.0F * this.random.nextFloat() - 1.0F);
        this.motionPitch += (double) (2.0F * this.random.nextFloat() - 1.0F);
        this.pitch = (float) ((double) this.pitch + 0.1 * this.motionPitch);
        this.yaw = (float) ((double) this.yaw + 0.1 * this.motionYaw);
        if (this.pitch < -60.0F) {
            this.pitch = -60.0F;
        }

        if (this.pitch > 60.0F) {
            this.pitch = 60.0F;
        }

        this.pitch = (float) ((double) this.pitch * 0.99);
        this.velocityX += 0.005 * Math.cos((double) this.yaw / 180.0 * Math.PI) * Math.cos((double) this.pitch / 180.0 * Math.PI);
        this.velocityY += 0.005 * Math.sin((double) this.pitch / 180.0 * Math.PI);
        this.velocityZ += 0.005 * Math.sin((double) this.yaw / 180.0 * Math.PI) * Math.cos((double) this.pitch / 180.0 * Math.PI);
        this.velocityX *= 0.98;
        this.velocityY *= 0.98;
        this.velocityZ *= 0.98;
        i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        if (this.velocityX > 0.0 && this.world.getBlockId(i + 1, j, k) != 0) {
            this.velocityX = -this.velocityX;
            this.motionYaw -= 10.0;
        } else if (this.velocityX < 0.0 && this.world.getBlockId(i - 1, j, k) != 0) {
            this.velocityX = -this.velocityX;
            this.motionYaw += 10.0;
        }

        if (this.velocityY > 0.0 && this.world.getBlockId(i, j + 1, k) != 0) {
            this.velocityY = -this.velocityY;
            this.motionPitch -= 10.0;
        } else if (this.velocityY < 0.0 && this.world.getBlockId(i, j - 1, k) != 0) {
            this.velocityY = -this.velocityY;
            this.motionPitch += 10.0;
        }

        if (this.velocityZ > 0.0 && this.world.getBlockId(i, j, k + 1) != 0) {
            this.velocityZ = -this.velocityZ;
            this.motionYaw -= 10.0;
        } else if (this.velocityZ < 0.0 && this.world.getBlockId(i, j, k - 1) != 0) {
            this.velocityZ = -this.velocityZ;
            this.motionYaw += 10.0;
        }

        this.fireTicks = 0;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.checkForBeingStuck();
    }

    public double getSpeed() {
        return Math.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
    }

    private double openSpace(float rotationYawOffset, float rotationPitchOffset) {
        float yaw = this.yaw + rotationYawOffset;
        float pitch = this.yaw + rotationYawOffset;
        Vec3d vec3d = Vec3d.createCached(this.x, this.y, this.z);
        float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 50.0;
        Vec3d vec3d1 = vec3d.add((double) f7 * d3, (double) f6 * d3, (double) f9 * d3);
        HitResult movingobjectposition = this.world.raycast(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return 50.0;
        } else if (movingobjectposition.type == HitResultType.BLOCK) {
            double i = (double) movingobjectposition.blockX - this.x;
            double j = (double) movingobjectposition.blockY - this.y;
            double k = (double) movingobjectposition.blockZ - this.z;
            return Math.sqrt(i * i + j * j + k * k);
        } else {
            return 50.0;
        }
    }

    protected void tickLiving() {
    }

    private void checkForBeingStuck() {
        long curtime = System.currentTimeMillis();
        if (curtime > this.checkTime + 3000L) {
            double diffx = this.x - this.checkX;
            double diffy = this.y - this.checkY;
            double diffz = this.z - this.checkZ;
            double distanceTravelled = Math.sqrt(diffx * diffx + diffy * diffy + diffz * diffz);
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

    protected String getRandomSound() {
        return "aether:mobs.aerwhale.aerwhalecall";
    }

    protected String getHurtSound() {
        return "aether:mobs.aerwhale.aerwhaledeath";
    }

    protected String getDeathSound() {
        return "aether:mobs.aerwhale.aerwhaledeath";
    }

    protected float getSoundVolume() {
        return 3.0F;
    }

    public int getLimitPerChunk() {
        return 1;
    }

    public boolean canDespawn() {
        return true;
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.random.nextInt(65) == 0 && this.world.canSpawnEntity(this.boundingBox) && this.world.getEntityCollisions(this, this.boundingBox).size() == 0 && !this.world.isBoxSubmergedInFluid(this.boundingBox) && this.world.getBlockId(i, j - 1, k) != AetherBlocks.DungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedLightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.Holystone.id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Aerwhale");
    }
}
