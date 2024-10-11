package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakeFeature;
import net.minecraft.world.gen.feature.PlantPatchFeature;
import java.util.Random;

public class AetherGenGumdrop extends Feature {
    public AetherGenGumdrop() {
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        return this.func_100009_a(world, random, i, j, k, 24);
    }

    public boolean func_100009_a(World world, Random random, int i, int j, int k, int l) {
        if (j - l <= 0) {
            j = l + 1;
        }

        if (j + l >= 116) {
            j = 116 - l - 1;
        }

        int i1 = 0;
        int j1 = MathHelper.floor((double) l * 0.875);

        int i2;
        int l2;
        for (int k1 = -j1; k1 <= j1; ++k1) {
            for (i2 = l; i2 >= -j1; --i2) {
                for (l2 = -j1; l2 <= j1; ++l2) {
                    if (!AetherBlocks.isGood(world.getBlockId(k1 + i, i2 + j, l2 + k), world.getBlockMeta(k1 + i, i2 + j, l2 + k))) {
                        ++i1;
                        if (i1 > l / 2) {
                            return false;
                        }
                    }
                }
            }
        }

        float f = 0.8F;

        int i3;
        int k3;
        int i4;
        int k4;
        int j5;
        for (i2 = -l; i2 <= l; ++i2) {
            for (l2 = l; l2 >= -l; --l2) {
                for (i3 = -l; i3 <= l; ++i3) {
                    k3 = MathHelper.floor((double) i2 * (1.0 + (double) l2 / ((double) l * 10.0)) / (double) f);
                    i4 = l2;
                    if ((double) l2 > (double) l * 0.625) {
                        i4 = MathHelper.floor((double) l2 * 1.375);
                        i4 -= MathHelper.floor((double) l * 0.25);
                    } else if ((double) l2 < (double) l * -0.625) {
                        i4 = MathHelper.floor((double) l2 * 1.350000023841858);
                        i4 += MathHelper.floor((double) l * 0.25);
                    }

                    k4 = MathHelper.floor((double) i3 * (1.0 + (double) l2 / ((double) l * 10.0)) / (double) f);
                    if (Math.sqrt(k3 * k3 + i4 * i4 + k4 * k4) <= (double) l) {
                        if (AetherBlocks.isGood(world.getBlockId(i2 + i, l2 + j + 1, i3 + k), world.getBlockMeta(i2 + i, l2 + j + 1, i3 + k)) && (double) l2 > (double) MathHelper.floor((double) l / 5.0)) {
                            world.setBlockWithoutNotifyingNeighbors(i2 + i, l2 + j, i3 + k, AetherBlocks.Grass.id);
                            world.setBlockWithoutNotifyingNeighbors(i2 + i, l2 + j - 1, i3 + k, AetherBlocks.Dirt.id);
                            world.setBlockWithoutNotifyingNeighbors(i2 + i, l2 + j - (1 + random.nextInt(2)), i3 + k, AetherBlocks.Dirt.id);
                            if (l2 >= l / 2) {
                                j5 = random.nextInt(48);
                                if (j5 < 2) {
                                    (new AetherGenGoldenOak()).generate(world, random, i2 + i, l2 + j + 1, i3 + k);
                                } else if (j5 == 3) {
                                    if (random.nextInt(2) == 0) {
                                        (new LakeFeature(Block.WATER.id)).generate(world, random, i2 + i + random.nextInt(3) - random.nextInt(3), l2 + j, i3 + k + random.nextInt(3) - random.nextInt(3));
                                    }
                                } else if (j5 == 4) {
                                    if (random.nextInt(2) == 0) {
                                        (new PlantPatchFeature(Block.DANDELION.id)).generate(world, random, i2 + i + random.nextInt(3) - random.nextInt(3), l2 + j + 1, i3 + k + random.nextInt(3) - random.nextInt(3));
                                    } else {
                                        (new PlantPatchFeature(Block.ROSE.id)).generate(world, random, i2 + i + random.nextInt(3) - random.nextInt(3), l2 + j + 1, i3 + k + random.nextInt(3) - random.nextInt(3));
                                    }
                                }
                            }
                        } else if (AetherBlocks.isGood(world.getBlockId(i2 + i, l2 + j, i3 + k), world.getBlockMeta(i2 + i, l2 + j, i3 + k))) {
                            world.setBlockWithoutNotifyingNeighbors(i2 + i, l2 + j, i3 + k, AetherBlocks.Holystone.id, 0);
                        }
                    }
                }
            }
        }

        i2 = 8 + random.nextInt(5);
        float f1 = 0.01745329F;

        int j6;
        for (i3 = 0; i3 < i2; ++i3) {
            float f2 = random.nextFloat() * 360.0F;
            float f3 = (random.nextFloat() * 0.125F + 0.7F) * (float) l;
            k4 = i + MathHelper.floor(Math.cos(f1 * f2) * (double) f3);
            j5 = j - MathHelper.floor((double) l * (double) random.nextFloat() * 0.3);
            j6 = k + MathHelper.floor(-Math.sin(f1 * f2) * (double) f3);
            this.func_100008_b(world, random, k4, j5, j6, MathHelper.floor((double) l / 3.0), true);
        }

        boolean flag = false;
        (new AetherGenDungeon()).generate(world, random, i, j, k, j1);
        k3 = MathHelper.floor((double) l * 0.75);

        for (i4 = 0; i4 < k3; ++i4) {
            k4 = i + random.nextInt(l) - random.nextInt(l);
            j5 = j + random.nextInt(l) - random.nextInt(l);
            j6 = k + random.nextInt(l) - random.nextInt(l);
            (new AetherGenGumdropCaves(0, 24 + k3 / 3)).generate(world, random, k4, j5, j6);
        }

        return true;
    }

