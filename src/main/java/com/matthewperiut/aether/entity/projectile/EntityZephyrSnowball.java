package com.matthewperiut.aether.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityZephyrSnowball extends Entity implements EntitySpawnDataProvider {
    private int ticksAlive = 0;
    private int field_9402_e = -1;
    private int field_9401_f = -1;
    private int field_9400_g = -1;
    private int field_9399_h = 0;
    private boolean field_9398_i = false;
    public int field_9406_a = 0;
    private LivingEntity field_9397_j;
    private int field_9396_k;
    private int field_9395_l = 0;
    public double field_9405_b;
    public double field_9404_c;
    public double field_9403_d;

    public EntityZephyrSnowball(World world) {
        super(world);
        this.setBoundingBoxSpacing(1.0F, 1.0F);
    }

    public EntityZephyrSnowball(World world, Double x, Double y, Double z) {
        super(world);
        setPosition(x, y, z);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRender(double d) {
        double d1 = this.boundingBox.getAverageSideLength() * 4.0;
        d1 *= 64.0;
        return d < d1 * d1;
    }

    public EntityZephyrSnowball(World world, LivingEntity entityliving, double d, double d1, double d2) {
        super(world);
        this.field_9397_j = entityliving;
        this.setBoundingBoxSpacing(1.0F, 1.0F);
        this.setPositionAndAnglesKeepPrevAngles(entityliving.x, entityliving.y, entityliving.z, entityliving.yaw, entityliving.pitch);
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        this.velocityX = this.velocityY = this.velocityZ = 0.0;
        d += this.random.nextGaussian() * 0.4;
        d1 += this.random.nextGaussian() * 0.4;
        d2 += this.random.nextGaussian() * 0.4;
        double d3 = (double) MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        this.field_9405_b = d / d3 * 0.1;
        this.field_9404_c = d1 / d3 * 0.1;
        this.field_9403_d = d2 / d3 * 0.1;
    }

    public void tick() {
        super.tick();
        if (this.field_9406_a > 0) {
            --this.field_9406_a;
        }

        // clean up still projectiles
        ticksAlive++;
        if (velocityX < 0.01 && velocityY < 0.01 && velocityZ < 0.01 && ticksAlive > 10) {
            markDead();
        }

        if (this.field_9398_i) {
            int i = this.world.getBlockId(this.field_9402_e, this.field_9401_f, this.field_9400_g);
            if (i == this.field_9399_h) {
                ++this.field_9396_k;
                if (this.field_9396_k == 1200) {
                    this.markDead();
                }

                return;
            }

            this.field_9398_i = false;
            this.velocityX *= (double) (this.random.nextFloat() * 0.2F);
            this.velocityY *= (double) (this.random.nextFloat() * 0.2F);
            this.velocityZ *= (double) (this.random.nextFloat() * 0.2F);
            this.field_9396_k = 0;
            this.field_9395_l = 0;
        } else {
            ++this.field_9395_l;
        }

        Vec3d vec3d = Vec3d.createCached(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.world.raycast(vec3d, vec3d1);
        vec3d = Vec3d.createCached(this.x, this.y, this.z);
        vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }

        Entity entity = null;
        List list = this.world.getEntities(this, this.boundingBox.stretch(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
        double d = 0.0;

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);
            if (entity1.isCollidable() && (entity1 != this.field_9397_j || this.field_9395_l >= 25)) {
                float f2 = 0.3F;
                Box axisalignedbb = entity1.boundingBox.expand((double) f2, (double) f2, (double) f2);
                HitResult movingobjectposition1 = axisalignedbb.raycast(vec3d, vec3d1);
                if (movingobjectposition1 != null) {
                    double d1 = vec3d.distanceTo(movingobjectposition1.pos);
                    if (d1 < d || d == 0.0) {
                        entity = entity1;
                        d = d1;
                    }
                }
            }
        }

        if (entity != null) {
            movingobjectposition = new HitResult(entity);
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.entity != null) {
                if (!movingobjectposition.entity.damage(this.field_9397_j, 0)) {
                }

                Entity var10000 = movingobjectposition.entity;
                var10000.velocityX += this.velocityX;
                var10000 = movingobjectposition.entity;
                var10000.velocityY += 0.2;
                var10000 = movingobjectposition.entity;
                var10000.velocityZ += this.velocityZ;
            }

            this.markDead();
        }

        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;
        float f = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);

        for (this.pitch = (float) (Math.atan2(this.velocityY, (double) f) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
        }

        while (this.pitch - this.prevPitch >= 180.0F) {
            this.prevPitch += 360.0F;
        }

        while (this.yaw - this.prevYaw < -180.0F) {
            this.prevYaw -= 360.0F;
        }

        while (this.yaw - this.prevYaw >= 180.0F) {
            this.prevYaw += 360.0F;
        }

        this.pitch = this.prevPitch + (this.pitch - this.prevPitch) * 0.2F;
        this.yaw = this.prevYaw + (this.yaw - this.prevYaw) * 0.2F;
        float f1 = 0.95F;
        if (this.checkWaterCollisions()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25F;
                this.world.addParticle("bubble", this.x - this.velocityX * (double) f3, this.y - this.velocityY * (double) f3, this.z - this.velocityZ * (double) f3, this.velocityX, this.velocityY, this.velocityZ);
            }

            f1 = 0.8F;
        }

        this.velocityX += this.field_9405_b;
        this.velocityY += this.field_9404_c;
        this.velocityZ += this.field_9403_d;
        this.velocityX *= (double) f1;
        this.velocityY *= (double) f1;
        this.velocityZ *= (double) f1;
        this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        nbttagcompound.putShort("xTile", (short) this.field_9402_e);
        nbttagcompound.putShort("yTile", (short) this.field_9401_f);
        nbttagcompound.putShort("zTile", (short) this.field_9400_g);
        nbttagcompound.putByte("inTile", (byte) this.field_9399_h);
        nbttagcompound.putByte("shake", (byte) this.field_9406_a);
        nbttagcompound.putByte("inGround", (byte) (this.field_9398_i ? 1 : 0));
    }

    public void readNbt(NbtCompound nbttagcompound) {
        this.field_9402_e = nbttagcompound.getShort("xTile");
        this.field_9401_f = nbttagcompound.getShort("yTile");
        this.field_9400_g = nbttagcompound.getShort("zTile");
        this.field_9399_h = nbttagcompound.getByte("inTile") & 255;
        this.field_9406_a = nbttagcompound.getByte("shake") & 255;
        this.field_9398_i = nbttagcompound.getByte("inGround") == 1;
    }

    public float getTargetingMargin() {
        return 1.0F;
    }

    public boolean damage(Entity entity, int i) {
        this.scheduleVelocityUpdate();
        if (entity != null) {
            Vec3d vec3d = entity.getLookVector();
            if (vec3d != null) {
                this.velocityX = vec3d.x;
                this.velocityY = vec3d.y;
                this.velocityZ = vec3d.z;
                this.field_9405_b = this.velocityX * 0.1;
                this.field_9404_c = this.velocityY * 0.1;
                this.field_9403_d = this.velocityZ * 0.1;
            }

            return true;
        } else {
            return false;
        }
    }

    public float getShadowRadius() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("ZephyrSnowball");
    }
}
