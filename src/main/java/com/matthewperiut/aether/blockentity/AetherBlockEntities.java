package com.matthewperiut.aether.blockentity;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import com.periut.retroapi.register.blockentity.RetroBlockEntities;

public class AetherBlockEntities {
    public static void registerBlockEntities() {
        RetroBlockEntities.register(Aether.id("treasure_chest"), BlockEntityTreasureChest.class);
        RetroBlockEntities.register(Aether.id("freezer"), BlockEntityFreezer.class);
        RetroBlockEntities.register(Aether.id("enchanter"), BlockEntityEnchanter.class);
        RetroBlockEntities.register(Aether.id("incubator"), BlockEntityIncubator.class);
    }
}
