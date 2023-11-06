package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.Accessory;
import com.matthewperiut.accessoryapi.api.PlayerVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemInvisibilityCloak extends ItemMoreArmor implements Accessory
{
    public ItemInvisibilityCloak(Identifier i, int j, int k, int l)
    {
        super(i, j, k, l);
    }

    public void onAccessoryAdded(PlayerEntity player, ItemStack accessory)
    {
        ((PlayerVisibility) player).setInvisible(true);
    }

    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory)
    {
        ((PlayerVisibility) player).setInvisible(false);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance)
    {
        if (player.field_1645 % 300 == 0)
        {
            itemInstance.applyDamage(1, player);
            if (itemInstance.getDamage() > itemInstance.getDurability() - 2)
            {
                itemInstance = null;
            }
        }

        return itemInstance;
    }
}
