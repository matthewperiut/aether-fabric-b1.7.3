package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.TreasureChest;
import com.matthewperiut.aether.entity.living.EntityFireMonster;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class AetherGenDungeon extends Feature {
    public int xoff;
    public int yoff;
    public int zoff;
    public int rad;
    public int truey;

    public AetherGenDungeon() {
    }

    private int floors() {
        return Block.DOUBLE_STONE_SLAB.id;
    }

    private int walls() {
        return AetherBlocks.LockedDungeonStone.id;
    }

    private int ceilings() {
        return AetherBlocks.LockedLightDungeonStone.id;
    }

    private int torches() {
        return 0;
    }

    private int doors() {
        return 0;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        return this.generate(world, random, x, y, z, 24);
    }

    public boolean generate(World world, Random random, int x, int y, int z, int r) {
        r = (int) Math.floor((double) r * 0.8);
        int wid = (int) Math.sqrt(r * r / 2);

        int j;
        for (int i1 = 4; i1 > -5; --i1) {
            int a = wid;
            if (i1 >= 3 || i1 <= -3) {
                --a;
            }
            if (i1 == 4 || i1 == -4) {
                --a;
            }
            for (int i = -a; i <= a; ++i) {
                for (int k = -a; k <= a; ++k) {
                    if (i1 == 4) {
                        this.setBlock(world, random, x + i, y + i1, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                    } else if (i1 > -4) {
                        if (i == a || -i == a || k == a || -k == a) {
                            this.setBlock(world, random, x + i, y + i1, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                        } else {
                            world.setBlockInChunk(x + i, y + i1, z + k, 0);
                            if (i1 == -2 && (i == a - 1 || -i == a - 1 || k == a - 1 || -k == a - 1) && (i % 3 == 0 || k % 3 == 0)) {
                                world.setBlockInChunk(x + i, y + i1 + 2, z + k, this.torches());
                            }
                        }
                    } else {
                        this.setBlock(world, random, x + i, y + i1, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                        if ((i == a - 2 || -i == a - 2) && (k == a - 2 || -k == a - 2)) {
                            world.setBlockInChunk(x + i, y + i1 + 1, z + k, Block.NETHERRACK.id);
                            world.setBlockInChunk(x + i, y + i1 + 2, z + k, Block.FIRE.id);
                        }
                    }
                }
            }
        }

        int i;
        j = random.nextInt(4);

        for (i = wid; i < wid + 32; ++i) {
            boolean flag = false;

            for (j = -3; j < 2; ++j) {
                for (int k = -3; k < 4; ++k) {
                    int a;
                    int b;
                    if (j / 2 == 0) {
                        a = i;
                        b = k;
                    } else {
                        a = k;
                        b = i;
                    }

                    if (j % 2 == 0) {
                        a = -a;
                        b = -b;
                    }

                    if (!AetherBlocks.isGood(world.getBlockId(x + a, y + j, z + b), world.getBlockMeta(x + a, y + j, z + b))) {
                        flag = true;
                        if (j == -3) {
                            this.setBlock(world, random, x + a, y + j, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                        } else if (j < 1) {
                            if (i == wid) {
                                if (k < 2 && k > -2 && j < 0) {
                                    world.setBlockInChunk(x + a, y + j, z + b, this.doors());
                                } else {
                                    this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                }
                            } else if (k != 3 && k != -3) {
                                world.setBlockInChunk(x + a, y + j, z + b, 0);
                                if (j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0) {
                                    world.setBlockInChunk(x + a, y + j, z + b, this.torches());
                                }
                            } else {
                                this.setBlock(world, random, x + a, y + j, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                            }
                        } else if (i == wid) {
                            this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 5);
                        } else {
                            this.setBlock(world, random, x + a, y + j, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                        }
                    }

                    a = -a;
                    b = -b;
                    if (i < wid + 6) {
                        if (j == -3) {
                            this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                        } else if (j < 1) {
                            if (i == wid) {
                                if (k < 2 && k > -2 && j < 0) {
                                    this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                } else {
                                    this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                }
                            } else if (i == wid + 5) {
                                this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                            } else if (i == wid + 4 && k == 0 && j == -2) {
                                TreasureChest.PlaceTreasureChest(world, x, y, z, 3);
                            } else if (k != 3 && k != -3) {
                                world.setBlockInChunk(x + a, y + j, z + b, 0);
                                if (j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0) {
                                    world.setBlockInChunk(x + a, y + j, z + b, this.torches());
                                }
                            } else {
                                this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                            }
                        } else {
                            this.setBlock(world, random, x + a, y + j, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                        }
                    }
                }
            }

            if (!flag) {
                break;
            }
        }

        EntityFireMonster boss = new EntityFireMonster(world, x, y - 1, z, wid, j);
        world.spawnEntity(boss);
        return true;
    }

    private void setBlock(World world, Random random, int i, int j, int k, int block1, int meta1, int block2, int meta2, int chance) {
        if (random.nextInt(chance) == 0) {
            world.setBlockWithMetadata(i, j, k, block2, meta2);
        } else {
            world.setBlockWithMetadata(i, j, k, block1, meta1);
        }

    }

    private ItemStack getGoldLoot(Random random) {
        int item = random.nextInt(8);
        switch (item) {
            case 0:
                return new ItemStack(AetherItems.IronBubble);
            case 1:
                return new ItemStack(AetherItems.VampireBlade);
            case 2:
                return new ItemStack(AetherItems.PigSlayer);
            case 3:
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixHelm);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixLegs);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixBody);
                }
                break;
            case 4:
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixBoots);
                }

                return new ItemStack(AetherItems.PhoenixGlove);
            case 5:
                return new ItemStack(AetherItems.LifeShard);
            case 6:
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeHelmet);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititePlatelegs);
                }

                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeBodyplate);
                }
                break;
            case 7:
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeBoots);
                }

                return new ItemStack(AetherItems.GravititeGlove);
        }

        return new ItemStack(AetherItems.ObsidianBody);
    }
}
