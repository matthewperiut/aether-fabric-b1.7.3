package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Aerogel extends TemplateBlock {
    public Aerogel(Identifier identifier) {
        super(identifier, Material.STONE);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getRenderLayer() {
        return 1;
    }

    @Override
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        return super.isSideVisible(blockView, x, y, z, 1 - side);
    }
}
