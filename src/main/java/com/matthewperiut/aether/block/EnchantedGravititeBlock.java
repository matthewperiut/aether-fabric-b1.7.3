package com.matthewperiut.aether.block;

import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.util.Identifier;

public class EnchantedGravititeBlock extends BlockFloating {
    public EnchantedGravititeBlock(Identifier i, int j, boolean bool) {
        super(i, j, bool);
    }

    public int getBaseColor(int i) {
        return 16755455;
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getBaseColor(iblockaccess.getBlockMeta(i, j, k));
    }
}
