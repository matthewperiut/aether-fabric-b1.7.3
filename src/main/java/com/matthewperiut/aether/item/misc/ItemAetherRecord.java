package com.matthewperiut.aether.item.misc;

import net.minecraft.item.MusicDiscItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAetherRecord extends MusicDiscItem {
    String jukeboxMessage;

    public ItemAetherRecord(String s, String jukeboxMessage) {
        super(RetroItemAccess.allocateId(), s);
        this.jukeboxMessage = jukeboxMessage;
    }

    public boolean useOnBlock(ItemStack arg, PlayerEntity arg2, World arg3, int i, int j, int k, int l) {
        boolean result = super.useOnBlock(arg, arg2, arg3, i, j, k, l);
        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            if (FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT) {
                com.matthewperiut.aether.client.ClientHelper.setRecordOverlay(jukeboxMessage);
            }
        }
        return result;
    }
}
