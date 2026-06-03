package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.periut.accessoryapi.api.Accessory;
import com.periut.accessoryapi.api.PlayerVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemInvisibilityCloak extends ItemMoreArmor implements Accessory {
    public ItemInvisibilityCloak(int j, int k, int l) {
        super(RetroItemAccess.allocateId(), j, k, l);
    }

    public void onAccessoryAdded(PlayerEntity player, ItemStack accessory) {
        ((PlayerVisibility) player).setInvisible(true);
    }

    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        ((PlayerVisibility) player).setInvisible(false);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.age % 300 == 0) {
            itemInstance.damage(1, player);
            if (itemInstance.getDamage2() > itemInstance.getMaxDamage() - 2) {
                itemInstance = null;
            }
        }

        return itemInstance;
    }
}
