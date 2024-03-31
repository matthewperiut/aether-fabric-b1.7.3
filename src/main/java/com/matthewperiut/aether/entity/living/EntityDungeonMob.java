package com.matthewperiut.aether.entity.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class EntityDungeonMob extends MobEntity implements Monster {
    protected int attackStrength = 2;

    public EntityDungeonMob(World world) {
        super(world);
        this.health = 20;
    }

    public void updateDespawnCounter() {
        float f = this.getBrightnessAtEyes(1.0F);
        if (f > 0.5F) {
            this.despawnCounter += 2;
        }

        super.updateDespawnCounter();
    }

    protected Entity getAttackTarget() {
        PlayerEntity entityplayer = this.world.getClosestPlayerTo(this, 16.0);
        return entityplayer != null && this.method_928(entityplayer) ? entityplayer : null;
    }

    public boolean damage(Entity entity, int i) {
        if (super.damage(entity, i)) {
            if (this.passenger != entity && this.vehicle != entity) {
                if (entity != this) {
                    this.entity = entity;
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected void tryAttack(Entity entity, float f) {
        if (this.attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            entity.damage(this, this.attackStrength);
        }

    }

    protected float getPathfindingFavour(int i, int j, int k) {
        return 0.5F - this.world.method_1782(i, j, k);
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        if (this.world.method_164(LightType.field_2757, i, j, k) > this.rand.nextInt(32)) {
            return false;
        } else {
            int l = this.world.placeBlock(i, j, k);
            if (this.world.isThundering()) {
                int i1 = this.world.field_202;
                this.world.field_202 = 10;
                l = this.world.placeBlock(i, j, k);
                this.world.field_202 = i1;
            }

            return l <= this.rand.nextInt(8) && super.canSpawn();
        }
    }
}
