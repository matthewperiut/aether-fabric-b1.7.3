package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

public class ZaniteBlock extends TemplateBlock {
    protected ZaniteBlock(Identifier blockID, int j) {
        super(blockID, j, Material.STONE);
    }

    public int getBaseColor(int i) {
        return 10066431;
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getBaseColor(iblockaccess.getBlockMeta(i, j, k));
    }
}
