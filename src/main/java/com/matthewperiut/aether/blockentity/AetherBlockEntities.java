package com.matthewperiut.aether.blockentity;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Null;
import org.jetbrains.annotations.NotNull;

public class AetherBlockEntities {
    @Entrypoint.ModID
    public static final net.modificationstation.stationapi.api.registry.@NotNull ModID MOD_ID = Null.get();

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        event.register(BlockEntityTreasureChest.class, MOD_ID.id("treasure_chest").toString());
        event.register(BlockEntityFreezer.class, MOD_ID.id("freezer").toString());
        event.register(BlockEntityEnchanter.class, MOD_ID.id("enchanter").toString());
        event.register(BlockEntityIncubator.class, MOD_ID.id("incubator").toString());
    }
}
