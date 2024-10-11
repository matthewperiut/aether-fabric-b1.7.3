package com.matthewperiut.aether.entity.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class EntityDungeonMob extends MobEntity implements Monster {
    protected int attackStrength = 2;

    public EntityDungeonMob(World world) {
        super(world);
        this.health = 20;
    }

    public void tickMovement() {
        float f = this.getBrightnessAtEyes(1.0F);
        if (f > 0.5F) {
            this.despawnCounter += 2;
        }

        super.tickMovement();
    }

    protected Entity getTargetInRange() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 16.0);
        return entityplayer != null && this.canSee(entityplayer) ? entityplayer : null;
    }

    public boolean damage(Entity entity, int i) {
        if (super.damage(entity, i)) {
            if (this.passenger != entity && this.vehicle != entity) {
                if (entity != this) {
                    this.target = entity;
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected void attack(Entity entity, float f) {
        if (this.attackCooldown <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackCooldown = 20;
            entity.damage(this, this.attackStrength);
        }

    }

    protected float getPathfindingFavor(int i, int j, int k) {
        return 0.5F - this.world.method_1782(i, j, k);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        if (this.world.getBrightness(LightType.SKY, i, j, k) > this.random.nextInt(32)) {
            return false;
        } else {
            int l = this.world.getLightLevel(i, j, k);
            if (this.world.isThundering()) {
                int i1 = this.world.ambientDarkness;
                this.world.ambientDarkness = 10;
                l = this.world.getLightLevel(i, j, k);
                this.world.ambientDarkness = i1;
            }

            return l <= this.random.nextInt(8) && super.canSpawn();
        }
    }
}
