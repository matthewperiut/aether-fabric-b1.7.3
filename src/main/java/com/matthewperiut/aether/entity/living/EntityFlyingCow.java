package com.matthewperiut.aether.entity.living;

import com.periut.retroapi.entity.spawn.RetroMobSpawnData;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import com.matthewperiut.aether.entity.MountInput;


public class EntityFlyingCow extends EntityAetherAnimal implements RetroMobSpawnData, MountInput {
    private float mountForward;
    private float mountStrafe;
    private boolean mountJump;
    private float mountYaw;
    private float mountPitch;
    public boolean getSaddled = false;
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;

    public EntityFlyingCow(World world) {
        super(world);
        this.texture = "aether:textures/mobs/Mob_FlyingCowBase.png";
        this.setBoundingBoxSpacing(0.9F, 1.3F);
        this.jrem = 0;
        this.jumps = 1;
        this.ticks = 0;
        this.stepHeight = 1.0F;
        this.ignoreFrustumCull = true;
    }

    protected boolean canDespawn() {
        return !this.getSaddled;
    }

    protected boolean bypassesSteppingEffects() {
        return this.onGround;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);  // saddle + onGround
        this.dataTracker.startTracking(17, (byte) 0);  // jrem
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("Jumps", (short) this.jumps);
        nbttagcompound.putShort("Remaining", (short) this.jrem);
        nbttagcompound.putBoolean("getSaddled", this.getSaddled);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.jumps = nbttagcompound.getShort("Jumps");
        this.jrem = nbttagcompound.getShort("Remaining");
        setSaddled(nbttagcompound.getBoolean("getSaddled"));
    }

    public void setSaddled(boolean saddled) {
        this.getSaddled = saddled;
        syncState();
    }

    private void syncState() {
        byte state = 0;
        if (this.getSaddled) state |= 1;
        if (this.onGround) state |= 2;
        this.dataTracker.set(16, state);
        this.dataTracker.set(17, (byte) this.jrem);
    }

    private void updateFromState() {
        byte state = this.dataTracker.getByte(16);
        this.texture = (state & 1) != 0
                ? "aether:textures/mobs/Mob_FlyingCowSaddle.png"
                : "aether:textures/mobs/Mob_FlyingCowBase.png";
    }

    protected void jump() {
        this.velocityY = 0.6;
    }

    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            syncState();
        }
        updateFromState();

        boolean grounded = this.world.isRemote ? (this.dataTracker.getByte(16) & 2) != 0 : this.onGround;
        if (this.world.isRemote) {
            this.jrem = this.dataTracker.getByte(17);
        }
        if (grounded) {
            this.wingAngle *= 0.8F;
            this.aimingForFold = 0.1F;
            this.jpress = false;
            this.jrem = this.jumps;
        } else {
            this.aimingForFold = 1.0F;
        }

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((double) ((float) this.ticks / 31.830988F));
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;
        if (this.velocityY < -0.2) {
            this.velocityY = -0.2;
        }

    }

    public void tickLiving() {
        if (this.passenger != null && this.passenger instanceof LivingEntity) {
            this.forwardSpeed = 0.0F;
            this.sidewaysSpeed = 0.0F;
            this.jumping = false;
            ((EntityAccessor) this.passenger).setFallDistance(0.0F);
            this.prevYaw = this.yaw = this.mountYaw;
            this.prevPitch = this.pitch = this.mountPitch;
            this.bodyYaw = this.mountYaw;

            float forward = this.mountForward;
            float strafe = this.mountStrafe;
            boolean jump = this.mountJump;
            float riderYaw = this.mountYaw;

            float f1 = 3.141593F / 180.0F;
            float f5;
            if (forward > 0.1F || forward < -0.1F) {
                f5 = riderYaw * f1;
                this.velocityX += (double) forward * -Math.sin((double) f5) * 0.17499999701976776;
                this.velocityZ += (double) forward * Math.cos((double) f5) * 0.17499999701976776;
            }

            if (strafe > 0.1F || strafe < -0.1F) {
                f5 = riderYaw * f1;
                this.velocityX += (double) strafe * Math.cos((double) f5) * 0.17499999701976776;
                this.velocityZ += (double) strafe * Math.sin((double) f5) * 0.17499999701976776;
            }

            if (this.onGround && jump) {
                this.onGround = false;
                this.velocityY = 1.4;
                this.jpress = true;
                --this.jrem;
            } else if (this.checkWaterCollisions() && jump) {
                this.velocityY = 0.5;
                this.jpress = true;
                --this.jrem;
            } else if (this.jrem > 0 && !this.jpress && jump) {
                this.velocityY = 1.2;
                this.jpress = true;
                --this.jrem;
            }

            if (this.jpress && !jump) {
                this.jpress = false;
            }

            double d = Math.abs(Math.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ));
            if (d > 0.375) {
                double d1 = 0.375 / d;
                this.velocityX *= d1;
                this.velocityZ *= d1;
            }

        } else {
            super.tickLiving();
        }
    }

    @Override
    public float getMountForward() { return mountForward; }
    @Override
    public float getMountStrafe() { return mountStrafe; }
    @Override
    public boolean getMountJump() { return mountJump; }
    @Override
    public float getMountYaw() { return mountYaw; }
    @Override
    public float getMountPitch() { return mountPitch; }
    @Override
    public void setMountInput(float forward, float strafe, boolean jump, float yaw, float pitch) {
        this.mountForward = forward;
        this.mountStrafe = strafe;
        this.mountJump = jump;
        this.mountYaw = yaw;
        this.mountPitch = pitch;
    }

    protected String getRandomSound() {
        return "mob.cow";
    }

    protected String getHurtSound() {
        return "mob.cowhurt";
    }

    protected String getDeathSound() {
        return "mob.cowhurt";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean interact(PlayerEntity entityplayer) {
        if (!this.getSaddled && entityplayer.inventory.getSelectedItem() != null && entityplayer.inventory.getSelectedItem().itemId == Item.SADDLE.id) {
            entityplayer.inventory.setStack(entityplayer.inventory.selectedSlot, (ItemStack) null);
            setSaddled(true);
            return true;
        } else if (!this.getSaddled || this.world.isRemote || this.passenger != null && this.passenger != entityplayer) {
            return false;
        } else {
            entityplayer.setVehicle(this);
            return true;
        }
    }

    protected void dropItems() {
        boolean skyrootSword = UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10));
        this.dropItem(Item.LEATHER.id, 2 + (skyrootSword ? 2 : 0));
    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("FlyingCow");
    }
}
