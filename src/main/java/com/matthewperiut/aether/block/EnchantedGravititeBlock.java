package com.matthewperiut.aether.block;

import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.world.BlockView;

public class EnchantedGravititeBlock extends BlockFloating {
    public EnchantedGravititeBlock(int j, boolean bool) {
        super(j, bool);
    }

    public int getColor(int i) {
        return 16755455;
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getColor(iblockaccess.getBlockMeta(i, j, k));
    }
}
