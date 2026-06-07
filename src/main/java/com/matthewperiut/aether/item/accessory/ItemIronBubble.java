package com.matthewperiut.aether.item.accessory;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemIronBubble extends ItemMoreArmor {
    public ItemIronBubble(int j, int k, int l) {
        super(j, k, l);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity playerBase, ItemStack itemInstance) {
        if (playerBase.isInFluid(Material.WATER)) {
            playerBase.air = 20;
            if (playerBase.age % 80 == 0) {
                itemInstance.damage(1, playerBase);
                if (itemInstance.getDamage2() > itemInstance.getMaxDamage() - 2) {
                    itemInstance = null;
                }
            }
        }
        return itemInstance;
    }
}
