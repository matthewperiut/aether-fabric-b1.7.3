package com.matthewperiut.aether;

import com.matthewperiut.aether.network.AerbunnyJumpPacket;
import com.matthewperiut.aether.network.MountInputPacket;
import com.matthewperiut.aether.optional.AetherRetroCommandsSupport;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Aether implements ModInitializer {

    public static boolean OLDSTAPI = false;

    @Override
    public void onInitialize() {
        MountInputPacket.register();
        AerbunnyJumpPacket.register();
        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {
            AetherRetroCommandsSupport.init();
        }
        String stapi_version = String.valueOf(FabricLoader.getInstance().getModContainer("station-api-base").get().getMetadata().getVersion());
        if (stapi_version.equals("2.0-alpha.2-1.0.0") || stapi_version.equals("2.0-alpha.1.1-1.0.0") || stapi_version.equals("2.0-alpha.1-1.0.0")) {
            OLDSTAPI = true;
        }
    }
}
