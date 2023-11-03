package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class GenGoldenOak extends Feature {
    public GenGoldenOak() {
    }

    public void branch(World world, Random random, int i, int j, int k, int slant) {
        int directionX = random.nextInt(3) - 1;
        int directionZ = random.nextInt(3) - 1;

        for (int n = 0; n < random.nextInt(2) + 1; ++n) {
            i += directionX;
            j += slant;
            k += directionZ;
            if (world.getBlockId(i, j, k) == AetherBlocks.GoldenOakLeaves.id) {
                world.setBlockWithMetadata(i, j, k, AetherBlocks.Log.id, 2);
            }
        }

    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        if (world.getBlockId(i, j - 1, k) != AetherBlocks.Grass.id && world.getBlockId(i, j - 1, k) != AetherBlocks.Dirt.id) {
            return false;
        } else {
            int height = random.nextInt(5) + 6;

            int x;
            for (x = i - 3; x < i + 4; ++x) {
                for (int y = j + 5; y < j + 12; ++y) {
                    for (int z = k - 3; z < k + 4; ++z) {
                        if ((x - i) * (x - i) + (y - j - 8) * (y - j - 8) + (z - k) * (z - k) < 12 + random.nextInt(5) && world.getBlockId(x, y, z) == 0) {
                            world.setBlockInChunk(x, y, z, AetherBlocks.GoldenOakLeaves.id);
                        }
                    }
                }
            }

            for (x = 0; x < height; ++x) {
                if (x > 4 && random.nextInt(3) > 0) {
                    this.branch(world, random, i, j + x, k, x / 4 - 1);
                }

                world.setBlockWithMetadata(i, j + x, k, AetherBlocks.Log.id, 2);
            }

            return true;
        }
    }
}