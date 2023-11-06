package com.matthewperiut.aether.entity;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Null;

public class AetherEntities {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerEntities(EntityRegister event) {
        //event.register(EntityClayMan.class, "claysoldier");
        event.register(EntityAechorPlant.class, "AechorPlant");
        event.register(EntityAerbunny.class, "Aerbunny");
        event.register(EntityAerwhale.class, "Aerwhale");

        event.register(EntityFlamingArrow.class, "FlamingArrow");
        event.register(EntityPoisonNeedle.class, "PoisonNeedle");
        event.register(EntityDartPoison.class, "PoisonDart");
        event.register(EntityDartGolden.class, "GoldenDart");
        event.register(EntityDartEnchanted.class, "EnchantedDart");
    }

    /*
    ModLoader.RegisterEntityID(EntityAerwhale.class, "Aerwhale", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityCockatrice.class, "Cockatrice", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntitySwet.class, "Swets", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityZephyr.class, "Zephyr", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntitySheepuff.class, "Sheepuff", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityPhyg.class, "FlyingPig", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityAechorPlant.class, "AechorPlant", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntitySentry.class, "Sentry", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntitySlider.class, "Slider", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityValkyrie.class, "Valkyrie", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityHomeShot.class, "HomeShot", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityFireMonster.class, "Fire Monster", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityFireMinion.class, "Fire Minion", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityFiroBall.class, "Firo Ball", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityMoa.class, "MoaBase", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityFlyingCow.class, "FlyingCow", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(EntityAerbunny.class, "Aerbunny", ModLoader.getUniqueEntityId());
    ModLoader.RegisterEntityID(Whirly.class, "Whirlwind", ModLoader.getUniqueEntityId());
     */

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        //Registry.register(event.registry, MOD_ID.id("claysoldier"), EntityClayMan::new);
        Registry.register(event.registry, MOD_ID.id("AechorPlant"), EntityAechorPlant::new);
        Registry.register(event.registry, MOD_ID.id("Aerbunny"), EntityAerbunny::new);
        Registry.register(event.registry, MOD_ID.id("Aerwhale"), EntityAerwhale::new);
    }
}
