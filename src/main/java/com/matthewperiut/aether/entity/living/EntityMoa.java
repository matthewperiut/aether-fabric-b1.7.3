package com.matthewperiut.aether.entity.living;

import com.periut.retroapi.entity.spawn.RetroMobSpawnData;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import com.matthewperiut.aether.util.MoaColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import com.matthewperiut.aether.entity.MountInput;


public class EntityMoa extends EntityAetherAnimal implements RetroMobSpawnData, MountInput {
    private float mountForward;
    private float mountStrafe;
    private boolean mountJump;
    private float mountYaw;
    private float mountPitch;
    public float field_752_b;
    public float destPos;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h;
    public int timeUntilNextEgg;
    public int jrem;
    int petalsEaten;
    boolean wellFed;
    boolean followPlayer;
    public boolean jpress;
    public boolean baby;
    public boolean grown;
    public boolean saddled;
    public MoaColor colour;

    public EntityMoa(World world) {
        this(world, false, false, false);
    }

    public EntityMoa(World world, boolean babyBool, boolean grownBool, boolean saddledBool) {
        this(world, babyBool, grownBool, saddledBool, MoaColor.pickRandomMoa());
    }

    public EntityMoa(World world, boolean babyBool, boolean grownBool, boolean saddledBool, MoaColor moaColour) {
        super(world);
        this.petalsEaten = 0;
        this.wellFed = false;
        this.followPlayer = false;
        this.baby = false;
        this.grown = false;
        this.saddled = false;
        this.destPos = 0.0F;
        this.field_755_h = 1.0F;
        this.stepHeight = 1.0F;
        this.jrem = 0;
        this.baby = babyBool;
        this.grown = grownBool;
        this.saddled = saddledBool;
        if (this.baby) {
            this.setBoundingBoxSpacing(0.4F, 0.5F);
        }

        this.colour = moaColour;
        this.texture = this.colour.getTexture(this.saddled);
        this.setBoundingBoxSpacing(1.0F, 2.0F);
        this.health = 40;
        syncMoaState();
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);  // state bits
        this.dataTracker.startTracking(17, 0);         // colour ID
        this.dataTracker.startTracking(18, (byte) 0);  // jrem
    }

    private void syncMoaState() {
        byte state = 0;
        if (this.saddled) state |= 1;
        if (this.baby) state |= 2;
        if (this.grown) state |= 4;
        if (this.onGround) state |= 8;
        this.dataTracker.set(16, state);
        this.dataTracker.set(17, (int) this.colour.ID);
        this.dataTracker.set(18, (byte) this.jrem);
    }

    private void updateMoaTexture() {
        byte state = this.dataTracker.getByte(16);
        boolean s = (state & 1) != 0;
        int colourId = this.dataTracker.getInt(17);
        MoaColor c = MoaColor.getColour(colourId);
        if (c != null) {
            this.texture = c.getTexture(s);
        }
        this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
    }

    public void tick() {
        super.tick();
        this.ignoreFrustumCull = this.passenger instanceof PlayerEntity;
        if (!this.world.isRemote) {
            syncMoaState();
        } else {
            this.jrem = this.dataTracker.getByte(18);
        }
        updateMoaTexture();
    }

    private boolean getSyncedOnGround() {
        return this.world.isRemote ? (this.dataTracker.getByte(16) & 8) != 0 : this.onGround;
    }

    public void tickMovement() {
        super.tickMovement();
        boolean grounded = getSyncedOnGround();
        this.field_756_e = this.field_752_b;
        this.field_757_d = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (grounded ? -1 : 4) * 0.05);
        if (this.destPos < 0.01F) {
            this.destPos = 0.01F;
        }

        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }

        if (grounded) {
            this.destPos = 0.0F;
            this.jpress = false;
            this.jrem = this.colour.jumps;
        }

        if (!grounded && this.field_755_h < 1.0F) {
            this.field_755_h = 1.0F;
        }

        this.field_755_h = (float) ((double) this.field_755_h * 0.9);
        if (!grounded && this.velocityY < 0.0) {
            if (this.passenger == null) {
                this.velocityY *= 0.6;
            } else {
                this.velocityY *= 0.6375;
            }
        }

        this.field_752_b += this.field_755_h * 2.0F;
        if (!this.world.isRemote && !this.baby && --this.timeUntilNextEgg <= 0) {
            this.world.playSound(this, "mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(new ItemStack(AetherItems.MoaEgg, 1, this.colour.ID), 0.0F);
            this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
        }

        if (this.wellFed && this.random.nextInt(2000) == 0) {
            this.wellFed = false;
        }

        if (this.saddled && this.passenger == null) {
            this.movementSpeed = 0.0F;
        } else {
            this.movementSpeed = 0.7F;
        }

    }

    protected void onLanding(float f) {
    }

    public boolean damage(Entity entity, int i) {
        boolean flag = super.damage(entity, i);
        if (flag && this.passenger != null && (this.health <= 0 || this.random.nextInt(3) == 0)) {
            this.passenger.setVehicle(null);
        }

        return flag;
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
                this.velocityY = 0.875;
                this.jpress = true;
                --this.jrem;
            } else if (this.checkWaterCollisions() && jump) {
                this.velocityY = 0.5;
                this.jpress = true;
                --this.jrem;
            } else if (this.jrem > 0 && !this.jpress && jump) {
                this.velocityY = 0.75;
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

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("Remaining", (short) this.jrem);
        nbttagcompound.putShort("ColourNumber", (short) this.colour.ID);
        nbttagcompound.putBoolean("Baby", this.baby);
        nbttagcompound.putBoolean("Grown", this.grown);
        nbttagcompound.putBoolean("Saddled", this.saddled);
        nbttagcompound.putBoolean("wellFed", this.wellFed);
        nbttagcompound.putInt("petalsEaten", this.petalsEaten);
        nbttagcompound.putBoolean("followPlayer", this.followPlayer);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.jrem = nbttagcompound.getShort("Remaining");
        this.colour = MoaColor.getColour(nbttagcompound.getShort("ColourNumber"));
        this.baby = nbttagcompound.getBoolean("Baby");
        this.grown = nbttagcompound.getBoolean("Grown");
        this.saddled = nbttagcompound.getBoolean("Saddled");
        this.wellFed = nbttagcompound.getBoolean("wellFed");
        this.petalsEaten = nbttagcompound.getInt("petalsEaten");
        this.followPlayer = nbttagcompound.getBoolean("followPlayer");
        if (this.baby) {
            this.grown = false;
            this.saddled = false;
        }

        if (this.grown) {
            this.baby = false;
            this.saddled = false;
        }

        if (this.saddled) {
            this.baby = false;
            this.grown = false;
        }

        syncMoaState();
    }

    protected String getRandomSound() {
        return "aether:mobs.moa.idlecall";
    }

    protected String getHurtSound() {
        return "aether:mobs.moa.idlecall";
    }

    protected String getDeathSound() {
        return "aether:mobs.moa.idlecall";
    }

    public boolean interact(PlayerEntity entityplayer) {
        if (!this.saddled && this.grown && !this.baby && entityplayer.inventory.getSelectedItem() != null && entityplayer.inventory.getSelectedItem().itemId == Item.SADDLE.id) {
            entityplayer.inventory.setStack(entityplayer.inventory.selectedSlot, (ItemStack) null);
            this.saddled = true;
            this.grown = false;
            syncMoaState();
            return true;
        } else if (this.saddled && !this.world.isRemote && (this.passenger == null || this.passenger == entityplayer)) {
            entityplayer.setVehicle(this);
            entityplayer.prevYaw = entityplayer.yaw = this.yaw;
            return true;
        } else if (!this.wellFed && !this.saddled && this.baby && !this.grown) {
            ItemStack itemstack = entityplayer.inventory.getSelectedItem();
            if (itemstack != null && itemstack.itemId == AetherItems.AechorPetal.id) {
                ++this.petalsEaten;
                entityplayer.inventory.removeStack(entityplayer.inventory.selectedSlot, 1);
                if (this.petalsEaten > this.colour.jumps) {
                    this.grown = true;
                    this.baby = false;
                    syncMoaState();
                }

                this.wellFed = true;
            }

            return true;
        } else {
            if (!this.saddled && (this.baby || this.grown)) {
                if (!this.followPlayer) {
                    this.followPlayer = true;
                    this.target = entityplayer;
                } else {
                    this.followPlayer = false;
                    this.target = null;
                }
            }

            return true;
        }
    }

    public boolean canDespawn() {
        return !this.baby && !this.grown && !this.saddled;
    }

    protected boolean bypassesSteppingEffects() {
        return this.onGround;
    }

    protected void dropItems() {
        boolean skyrootSword = UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10));
        this.dropItem(Item.FEATHER.id, 3 * (skyrootSword ? 2 : 1));
    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("Moa");
    }
}
