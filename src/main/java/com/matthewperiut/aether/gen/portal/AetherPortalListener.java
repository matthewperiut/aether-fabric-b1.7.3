package com.matthewperiut.aether.gen.portal;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.AetherPortal;
import com.periut.retroapi.world.event.BlockSetCallback;
import net.minecraft.block.Block;

public class AetherPortalListener {
    /** Water placed on glowstone becomes an Aether portal. Registered from Aether.init(). */
    public static void register() {
        BlockSetCallback.EVENT.register((world, x, y, z, blockId, meta) -> {
            if ((blockId == Block.WATER.id || blockId == Block.FLOWING_WATER.id)
                    && world.getBlockId(x, y - 1, z) == Block.GLOWSTONE.id
                    && ((AetherPortal) AetherBlocks.Portal).create(world, x, y, z)) {
                world.setBlock(x, y, z, AetherBlocks.Portal.id);
                return true;
            }
            return false;
        });
    }
}
