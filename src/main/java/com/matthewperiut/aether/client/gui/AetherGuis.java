package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.client.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class AetherGuis {
    @Entrypoint.Namespace
    private static final Namespace MOD_ID = Null.get();

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        Registry.register(registry, Identifier.of(MOD_ID, "treasure_chest"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openTreasureChest, BlockEntityTreasureChest::new));
        Registry.register(registry, Identifier.of(MOD_ID, "freezer"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openFreezer, BlockEntityFreezer::new));
        Registry.register(registry, Identifier.of(MOD_ID, "enchanter"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openEnchanter, BlockEntityEnchanter::new));
        Registry.register(registry, Identifier.of(MOD_ID, "incubator"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openIncubator, BlockEntityIncubator::new));
    }

    @Environment(EnvType.CLIENT)
    public HandledScreen openTreasureChest(PlayerEntity player, Inventory inventoryBase) {
        return new GuiTreasureChest(player.inventory, (BlockEntityTreasureChest) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public HandledScreen openFreezer(PlayerEntity player, Inventory inventoryBase) {
        return new GuiFreezer(player.inventory, (BlockEntityFreezer) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public HandledScreen openEnchanter(PlayerEntity player, Inventory inventoryBase) {
        return new GuiEnchanter(player.inventory, (BlockEntityEnchanter) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public HandledScreen openIncubator(PlayerEntity player, Inventory inventoryBase) {
        return new GuiIncubator(player.inventory, (BlockEntityIncubator) inventoryBase);
    }
}
