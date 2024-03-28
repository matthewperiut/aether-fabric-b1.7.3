package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class QuicksoilGlass extends TemplateTranslucentBlock {
    public QuicksoilGlass(Identifier blockID) {
        super(blockID, 0, Material.GLASS, false);
        this.slipperiness = 1.05F;
    }

    public int getDropCount(Random random) {
        return 0;
    }

    public int getRenderPass() {
        return 1;
    }

    public boolean isSideRendered(BlockView iblockaccess, int i, int j, int k, int l) {
        return super.isSideRendered(iblockaccess, i, j, k, 1 - l);
    }

    public int getBaseColor(int i) {
        return 16776960;
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getBaseColor(iblockaccess.getBlockMeta(i, j, k));
    }
}
