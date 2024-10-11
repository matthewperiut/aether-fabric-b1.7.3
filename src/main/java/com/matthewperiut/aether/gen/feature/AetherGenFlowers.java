package com.matthewperiut.aether.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import java.util.Random;

public class AetherGenFlowers extends Feature {
    private final int plantBlockId;

    public AetherGenFlowers(int i) {
        this.plantBlockId = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isAir(i1, j1, k1) && Block.BLOCKS[this.plantBlockId].canGrow(world, i1, j1, k1)) {
                world.setBlockWithoutNotifyingNeighbors(i1, j1, k1, this.plantBlockId);
            }
        }

        return true;
    }
}
