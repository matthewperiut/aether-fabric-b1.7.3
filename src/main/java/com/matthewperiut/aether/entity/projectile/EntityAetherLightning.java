package com.matthewperiut.aether.entity.projectile;

import com.periut.retroapi.entity.spawn.RetroEntitySpawnData;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;


public class EntityAetherLightning extends LightningEntity implements RetroEntitySpawnData {
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
            this.world.playSound(this.x, this.y, this.z, "ambient.weather.thunder", 10000.0F, 0.8F + this.random.nextFloat() * 0.2F);
            this.world.playSound(this.x, this.y, this.z, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);

            double var6 = 3.0D;
            List<Entity> entities = this.world.getEntities(this, Box.create(this.x - var6, this.y - var6, this.z - var6, this.x + var6, this.y + 6.0 + var6, this.z + var6));

            for (Entity entity : entities) {
                if (!(PlayerEntity.class.isAssignableFrom(entity.getClass()))) {
                    entity.onStruckByLightning(this);
                }
            }
        }
        if (ticks > 20) {
            world.remove(this);
        }
    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("AetherLightning");
    }
}
