package com.matthewperiut.aether;

import com.matthewperiut.aether.block.TreasureChest;
import com.matthewperiut.aether.optional.AetherSummonEntities;
import com.matthewperiut.spc.api.Command;
import com.matthewperiut.spc.api.CommandRegistry;
import com.matthewperiut.spc.util.SharedCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

public class Aether {

    @EventListener
    private static void init(InitEvent initEvent) {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AetherSummonEntities.register();
            CommandRegistry.add(new Command() {
                @Override
                public void command(SharedCommandSource commandSource, String[] parameters) {
                    PlayerEntity p = commandSource.getPlayer();
                    TreasureChest.PlaceTreasureChest(p.world, (int) p.x, (int) p.y, (int) p.z, Integer.parseInt(parameters[1]));
                }

                @Override
                public String name() {
                    return "test";
                }

                @Override
                public void manual(SharedCommandSource commandSource) {

                }
            });
        }
    }
}
