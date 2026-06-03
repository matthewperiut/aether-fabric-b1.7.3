package com.matthewperiut.aether.client;

import com.matthewperiut.aether.client.entity.AetherEntityRenderers;
import com.matthewperiut.aether.client.gui.AetherGuis;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;

public class AetherClient implements ClientModInitializer {

    @Override
    public void initClient() {
        AetherEntityRenderers.registerRenderers();
        AetherGuis.registerGuis();
    }
}
