package com.matthewperiut.aether.item.misc;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.util.MoaColor;
import net.minecraft.item.ItemStack;

public class ItemMoaEgg extends Item {
    public ItemMoaEgg() {
        super(RetroItemAccess.allocateId());
        //this.setTexturePosition(ModLoader.addOverride("/gui/items.png", "/aether/items/MoaEgg.png"));
        this.setHasSubtypes(true);
    }

    public int getColorMultiplier(int damage) {
        return MoaColor.getColour(damage).colour;
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getDamage();
        if (i > MoaColor.colors.size() - 1) {
            i = MoaColor.colors.size() - 1;
        }

        return this.getTranslationKey() + i;
    }
}
