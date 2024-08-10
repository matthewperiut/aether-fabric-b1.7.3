package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateTorchBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BurntTorch extends TemplateTorchBlock {
    public BurntTorch(Identifier i) {
        super(i, 0);
    }

    @Override
    public void randomDisplayTick(World arg, int i, int j, int k, Random random) {

    }

    @Override
    public int getDroppedItemId(int i, Random random) {
        return Block.TORCH.id;
    }
}
