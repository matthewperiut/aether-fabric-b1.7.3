package com.matthewperiut.aether.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.List;

public class ChatUtil {
    public static void chatLine(String s) {
        SideUtil.run(() -> chatLineClient(s), () -> chatLineServer(s));
    }

    @Environment(EnvType.CLIENT)
    public static void chatLineClient(String s) {
        Minecraft mc = ((Minecraft) FabricLoader.getInstance().getGameInstance());
        mc.inGameHud.addChatMessage(s);
    }

    @Environment(EnvType.SERVER)
    public static void chatLineServer(String s) {
        int areaOfEffect = 50;
        Minecraft mc = ((Minecraft) FabricLoader.getInstance().getGameInstance());
        World world = mc.world;
        Entity PlayerEntity = (Entity) mc.player;
        List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(PlayerEntity.x - areaOfEffect, PlayerEntity.y - areaOfEffect, PlayerEntity.z - 0.5 - areaOfEffect, PlayerEntity.x + areaOfEffect, PlayerEntity.y + areaOfEffect, PlayerEntity.z - 0.5 + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            ((ServerPlayerEntity) player).sendMessage(s);
        }
    }
}