package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import com.periut.retroapi.gui.client.RetroGuiHandler;
import com.periut.retroapi.gui.client.RetroGuiRegistry;

public class AetherGuis {
    public static void registerGuis() {
        RetroGuiRegistry.register(Aether.id("treasure_chest"), new RetroGuiHandler(
                (player, inv) -> new GuiTreasureChest(player.inventory, (BlockEntityTreasureChest) inv),
                BlockEntityTreasureChest::new));
        RetroGuiRegistry.register(Aether.id("freezer"), new RetroGuiHandler(
                (player, inv) -> new GuiFreezer(player.inventory, (BlockEntityFreezer) inv),
                BlockEntityFreezer::new));
        RetroGuiRegistry.register(Aether.id("enchanter"), new RetroGuiHandler(
                (player, inv) -> new GuiEnchanter(player.inventory, (BlockEntityEnchanter) inv),
                BlockEntityEnchanter::new));
        RetroGuiRegistry.register(Aether.id("incubator"), new RetroGuiHandler(
                (player, inv) -> new GuiIncubator(player.inventory, (BlockEntityIncubator) inv),
                BlockEntityIncubator::new));
    }
}
