package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityPhyg extends EntityAetherAnimal implements MobSpawnDataProvider {
    public boolean getSaddled = false;
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;

    public EntityPhyg(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/Mob_FlyingPigBase.png";
        this.setSize(0.9F, 1.3F);
        this.jrem = 0;
        this.jumps = 1;
        this.ticks = 0;
        this.field_1641 = 1.0F;
        this.field_1622 = true;
    }

    protected boolean canDespawn() {
        return !this.getSaddled;
    }

    protected boolean canClimb() {
        return this.onGround;
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(16, (byte) 0);
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Jumps", (short) this.jumps);
        nbttagcompound.put("Remaining", (short) this.jrem);
        nbttagcompound.put("getSaddled", this.getSaddled);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.jumps = nbttagcompound.getShort("Jumps");
        this.jrem = nbttagcompound.getShort("Remaining");
        this.getSaddled = nbttagcompound.getBoolean("getSaddled");
        if (this.getSaddled) {
            this.texture = "aether:stationapi/textures/mobs/Mob_FlyingPigSaddle.png";
        }

    }

    protected void jump() {
        this.yVelocity = 0.6;
    }

    public void tick() {
        super.tick();
        if (this.onGround) {
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
        if (this.yVelocity < -0.2) {
            this.yVelocity = -0.2;
        }

    }

    public void tickHandSwing() {
        if (!this.world.isClient) {
            if (this.passenger != null && this.passenger instanceof LivingEntity) {
                this.forwardVelocity = 0.0F;
                this.horizontalVelocity = 0.0F;
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
                    this.xVelocity += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f5) * 0.17499999701976776;
                    this.zVelocity += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                } else if (((LivingEntityAccessor) entityliving).getForwardVelocity() < -0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.xVelocity += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f5) * 0.17499999701976776;
                    this.zVelocity += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                }

                if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() > 0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.xVelocity += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                    this.zVelocity += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f5) * 0.17499999701976776;
                } else if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() < -0.1F) {
                    f5 = entityliving.yaw * f1;
                    this.xVelocity += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f5) * 0.17499999701976776;
                    this.zVelocity += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f5) * 0.17499999701976776;
                }

                if (this.onGround && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.onGround = false;
                    this.yVelocity = 1.4;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.method_1393() && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.yVelocity = 0.5;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.jrem > 0 && !this.jpress && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.yVelocity = 1.2;
                    this.jpress = true;
                    --this.jrem;
                }

                if (this.jpress && !((LivingEntityAccessor) entityliving).getJumping()) {
                    this.jpress = false;
                }

                double d = Math.abs(Math.sqrt(this.xVelocity * this.xVelocity + this.zVelocity * this.zVelocity));
                if (d > 0.375) {
                    double d1 = 0.375 / d;
                    this.xVelocity *= d1;
                    this.zVelocity *= d1;
                }

            } else {
                super.tickHandSwing();
            }
        }
    }

    protected String getAmbientSound() {
        return "mob.pig";
    }

    protected String getHurtSound() {
        return "mob.pig";
    }

    protected String getDeathSound() {
        return "mob.pigdeath";
    }

    public boolean interact(PlayerEntity entityplayer) {
        if (!this.getSaddled && entityplayer.inventory.getHeldItem() != null && entityplayer.inventory.getHeldItem().itemId == Item.SADDLE.id) {
            entityplayer.inventory.setInventoryItem(entityplayer.inventory.selectedHotBarSlot, (ItemStack) null);
            this.getSaddled = true;
            this.texture = "aether:stationapi/textures/mobs/Mob_FlyingPigSaddle.png";
            return true;
        } else if (!this.getSaddled || this.world.isClient || this.passenger != null && this.passenger != entityplayer) {
            return false;
        } else {
            entityplayer.startRiding(this);
            AetherAchievements.giveAchievement(AetherAchievements.flyingPig, entityplayer);
            return true;
        }
    }

    protected void getDrops() {
        boolean skyrootSword = UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10));
        this.dropItem(this.rand.nextBoolean() ? Item.FEATHER.id : Item.RAW_PORKCHOP.id, (skyrootSword ? 2 : 1));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Phyg");
    }
}
