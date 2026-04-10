package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntitySentry extends EntityDungeonMob implements MobSpawnDataProvider {
    public float field_100021_a;
    public float field_100020_b;
    private int jcount;
    public int size;
    public int counter;
    public int lostyou;
    public boolean active;

    public EntitySentry(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/Sentry.png";
        this.size = 2;
        this.standingEyeHeight = 0.0F;
        this.movementSpeed = 1.0F;
        this.field_100021_a = 1.0F;
        this.field_100020_b = 1.0F;
        this.jcount = this.random.nextInt(20) + 10;
        this.func_100019_e(this.size);
    }

    public EntitySentry(World world, double x, double y, double z) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/Sentry.png";
        this.size = 2;
        this.standingEyeHeight = 0.0F;
        this.movementSpeed = 1.0F;
        this.field_100021_a = 1.0F;
        this.field_100020_b = 1.0F;
        this.jcount = this.random.nextInt(20) + 10;
        this.func_100019_e(this.size);
        this.yaw = (float) this.random.nextInt(4) * 1.5707965F;
        this.setPosition(x, y, z);
    }

    public void func_100019_e(int i) {
        this.health = 10;
        this.width = 0.85F;
        this.height = 0.85F;
        this.setPosition(this.x, this.y, this.z);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putInt("Size", this.size - 1);
        nbttagcompound.putInt("LostYou", this.lostyou);
        nbttagcompound.putInt("Counter", this.counter);
        nbttagcompound.putBoolean("Active", this.active);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.size = nbttagcompound.getInt("Size") + 1;
        this.lostyou = nbttagcompound.getInt("LostYou");
        this.counter = nbttagcompound.getInt("Counter");
        this.active = nbttagcompound.getBoolean("Active");
    }

    public void tick() {
        boolean flag = this.onGround;
        super.tick();
        if (this.onGround && !flag) {
            this.world.playSound(this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        } else if (!this.onGround && flag && this.target != null) {
            this.velocityX *= 3.0;
            this.velocityZ *= 3.0;
        }

        if (this.target != null && this.target.dead) {
            this.target = null;
        }

    }

    public void markDead() {
        if (this.health <= 0 || this.dead) {
            super.markDead();
        }

    }

    public boolean damage(Entity entity, int i) {
        boolean flag = super.damage(entity, i);
        if (flag && entity instanceof LivingEntity) {
            this.active = true;
            this.lostyou = 0;
            this.target = entity;
            this.texture = "aether:stationapi/textures/mobs/SentryLit.png";
        }

        return flag;
    }

    public void shutdown() {
        this.counter = -64;
        this.active = false;
        this.target = null;
        this.texture = "aether:stationapi/textures/mobs/Sentry.png";
        this.setPath((Path) null);
        this.sidewaysSpeed = 0.0F;
        this.forwardSpeed = 0.0F;
        this.jumping = false;
        this.velocityX = 0.0;
        this.velocityZ = 0.0;
    }

    public void onCollision(Entity entity) {
        if (!this.dead && this.target != null && entity != null && this.target == entity) {
            this.world.createExplosion(this, this.x, this.y, this.z, 0.1F);
            entity.damage((Entity) null, 2);
            if (entity instanceof LivingEntity) {
                LivingEntity entityliving = (LivingEntity) entity;
                double d = entityliving.x - this.x;

                double d2;
                for (d2 = entityliving.z - this.z; d * d + d2 * d2 < 1.0E-4; d2 = (Math.random() - Math.random()) * 0.01) {
                    d = (Math.random() - Math.random()) * 0.01;
                }

                entityliving.applyKnockback(this, 5, -d, -d2);
                entityliving.velocityX *= 4.0;
                entityliving.velocityY *= 4.0;
                entityliving.velocityZ *= 4.0;
            }

            float f = 0.01745329F;

            for (int i = 0; i < 40; ++i) {
                double d1 = (double) ((float) this.x + this.random.nextFloat() * 0.25F);
                double d3 = (double) ((float) this.y + 0.5F);
                double d4 = (double) ((float) this.z + this.random.nextFloat() * 0.25F);
                float f1 = this.random.nextFloat() * 360.0F;
                this.world.addParticle("explode", d1, d3, d4, -Math.sin((double) (f * f1)) * 0.75, 0.125, Math.cos((double) (f * f1)) * 0.75);
            }

            this.health = 0;
            this.markDead();
        }

    }

    protected void tickLiving() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 8.0);
        if (!this.active && this.counter >= 8) {
            if (entityplayer != null && this.canSee(entityplayer)) {
                this.lookAt(entityplayer, 10.0F, 10.0F);
                this.target = entityplayer;
                this.active = true;
                this.lostyou = 0;
                this.texture = "aether:stationapi/textures/mobs/SentryLit.png";
            }

            this.counter = 0;
        } else if (this.active && this.counter >= 8) {
            if (this.target == null) {
                if (entityplayer != null && this.canSee(entityplayer)) {
                    this.target = entityplayer;
                    this.active = true;
                    this.lostyou = 0;
                } else {
                    ++this.lostyou;
                    if (this.lostyou >= 4) {
                        this.shutdown();
                    }
                }
            } else if (this.canSee(this.target) && !(this.getDistance(this.target) >= 16.0F)) {
                this.lostyou = 0;
            } else {
                ++this.lostyou;
                if (this.lostyou >= 4) {
                    this.shutdown();
                }
            }

            this.counter = 0;
        } else {
            ++this.counter;
        }

        if (this.active) {
            if (this.target != null) {
                this.lookAt(this.target, 10.0F, 10.0F);
            }

            if (this.onGround && this.jcount-- <= 0) {
                this.jcount = this.random.nextInt(20) + 10;
                this.jumping = true;
                this.sidewaysSpeed = 0.5F - this.random.nextFloat();
                this.forwardSpeed = 1.0F;
                this.world.playSound(this, "mob.slime", this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
                if (this.target != null) {
                    this.jcount /= 2;
                    this.forwardSpeed = 1.0F;
                }
            } else {
                this.jumping = false;
                if (this.onGround) {
                    this.sidewaysSpeed = this.forwardSpeed = 0.0F;
                }
            }

        }
    }

    protected String getHurtSound() {
        return "mob.slime";
    }

    protected String getDeathSound() {
        return "mob.slime";
    }

    public boolean canSpawn() {
        return super.canSpawn();
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    protected int getDroppedItemId() {
        return this.random.nextInt(5) == 0 ? AetherBlocks.LightDungeonStone.asItem().id : AetherBlocks.DungeonStone.asItem().id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Sentry");
    }
}
