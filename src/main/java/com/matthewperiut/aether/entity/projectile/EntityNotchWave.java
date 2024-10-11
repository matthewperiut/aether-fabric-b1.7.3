package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.entity.living.EntitySlider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        this.setBoundingBoxSpacing(0.25F, 0.25F);
    }

    protected void initDataTracker() {
    }

    public boolean shouldRender(double d) {
        double d1 = this.boundingBox.getAverageSideLength() * 4.0;
        d1 *= 64.0;
        return d < d1 * d1;
    }

    public EntityNotchWave(World world, LivingEntity entityliving) {
        super(world);
        this.thrower = entityliving;
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPositionAndAnglesKeepPrevAngles(entityliving.x, entityliving.y + (double) entityliving.getEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        float f = 0.4F;
        this.velocityX = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F) * f);
        this.velocityZ = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F) * f);
        this.velocityY = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F) * f);
        this.setSnowballHeading(this.velocityX, this.velocityY, this.velocityZ, 1.5F, 1.0F);
    }

    public EntityNotchWave(World world, double d, double d1, double d2) {
        super(world);
        this.ticksInGroundSnowball = 0;
        this.setBoundingBoxSpacing(0.25F, 0.25F);
        this.setPositionAndAngles(d, d1, d2, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
    }

    public void setSnowballHeading(double d, double d1, double d2, float f, float f1) {
        float f2 = MathHelper.sqrt(d * d + d1 * d1 + d2 * d2);
        d /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d += this.random.nextGaussian() * 0.007499999832361937 * (double) f1;
        d1 += this.random.nextGaussian() * 0.007499999832361937 * (double) f1;
        d2 += this.random.nextGaussian() * 0.007499999832361937 * (double) f1;
        d *= (double) f;
        d1 *= (double) f;
        d2 *= (double) f;
        this.velocityX = d;
        this.velocityY = d1;
        this.velocityZ = d2;
        float f3 = MathHelper.sqrt(d * d + d2 * d2);
        this.prevYaw = this.yaw = (float) (Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
        this.prevPitch = this.pitch = (float) (Math.atan2(d1, (double) f3) * 180.0 / 3.1415927410125732);
        this.ticksInGroundSnowball = 0;
    }

    public void setVelocityClient(double d, double d1, double d2) {
        this.velocityX = d;
        this.velocityY = d1;
        this.velocityZ = d2;
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(d * d + d2 * d2);
            this.prevYaw = this.yaw = (float) (Math.atan2(d, d2) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(d1, (double) f) * 180.0 / 3.1415927410125732);
        }

    }

    public void tick() {
        this.lastTickX = this.x;
        this.lastTickY = this.y;
        this.lastTickZ = this.z;
        super.tick();
        if (this.shakeSnowball > 0) {
            --this.shakeSnowball;
        }

        if (this.ticksInAirSnowball > 100) {
            this.markDead();
        }

        if (this.inGroundSnowball) {
            this.markDead();
        } else {
            ++this.ticksInAirSnowball;
        }

        Vec3d vec3d = Vec3d.createCached(this.x, this.y, this.z);
        Vec3d vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        HitResult movingobjectposition = this.world.raycast(vec3d, vec3d1);
        vec3d = Vec3d.createCached(this.x, this.y, this.z);
        vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
        if (movingobjectposition != null) {
            vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
        }

        if (!this.world.isRemote) {
            Entity entity = null;
            List list = this.world.getEntities(this, this.boundingBox.stretch(this.velocityX, this.velocityY, this.velocityZ).expand(4.0, 4.0, 4.0));
            double d = 0.0;

            for (int l = 0; l < list.size(); ++l) {
                Entity entity1 = (Entity) list.get(l);
                if (entity1.isCollidable() && (entity1 != this.thrower || this.ticksInAirSnowball >= 5)) {
                    float f4 = 0.3F;
                    if (entity1 != this.thrower) {
                        entity1.damage(this.thrower, 5);
                    }

                    Box axisalignedbb = entity1.boundingBox.expand((double) f4, (double) f4, (double) f4);
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
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.entity != null && movingobjectposition.entity != this.thrower && movingobjectposition.entity != this.slider) {
                if (!movingobjectposition.entity.damage(this.thrower, 0)) {
                }

                Entity var10000 = movingobjectposition.entity;
                var10000.velocityX += this.velocityX;
                var10000 = movingobjectposition.entity;
                var10000.velocityY += 0.6;
                var10000 = movingobjectposition.entity;
                var10000.velocityZ += this.velocityZ;
            }

            for (int j = 0; j < 8; ++j) {
                this.world.addParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("explode", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("largesmoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.addParticle("flame", this.x, this.y, this.z, 0.0, 0.0, 0.0);
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
        float f1 = 0.99F;
        float f2 = 0.03F;
        if (this.isSubmergedInWater()) {
            for (int k = 0; k < 4; ++k) {
                float f3 = 0.25F;
                this.world.addParticle("bubble", this.x - this.velocityX * (double) f3, this.y - this.velocityY * (double) f3, this.z - this.velocityZ * (double) f3, this.velocityX, this.velocityY, this.velocityZ);
            }

            f1 = 0.8F;
        }

        this.velocityX *= (double) f1;
        this.velocityY *= (double) f1;
        this.velocityZ *= (double) f1;
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        nbttagcompound.putShort("xTile", (short) this.xTileSnowball);
        nbttagcompound.putShort("yTile", (short) this.yTileSnowball);
        nbttagcompound.putShort("zTile", (short) this.zTileSnowball);
        nbttagcompound.putByte("inTile", (byte) this.inTileSnowball);
        nbttagcompound.putByte("shake", (byte) this.shakeSnowball);
        nbttagcompound.putByte("inGround", (byte) (this.inGroundSnowball ? 1 : 0));
    }

    public void readNbt(NbtCompound nbttagcompound) {
        this.xTileSnowball = nbttagcompound.getShort("xTile");
        this.yTileSnowball = nbttagcompound.getShort("yTile");
        this.zTileSnowball = nbttagcompound.getShort("zTile");
        this.inTileSnowball = nbttagcompound.getByte("inTile") & 255;
        this.shakeSnowball = nbttagcompound.getByte("shake") & 255;
        this.inGroundSnowball = nbttagcompound.getByte("inGround") == 1;
    }

    public void onPlayerInteraction(PlayerEntity entityplayer) {
        if (this.inGroundSnowball && this.thrower == entityplayer && this.shakeSnowball <= 0 && entityplayer.inventory.addStack(new ItemStack(Item.ARROW, 1))) {
            this.world.playSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            entityplayer.sendPickup(this, 1);
            this.markDead();
        }

    }

    public float getShadowRadius() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("NotchWave");
    }
}
