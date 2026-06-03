package com.matthewperiut.aether.item.misc;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.item.ItemStack;

public class ItemAetherKey extends Item {
    public ItemAetherKey() {
        super(RetroItemAccess.allocateId());
        this.setHasSubtypes(true);
        this.maxCount = 1;
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getDamage();
        if (i > 2) {
            i = 2;
        }

        return this.getTranslationKey() + i;
    }

    public int getColorMultiplier(int damage) {
        if (damage == 1) {
            return -6710887;
        } else {
            return damage == 2 ? -13312 : -7638187;
        }
    }
}
