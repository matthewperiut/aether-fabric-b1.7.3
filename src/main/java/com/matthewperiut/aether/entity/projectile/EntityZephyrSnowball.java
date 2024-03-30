package com.matthewperiut.aether.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

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
        this.setSize(1.0F, 1.0F);
    }

    public EntityZephyrSnowball(World world, Double x, Double y, Double z) {
        super(world);
        setPosition(x, y, z);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRenderAtDistance(double d) {
        double d1 = this.boundingBox.averageDimension() * 4.0;
        d1 *= 64.0;
        return d < d1 * d1;
    }

    public EntityZephyrSnowball(World world, LivingEntity entityliving, double d, double d1, double d2) {
        super(world);
        this.field_9397_j = entityliving;
        this.setSize(1.0F, 1.0F);
        this.setPositionAndAngles(entityliving.x, entityliving.y, entityliving.z, entityliving.yaw, entityliving.pitch);
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        this.xVelocity = this.yVelocity = this.zVelocity = 0.0;
        d += this.rand.nextGaussian() * 0.4;
        d1 += this.rand.nextGaussian() * 0.4;
        d2 += this.rand.nextGaussian() * 0.4;
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
        if (xVelocity < 0.01 && yVelocity < 0.01 && zVelocity < 0.01 && ticksAlive > 10) {
            remove();
        }

        if (this.field_9398_i) {
            int i = this.world.getBlockId(this.field_9402_e, this.field_9401_f, this.field_9400_g);
            if (i == this.field_9399_h) {
                ++this.field_9396_k;
                if (this.field_9396_k == 1200) {
                    this.remove();
                }

                return;
            }

            this.field_9398_i = false;
            this.xVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.yVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.zVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.field_9396_k = 0;
            this.field_9395_l = 0;
        } else {
            ++this.field_9395_l;
        }

        Vec3d vec3d = Vec3d.from(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
        HitResult movingobjectposition = this.world.method_160(vec3d, vec3d1);
        vec3d = Vec3d.from(this.x, this.y, this.z);
        vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }

        Entity entity = null;
        List list = this.world.getEntities(this, this.boundingBox.duplicateAndExpand(this.xVelocity, this.yVelocity, this.zVelocity).expand(1.0, 1.0, 1.0));
        double d = 0.0;

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);
            if (entity1.method_1356() && (entity1 != this.field_9397_j || this.field_9395_l >= 25)) {
                float f2 = 0.3F;
                AxixAlignedBoundingBox axisalignedbb = entity1.boundingBox.expand((double) f2, (double) f2, (double) f2);
                HitResult movingobjectposition1 = axisalignedbb.method_89(vec3d, vec3d1);
                if (movingobjectposition1 != null) {
                    double d1 = vec3d.distanceTo(movingobjectposition1.field_1988);
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
            if (movingobjectposition.field_1989 != null) {
                if (!movingobjectposition.field_1989.damage(this.field_9397_j, 0)) {
                }

                Entity var10000 = movingobjectposition.field_1989;
                var10000.xVelocity += this.xVelocity;
                var10000 = movingobjectposition.field_1989;
                var10000.yVelocity += 0.2;
                var10000 = movingobjectposition.field_1989;
                var10000.zVelocity += this.zVelocity;
            }

            this.remove();
        }

        this.x += this.xVelocity;
        this.y += this.yVelocity;
        this.z += this.zVelocity;
        float f = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity);
        this.yaw = (float) (Math.atan2(this.xVelocity, this.zVelocity) * 180.0 / 3.1415927410125732);

        for (this.pitch = (float) (Math.atan2(this.yVelocity, (double) f) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
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
        if (this.method_1393()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25F;
                this.world.addParticle("bubble", this.x - this.xVelocity * (double) f3, this.y - this.yVelocity * (double) f3, this.z - this.zVelocity * (double) f3, this.xVelocity, this.yVelocity, this.zVelocity);
            }

            f1 = 0.8F;
        }

        this.xVelocity += this.field_9405_b;
        this.yVelocity += this.field_9404_c;
        this.zVelocity += this.field_9403_d;
        this.xVelocity *= (double) f1;
        this.yVelocity *= (double) f1;
        this.zVelocity *= (double) f1;
        this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, 0.0, 0.0, 0.0);
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.field_9402_e);
        nbttagcompound.put("yTile", (short) this.field_9401_f);
        nbttagcompound.put("zTile", (short) this.field_9400_g);
        nbttagcompound.put("inTile", (byte) this.field_9399_h);
        nbttagcompound.put("shake", (byte) this.field_9406_a);
        nbttagcompound.put("inGround", (byte) (this.field_9398_i ? 1 : 0));
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        this.field_9402_e = nbttagcompound.getShort("xTile");
        this.field_9401_f = nbttagcompound.getShort("yTile");
        this.field_9400_g = nbttagcompound.getShort("zTile");
        this.field_9399_h = nbttagcompound.getByte("inTile") & 255;
        this.field_9406_a = nbttagcompound.getByte("shake") & 255;
        this.field_9398_i = nbttagcompound.getByte("inGround") == 1;
    }

    public float method_1369() {
        return 1.0F;
    }

    public boolean damage(Entity entity, int i) {
        this.setAttacked();
        if (entity != null) {
            Vec3d vec3d = entity.getRotation();
            if (vec3d != null) {
                this.xVelocity = vec3d.x;
                this.yVelocity = vec3d.y;
                this.zVelocity = vec3d.z;
                this.field_9405_b = this.xVelocity * 0.1;
                this.field_9404_c = this.yVelocity * 0.1;
                this.field_9403_d = this.zVelocity * 0.1;
            }

            return true;
        } else {
            return false;
        }
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("ZephyrSnowball");
    }
}
