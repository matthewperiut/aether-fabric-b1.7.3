package com.matthewperiut.aether.item.tool;

import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmourTextureProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.armour.TemplateArmour;

import static com.matthewperiut.aether.item.AetherItems.MOD_ID;

public class ItemColouredArmor extends TemplateArmour implements ArmourTextureProvider {
    private final int colour;
    private final String name;

    public ItemColouredArmor(final Identifier i, final int j, final String s, final int l, final int col) {
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