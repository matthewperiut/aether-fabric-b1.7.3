package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class Pillar extends TemplateBlockBase implements MetaNamedBlockItemProvider {
    public static int sprTop;
    public static int sprSide;
    public static int sprTopSide;

    protected Pillar(Identifier i) {
        super(i, Material.STONE);
    }

    public int getTextureForSide(int i, int j) {
        if (i != 0 && i != 1) {
            return j == 0 ? sprSide : sprTopSide;
        } else {
            return sprTop;
        }
    }

    protected int droppedMeta(int i) {
        return i;
    }

    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
