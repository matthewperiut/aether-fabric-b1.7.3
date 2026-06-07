package com.matthewperiut.aether.gen.portal;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.AetherPortal;
import com.periut.retroapi.world.event.BlockSetCallback;
import net.minecraft.block.Block;

public class AetherPortalListener {
    /**
     * A water source block set anywhere inside a glowstone frame becomes an Aether portal.
     * Sources have meta 0; spreading/falling water (meta != 0) must not light the portal.
     * Registered from Aether.init().
     */
    public static void register() {
        BlockSetCallback.EVENT.register((world, x, y, z, blockId, meta) -> {
            // create() fills the whole interior with portal blocks; returning true
            // cancels the water placement itself.
            return (blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id) && meta == 0
                    && ((AetherPortal) AetherBlocks.Portal).create(world, x, y, z);
        });
    }
}
