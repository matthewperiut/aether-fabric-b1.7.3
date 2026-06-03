package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;

import java.util.Random;

public class ZaniteOre extends Block {
    protected ZaniteOre() {
        super(RetroBlockAccess.allocateId(), Material.STONE);
    }

    public int getDroppedItemId(int i, Random random) {
        return AetherItems.Zanite.id;
    }
}
