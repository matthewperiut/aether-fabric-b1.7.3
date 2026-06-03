package com.matthewperiut.aether.client.entity;

import com.matthewperiut.aether.client.entity.model.*;
import com.matthewperiut.aether.client.entity.renderer.living.*;
import com.matthewperiut.aether.client.entity.renderer.projectile.*;
import com.matthewperiut.aether.client.entity.renderer.special.RenderCloudParachute;
import com.matthewperiut.aether.client.entity.renderer.special.RenderFloatingBlock;
import com.matthewperiut.aether.entity.living.*;
import com.matthewperiut.aether.entity.projectile.*;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import com.periut.retroapi.entity.client.RetroEntityRenderers;

public class AetherEntityRenderers {
    public static void registerRenderers() {
        RetroEntityRenderers.register(EntityAechorPlant.class, new RenderAechorPlant(new ModelAechorPlant(), 0.3F));
        RetroEntityRenderers.register(EntityAerbunny.class, new RenderAerbunny(new ModelAerbunny(), 0.3F));
        RetroEntityRenderers.register(EntityAerwhale.class, new RenderAerwhale());
        RetroEntityRenderers.register(EntityCockatrice.class, new RenderCockatrice(new ModelCockatrice(), 1.0F));
        RetroEntityRenderers.register(EntityFlyingCow.class, new RenderFlyingCow(new ModelFlyingCow1(), new ModelFlyingCow2(), 0.7F));
        RetroEntityRenderers.register(EntityMimic.class, new RenderMimic());
        RetroEntityRenderers.register(EntityMoa.class, new RenderMoa(new ModelMoa(), 1.0F));
        RetroEntityRenderers.register(EntitySlider.class, new RenderSlider(new ModelSlider(0.0F, 12.0F), 1.5F));
        RetroEntityRenderers.register(EntityPhyg.class, new RenderPhyg(new ModelFlyingPig1(), new ModelFlyingPig2(), 0.7F));
        RetroEntityRenderers.register(EntitySheepuff.class, new RenderSheepuff(new ModelSheepuff1(), new ModelSheepuff2(), new ModelSheepuff3(), 0.7F));
        RetroEntityRenderers.register(EntitySwet.class, new RenderSwet(new SlimeEntityModel(16), new SlimeEntityModel(0), 0.3F));
        RetroEntityRenderers.register(EntityValkyrie.class, new RenderValkyrie(new ModelValkyrie(), 0.3F));
        RetroEntityRenderers.register(EntitySentry.class, new RenderSentry(new SlimeEntityModel(0), 0.2F));

        RetroEntityRenderers.register(EntityWhirly.class, new RenderWhirly());
        RetroEntityRenderers.register(EntityZephyr.class, new RenderZephyr());

        RetroEntityRenderers.register(EntityFireMonster.class, new UndeadEntityRenderer(new ModelFireMonster(0.0F, 0.0F), 0.4F));
        RetroEntityRenderers.register(EntityFireMinion.class, new UndeadEntityRenderer(new ModelFireMinion(0.0F, 0.0F), 0.4F));

        RetroEntityRenderers.register(EntityPoisonNeedle.class, new RenderPoisonNeedle());
        RetroEntityRenderers.register(EntityZephyrSnowball.class, new RenderZephyrSnowball());
        RetroEntityRenderers.register(EntityFlamingArrow.class, new RenderFlamingArrow());
        RetroEntityRenderers.register(EntityDartPoison.class, new RenderDartPoison());
        RetroEntityRenderers.register(EntityDartGolden.class, new RenderDartGolden());
        RetroEntityRenderers.register(EntityDartEnchanted.class, new RenderDartEnchanted());
        RetroEntityRenderers.register(EntityCloudParachute.class, new RenderCloudParachute());
        RetroEntityRenderers.register(EntityFloatingBlock.class, new RenderFloatingBlock());
        RetroEntityRenderers.register(EntityFiroBall.class, new RenderFiroBall(new ModelHomeShot(0.5F, 0.0F), 0.25F));
        RetroEntityRenderers.register(EntityHomeShot.class, new RenderHomeShot(new ModelHomeShot(0.0F, 0.0F), 0.2F));
        RetroEntityRenderers.register(EntityLightningKnife.class, new RenderLightningKnife());
        RetroEntityRenderers.register(EntityMiniCloud.class, new LivingEntityRenderer(new ModelMiniCloud(0.0f, 20.0f), 0.35f));
        RetroEntityRenderers.register(EntityNotchWave.class, new RenderNotchWave());

        /*
         */
    }
}
