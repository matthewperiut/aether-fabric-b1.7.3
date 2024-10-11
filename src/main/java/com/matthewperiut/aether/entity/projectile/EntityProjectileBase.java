package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.TriState;

import java.util.List;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public abstract class EntityProjectileBase extends ArrowEntity {
    public float speed;
    public float slowdown;
    public float curvature;
    public float precision;
    public float hitBox;
    public int dmg;
    public ItemStack item;
    public int ttlInGround;
    public int xTile;
    public int yTile;
    public int zTile;
    public int inTile;
    public int inData;
    public boolean inGround;
    public int arrowShake;
    public LivingEntity owner;
    public int ticksInGround;
    public int ticksFlying;
    public boolean shotByPlayer;

    public EntityProjectileBase(World world) {
        super(world);
    }

    public EntityProjectileBase(World world, double d, double d1, double d2) {
        this(world);
        this.setPositionAndAngles(d, d1, d2, this.yaw, this.pitch);
    }

    public EntityProjectileBase(World world, LivingEntity entityliving) {
        this(world);
        this.owner = entityliving;
        this.shotByPlayer = entityliving instanceof PlayerEntity;
        this.setPositionAndAnglesKeepPrevAngles(entityliving.x, entityliving.y + (double) entityliving.getEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        this.velocityX = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.velocityZ = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.velocityY = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
        this.setArrowHeading(this.velocityX, this.velocityY, this.velocityZ, this.speed, this.precision);
    }

    protected void initDataTracker() {
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.arrowShake = 0;
        this.ticksFlying = 0;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.standingEyeHeight = 0.0F;
        this.hitBox = 0.3F;
        this.speed = 1.0F;
        this.slowdown = 0.99F;
        this.curvature = 0.03F;
        this.dmg = 4;
        this.precision = 1.0F;
        this.ttlInGround = 1200;
        this.item = null;
    }

    public void markDead() {
        this.owner = null;
        super.markDead();
    }

    public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
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
        this.ticksInGround = 0;
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
        super.tick();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.prevYaw = this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(this.velocityY, (double) f) * 180.0 / 3.1415927410125732);
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.inGround) {
            int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int k = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
            if (i == this.inTile && k == this.inData) {
                ++this.ticksInGround;
                this.tickInGround();
                if (this.ticksInGround == this.ttlInGround) {
                    this.markDead();
                }

                return;
            }

            this.inGround = false;
            this.velocityX *= (double) (this.random.nextFloat() * 0.2F);
            this.velocityY *= (double) (this.random.nextFloat() * 0.2F);
            this.velocityZ *= (double) (this.random.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksFlying = 0;
        } else {
            ++this.ticksFlying;
        }

        this.tickFlying();
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
            if (this.canBeShot(entity1)) {
                float f4 = this.hitBox;
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

        if (movingobjectposition != null && this.onHit()) {
            Entity ent = movingobjectposition.entity;
            if (ent != null) {
                if (this.onHitTarget(ent)) {
                    if (ent instanceof LivingEntity && !(ent instanceof PlayerEntity)) {
                        ((LivingEntityAccessor) ent).setPrevHealth(0);
                    }

                    ent.damage(this.owner, this.dmg);
                    this.markDead();
                }
            } else {
                this.xTile = movingobjectposition.blockX;
                this.yTile = movingobjectposition.blockY;
                this.zTile = movingobjectposition.blockZ;
                this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                this.inData = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
                if (this.onHitBlock(movingobjectposition)) {
                    this.velocityX = (double) ((float) (movingobjectposition.pos.x - this.x));
                    this.velocityY = (double) ((float) (movingobjectposition.pos.y - this.y));
                    this.velocityZ = (double) ((float) (movingobjectposition.pos.z - this.z));
                    float f1 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
                    this.x -= this.velocityX / (double) f1 * 0.05000000074505806;
                    this.y -= this.velocityY / (double) f1 * 0.05000000074505806;
                    this.z -= this.velocityZ / (double) f1 * 0.05000000074505806;
                    this.inGround = true;
                    this.arrowShake = 7;
                } else {
                    this.inTile = 0;
                    this.inData = 0;
                }
            }
        }

        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;
        this.handleMotionUpdate();
        float f2 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
        this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);

        for (this.pitch = (float) (Math.atan2(this.velocityY, (double) f2) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
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
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void handleMotionUpdate() {
        float slow = this.slowdown;
        if (this.checkWaterCollisions()) {
            for (int k = 0; k < 4; ++k) {
                float f6 = 0.25F;
                this.world.addParticle("bubble", this.x - this.velocityX * (double) f6, this.y - this.velocityY * (double) f6, this.z - this.velocityZ * (double) f6, this.velocityX, this.velocityY, this.velocityZ);
            }

            slow *= 0.8F;
        }

        this.velocityX *= (double) slow;
        this.velocityY *= (double) slow;
        this.velocityZ *= (double) slow;
        this.velocityY -= (double) this.curvature;
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        nbttagcompound.putShort("xTile", (short) this.xTile);
        nbttagcompound.putShort("yTile", (short) this.yTile);
        nbttagcompound.putShort("zTile", (short) this.zTile);
        nbttagcompound.putByte("inTile", (byte) this.inTile);
        nbttagcompound.putByte("inData", (byte) this.inData);
        nbttagcompound.putByte("shake", (byte) this.arrowShake);
        nbttagcompound.putByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.putBoolean("player", this.shotByPlayer);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.inTile = nbttagcompound.getByte("inTile") & 255;
        this.inData = nbttagcompound.getByte("inData") & 255;
        this.arrowShake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.shotByPlayer = nbttagcompound.getBoolean("player");
    }

    public void onPlayerInteraction(PlayerEntity entityplayer) {
        if (this.item != null) {
            if (!this.world.isRemote) {
                if (this.inGround && this.shotByPlayer && this.arrowShake <= 0 && entityplayer.inventory.addStack(this.item.copy())) {
                    this.world.playSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    entityplayer.sendPickup(this, 1);
                    this.markDead();
                }

            }
        }
    }

    public boolean canBeShot(Entity ent) {
        return ent.isCollidable() && (ent != this.owner || this.ticksFlying >= 5) && (!(ent instanceof LivingEntity) || ((LivingEntity) ent).deathTicks <= 0);
    }

    public boolean onHit() {
        return true;
    }

    public boolean onHitTarget(Entity target) {
        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        return true;
    }

    public void tickFlying() {
    }

    public void tickInGround() {
    }

    public boolean onHitBlock(HitResult mop) {
        return this.onHitBlock();
    }

    public boolean onHitBlock() {
        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        return true;
    }

    public float getShadowRadius() {
        return 0.0F;
    }
}
