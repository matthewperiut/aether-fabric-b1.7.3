package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemRegenerationStone extends ItemMoreArmor {
    public ItemRegenerationStone(Identifier i, int j, int k, int l) {
        super(i, j, k, l);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.field_1645 % 200 == 0) {
            int maxHealth = 20 + ((PlayerExtraHP) player).getExtraHP();
            if (player.health < maxHealth) {
                player.health += 1;
                itemInstance.applyDamage(1, player);
                if (itemInstance.getDamage() > itemInstance.getDurability() - 2) {
                    itemInstance = null;
                }
            }
        }

        return itemInstance;
    }
}
