package com.matthewperiut.aether.gen.biome;

import com.matthewperiut.aether.entity.living.*;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;

public class AetherBiomes {

    public static Biome AETHER;

    public static int raritySwet = 8;
    public static int rarityAechorPlant = 8;
    public static int rarityCockatrice = 3;
    public static int rarityAerwhale = 8;
    public static int rarityZephyr = 5;
    public static int raritySheepuff = 10;
    public static int rarityPhyg = 12;
    public static int rarityMoa = 10;
    public static int rarityFlyingCow = 10;
    public static int rarityWhirly = 8;
    public static int rarityAerbunny = 11;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        AETHER = BiomeBuilder.start("aether")
                .grassAndLeavesColor(353825)
                .precipitation(false)
                .passiveEntity(EntitySwet.class, raritySwet)
                .passiveEntity(EntityAechorPlant.class, rarityAechorPlant)
                .passiveEntity(EntityCockatrice.class, rarityCockatrice)
                .passiveEntity(EntityAerwhale.class, rarityAerwhale)
                .passiveEntity(EntityZephyr.class, rarityZephyr)
                .passiveEntity(EntitySheepuff.class, raritySheepuff)
                .passiveEntity(EntityPhyg.class, rarityPhyg)
                .passiveEntity(EntityMoa.class, rarityMoa)
                .passiveEntity(EntityFlyingCow.class, rarityFlyingCow)
                .passiveEntity(EntityWhirly.class, rarityWhirly)
                .passiveEntity(EntityAerbunny.class, rarityAerbunny)
                .snow(false)
                .build();

    }
}
