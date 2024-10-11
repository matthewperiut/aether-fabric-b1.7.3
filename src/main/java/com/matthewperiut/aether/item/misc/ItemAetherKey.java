package com.matthewperiut.aether.item.misc;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemAetherKey extends TemplateItem {
    public ItemAetherKey(Identifier itemID) {
        super(itemID);
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
