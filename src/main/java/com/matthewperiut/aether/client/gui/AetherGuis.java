package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class AetherGuis
{
    @Entrypoint.ModID
    private static final ModID MOD_ID = Null.get();

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event)
    {
        GuiHandlerRegistry registry = event.registry;
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "treasure_chest"), BiTuple.of(this::openTreasureChest, BlockEntityTreasureChest::new));
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "freezer"), BiTuple.of(this::openFreezer, BlockEntityFreezer::new));
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "enchanter"), BiTuple.of(this::openEnchanter, BlockEntityEnchanter::new));
        registry.registerValueNoMessage(Identifier.of(MOD_ID, "incubator"), BiTuple.of(this::openIncubator, BlockEntityIncubator::new));
    }

    @Environment(EnvType.CLIENT)
    public ContainerScreen openTreasureChest(PlayerEntity player, Inventory inventoryBase)
    {
        return new GuiTreasureChest(player.inventory, (BlockEntityTreasureChest) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public ContainerScreen openFreezer(PlayerEntity player, Inventory inventoryBase)
    {
        return new GuiFreezer(player.inventory, (BlockEntityFreezer) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public ContainerScreen openEnchanter(PlayerEntity player, Inventory inventoryBase)
    {
        return new GuiEnchanter(player.inventory, (BlockEntityEnchanter) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public ContainerScreen openIncubator(PlayerEntity player, Inventory inventoryBase)
    {
        return new GuiIncubator(player.inventory, (BlockEntityIncubator) inventoryBase);
    }
}
