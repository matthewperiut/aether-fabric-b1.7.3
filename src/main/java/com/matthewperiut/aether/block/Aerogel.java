package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;

public class Aerogel extends Block {
    public Aerogel() {
        super(RetroBlockAccess.allocateId(), Material.STONE);
    }

    public boolean isOpaque() {
        return false;
    }

    public int getRenderLayer() {
        return 1;
    }

    public boolean isSideVisible(BlockView iblockaccess, int i, int j, int k, int l) {
        return super.isSideVisible(iblockaccess, i, j, k, 1 - l);
    }
}
