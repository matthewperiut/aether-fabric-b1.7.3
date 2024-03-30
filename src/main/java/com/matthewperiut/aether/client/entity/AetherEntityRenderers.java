package com.matthewperiut.aether.client.entity;

import com.matthewperiut.aether.client.entity.model.*;
import com.matthewperiut.aether.client.entity.renderer.living.*;
import com.matthewperiut.aether.client.entity.renderer.projectile.*;
import com.matthewperiut.aether.entity.living.*;
import com.matthewperiut.aether.entity.projectile.*;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class AetherEntityRenderers {
    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(EntityAechorPlant.class, new RenderAechorPlant(new ModelAechorPlant(), 0.3F));
        event.renderers.put(EntityAerbunny.class, new RenderAerbunny(new ModelAerbunny(), 0.3F));
        event.renderers.put(EntityAerwhale.class, new RenderAerwhale());
        event.renderers.put(EntityCockatrice.class, new RenderCockatrice(new ModelCockatrice(), 1.0F));

        event.renderers.put(EntityWhirly.class, new RenderWhirly());
        event.renderers.put(EntityZephyr.class, new RenderZephyr());

        event.renderers.put(EntityFireMonster.class, new BipedEntityRenderer(new ModelFireMonster(0.0F, 0.0F), 0.4F));
        event.renderers.put(EntityFireMinion.class, new BipedEntityRenderer(new ModelFireMinion(0.0F, 0.0F), 0.4F));

        event.renderers.put(EntityPoisonNeedle.class, new RenderPoisonNeedle());
        event.renderers.put(EntityZephyrSnowball.class, new RenderZephyrSnowball());
        event.renderers.put(EntityFlamingArrow.class, new RenderFlamingArrow());
        event.renderers.put(EntityDartPoison.class, new RenderDartPoison());
        event.renderers.put(EntityDartGolden.class, new RenderDartGolden());
        event.renderers.put(EntityDartEnchanted.class, new RenderDartEnchanted());
        event.renderers.put(EntityCloudParachute.class, new RenderCloudParachute());
        event.renderers.put(EntityFloatingBlock.class, new RenderFloatingBlock());
        event.renderers.put(EntityFiroBall.class, new RenderFiroBall(new ModelHomeShot(0.5F, 0.0F), 0.25F));

    }
    /*
        map.put(EntityZephyr.class, new RenderZephyr());
        map.put(EntityAerwhale.class, new RenderAerwhale());
        map.put(EntityCockatrice.class, new RenderCockatrice(new ModelCockatrice(), 1.0F));
        map.put(EntitySheepuff.class, new RenderSheepuff(new ModelSheepuff1(), new ModelSheepuff2(), new ModelSheepuff3(), 0.7F));
        map.put(EntityPhyg.class, new RenderPhyg(new ModelFlyingPig1(), new ModelFlyingPig2(), 0.7F));
        map.put(EntitySwet.class, new RenderSwet(new SlimeEntityModel(16), new SlimeEntityModel(0), 0.3F));
        map.put(EntityAechorPlant.class, new RenderAechorPlant(new ModelAechorPlant(), 0.3F));
        map.put(EntityZephyrSnowball.class, new RenderZephyrSnowball());
        map.put(EntitySentry.class, new RenderSentry(new SlimeEntityModel(0), 0.2F));
        map.put(EntitySlider.class, new RenderSlider(new ModelSlider(0.0F, 12.0F), 1.5F));
        map.put(EntityValkyrie.class, new RenderValkyrie(new ModelValkyrie(), 0.3F));
        map.put(EntityHomeShot.class, new RenderHomeShot(new ModelHomeShot(0.0F, 0.0F), 0.2F));
        map.put(EntityFireMonster.class, new BipedEntityRenderer(new ModelFireMonster(0.0F, 0.0F), 0.4F));
        map.put(EntityFireMinion.class, new BipedEntityRenderer(new ModelFireMinion(0.0F, 0.0F), 0.4F));
        map.put(EntityFiroBall.class, new RenderFiroBall(new ModelHomeShot(0.5F, 0.0F), 0.25F));
        map.put(EntityMoa.class, new RenderMoa(new ModelMoa(), 1.0F));
        map.put(EntityFlyingCow.class, new RenderFlyingCow(new ModelFlyingCow1(), new ModelFlyingCow2(), 0.7F));
        map.put(EntityAerbunny.class, new RenderAerbunny(new ModelAerbunny(), 0.3F));
        map.put(Whirly.class, new RenderWhirly());
     */
}
