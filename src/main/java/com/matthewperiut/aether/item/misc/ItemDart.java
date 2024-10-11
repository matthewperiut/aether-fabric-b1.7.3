package com.matthewperiut.aether.item.misc;

import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemDart extends TemplateItem {
    public static int sprGolden;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartGolden.png");
    public static int sprEnchanted;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartEnchanted.png");
    public static int sprPoison;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartPoison.png");

    public ItemDart(Identifier itemID) {
        super(itemID);
        this.setHasSubtypes(true);
    }

    public int getTextureId(int damage) {
        if (damage == 0) {
            return sprGolden;
        } else if (damage == 1) {
            return sprPoison;
        } else {
            return damage == 2 ? sprEnchanted : sprGolden;
        }
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getDamage();
        if (i > 2) {
            i = 2;
        }

        return this.getTranslationKey() + i;
    }
}
