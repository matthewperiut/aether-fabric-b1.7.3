package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Pillar extends TemplateBlock implements MetaNamedBlockItemProvider {
    public static int sprTop;
    public static int sprSide;
    public static int sprTopSide;

    protected Pillar(Identifier i) {
        super(i, Material.STONE);
    }

    public int getTexture(int i, int j) {
        if (i != 0 && i != 1) {
            return j == 0 ? sprSide : sprTopSide;
        } else {
            return sprTop;
        }
    }

    protected int getDroppedItemMeta(int i) {
        return i;
    }

    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
