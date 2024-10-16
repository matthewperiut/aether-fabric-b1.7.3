package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ZaniteOre extends TemplateBlock {
    protected ZaniteOre(Identifier blockID) {
        super(blockID, Material.STONE);
    }

    public int getDroppedItemId(int i, Random random) {
        return AetherItems.Zanite.id;
    }
}
