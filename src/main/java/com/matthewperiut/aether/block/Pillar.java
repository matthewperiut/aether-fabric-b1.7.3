package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.material.Material;

public class Pillar extends Block {
    public static int sprTop;
    public static int sprSide;
    public static int sprTopSide;

    protected Pillar() {
        super(RetroBlockAccess.allocateId(), Material.STONE);
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
