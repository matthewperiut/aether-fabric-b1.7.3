package com.matthewperiut.aether.block;

import net.minecraft.block.TorchBlock;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class BurntTorch extends TorchBlock {
    public BurntTorch() {
        super(RetroBlockAccess.allocateId(), 0);
    }

    @Override
    public void randomDisplayTick(World arg, int i, int j, int k, Random random) {

    }

    @Override
    public int getDroppedItemId(int i, Random random) {
        return Block.TORCH.id;
    }
}
