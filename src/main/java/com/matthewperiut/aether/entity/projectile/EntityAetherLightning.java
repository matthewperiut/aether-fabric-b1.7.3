package com.matthewperiut.aether.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityAetherLightning extends LightningEntity implements EntitySpawnDataProvider {
    public EntityAetherLightning(World var1) {
        super(var1, 0, 0, 0);
    }

    public EntityAetherLightning(World var1, double var2, double var4, double var6) {
        super(var1, var2, var4, var6);
    }

    private boolean strike = false;
    private int ticks = 0;

    @Override
    public void tick() {
        ticks++;
        if (!strike) {
            strike = true;
            this.world.playSound(this.x, this.y, this.z, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
            this.world.playSound(this.x, this.y, this.z, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);

            double var6 = 3.0D;
            List<Entity> entities = this.world.getEntities(this, AxixAlignedBoundingBox.create(this.x - var6, this.y - var6, this.z - var6, this.x + var6, this.y + 6.0 + var6, this.z + var6));

            for (Entity entity : entities) {
                if (!(PlayerEntity.class.isAssignableFrom(entity.getClass()))) {
                    entity.onStruckByLightning(this);
                }
            }
        }
        if (ticks > 20) {
            world.removeEntity(this);
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("AetherLightning");
    }
}
