package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AetherLeaves extends TemplateTranslucentBlock {
    public static int sprSkyroot;
    public static int sprGoldenOak;
    public boolean golden;
    int[] blocks;

    public AetherLeaves(Identifier identifier, boolean golden) {
        super(identifier, golden ? sprGoldenOak : sprSkyroot, Material.LEAVES, false);
        this.golden = golden;
        this.setTickRandomly(true);
    }

    @Override
    public int getTexture(int i) {
        return golden ? sprGoldenOak : sprSkyroot;
    }

    public int getDroppedItemCount(Random random) {
        if (this.id == AetherBlocks.GoldenOakLeaves.id) {
            return random.nextInt(60) != 0 ? 0 : 1;
        } else {
            return random.nextInt(40) != 0 ? 0 : 1;
        }
    }

    public int getDroppedItemId(int i, Random random) {
        if (this.id == AetherBlocks.SkyrootLeaves.id) {
            return AetherBlocks.SkyrootSapling.asItem().id;
        } else {
            return random.nextInt(10) == 0 ? Item.GOLDEN_APPLE.id : AetherBlocks.GoldenOakSapling.asItem().id;
        }
    }

    public void onBreak(World world, int i, int j, int k) {
        int l = 1;
        int i1 = l + 1;
        if (world.isRegionLoaded(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
            for (int j1 = -l; j1 <= l; ++j1) {
                for (int k1 = -l; k1 <= l; ++k1) {
                    for (int l1 = -l; l1 <= l; ++l1) {
                        int i2 = world.getBlockId(i + j1, j + k1, k + l1);
                        if (i2 == this.id) {
                            int j2 = world.getBlockMeta(i + j1, j + k1, k + l1);
                            world.setBlockMetaWithoutNotifyingNeighbors(i + j1, j + k1, k + l1, j2 | 8);
                        }
                    }
                }
            }
        }

    }

    public void onTick(World arg, int i, int j, int k, Random random) {
        if (!arg.isRemote) {
            int var6 = arg.getBlockMeta(i, j, k);
            if ((var6 & 8) != 0) {
                byte var7 = 4;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;
                if (this.blocks == null) {
                    this.blocks = new int[var9 * var9 * var9];
                }

                int var12;
                if (arg.isRegionLoaded(i - var8, j - var8, k - var8, i + var8, j + var8, k + var8)) {
                    var12 = -var7;

                    label111:
                    while (true) {
                        int var13;
                        int var14;
                        int var15;
                        if (var12 > var7) {
                            var12 = 1;

                            while (true) {
                                if (var12 > 4) {
                                    break label111;
                                }

                                for (var13 = -var7; var13 <= var7; ++var13) {
                                    for (var14 = -var7; var14 <= var7; ++var14) {
                                        for (var15 = -var7; var15 <= var7; ++var15) {
                                            if (this.blocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1) {
                                                if (this.blocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                                    this.blocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                                }

                                                if (this.blocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2) {
                                                    this.blocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                                }

                                                if (this.blocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2) {
                                                    this.blocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                                }

                                                if (this.blocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2) {
                                                    this.blocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                                }

                                                if (this.blocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2) {
                                                    this.blocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                                }

                                                if (this.blocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2) {
                                                    this.blocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                                }
                                            }
                                        }
                                    }
                                }

                                ++var12;
                            }
                        }

                        for (var13 = -var7; var13 <= var7; ++var13) {
                            for (var14 = -var7; var14 <= var7; ++var14) {
                                var15 = arg.getBlockId(i + var12, j + var13, k + var14);
                                if (var15 == AetherBlocks.Log.id) {
                                    this.blocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                } else if (var15 == AetherBlocks.SkyrootLeaves.id || var15 == AetherBlocks.GoldenOakLeaves.id) {
                                    this.blocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                } else {
                                    this.blocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }

                        ++var12;
                    }
                }

                var12 = this.blocks[var11 * var10 + var11 * var9 + var11];
                if (var12 >= 0) {
                    arg.setBlockMetaWithoutNotifyingNeighbors(i, j, k, var6 & -9);
                } else {
                    this.dropAndRemove(arg, i, j, k);
                }
            }

        }
    }

    private void dropAndRemove(World arg, int i, int j, int k) {
        this.dropStacks(arg, i, j, k, arg.getBlockMeta(i, j, k));
        arg.setBlock(i, j, k, 0);
    }

    protected int getDroppedItemMeta(int i) {
        return i & 3;
    }

    public boolean isOpaque() {
        return false;
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        if (!world.isRemote && entityplayer.getHand() != null && entityplayer.getHand().itemId == Item.SHEARS.id) {
            //entityplayer.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStack(world, i, j, k, new ItemStack(this.asItem().id, 1, l & 3));
        } else {
            super.afterBreak(world, entityplayer, i, j, k, l);
        }

    }

    public boolean isSideVisible(BlockView iblockaccess, int i, int j, int k, int l) {
        iblockaccess.getBlockId(i, j, k);
        return true;
    }
}