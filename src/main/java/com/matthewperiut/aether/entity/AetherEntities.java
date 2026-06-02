package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.entity.living.*;
import com.matthewperiut.aether.entity.projectile.*;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import com.matthewperiut.aether.optional.AetherSPCSupport;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class AetherEntities {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(EntityAechorPlant.class, "AechorPlant");
        event.register(EntityAerbunny.class, "Aerbunny");
        event.register(EntityAerwhale.class, "Aerwhale");
        event.register(EntityCockatrice.class, "Cockatrice");
        event.register(EntityFlyingCow.class, "FlyingCow");
        event.register(EntityMimic.class, "Mimic");
        event.register(EntityMiniCloud.class, "Minicloud");
        event.register(EntityMoa.class, "Moa");
        event.register(EntitySlider.class, "Slider");
        event.register(EntityPhyg.class, "Phyg");
        event.register(EntitySheepuff.class, "Sheepuff");
        event.register(EntitySwet.class, "Swet");
        event.register(EntityValkyrie.class, "Valkyrie");
        event.register(EntitySentry.class, "Sentry");
        event.register(EntityWhirly.class, "Whirlwind");
        event.register(EntityZephyr.class, "Zephyr");

        event.register(EntityFireMonster.class, "FireMonster");
        event.register(EntityFireMinion.class, "FireMinion");

        event.register(EntityAetherLightning.class, "AetherLightning");
        event.register(EntityFlamingArrow.class, "FlamingArrow");
        event.register(EntityZephyrSnowball.class, "ZephyrSnowball");
        event.register(EntityPoisonNeedle.class, "PoisonNeedle");
        event.register(EntityDartPoison.class, "PoisonDart");
        event.register(EntityDartGolden.class, "GoldenDart");
        event.register(EntityDartEnchanted.class, "EnchantedDart");
        event.register(EntityCloudParachute.class, "CloudParachute");
        event.register(EntityFloatingBlock.class, "FloatingBlock");
        event.register(EntityFiroBall.class, "FiroBall");
        event.register(EntityHomeShot.class, "HomeShot");
        event.register(EntityLightningKnife.class, "LightningKnife");
        event.register(EntityNotchWave.class, "NotchWave");

    }

    /*
     */

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        //Registry.register(event.registry, MOD_ID.id("claysoldier"), EntityClayMan::new);
        Registry.register(event.registry, MOD_ID.id("AechorPlant"), EntityAechorPlant::new);
        Registry.register(event.registry, MOD_ID.id("Aerbunny"), EntityAerbunny::new);
        Registry.register(event.registry, MOD_ID.id("Aerwhale"), EntityAerwhale::new);
        Registry.register(event.registry, MOD_ID.id("Cockatrice"), EntityCockatrice::new);
        Registry.register(event.registry, MOD_ID.id("FlyingCow"), EntityFlyingCow::new);
        Registry.register(event.registry, MOD_ID.id("Mimic"), EntityMimic::new);
        Registry.register(event.registry, MOD_ID.id("Minicloud"), EntityMiniCloud::new);
        Registry.register(event.registry, MOD_ID.id("Moa"), EntityMoa::new);
        Registry.register(event.registry, MOD_ID.id("Slider"), EntitySlider::new);
        Registry.register(event.registry, MOD_ID.id("Phyg"), EntityPhyg::new);
        Registry.register(event.registry, MOD_ID.id("Sheepuff"), EntitySheepuff::new);
        Registry.register(event.registry, MOD_ID.id("Swet"), EntitySwet::new);
        Registry.register(event.registry, MOD_ID.id("Valkyrie"), EntityValkyrie::new);
        Registry.register(event.registry, MOD_ID.id("Sentry"), EntitySentry::new);

        Registry.register(event.registry, MOD_ID.id("Whirlwind"), EntityWhirly::new);
        Registry.register(event.registry, MOD_ID.id("Zephyr"), EntityZephyr::new);

        Registry.register(event.registry, MOD_ID.id("FireMonster"), EntityFireMonster::new);
        Registry.register(event.registry, MOD_ID.id("FireMinion"), EntityFireMinion::new);

        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AetherSPCSupport.init();
        }
    }

    @EventListener
    public void registerEntityHandlers(EntityHandlerRegistryEvent event) {
        Registry.register(event.registry, MOD_ID.id("AetherLightning"), EntityAetherLightning::new);
        Registry.register(event.registry, MOD_ID.id("FlamingArrow"), EntityFlamingArrow::new);
        Registry.register(event.registry, MOD_ID.id("ZephyrSnowball"), EntityZephyrSnowball::new);
        Registry.register(event.registry, MOD_ID.id("PoisonNeedle"), EntityPoisonNeedle::new);
        Registry.register(event.registry, MOD_ID.id("PoisonDart"), EntityDartPoison::new);
        Registry.register(event.registry, MOD_ID.id("GoldenDart"), EntityDartGolden::new);
        Registry.register(event.registry, MOD_ID.id("EnchantedDart"), EntityDartEnchanted::new);
        Registry.register(event.registry, MOD_ID.id("CloudParachute"), EntityCloudParachute::new);
        Registry.register(event.registry, MOD_ID.id("FloatingBlock"), EntityFloatingBlock::new);
        Registry.register(event.registry, MOD_ID.id("FiroBall"), EntityFiroBall::new);
        Registry.register(event.registry, MOD_ID.id("LightningKnife"), EntityLightningKnife::new);
        Registry.register(event.registry, MOD_ID.id("NotchWave"), EntityNotchWave::new);
    }
}
