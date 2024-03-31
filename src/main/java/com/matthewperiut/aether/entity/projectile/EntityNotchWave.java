//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.entity.living.EntitySlider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class EntityNotchWave extends Entity implements EntitySpawnDataProvider {
    private int xTileSnowball = -1;
    private int yTileSnowball = -1;
    private int zTileSnowball = -1;
    private int inTileSnowball = 0;
    private boolean inGroundSnowball = false;
    public int shakeSnowball = 0;
    private LivingEntity thrower;
    private EntitySlider slider;
    private int ticksInGroundSnowball;
    private int ticksInAirSnowball = 0;

    public EntityNotchWave(World world) {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRenderAtDistance(double d) {
        double d1 = this.boundingBox.averageDimension() * 4.0;
        d1 *= 64.0;
        return d < d1 * d1;
    }

    public EntityNotchWave(World world, LivingEntity entityliving) {
        super(world);
        this.thrower = entityliving;
        this.setSize(0.25F, 0.25F);
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double) entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        float f = 0.4F;
        this.xVelocity = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F) * f);
        this.zVelocity = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F) * f);
        this.yVelocity = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F) * f);
        this.setSnowballHeading(this.xVelocity, this.yVelocity, this.zVelocity, 1.5F, 1.0F);
    }

    public EntityNotchWave(World world, double d, double d1, double d2) {
        super(world);
        this.ticksInGroundSnowball = 0;
        this.setSize(0.25F, 0.25F);
        this.method_1338(d, d1, d2, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
    }

    public void setSnowballHeading(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d += this.rand.nextGaussian() * 0.007499999832361937 * (double) f1;
        d1 += this.rand.nextGaussian() * 0.007499999832361937 * (double) f1;
        d2 += this.rand.nextGaussian() * 0.007499999832361937 * (double) f1;
        d *= (double) f;
        d1 *= (double) f;
        d2 *= (double) f;
        this.xVelocity = d;
        this.yVelocity = d1;
        this.zVelocity = d2;
        float f3 = MathHelper.sqrt(d * d + d2 * d2);
        this.prevYaw = this.yaw = (float) (Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float) (Math.atan2(d1, (double) f3) * 180.0 / 3.1415927410125732);
        this.ticksInGroundSnowball = 0;
    }

    public void setVelocity(double d, double d1, double d2) {
        this.xVelocity = d;
        this.yVelocity = d1;
        this.zVelocity = d2;
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(d * d + d2 * d2);
            this.prevYaw = this.yaw = (float) (Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(d1, (double) f) * 180.0 / 3.1415927410125732);
        }

    }

    public void tick() {
        this.prevRenderX = this.x;
        this.prevRenderY = this.y;
        this.prevRenderZ = this.z;
        super.tick();
        if (this.shakeSnowball > 0) {
            --this.shakeSnowball;
        }

        if (this.ticksInAirSnowball > 100) {
            this.remove();
        }

        if (this.inGroundSnowball) {
            this.remove();
        } else {
            ++this.ticksInAirSnowball;
        }

        Vec3d vec3d = Vec3d.from(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
        HitResult movingobjectposition = this.world.method_160(vec3d, vec3d1);
        vec3d = Vec3d.from(this.x, this.y, this.z);
        vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
        }

        if (!this.world.isClient) {
            Entity entity = null;
            List list = this.world.getEntities(this, this.boundingBox.duplicateAndExpand(this.xVelocity, this.yVelocity, this.zVelocity).expand(4.0, 4.0, 4.0));
            double d = 0.0;

            for (int l = 0; l < list.size(); ++l) {
                Entity entity1 = (Entity) list.get(l);
                if (entity1.method_1356() && (entity1 != this.thrower || this.ticksInAirSnowball >= 5)) {
                    float f4 = 0.3F;
                    if (entity1 != this.thrower) {
                        entity1.damage(this.thrower, 5);
                    }

                    AxixAlignedBoundingBox axisalignedbb = entity1.boundingBox.expand((double) f4, (double) f4, (double) f4);
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
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.field_1989 != null && movingobjectposition.field_1989 != this.thrower && movingobjectposition.field_1989 != this.slider) {
                if (!movingobjectposition.field_1989.damage(this.thrower, 0)) {
                }

                Entity var10000 = movingobjectposition.field_1989;
                var10000.xVelocity += this.xVelocity;
                var10000 = movingobjectposition.field_1989;
                var10000.yVelocity += 0.6;
                var10000 = movingobjectposition.field_1989;
                var10000.zVelocity += this.zVelocity;
            }

            for (int j = 0; j < 8; ++j) {
                this.world.addParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0);
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
        float f1 = 0.99F;
        float f2 = 0.03F;
        if (this.method_1334()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25F;
                this.world.addParticle("bubble", this.x - this.xVelocity * (double) f3, this.y - this.yVelocity * (double) f3, this.z - this.zVelocity * (double) f3, this.xVelocity, this.yVelocity, this.zVelocity);
            }

            f1 = 0.8F;
        }

        this.xVelocity *= (double) f1;
        this.yVelocity *= (double) f1;
        this.zVelocity *= (double) f1;
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.xTileSnowball);
        nbttagcompound.put("yTile", (short) this.yTileSnowball);
        nbttagcompound.put("zTile", (short) this.zTileSnowball);
        nbttagcompound.put("inTile", (byte) this.inTileSnowball);
        nbttagcompound.put("shake", (byte) this.shakeSnowball);
        nbttagcompound.put("inGround", (byte) (this.inGroundSnowball ? 1 : 0));
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        this.xTileSnowball = nbttagcompound.getShort("xTile");
        this.yTileSnowball = nbttagcompound.getShort("yTile");
        this.zTileSnowball = nbttagcompound.getShort("zTile");
        this.inTileSnowball = nbttagcompound.getByte("inTile") & 255;
        this.shakeSnowball = nbttagcompound.getByte("shake") & 255;
        this.inGroundSnowball = nbttagcompound.getByte("inGround") == 1;
    }

    public void onPlayerCollision(PlayerEntity entityplayer) {
        if (this.inGroundSnowball && this.thrower == entityplayer && this.shakeSnowball <= 0 && entityplayer.inventory.addStack(new ItemStack(Item.ARROW, 1))) {
            this.world.playSound(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.onItemPickup(this, 1);
            this.remove();
        }

    }

    public float getEyeHeight() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("NotchWave");
    }
}
