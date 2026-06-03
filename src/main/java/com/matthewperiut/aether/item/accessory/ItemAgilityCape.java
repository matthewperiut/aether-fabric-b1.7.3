package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemAgilityCape extends ItemCape {
    public ItemAgilityCape(int j, String path, int l) {
        super(j, path, l);
    }


    @Override
    public void onAccessoryAdded(PlayerEntity player, ItemStack accessory) {
        player.stepHeight = 1.0f;
    }

    @Override
    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        player.stepHeight = 0.5f;
    }
}
