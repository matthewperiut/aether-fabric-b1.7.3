package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.util.MoaColour;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemMoaEgg extends TemplateItemBase {
    public ItemMoaEgg(Identifier itemID) {
        super(itemID);
        //this.setTexturePosition(ModLoader.addOverride("/gui/items.png", "/aether/items/MoaEgg.png"));
        this.setHasSubItems(true);
    }

    public int getNameColor(int damage) {
        return MoaColour.getColour(damage).colour;
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getMeta();
        if (i > MoaColour.colours.size() - 1) {
            i = MoaColour.colours.size() - 1;
        }

        return this.getTranslationKey() + i;
    }
}
