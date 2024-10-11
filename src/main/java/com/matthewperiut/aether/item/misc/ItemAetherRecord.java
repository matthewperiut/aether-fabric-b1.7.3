package com.matthewperiut.aether.item.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateMusicDiscItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemAetherRecord extends TemplateMusicDiscItem {
    String jukeboxMessage;

    public ItemAetherRecord(Identifier i, String s, String jukeboxMessage) {
        super(i, s);
        this.jukeboxMessage = jukeboxMessage;
    }

    public boolean useOnBlock(ItemStack arg, PlayerEntity arg2, World arg3, int i, int j, int k, int l) {
        boolean result = super.useOnBlock(arg, arg2, arg3, i, j, k, l);
        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            ((Minecraft) FabricLoader.getInstance().getGameInstance()).inGameHud.setRecordPlayingOverlay(jukeboxMessage);
        }
        return result;
    }
}
