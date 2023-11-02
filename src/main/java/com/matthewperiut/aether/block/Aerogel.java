package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class Aerogel extends TemplateBlockBase {
    public Aerogel(Identifier identifier) {
        super(identifier, Material.STONE);
    }

    public boolean isFullOpaque() {
        return false;
    }

    public int getRenderPass() {
        return 1;
    }

    public boolean isSideRendered(BlockView iblockaccess, int i, int j, int k, int l) {
        return super.isSideRendered(iblockaccess, i, j, k, 1 - l);
    }
}
