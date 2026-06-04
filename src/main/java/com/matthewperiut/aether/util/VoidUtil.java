package com.matthewperiut.aether.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;

public class VoidUtil {
    public static void teleport(PlayerEntity p, double x, double y, double z) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            p.setPosition(x, y, z);
            p.setVelocityClient(0, 0, 0);
        } else {
            com.matthewperiut.aether.server.ServerHelper.teleport(p, x, y, z, p.yaw, p.pitch);
        }
    }
}
