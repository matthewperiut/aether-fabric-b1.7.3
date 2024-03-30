package com.matthewperiut.aether.gen.portal;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.AetherPortal;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.world.BlockSetEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EventBusPolicy;

@Entrypoint(eventBus = @EventBusPolicy(registerInstance = false))
public class AetherPortalListener {
    @EventListener
    private static void blockSet(BlockSetEvent event) {
        if (
                (event.blockState.getBlock().id == Block.STILL_WATER.id || event.blockState.getBlock().id == Block.FLOWING_WATER.id) &&
                        event.world.getBlockId(event.x, event.y - 1, event.z) == Block.GLOWSTONE.id &&
                        ((AetherPortal) AetherBlocks.Portal).method_736(event.world, event.x, event.y, event.z)
        ) {
            event.cancel();
            event.world.setBlock(event.x, event.y, event.z, AetherBlocks.Portal.id);
        }
    }
}