    public boolean func_100008_b(World world, Random random, int i, int j, int k, int l, boolean flag) {
        if (j - l <= 0) {
            j = l + 1;
        }

        if (j + l >= 127) {
            j = 127 - l - 1;
        }

        float f = 1.0F;

        int i1;
        int k1;
        int i2;
        int k2;
        int i3;
        for (i1 = -l; i1 <= l; ++i1) {
            for (k1 = l; k1 >= -l; --k1) {
                for (i2 = -l; i2 <= l; ++i2) {
                    k2 = MathHelper.floor((double) i1 / (double) f);
                    i3 = k1;
                    if ((double) k1 > (double) l * 0.625) {
                        i3 = MathHelper.floor((double) k1 * 1.375);
                        i3 -= MathHelper.floor((double) l * 0.25);
                    } else if ((double) k1 < (double) l * -0.625) {
                        i3 = MathHelper.floor((double) k1 * 1.350000023841858);
                        i3 += MathHelper.floor((double) l * 0.25);
                    }

                    int k3 = MathHelper.floor((double) i2 / (double) f);
                    if (Math.sqrt(k2 * k2 + i3 * i3 + k3 * k3) <= (double) l) {
                        if (AetherBlocks.isGood(world.getBlockId(i1 + i, k1 + j + 1, i2 + k), world.getBlockMeta(i1 + i, k1 + j + 1, i2 + k)) && (double) k1 > (double) MathHelper.floor((double) l / 5.0)) {
                            world.setBlockWithoutNotifyingNeighbors(i1 + i, k1 + j, i2 + k, AetherBlocks.Grass.id);
                            world.setBlockWithoutNotifyingNeighbors(i1 + i, k1 + j - 1, i2 + k, AetherBlocks.Dirt.id);
                            world.setBlockWithoutNotifyingNeighbors(i1 + i, k1 + j - (1 + random.nextInt(2)), i2 + k, AetherBlocks.Dirt.id);
                            if (k1 >= l / 2) {
                                int l3 = random.nextInt(64);
                                if (l3 == 0) {
                                    (new AetherGenGoldenOak()).generate(world, random, i1 + i, k1 + j + 1, i2 + k);
                                } else if (l3 == 5 && random.nextInt(3) == 0) {
                                    (new LakeFeature(Block.WATER.id)).generate(world, random, i1 + i + random.nextInt(3) - random.nextInt(3), k1 + j, i2 + k + random.nextInt(3) - random.nextInt(3));
                                }
                            }
                        } else if (AetherBlocks.isGood(world.getBlockId(i1 + i, k1 + j, i2 + k), world.getBlockMeta(i1 + i, k1 + j, i2 + k))) {
                            world.setBlockWithoutNotifyingNeighbors(i1 + i, k1 + j, i2 + k, AetherBlocks.Holystone.id, 0);
                        }
                    }
                }
            }
        }

        if (!flag) {
            i1 = MathHelper.floor((double) l * 1.25);

            for (k1 = 0; k1 < i1; ++k1) {
                i2 = i + random.nextInt(l) - random.nextInt(l);
                k2 = j + random.nextInt(l) - random.nextInt(l);
                i3 = k + random.nextInt(l) - random.nextInt(l);
                (new AetherGenGumdropCaves(0, 16 + i1 / 3)).generate(world, random, i2, k2, i3);
            }
        }

        return true;
    }
}
