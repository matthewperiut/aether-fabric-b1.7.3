package com.matthewperiut.aether.entity.projectile;

import net.minecraft.block.Block;
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
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.fireTicks = 1;
    }

    public EntityFlamingArrow(World world, double d, double d1, double d2) {
        super(world);
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPositionAndAngles(d, d1, d2, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
    }

    public EntityFlamingArrow(World world, LivingEntity entityliving) {
        super(world);
        this.owner = entityliving;
        this.doesArrowBelongToPlayer = entityliving instanceof PlayerEntity;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPositionAndAnglesKeepPrevAngles(entityliving.x, entityliving.y + (double) entityliving.getEyeHeight(), entityliving.z, entityliving.yaw, entityliving.pitch);
        this.x -= (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double) (MathHelper.sin(this.yaw / 180.0F * 3.141593F) * 0.16F);
        this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        this.standingEyeHeight = 0.0F;
        this.velocityX = (double) (-MathHelper.sin(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.velocityZ = (double) (MathHelper.cos(this.yaw / 180.0F * 3.141593F) * MathHelper.cos(this.pitch / 180.0F * 3.141593F));
        this.velocityY = (double) (-MathHelper.sin(this.pitch / 180.0F * 3.141593F));
        this.setArrowHeading(this.velocityX, this.velocityY, this.velocityZ, 1.5F, 1.0F);
    }

    protected void initDataTracker() {
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
            this.prevPitch = this.pitch;
            this.prevYaw = this.yaw;
            this.setPositionAndAnglesKeepPrevAngles(this.x, this.y, this.z, this.yaw, this.pitch);
            this.ticksInGround = 0;
        }

    }

    public void tick() {
        super.tick();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            float f = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.prevYaw = this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);
            this.prevPitch = this.pitch = (float) (Math.atan2(this.velocityY, (double) f) * 180.0 / 3.1415927410125732);
        }

        int i = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
        if (i > 0) {
            Block.BLOCKS[i].updateBoundingBox(this.world, this.xTile, this.yTile, this.zTile);
            Box axisalignedbb = Block.BLOCKS[i].getCollisionShape(this.world, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null && axisalignedbb.contains(Vec3d.createCached(this.x, this.y, this.z))) {
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
                    this.markDead();
                }

            } else {
                this.inGround = false;
                this.velocityX *= (double) (this.random.nextFloat() * 0.2F);
                this.velocityY *= (double) (this.random.nextFloat() * 0.2F);
                this.velocityZ *= (double) (this.random.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        } else {
            this.world.addParticle(this.random.nextBoolean() ? "flame" : "smoke", this.x, this.y, this.z, 0.0, 0.0, 0.0);
            ++this.ticksInAir;
            Vec3d vec3d = Vec3d.createCached(this.x, this.y, this.z);
            Vec3d vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
            HitResult movingobjectposition = this.world.raycast(vec3d, vec3d1, false, true);
            vec3d = Vec3d.createCached(this.x, this.y, this.z);
            vec3d1 = Vec3d.createCached(this.x + this.velocityX, this.y + this.velocityY, this.z + this.velocityZ);
            if (movingobjectposition != null) {
                vec3d1 = Vec3d.createCached(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
            }

            Entity entity = null;
            List list = this.world.getEntities(this, this.boundingBox.stretch(this.velocityX, this.velocityY, this.velocityZ).expand(1.0, 1.0, 1.0));
            double d = 0.0;

            int x;
            float f5;
            for (x = 0; x < list.size(); ++x) {
                Entity entity1 = (Entity) list.get(x);
                if (entity1.isCollidable() && (entity1 != this.owner || this.ticksInAir >= 5)) {
                    f5 = 0.3F;
                    Box axisalignedbb1 = entity1.boundingBox.expand((double) f5, (double) f5, (double) f5);
                    HitResult movingobjectposition1 = axisalignedbb1.raycast(vec3d, vec3d1);
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

            float f1;
            int i1;
            if (movingobjectposition != null) {
                int y;
                int z;
                if (movingobjectposition.entity != null) {
                    if (movingobjectposition.entity.damage(this.owner, 4)) {
                        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                        movingobjectposition.entity.fireTicks = 100;
                        x = MathHelper.floor(movingobjectposition.entity.boundingBox.minX);
                        y = MathHelper.floor(movingobjectposition.entity.boundingBox.minY);
                        z = MathHelper.floor(movingobjectposition.entity.boundingBox.minZ);
                        this.world.setBlock(x, y, z, 51);
                        this.markDead();
                    } else {
                        this.velocityX *= -0.10000000149011612;
                        this.velocityY *= -0.10000000149011612;
                        this.velocityZ *= -0.10000000149011612;
                        this.yaw += 180.0F;
                        this.prevYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                } else {
                    this.xTile = movingobjectposition.blockX;
                    this.yTile = movingobjectposition.blockY;
                    this.zTile = movingobjectposition.blockZ;
                    this.inTile = this.world.getBlockId(this.xTile, this.yTile, this.zTile);
                    this.field_28019_h = this.world.getBlockMeta(this.xTile, this.yTile, this.zTile);
                    this.velocityX = (double) ((float) (movingobjectposition.pos.x - this.x));
                    this.velocityY = (double) ((float) (movingobjectposition.pos.y - this.y));
                    this.velocityZ = (double) ((float) (movingobjectposition.pos.z - this.z));
                    f1 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
                    this.x -= this.velocityX / (double) f1 * 0.05000000074505806;
                    this.y -= this.velocityY / (double) f1 * 0.05000000074505806;
                    this.z -= this.velocityZ / (double) f1 * 0.05000000074505806;
                    this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                    y = MathHelper.floor(this.x);
                    z = MathHelper.floor(this.y);
                    i1 = MathHelper.floor(this.z);
                    this.world.setBlock(y, z, i1, 51);
                    this.inGround = true;
                    this.arrowShake = 7;
                }
            }

            this.x += this.velocityX;
            this.y += this.velocityY;
            this.z += this.velocityZ;
            f1 = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ);
            this.yaw = (float) (Math.atan2(this.velocityX, this.velocityZ) * 180.0 / 3.1415927410125732);

            for (this.pitch = (float) (Math.atan2(this.velocityY, (double) f1) * 180.0 / 3.1415927410125732); this.pitch - this.prevPitch < -180.0F; this.prevPitch -= 360.0F) {
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
            if (this.isSubmergedInWater()) {
                for (i1 = 0; i1 < 4; ++i1) {
                    float f6 = 0.25F;
                    this.world.addParticle("bubble", this.x - this.velocityX * (double) f6, this.y - this.velocityY * (double) f6, this.z - this.velocityZ * (double) f6, this.velocityX, this.velocityY, this.velocityZ);
                }

                f3 = 0.8F;
            }

            this.velocityX *= (double) f3;
            this.velocityY *= (double) f3;
            this.velocityZ *= (double) f3;
            this.velocityY -= (double) f5;
            this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        nbttagcompound.putShort("xTile", (short) this.xTile);
        nbttagcompound.putShort("yTile", (short) this.yTile);
        nbttagcompound.putShort("zTile", (short) this.zTile);
        nbttagcompound.putByte("inTile", (byte) this.inTile);
        nbttagcompound.putByte("inData", (byte) this.field_28019_h);
        nbttagcompound.putByte("shake", (byte) this.arrowShake);
        nbttagcompound.putByte("inGround", (byte) (this.inGround ? 1 : 0));
        nbttagcompound.putBoolean("player", this.doesArrowBelongToPlayer);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.inTile = nbttagcompound.getByte("inTile") & 255;
        this.field_28019_h = nbttagcompound.getByte("inData") & 255;
        this.arrowShake = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
        this.doesArrowBelongToPlayer = nbttagcompound.getBoolean("player");
    }

    public void onPlayerInteraction(PlayerEntity entityplayer) {
        if (!this.world.isRemote) {
            if (this.inGround && this.doesArrowBelongToPlayer && this.arrowShake <= 0 && entityplayer.inventory.addStack(new ItemStack(Item.ARROW, 1))) {
                this.world.playSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityplayer.sendPickup(this, 1);
                this.markDead();
            }

        }
    }

    public float getShadowRadius() {
        return 0.0F;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("FlamingArrow");
    }
}
