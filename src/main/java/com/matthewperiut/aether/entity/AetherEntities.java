package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.entity.living.*;
import com.matthewperiut.aether.entity.projectile.*;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import com.matthewperiut.aether.optional.AetherRetroCommandsSupport;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class AetherEntities {
    @Entrypoint.Namespace
    public static Namespace MOD_ID = Null.get();

    private static Identifier id(String s) {
        return Identifier.of(Namespace.of("aether"), s);
    }

    @EventListener
    public void registerEntities(EntityRegisterEvent event) {
        event.register(MOD_ID.id("AechorPlant"), EntityAechorPlant.class);
        event.register(MOD_ID.id("Aerbunny"), EntityAerbunny.class);
        event.register(MOD_ID.id("Aerwhale"), EntityAerwhale.class);
        event.register(MOD_ID.id("Cockatrice"), EntityCockatrice.class);
        event.register(MOD_ID.id("FlyingCow"), EntityFlyingCow.class);
        event.register(MOD_ID.id("Mimic"), EntityMimic.class);
        event.register(MOD_ID.id("Minicloud"), EntityMiniCloud.class);
        event.register(MOD_ID.id("Moa"), EntityMoa.class);
        event.register(MOD_ID.id("Slider"), EntitySlider.class);
        event.register(MOD_ID.id("Phyg"), EntityPhyg.class);
        event.register(MOD_ID.id("Sheepuff"), EntitySheepuff.class);
        event.register(MOD_ID.id("Swet"), EntitySwet.class);
        event.register(MOD_ID.id("Valkyrie"), EntityValkyrie.class);
        event.register(MOD_ID.id("Sentry"), EntitySentry.class);
        event.register(MOD_ID.id("Whirlwind"), EntityWhirly.class);
        event.register(MOD_ID.id("Zephyr"), EntityZephyr.class);

        event.register(MOD_ID.id("FireMonster"), EntityFireMonster.class);
        event.register(MOD_ID.id("FireMinion"), EntityFireMinion.class);

        event.register(MOD_ID.id("AetherLightning"), EntityAetherLightning.class);
        event.register(MOD_ID.id("FlamingArrow"), EntityFlamingArrow.class);
        event.register(MOD_ID.id("ZephyrSnowball"), EntityZephyrSnowball.class);
        event.register(MOD_ID.id("PoisonNeedle"), EntityPoisonNeedle.class);
        event.register(MOD_ID.id("PoisonDart"), EntityDartPoison.class);
        event.register(MOD_ID.id("GoldenDart"), EntityDartGolden.class);
        event.register(MOD_ID.id("EnchantedDart"), EntityDartEnchanted.class);
        event.register(MOD_ID.id("CloudParachute"), EntityCloudParachute.class);
        event.register(MOD_ID.id("FloatingBlock"), EntityFloatingBlock.class);
        event.register(MOD_ID.id("FiroBall"), EntityFiroBall.class);
        event.register(MOD_ID.id("HomeShot"), EntityHomeShot.class);
        event.register(MOD_ID.id("LightningKnife"), EntityLightningKnife.class);
        event.register(MOD_ID.id("NotchWave"), EntityNotchWave.class);
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
            AetherRetroCommandsSupport.init();
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
