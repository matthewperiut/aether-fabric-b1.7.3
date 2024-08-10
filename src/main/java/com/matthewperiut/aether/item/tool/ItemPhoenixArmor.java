package com.matthewperiut.aether.item.tool;

import com.matthewperiut.accessoryapi.api.TickableInArmorSlot;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemPhoenixArmor extends ItemColoredArmor implements TickableInArmorSlot {
    public ItemPhoenixArmor(Identifier i, int j, String s, int l, int col) {
        super(i, j, s, l, col);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.isSubmergedInWater()) {
            itemInstance.damage(1, player);
        }

        if (itemInstance.getDamage() > itemInstance.getMaxDamage() - 2) {
            switch (equipmentSlot) {
                case 0 -> {
                    return new ItemStack(AetherItems.ObsidianHelm, 1);
                }
                case 1 -> {
                    return new ItemStack(AetherItems.ObsidianBody, 1);
                }
                case 2 -> {
                    return new ItemStack(AetherItems.ObsidianLegs, 1);
                }
                case 3 -> {
                    return new ItemStack(AetherItems.ObsidianBoots, 1);
                }
            }
        }

        return itemInstance;
    }
}
