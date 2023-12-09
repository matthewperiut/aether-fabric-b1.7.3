package com.matthewperiut.aether.item.accessory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemAgilityCape extends ItemCape {
    public ItemAgilityCape(Identifier i, int j, String path, int l) {
        super(i, j, path, l);
    }


    @Override
    public void onAccessoryAdded(PlayerEntity player, ItemStack accessory) {
        player.field_1641 = 1.0f;
    }

    @Override
    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        player.field_1641 = 0.5f;
    }
}
