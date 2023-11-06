package com.matthewperiut.aether;

import com.matthewperiut.aether.command.SummonAetherEntities;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

public class Aether {

    @EventListener
    private static void init(InitEvent initEvent) {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            SummonAetherEntities.register();
        }
    }
}
