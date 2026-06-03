package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.periut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemRegenerationStone extends ItemMoreArmor {
    public ItemRegenerationStone(int j, int k, int l) {
        super(RetroItemAccess.allocateId(), j, k, l);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.age % 200 == 0) {
            int maxHealth = 20 + ((PlayerExtraHP) player).getExtraHP();
            if (player.health < maxHealth) {
                player.health += 1;
                itemInstance.damage(1, player);
                if (itemInstance.getDamage2() > itemInstance.getMaxDamage() - 2) {
                    itemInstance = null;
                }
            }
        }

        return itemInstance;
    }
}
