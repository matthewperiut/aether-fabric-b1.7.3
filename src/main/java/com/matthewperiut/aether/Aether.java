package com.matthewperiut.aether;

import com.matthewperiut.aether.optional.AetherSPCSupport;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

public class Aether {

    public static boolean OLDSTAPI = false;

    @EventListener
    private static void init(InitEvent initEvent) {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AetherSPCSupport.init();
        }
        String stapi_version = String.valueOf(FabricLoader.getInstance().getModContainer("station-api-base").get().getMetadata().getVersion());
        if (stapi_version.equals("2.0-alpha.2-1.0.0") || stapi_version.equals("2.0-alpha.1.1-1.0.0") || stapi_version.equals("2.0-alpha.1-1.0.0")) {
            OLDSTAPI = true;
        }
    }
}
