package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.AxixAlignedBoundingBox;
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
        this.method_1338(d, d1, d2, this.yaw, this.pitch);
    }

    public EntityProjectileBase(World world, LivingEntity entityliving) {
        this(world);
        this.owner = entityliving;
        this.shotByPlayer = entityliving instanceof PlayerEntity;
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double) entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
        this.xVelocity = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.zVelocity = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.yVelocity = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
        this.setArrowHeading(this.xVelocity, this.yVelocity, this.zVelocity, this.speed, this.precision);
    }

    protected void initDataTracker() {
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = 0;
        this.inGround = false;
        this.arrowShake = 0;
        this.ticksFlying = 0;
        this.setSize(0.5F, 0.5F);
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

    public void remove() {
        this.owner = null;
        super.remove();
    }

    public void setArrowHeading(double d, double d1, double d2, float f, float f1) {
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
        this.ticksInGround = 0;
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
        super.tick();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity);
            this.prevYaw = this.yaw = (float) (Math.atan2(this.xVelocity, this.zVelocity) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(this.yVelocity, (double) f) * 180.0 / 3.1415927410125732);
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
                    this.remove();
                }

                return;
            }

            this.inGround = false;
            this.xVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.yVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.zVelocity *= (double) (this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksFlying = 0;
        } else {
            ++this.ticksFlying;
        }

        this.tickFlying();
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
            if (this.canBeShot(entity1)) {
                float f4 = this.hitBox;
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

        if (movingobjectposition != null && this.onHit()) {
            Entity ent = movingobjectposition.field_1989;
            if (ent != null) {
                if (this.onHitTarget(ent)) {
                    if (ent instanceof LivingEntity && !(ent instanceof PlayerEntity)) {
                        ((LivingEntityAccessor) ent).set1058(0);
                    }

                    ent.damage(this.owner, this.dmg);
                    this.remove();
                }
            } else {
                this.xTile = movingobjectposition.x;
                this.yTile = movingobjectposition.y;
                this.zTile = movingobjectposition.z;
                this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                this.inData = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
                if (this.onHitBlock(movingobjectposition)) {
                    this.xVelocity = (double) ((float) (movingobjectposition.field_1988.x - this.x));
                    this.yVelocity = (double) ((float) (movingobjectposition.field_1988.y - this.y));
                    this.zVelocity = (double) ((float) (movingobjectposition.field_1988.z - this.z));
                    float f1 = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.yVelocity * this.yVelocity + this.zVelocity * this.zVelocity);
                    this.x -= this.xVelocity / (double) f1 * 0.05000000074505806;
                    this.y -= this.yVelocity / (double) f1 * 0.05000000074505806;
                    this.z -= this.zVelocity / (double) f1 * 0.05000000074505806;
                    this.inGround = true;
                    this.arrowShake = 7;
                } else {
                    this.inTile = 0;
                    this.inData = 0;
                }
            }
        }

        this.x += this.xVelocity;
        this.y += this.yVelocity;
        this.z += this.zVelocity;
        this.handleMotionUpdate();
        float f2 = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity);
        this.yaw = (float) (Math.atan2(this.xVelocity, this.zVelocity) * 180.0 / 3.1415927410125732);

        for (this.pitch = (float) (Math.atan2(this.yVelocity, (double) f2) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
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
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void handleMotionUpdate() {
        float slow = this.slowdown;
        if (this.method_1393()) {
            for (int k = 0; k < 4; ++k) {
                float f6 = 0.25F;
                this.world.addParticle("bubble", this.x - this.xVelocity * (double) f6, this.y - this.yVelocity * (double) f6, this.z - this.zVelocity * (double) f6, this.xVelocity, this.yVelocity, this.zVelocity);
            }

            slow *= 0.8F;
        }

        this.xVelocity *= (double) slow;
        this.yVelocity *= (double) slow;
        this.zVelocity *= (double) slow;
        this.yVelocity -= (double) this.curvature;
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.xTile);
        nbttagcompound.put("yTile", (short) this.yTile);
        nbttagcompound.put("zTile", (short) this.zTile);
        nbttagcompound.put("inTile", (byte) this.inTile);
        nbttagcompound.put("inData", (byte) this.inData);
        nbttagcompound.put("shake", (byte) this.arrowShake);
        nbttagcompound.put("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.put("player", this.shotByPlayer);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.inTile = nbttagcompound.getByte("inTile") & 255;
        this.inData = nbttagcompound.getByte("inData") & 255;
        this.arrowShake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.shotByPlayer = nbttagcompound.getBoolean("player");
    }

    public void onPlayerCollision(PlayerEntity entityplayer) {
        if (this.item != null) {
            if (!this.world.isClient) {
                if (this.inGround && this.shotByPlayer && this.arrowShake <= 0 && entityplayer.inventory.addStack(this.item.copy())) {
                    this.world.playSound(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    entityplayer.onItemPickup(this, 1);
                    this.remove();
                }

            }
        }
    }

    public boolean canBeShot(Entity ent) {
        return ent.method_1356() && (ent != this.owner || this.ticksFlying >= 5) && (!(ent instanceof LivingEntity) || ((LivingEntity) ent).deathTime <= 0);
    }

    public boolean onHit() {
        return true;
    }

    public boolean onHitTarget(Entity target) {
        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
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
        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        return true;
    }

    public float getEyeHeight() {
        return 0.0F;
    }
}
