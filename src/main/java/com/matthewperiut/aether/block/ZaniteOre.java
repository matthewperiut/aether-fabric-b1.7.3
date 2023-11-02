package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class ZaniteOre extends TemplateBlockBase {
    protected ZaniteOre(Identifier blockID) {
        super(blockID, Material.STONE);
    }

    public int getDropId(int i, Random random) {
        return AetherItems.Zanite.id;
    }
}
