package com.matthewperiut.aether.item.misc;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.item.ItemStack;

public class ItemDart extends Item {
    public static int sprGolden;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartGolden.png");
    public static int sprEnchanted;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartEnchanted.png");
    public static int sprPoison;// = ModLoader.addOverride("/gui/items.png", "/aether/items/DartPoison.png");

    public ItemDart() {
        super(RetroItemAccess.allocateId());
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
