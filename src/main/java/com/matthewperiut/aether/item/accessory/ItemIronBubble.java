package com.matthewperiut.aether.item.accessory;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemIronBubble extends ItemMoreArmor {
    public ItemIronBubble(Identifier i, int j, int k, int l) {
        super(i, j, k, l);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity playerBase, ItemStack itemInstance) {
        if (playerBase.isInFluid(Material.WATER)) {
            playerBase.air = 20;
            if (playerBase.field_1645 % 80 == 0) {
                itemInstance.applyDamage(1, playerBase);
                if (itemInstance.getDamage() > itemInstance.getDurability() - 2) {
                    itemInstance = null;
                }
            }
        }
        return itemInstance;
    }
}
