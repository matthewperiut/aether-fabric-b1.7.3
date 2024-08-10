package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntityWhirly;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_75;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ExplosionParticle;
import net.minecraft.client.particle.FireSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;

import java.util.List;
import java.util.Random;

public class RenderWhirly extends EntityRenderer {
    private final Random random = new Random();

    public RenderWhirly() {
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        EntityWhirly entityWhirl = (EntityWhirly) entity;
        // class_75 is ParticleManager
        class_75 particleManager = ((Minecraft) FabricLoader.getInstance().getGameInstance()).field_2808;

        double d3 = ((float) entityWhirl.x);
        double d4 = ((float) entityWhirl.y);
        double d5 = ((float) entityWhirl.z);

        List<Entity> list = entityWhirl.world.getEntities(entityWhirl, entityWhirl.boundingBox.expand(2.5, 2.5, 2.5));
        int i;
        double d6;
        double d7;
        double d8;
        float f2;
        if (!entityWhirl.evil) {
            for (i = 0; i < 2; ++i) {
                d6 = (double) ((float) entityWhirl.x + random.nextFloat() * 0.25F);
                d7 = (double) ((float) entityWhirl.y + entityWhirl.height + 0.125F);
                d8 = (double) ((float) entityWhirl.z + random.nextFloat() * 0.25F);
                f2 = random.nextFloat() * 360.0F;
                ExplosionParticle entityExplodeFx = new ExplosionParticle(entityWhirl.world, -Math.sin((double) (0.01745329F * f2)) * 0.75, d7 - 0.25, Math.cos((double) (0.01745329F * f2)) * 0.75, d6, 0.125, d8);
                // method_325 -> addParticle
                particleManager.method_325(entityExplodeFx);
                entityWhirl.fluffies.add(entityExplodeFx);
                entityExplodeFx.renderDistanceMultiplier = 10.0;
                entityExplodeFx.noClip = true;
                entityExplodeFx.setBoundingBoxSpacing(0.25F, 0.25F);
                entityExplodeFx.setPosition(entityWhirl.x, entityWhirl.y, entityWhirl.z);
                entityExplodeFx.y = d7;
            }
        } else {
            for (i = 0; i < 3; ++i) {
                d6 = (double) ((float) entityWhirl.x + random.nextFloat() * 0.25F);
                d7 = (double) ((float) entityWhirl.y + entityWhirl.height + 0.125F);
                d8 = (double) ((float) entityWhirl.z + random.nextFloat() * 0.25F);
                f2 = random.nextFloat() * 360.0F;
                FireSmokeParticle entitySmokeFx = new FireSmokeParticle(entityWhirl.world, -Math.sin((double) (0.01745329F * f2)) * 0.75, d7 - 0.25, Math.cos((double) (0.01745329F * f2)) * 0.75, d6, 0.125, d8, 3.5F);
                particleManager.method_325(entitySmokeFx);
                entityWhirl.fluffies.add(entitySmokeFx);
                entitySmokeFx.renderDistanceMultiplier = 10.0;
                entitySmokeFx.noClip = true;
                entitySmokeFx.setBoundingBoxSpacing(0.25F, 0.25F);
                entitySmokeFx.setPosition(entityWhirl.x, entityWhirl.y, entityWhirl.z);
                entitySmokeFx.y = d7;
            }
        }

        if (entityWhirl.fluffies.size() > 0) {
            for (i = 0; i < entityWhirl.fluffies.size(); ++i) {
                Particle entityFx = (Particle) entityWhirl.fluffies.get(i);
                if (entityFx.dead) {
                    entityWhirl.fluffies.remove(entityFx);
                } else {
                    d6 = (double) ((float) entityFx.x);
                    d7 = (double) ((float) entityFx.boundingBox.minY);
                    d8 = (double) ((float) entityFx.z);
                    double d9 = (double) entityWhirl.getDistance(entityFx);
                    double d10 = d7 - d4;
                    entityFx.velocityY = 0.11500000208616257;
                    double d11 = Math.atan2(d3 - d6, d5 - d8) / 0.01745329424738884;
                    d11 += 160.0;
                    entityFx.velocityX = -Math.cos(0.01745329424738884 * d11) * (d9 * 2.5 - d10) * 0.10000000149011612;
                    entityFx.velocityZ = Math.sin(0.01745329424738884 * d11) * (d9 * 2.5 - d10) * 0.10000000149011612;
                }
            }
        }
    }
}