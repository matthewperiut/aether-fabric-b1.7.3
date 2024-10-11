package com.matthewperiut.aether.entity.living;

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
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityMoa extends EntityAetherAnimal implements MobSpawnDataProvider {
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
        this.timeUntilNextEgg = this.random.nextInt(6000) + 6000;
    }

    public void tick() {
        super.tick();
        this.ignoreFrustumCull = this.passenger instanceof PlayerEntity;
    }

    public void tickMovement() {
        super.tickMovement();
        this.field_756_e = this.field_752_b;
        this.field_757_d = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.05);
        if (this.destPos < 0.01F) {
            this.destPos = 0.01F;
        }

        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }

        if (this.onGround) {
            this.destPos = 0.0F;
            this.jpress = false;
            this.jrem = this.colour.jumps;
        }

        if (!this.onGround && this.field_755_h < 1.0F) {
            this.field_755_h = 1.0F;
        }

        this.field_755_h = (float) ((double) this.field_755_h * 0.9);
        if (!this.onGround && this.velocityY < 0.0) {
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
            this.passenger.setVehicle(this);
        }

        return flag;
    }

    public void tickLiving() {
        if (!this.world.isRemote) {
            if (this.passenger != null && this.passenger instanceof LivingEntity) {
                this.forwardSpeed = 0.0F;
                this.sidewaysSpeed = 0.0F;
                this.jumping = false;
                ((EntityAccessor) this.passenger).setFallDistance(0.0F);
                this.prevYaw = this.yaw = this.passenger.yaw;
                this.prevPitch = this.pitch = this.passenger.pitch;
                LivingEntity entityliving = (LivingEntity) this.passenger;
                float f = 3.141593F;
                float f1 = f / 180.0F;
                float f5;
                if (((LivingEntityAccessor) entityliving).getForwardVelocity() > 0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f5) * 0.17499999701976776;
                    this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                } else if (((LivingEntityAccessor) entityliving).getForwardVelocity() < -0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f5) * 0.17499999701976776;
                    this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                }

                if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() > 0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                    this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f5) * 0.17499999701976776;
                } else if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() < -0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                    this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f5) * 0.17499999701976776;
                }

                if (this.onGround && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.onGround = false;
                    this.velocityY = 0.875;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.checkWaterCollisions() && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.velocityY = 0.5;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.jrem > 0 && !this.jpress && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.velocityY = 0.75;
                    this.jpress = true;
                    --this.jrem;
                }

                if (this.jpress && !((LivingEntityAccessor) entityliving).getJumping()) {
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

        this.texture = this.colour.getTexture(this.saddled);
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
            this.texture = this.colour.getTexture(this.saddled);
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
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Moa");
    }
}
