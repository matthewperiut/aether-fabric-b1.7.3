package com.matthewperiut.aether.gen;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class GenSkyroot extends Feature {
    public GenSkyroot() {
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        int l = random.nextInt(3) + 4;
        boolean flag = true;
        if (j >= 1 && j + l + 1 <= 128) {
            int i1;
            int k1;
            int j2;
            int i3;
            int k3;
            for (i1 = j; i1 <= j + 1 + l; ++i1) {
                k1 = 1;
                if (i1 == j) {
                    k1 = 0;
                }

                if (i1 >= j + 1 + l - 2) {
                    k1 = 2;
                }

                for (j2 = i - k1; j2 <= i + k1 && flag; ++j2) {
                    for (i3 = k - k1; i3 <= k + k1 && flag; ++i3) {
                        if (i1 >= 0 && i1 < 128) {
                            k3 = world.getBlockId(j2, i1, i3);
                            if (k3 != 0 && k3 != AetherBlocks.SkyrootLeaves.id) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            } else {
                i1 = world.getBlockId(i, j - 1, k);
                if ((i1 == AetherBlocks.Grass.id || i1 == AetherBlocks.Dirt.id) && j < 128 - l - 1) {
                    world.setBlockInChunk(i, j - 1, k, AetherBlocks.Dirt.id);

                    for (k1 = j - 3 + l; k1 <= j + l; ++k1) {
                        j2 = k1 - (j + l);
                        i3 = 1 - j2 / 2;

                        for (k3 = i - i3; k3 <= i + i3; ++k3) {
                            int l3 = k3 - i;

                            for (int i4 = k - i3; i4 <= k + i3; ++i4) {
                                int j4 = i4 - k;
                                if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !Block.FULL_OPAQUE[world.getBlockId(k3, k1, i4)]) {
                                    world.setBlockInChunk(k3, k1, i4, AetherBlocks.SkyrootLeaves.id);
                                }
                            }
                        }
                    }

                    for (k1 = 0; k1 < l; ++k1) {
                        j2 = world.getBlockId(i, j + k1, k);
                        if (j2 == 0 || j2 == AetherBlocks.SkyrootLeaves.id) {
                            world.setBlockWithMetadata(i, j + k1, k, AetherBlocks.Log.id, 0);
                        }
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
