package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Aerogel extends TemplateBlock {
    public Aerogel(Identifier identifier) {
        super(identifier, Material.STONE);
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
