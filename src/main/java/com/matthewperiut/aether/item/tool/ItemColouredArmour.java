package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.item.util.CustomArmorTexture;
import com.periut.retroapi.register.item.RetroItemAccess;
import net.minecraft.item.ArmorItem;

public class ItemColouredArmour extends ArmorItem implements CustomArmorTexture {
    private final int color;
    private final String name;

    public ItemColouredArmour(final int j, final String s, final int l, final int col) {
        super(RetroItemAccess.allocateId(), j, 0, l);
        this.name = s;
        this.color = col;
    }

    @Override
    public int getColorMultiplier(int i) {
        return this.color;
    }

    @Override
    public String getArmorTexture(ArmorItem item, int layer) {
        return "/assets/" + Aether.MOD_ID + "/stationapi/textures/armor/" + name + "_" + (layer == 2 ? 2 : 1) + ".png";
    }
}
