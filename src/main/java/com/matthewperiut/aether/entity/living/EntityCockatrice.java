//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.entity.projectile.EntityPoisonNeedle;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityCockatrice extends MonsterEntity implements MobSpawnDataProvider {
    public float field_752_b;
    public float destPos = 0.0F;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h = 1.0F;
    public int timeUntilNextEgg;
    public int jumps;
    public int jrem;
    public boolean jpress;
    public boolean gotrider;

    public EntityCockatrice(World world) {
        super(world);
        this.field_1641 = 1.0F;
        this.jrem = 0;
        this.jumps = 3;
        this.texture = "aether:stationapi/textures/mobs/Cockatrice.png";
        this.setSize(1.0F, 2.0F);
        this.health = 20;
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.rand.nextInt(25) == 0 && this.getPathfindingFavour(i, j, k) >= 0.0F && this.world.canSpawnEntity(this.boundingBox) && this.world.method_190(this, this.boundingBox).size() == 0 && !this.world.method_218(this.boundingBox) && this.world.getBlockId(i, j - 1, k) != AetherBlocks.DungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.LockedLightDungeonStone.id && this.world.getBlockId(i, j - 1, k) != AetherBlocks.Holystone.id && this.world.difficulty > 0;
    }

    public void tick() {
        super.tick();
        this.field_1622 = this.passenger instanceof PlayerEntity;
        if (!this.world.isClient && this.gotrider) {
            if (this.passenger != null) {
                return;
            }

            List list = this.world.getEntities(this, this.boundingBox.expand(0.5, 0.75, 0.5));
            int i = 0;
            if (i < list.size()) {
                Entity entity = (Entity) list.get(i);
                entity.startRiding(this);
            }

            this.gotrider = false;
        }

        if (!this.world.isClient && this.world.difficulty == 0) {
            this.remove();
        }

    }

    protected void tryAttack(Entity entity, float f) {
        if (f < 10.0F) {
            double d = entity.x - this.x;
            double d1 = entity.z - this.z;
            if (this.attackTime == 0) {
                EntityPoisonNeedle entityarrow = new EntityPoisonNeedle(this.world, this);
                ++entityarrow.y;
                double d2 = entity.y + (double) entity.getStandingEyeHeight() - 0.20000000298023224 - entityarrow.y;
                float f1 = MathHelper.sqrt(d * d + d1 * d1) * 0.2F;
                this.world.playSound(this, "aether:other.dartshooter.shootdart", 1.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(entityarrow);
                entityarrow.setArrowHeading(d, d2 + (double) f1, d1, 0.6F, 12.0F);
                this.attackTime = 30;
            }

            this.yaw = (float) (Math.atan2(d1, d) * 180.0 / 3.1415927410125732) - 90.0F;
            this.field_663 = true;
        }

    }

    public void updateDespawnCounter() {
        super.updateDespawnCounter();
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
            this.jrem = this.jumps;
        }

        if (!this.onGround && this.field_755_h < 1.0F) {
            this.field_755_h = 1.0F;
        }

        this.field_755_h = (float) ((double) this.field_755_h * 0.9);
        if (!this.onGround && this.yVelocity < 0.0) {
            if (this.passenger == null) {
                this.yVelocity *= 0.6;
            } else {
                this.yVelocity *= 0.6375;
            }
        }

        this.field_752_b += this.field_755_h * 2.0F;
        if (!this.world.isClient && --this.timeUntilNextEgg <= 0) {
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }

    }

    protected void handleFallDamage(float f) {
    }

    public boolean damage(Entity entity, int i) {
        if (entity != null && this.passenger != null && entity == this.passenger) {
            return false;
        } else {
            boolean flag = super.damage(entity, i);
            if (flag && this.passenger != null && (this.health <= 0 || this.rand.nextInt(3) == 0)) {
                this.passenger.startRiding(this);
            }

            return flag;
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
                    this.yVelocity = 0.875;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.method_1393() && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.yVelocity = 0.5;
                    this.jpress = true;
                    --this.jrem;
                } else if (this.jrem > 0 && !this.jpress && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.yVelocity = 0.75;
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

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Jumps", (short) this.jumps);
        nbttagcompound.put("Remaining", (short) this.jrem);
        if (this.passenger != null) {
            this.gotrider = true;
        }

        nbttagcompound.put("GotRider", this.gotrider);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.jumps = nbttagcompound.getShort("Jumps");
        this.jrem = nbttagcompound.getShort("Remaining");
        this.gotrider = nbttagcompound.getBoolean("GotRider");
    }

    protected String getAmbientSound() {
        return "aether:mobs.moa.idlecall";
    }

    protected String getHurtSound() {
        return "aether:mobs.moa.idlecall";
    }

    protected String getDeathSound() {
        return "aether:mobs.moa.idlecall";
    }

    public boolean interact(PlayerEntity entityplayer) {
        return true;
    }

    protected void getDrops() {
        boolean skyrootSword = UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10));
        this.dropItem(Item.FEATHER.id, 3 * (skyrootSword ? 2 : 1));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Cockatrice");
    }
}
