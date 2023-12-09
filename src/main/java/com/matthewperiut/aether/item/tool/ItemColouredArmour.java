package com.matthewperiut.aether.item.tool;

import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.item.AetherItems.MOD_ID;

public class ItemColouredArmour extends TemplateArmorItem implements ArmorTextureProvider {
    private final int colour;
    private final String name;

    public ItemColouredArmour(final Identifier i, final int j, final String s, final int l, final int col) {
        super(i, j, 0, l);
        this.name = s;
        this.colour = col;
    }

    @Override
    public int getNameColor(int i) {
        return this.colour;
    }

    @Override
    public Identifier getTexture(ArmorItem armorItem) {
        return Identifier.of(MOD_ID, name);
    }
}
