package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;

public class ZaniteBlock extends Block {
    protected ZaniteBlock(int j) {
        super(RetroBlockAccess.allocateId(), j, Material.STONE);
    }

    public int getColor(int i) {
        return 10066431;
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getColor(iblockaccess.getBlockMeta(i, j, k));
    }
}
