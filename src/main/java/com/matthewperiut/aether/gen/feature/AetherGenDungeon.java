package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.TreasureChest;
import com.matthewperiut.aether.entity.living.EntityFireMonster;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class AetherGenDungeon extends Feature
{
    //public boolean generate(World World, Random random, int i, int i2, int i3) {return false;}
    //public boolean generate(World World, Random random, int i, int i2, int i3,int i4) {return false;}
    public int xoff;
    public int yoff;
    public int zoff;
    public int rad;
    public int truey;

    private int floors()
    {
        return Block.DOUBLE_STONE_SLAB.id;
    }

    private int walls()
    {
        return AetherBlocks.LockedDungeonStone.id;
    }

    private int ceilings()
    {
        return AetherBlocks.LockedLightDungeonStone.id;
    }

    private int torches()
    {
        return 0;
    }

    private int doors()
    {
        return 0;
    }

    @Override
    public boolean generate(final World World, final Random rand, final int x, final int y, final int z)
    {
        return this.generate(World, rand, x, y, z, 24);
    }

    public boolean generate(final World world, final Random random, final int x, final int y, final int z, int r)
    {
        r = (int) Math.floor(r * 0.8);
        final int wid = (int) Math.sqrt((double) (r * r / 2));
        for (int j = 4; j > -5; --j)
        {
            int a = wid;
            if (j >= 3 || j <= -3)
            {
                --a;
            }
            if (j == 4 || j == -4)
            {
                --a;
            }
            for (int i = -a; i <= a; ++i)
            {
                for (int k = -a; k <= a; ++k)
                {
                    if (j == 4)
                    {
                        this.setBlock(world, random, x + i, y + j, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                    }
                    else if (j > -4)
                    {
                        if (i == a || -i == a || k == a || -k == a)
                        {
                            this.setBlock(world, random, x + i, y + j, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                        }
                        else
                        {
                            world.setBlockInChunk(x + i, y + j, z + k, 0);
                            if (j == -2 && (i == a - 1 || -i == a - 1 || k == a - 1 || -k == a - 1) && (i % 3 == 0 || k % 3 == 0))
                            {
                                world.setBlockInChunk(x + i, y + j + 2, z + k, this.torches());
                            }
                        }
                    }
                    else
                    {
                        this.setBlock(world, random, x + i, y + j, z + k, this.walls(), 2, this.ceilings(), 2, 10);
                        if ((i == a - 2 || -i == a - 2) && (k == a - 2 || -k == a - 2))
                        {
                            world.setBlockInChunk(x + i, y + j + 1, z + k, Block.NETHERRACK.id);
                            world.setBlockInChunk(x + i, y + j + 2, z + k, Block.FIRE.id);
                        }
                    }
                }
            }
        }
        final int direction = random.nextInt(4);
        for (int l = wid; l < wid + 32; ++l)
        {
            boolean flag = false;
            for (int m = -3; m < 2; ++m)
            {
                for (int k2 = -3; k2 < 4; ++k2)
                {
                    int a2;
                    int b;
                    if (direction / 2 == 0)
                    {
                        a2 = l;
                        b = k2;
                    }
                    else
                    {
                        a2 = k2;
                        b = l;
                    }
                    if (direction % 2 == 0)
                    {
                        a2 = -a2;
                        b = -b;
                    }
                    if (!AetherBlocks.isGood(world.getBlockId(x + a2, y + m, z + b), world.getBlockMeta(x + a2, y + m, z + b)))
                    {
                        flag = true;
                        if (m == -3)
                        {
                            this.setBlock(world, random, x + a2, y + m, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                        }
                        else if (m < 1)
                        {
                            if (l == wid)
                            {
                                if (k2 < 2 && k2 > -2 && m < 0)
                                {
                                    world.setBlockInChunk(x + a2, y + m, z + b, this.doors());
                                }
                                else
                                {
                                    this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                }
                            }
                            else if (k2 == 3 || k2 == -3)
                            {
                                this.setBlock(world, random, x + a2, y + m, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                            }
                            else
                            {
                                world.setBlockInChunk(x + a2, y + m, z + b, 0);
                                if (m == -1 && (k2 == 2 || k2 == -2) && (l - wid - 2) % 3 == 0)
                                {
                                    world.setBlockInChunk(x + a2, y + m, z + b, this.torches());
                                }
                            }
                        }
                        else if (l == wid)
                        {
                            this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 5);
                        }
                        else
                        {
                            this.setBlock(world, random, x + a2, y + m, z + b, AetherBlocks.Holystone.id, 0, AetherBlocks.Holystone.id, 2, 5);
                        }
                    }
                    a2 = -a2;
                    b = -b;
                    if (l < wid + 6)
                    {
                        if (m == -3)
                        {
                            this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                        }
                        else if (m < 1)
                        {
                            if (l == wid)
                            {
                                if (k2 < 2 && k2 > -2 && m < 0)
                                {
                                    this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                }
                                else
                                {
                                    this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                                }
                            }
                            else if (l == wid + 5)
                            {
                                this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                            }
                            else if (l == wid + 4 && k2 == 0 && m == -2)
                            {
                                TreasureChest.PlaceTreasureChest(world, x + a2, y + m, z + b, 3);
                            }
                            else if (k2 == 3 || k2 == -3)
                            {
                                this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                            }
                            else
                            {
                                world.setBlockInChunk(x + a2, y + m, z + b, 0);
                                if (m == -1 && (k2 == 2 || k2 == -2) && (l - wid - 2) % 3 == 0)
                                {
                                    world.setBlockInChunk(x + a2, y + m, z + b, this.torches());
                                }
                            }
                        }
                        else
                        {
                            this.setBlock(world, random, x + a2, y + m, z + b, this.walls(), 2, this.ceilings(), 2, 10);
                        }
                    }
                }
            }
            if (!flag)
            {
                break;
            }
        }
        final EntityFireMonster boss = new EntityFireMonster(world, x, y - 1, z, wid, direction);
        world.spawnEntity(boss);
        System.out.println("Gold Dungeon: " + x + ", " + y + ", " + z);
        return true;
    }

    private void setBlock(final World world, final Random random, final int i, final int j, final int k, final int block1, final int meta1, final int block2, final int meta2, final int chance)
    {
        if (random.nextInt(chance) == 0)
        {
            world.setBlockWithMetadata(i, j, k, block2, meta2);
        }
        else
        {
            world.setBlockWithMetadata(i, j, k, block1, meta1);
        }
    }
}
