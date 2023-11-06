package com.matthewperiut.aether.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.entity.player.ServerPlayerEntity;

public class VoidUtil {
    public static void teleport(PlayerEntity p, double x, double y, double z) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            p.setPosition(x, y, z);
            p.setVelocity(0, 0, 0);
        } else {
            ServerPlayerEntity sp = (ServerPlayerEntity) p;
            sp.packetHandler.method_832(x, y, z, p.yaw, p.pitch);
        }
    }
}
