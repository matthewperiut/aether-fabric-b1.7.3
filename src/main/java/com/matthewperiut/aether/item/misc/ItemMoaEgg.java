package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.util.MoaColor;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemMoaEgg extends TemplateItem {
    public ItemMoaEgg(Identifier itemID) {
        super(itemID);
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
