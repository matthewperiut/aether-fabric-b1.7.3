package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateTorch;

import java.util.Random;

public class BurntTorch extends TemplateTorch {
    public BurntTorch(Identifier i) {
        super(i, 0);
    }

    @Override
    public void randomDisplayTick(World arg, int i, int j, int k, Random random) {

    }

    @Override
    public int getDropId(int i, Random random) {
        return Block.TORCH.id;
    }
}
