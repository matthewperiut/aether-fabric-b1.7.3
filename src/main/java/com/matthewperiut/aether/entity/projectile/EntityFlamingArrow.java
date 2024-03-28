package com.matthewperiut.aether.entity.projectile;

import net.minecraft.block.Block;
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

public class EntityFlamingArrow extends Entity implements EntitySpawnDataProvider {
    public boolean doesArrowBelongToPlayer = false;
    public int arrowShake = 0;
    public LivingEntity owner;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int inTile = 0;
    private int field_28019_h = 0;
    private boolean inGround = false;
    private int ticksInGround;
    private int ticksInAir = 0;

    public EntityFlamingArrow(World world) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.fireTicks = 1;
    }

    public EntityFlamingArrow(World world, double d, double d1, double d2) {
        super(world);
        this.setSize(0.5F, 0.5F);
        this.method_1338(d, d1, d2, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
    }

    public EntityFlamingArrow(World world, LivingEntity entityliving) {
        super(world);
        this.owner = entityliving;
        this.doesArrowBelongToPlayer = entityliving instanceof PlayerEntity;
        this.setSize(0.5F, 0.5F);
        this.setPositionAndAngles(entityliving.x, entityliving.y + (double) entityliving.getStandingEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        this.xVelocity = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.zVelocity = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.yVelocity = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
        this.setArrowHeading(this.xVelocity, this.yVelocity, this.zVelocity, 1.5F, 1.0F);
    }

    protected void initDataTracker() {
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
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;
            this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
            this.ticksInGround = 0;
        }

    }

    public void tick() {
        super.tick();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity);
            this.prevYaw = this.yaw = (float) (Math.atan2(this.xVelocity, this.zVelocity) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(this.yVelocity, (double) f) * 180.0 / 3.1415927410125732);
        }

        int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
        if (i > 0) {
            Block.BY_ID[i].updateBoundingBox(this.world, this.xTile, this.yTile, this.zTile);
            AxixAlignedBoundingBox axisalignedbb = Block.BY_ID[i].getCollisionShape(this.world, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.contains(Vec3d.from(this.x, this.y, this.z))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            --this.arrowShake;
        }

        if (this.inGround) {
            int j = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
            int k = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
            if (j == this.inTile && k == this.field_28019_h) {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200) {
                    this.remove();
                }

            } else {
                this.inGround = false;
                this.xVelocity *= (double) (this.rand.nextFloat() * 0.2F);
                this.yVelocity *= (double) (this.rand.nextFloat() * 0.2F);
                this.zVelocity *= (double) (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.world.addParticle(this.rand.nextBoolean() ? "flame" : "smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            ++this.ticksInAir;
            Vec3d vec3d = Vec3d.from(this.x, this.y, this.z);
            Vec3d vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
            HitResult movingobjectposition = this.world.method_162(vec3d, vec3d1, false, true);
            vec3d = Vec3d.from(this.x, this.y, this.z);
            vec3d1 = Vec3d.from(this.x + this.xVelocity, this.y + this.yVelocity, this.z + this.zVelocity);
            if (movingobjectposition != null) {
                vec3d1 = Vec3d.from(movingobjectposition.field_1988.x, movingobjectposition.field_1988.y, movingobjectposition.field_1988.z);
            }

            Entity entity = null;
            List list = this.world.getEntities(this, this.boundingBox.duplicateAndExpand(this.xVelocity, this.yVelocity, this.zVelocity).expand(1.0, 1.0, 1.0));
            double d = 0.0;

            int x;
            float f5;
            for (x = 0; x < list.size(); ++x) {
                Entity entity1 = (Entity) list.get(x);
                if (entity1.method_1356() && (entity1 != this.owner || this.ticksInAir >= 5)) {
                    f5 = 0.3F;
                    AxixAlignedBoundingBox axisalignedbb1 = entity1.boundingBox.expand((double) f5, (double) f5, (double) f5);
                    HitResult movingobjectposition1 = axisalignedbb1.method_89(vec3d, vec3d1);
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

            float f1;
            int i1;
            if (movingobjectposition != null) {
                int y;
                int z;
                if (movingobjectposition.field_1989 != null) {
                    if (movingobjectposition.field_1989.damage(this.owner, 4)) {
                        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                        movingobjectposition.field_1989.fireTicks = 100;
                        x = MathHelper.floor(movingobjectposition.field_1989.boundingBox.minX);
                        y = MathHelper.floor(movingobjectposition.field_1989.boundingBox.minY);
                        z = MathHelper.floor(movingobjectposition.field_1989.boundingBox.minZ);
                        this.world.setBlock(x, y, z, 51);
                        this.remove();
                    } else {
                        this.xVelocity *= -0.10000000149011612;
                        this.yVelocity *= -0.10000000149011612;
                        this.zVelocity *= -0.10000000149011612;
                        this.yaw += 180.0F;
                        this.prevYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.xTile = movingobjectposition.x;
                    this.yTile = movingobjectposition.y;
                    this.zTile = movingobjectposition.z;
                    this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.field_28019_h = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
                    this.xVelocity = (double) ((float) (movingobjectposition.field_1988.x - this.x));
                    this.yVelocity = (double) ((float) (movingobjectposition.field_1988.y - this.y));
                    this.zVelocity = (double) ((float) (movingobjectposition.field_1988.z - this.z));
                    f1 = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.yVelocity * this.yVelocity + this.zVelocity * this.zVelocity);
                    this.x -= this.xVelocity / (double) f1 * 0.05000000074505806;
                    this.y -= this.yVelocity / (double) f1 * 0.05000000074505806;
                    this.z -= this.zVelocity / (double) f1 * 0.05000000074505806;
                    this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    y = MathHelper.floor(this.x);
                    z = MathHelper.floor(this.y);
                    i1 = MathHelper.floor(this.z);
                    this.world.setBlock(y, z, i1, 51);
                    this.inGround = true;
                    this.arrowShake = 7;
                }
            }

            this.x += this.xVelocity;
            this.y += this.yVelocity;
            this.z += this.zVelocity;
            f1 = MathHelper.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity);
            this.yaw = (float) (Math.atan2(this.xVelocity, this.zVelocity) * 180.0 / 3.1415927410125732);

            for (this.pitch = (float) (Math.atan2(this.yVelocity, (double) f1) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
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
            float f3 = 0.99F;
            f5 = 0.03F;
            if (this.method_1334()) {
                for (i1 = 0; i1 < 4; ++i1) {
                    float f6 = 0.25F;
                    this.world.addParticle("bubble", this.x - this.xVelocity * (double) f6, this.y - this.yVelocity * (double) f6, this.z - this.zVelocity * (double) f6, this.xVelocity, this.yVelocity, this.zVelocity);
                }

                f3 = 0.8F;
            }

            this.xVelocity *= (double) f3;
            this.yVelocity *= (double) f3;
            this.zVelocity *= (double) f3;
            this.yVelocity -= (double) f5;
            this.method_1338(this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        nbttagcompound.put("xTile", (short) this.xTile);
        nbttagcompound.put("yTile", (short) this.yTile);
        nbttagcompound.put("zTile", (short) this.zTile);
        nbttagcompound.put("inTile", (byte) this.inTile);
        nbttagcompound.put("inData", (byte) this.field_28019_h);
        nbttagcompound.put("shake", (byte) this.arrowShake);
        nbttagcompound.put("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.put("player", this.doesArrowBelongToPlayer);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.inTile = nbttagcompound.getByte("inTile") & 255;
        this.field_28019_h = nbttagcompound.getByte("inData") & 255;
        this.arrowShake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.doesArrowBelongToPlayer = nbttagcompound.getBoolean("player");
    }

    public void onPlayerCollision(PlayerEntity entityplayer) {
        if (!this.world.isClient) {
            if (this.inGround && this.doesArrowBelongToPlayer && this.arrowShake <= 0 && entityplayer.inventory.addStack(new ItemStack(Item.ARROW, 1))) {
                this.world.playSound(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityplayer.onItemPickup(this, 1);
                this.remove();
            }

        }
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("FlamingArrow");
    }
}
