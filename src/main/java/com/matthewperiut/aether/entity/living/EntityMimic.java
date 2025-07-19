package com.matthewperiut.aether.entity.living;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityMimic extends EntityDungeonMob implements MobSpawnDataProvider {
    public float mouth;
    public float legs;
    private float legsDirection = 1.0F;

    public EntityMimic(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/Mimic.png";
        this.standingEyeHeight = 0.0F;
        this.setBoundingBoxSpacing(1.0F, 2.0F);
        this.health = 40;
        this.attackStrength = 5;
        this.target = world.getClosestPlayer(this, 64.0);
    }

    public void tick() {
        super.tick();
        this.mouth = (float) (Math.cos((double) ((float) this.age / 10.0F * 3.1415927F)) + 1.0) * 0.6F;
        this.legs *= 0.9F;
        if (this.velocityX > 0.001 || this.velocityX < -0.001 || this.velocityZ > 0.001 || this.velocityZ < -0.001) {
            this.legs += this.legsDirection * 0.2F;
            if (this.legs > 1.0F) {
                this.legsDirection = -1.0F;
            }

            if (this.legs < -1.0F) {
                this.legsDirection = 1.0F;
            }
        }

    }

    public void onCollision(Entity entity) {
        if (!this.dead && entity != null) {
            entity.damage(this, 4);
        }

    }

    public boolean damage(Entity entity, int i) {
        if (entity instanceof PlayerEntity) {
            this.lookAt(entity, 10.0F, 10.0F);
            this.target = (PlayerEntity) entity;
        }

        return super.damage(entity, i);
    }

    protected String getHurtSound() {
        return "mob.slime";
    }

    protected String getDeathSound() {
        return "mob.slime";
    }

    protected float getSoundVolume() {
        return 0.6F;
    }

    protected int getDroppedItemId() {
        return Block.CHEST.asItem().id;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Mimic");
    }
}
