package com.matthewperiut.aether;

import com.matthewperiut.aether.optional.AetherSPCSupport;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.InitEvent;

public class Aether implements ModInitializer {

    public static boolean OLDSTAPI = false;

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            AetherSPCSupport.init();
        }
        String stapi_version = String.valueOf(FabricLoader.getInstance().getModContainer("station-api-base").get().getMetadata().getVersion());
        if (stapi_version.equals("2.0-alpha.2-1.0.0") || stapi_version.equals("2.0-alpha.1.1-1.0.0") || stapi_version.equals("2.0-alpha.1-1.0.0")) {
            OLDSTAPI = true;
        }
    }
}
