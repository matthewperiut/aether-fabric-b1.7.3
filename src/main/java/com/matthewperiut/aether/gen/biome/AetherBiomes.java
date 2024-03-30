package com.matthewperiut.aether.gen.biome;

import com.matthewperiut.aether.entity.living.EntityZephyr;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;

public class AetherBiomes {

    public static Biome AETHER;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        AETHER = BiomeBuilder.start("aether")
                .grassAndLeavesColor(353825)
                .precipitation(false)
                .passiveEntity(EntityZephyr.class, 100)
                .snow(false)
                .build();

    }
}
