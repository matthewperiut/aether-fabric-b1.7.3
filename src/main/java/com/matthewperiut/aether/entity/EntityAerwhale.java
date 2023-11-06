package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitType;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;

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
        this.immuneToFire = true;
        this.aggroCooldown = 0;
        this.prevAttackCounter = 0;
        this.attackCounter = 0;
        this.texture = "aether:stationapi/textures/mobs/Mob_Aerwhale.png";
        this.setSize(4.0F, 4.0F);
        this.movementSpeed = 0.5F;
        this.health = 20;
        this.yaw = 360.0F * this.rand.nextFloat();
        this.pitch = 90.0F * this.rand.nextFloat() - 45.0F;
        this.field_1622 = true;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
    }

    public void updateDespawnCounter() {
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

        this.motionYaw += (double) (2.0F * this.rand.nextFloat() - 1.0F);
        this.motionPitch += (double) (2.0F * this.rand.nextFloat() - 1.0F);
        this.pitch = (float) ((double) this.pitch + 0.1 * this.motionPitch);
        this.yaw = (float) ((double) this.yaw + 0.1 * this.motionYaw);
        if (this.pitch < -60.0F) {
            this.pitch = -60.0F;
        }

        if (this.pitch > 60.0F) {
            this.pitch = 60.0F;
        }

        this.pitch = (float) ((double) this.pitch * 0.99);
        this.xVelocity += 0.005 * Math.cos((double) this.yaw / 180.0 * Math.PI) * Math.cos((double) this.pitch / 180.0 * Math.PI);
        this.yVelocity += 0.005 * Math.sin((double) this.pitch / 180.0 * Math.PI);
        this.zVelocity += 0.005 * Math.sin((double) this.yaw / 180.0 * Math.PI) * Math.cos((double) this.pitch / 180.0 * Math.PI);
        this.xVelocity *= 0.98;
        this.yVelocity *= 0.98;
        this.zVelocity *= 0.98;
        i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        if (this.xVelocity > 0.0 && this.world.getBlockId(i + 1, j, k) != 0) {
            this.xVelocity = -this.xVelocity;
            this.motionYaw -= 10.0;
        } else if (this.xVelocity < 0.0 && this.world.getBlockId(i - 1, j, k) != 0) {
            this.xVelocity = -this.xVelocity;
            this.motionYaw += 10.0;
        }

        if (this.yVelocity > 0.0 && this.world.getBlockId(i, j + 1, k) != 0) {
            this.yVelocity = -this.yVelocity;
            this.motionPitch -= 10.0;
        } else if (this.yVelocity < 0.0 && this.world.getBlockId(i, j - 1, k) != 0) {
            this.yVelocity = -this.yVelocity;
            this.motionPitch += 10.0;
        }

        if (this.zVelocity > 0.0 && this.world.getBlockId(i, j, k + 1) != 0) {
            this.zVelocity = -this.zVelocity;
            this.motionYaw -= 10.0;
        } else if (this.zVelocity < 0.0 && this.world.getBlockId(i, j, k - 1) != 0) {
            this.zVelocity = -this.zVelocity;
            this.motionYaw += 10.0;
        }

        this.fireTicks = 0;
        this.move(this.xVelocity, this.yVelocity, this.zVelocity);
        this.checkForBeingStuck();
    }

    public double getSpeed() {
        return Math.sqrt(this.xVelocity * this.xVelocity + this.yVelocity * this.yVelocity + this.zVelocity * this.zVelocity);
    }

    private double openSpace(float rotationYawOffset, float rotationPitchOffset) {
        float yaw = this.yaw + rotationYawOffset;
        float pitch = this.yaw + rotationYawOffset;
        Vec3d vec3d = Vec3d.from(this.x, this.y, this.z);
        float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 50.0;
        Vec3d vec3d1 = vec3d.translate((double) f7 * d3, (double) f6 * d3, (double) f9 * d3);
        HitResult movingobjectposition = this.world.method_161(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return 50.0;
        } else if (movingobjectposition.type == HitType.field_789) {
            double i = (double) movingobjectposition.x - this.x;
            double j = (double) movingobjectposition.y - this.y;
            double k = (double) movingobjectposition.z - this.z;
            return Math.sqrt(i * i + j * j + k * k);
        } else {
            return 50.0;
        }
    }

    protected void tickHandSwing() {
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
                    this.remove();
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
        AxixAlignedBoundingBox axisalignedbb = this.boundingBox.method_92();

        for (int i = 1; (double) i < d3; ++i) {
            axisalignedbb.addPos(d4, d5, d6);
            if (this.world.method_190(this, axisalignedbb).size() > 0) {
                return false;
            }
        }

        return true;
    }

    protected String getAmbientSound() {
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
        return this.rand.nextInt(65) == 0 && this.world.canSpawnEntity(this.boundingBox) && this.world.method_190(this, this.boundingBox).size() == 0 && !this.world.method_218(this.boundingBox) && this.world.getBlockId(i, j - 1, k) != AetherBlocks.DungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedLightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.Holystone.id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Aerwhale");
    }
}
