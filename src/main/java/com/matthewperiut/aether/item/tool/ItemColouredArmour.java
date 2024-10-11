package com.matthewperiut.aether.item.tool;

import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.item.AetherItems.MOD_ID;

public class ItemColouredArmour extends TemplateArmorItem implements ArmorTextureProvider {
    private final int color;
    private final String name;

    public ItemColouredArmour(final Identifier i, final int j, final String s, final int l, final int col) {
        super(i, j, 0, l);
        this.name = s;
        this.color = col;
    }

    @Override
    public int getColorMultiplier(int i) {
        return this.color;
    }

    @Override
    public Identifier getTexture(ArmorItem armorItem) {
        return Identifier.of(MOD_ID, name);
    }
}
