package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class AetherGenLakes extends Feature
{
    private int field_15235_a;

    public AetherGenLakes(int i)
    {
        this.field_15235_a = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        i -= 8;

        for (k -= 8; j > 0 && world.isAir(i, j, k); --j)
        {
        }

        j -= 4;
        boolean[] aflag = new boolean[2048];
        int l = random.nextInt(4) + 4;

        int i2;
        for (i2 = 0; i2 < l; ++i2)
        {
            double d = random.nextDouble() * 6.0 + 3.0;
            double d1 = random.nextDouble() * 4.0 + 2.0;
            double d2 = random.nextDouble() * 6.0 + 3.0;
            double d3 = random.nextDouble() * (16.0 - d - 2.0) + 1.0 + d / 2.0;
            double d4 = random.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
            double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;

            for (int j4 = 1; j4 < 15; ++j4)
            {
                for (int k4 = 1; k4 < 15; ++k4)
                {
                    for (int l4 = 1; l4 < 7; ++l4)
                    {
                        double d6 = ((double) j4 - d3) / (d / 2.0);
                        double d7 = ((double) l4 - d4) / (d1 / 2.0);
                        double d8 = ((double) k4 - d5) / (d2 / 2.0);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                        if (d9 < 1.0)
                        {
                            aflag[(j4 * 16 + k4) * 8 + l4] = true;
                        }
                    }
                }
            }
        }

        int i4;
        int i3;
        boolean flag1;
        for (i2 = 0; i2 < 16; ++i2)
        {
            for (i3 = 0; i3 < 16; ++i3)
            {
                for (i4 = 0; i4 < 8; ++i4)
                {
                    flag1 = !aflag[(i2 * 16 + i3) * 8 + i4] && (i2 < 15 && aflag[((i2 + 1) * 16 + i3) * 8 + i4] || i2 > 0 && aflag[((i2 - 1) * 16 + i3) * 8 + i4] || i3 < 15 && aflag[(i2 * 16 + i3 + 1) * 8 + i4] || i3 > 0 && aflag[(i2 * 16 + (i3 - 1)) * 8 + i4] || i4 < 7 && aflag[(i2 * 16 + i3) * 8 + i4 + 1] || i4 > 0 && aflag[(i2 * 16 + i3) * 8 + (i4 - 1)]);
                    if (flag1)
                    {
                        Material material = world.getMaterial(i + i2, j + i4, k + i3);
                        if (i4 >= 4 && material.isLiquid())
                        {
                            return false;
                        }

                        if (i4 < 4 && !material.isSolid() && world.getBlockId(i + i2, j + i4, k + i3) != this.field_15235_a)
                        {
                            return false;
                        }
                    }
                }
            }
        }

        for (i2 = 0; i2 < 16; ++i2)
        {
            for (i3 = 0; i3 < 16; ++i3)
            {
                for (i4 = 0; i4 < 8; ++i4)
                {
                    if (aflag[(i2 * 16 + i3) * 8 + i4])
                    {
                        world.setBlockInChunk(i + i2, j + i4, k + i3, i4 < 4 ? this.field_15235_a : 0);
                    }
                }
            }
        }

        for (i2 = 0; i2 < 16; ++i2)
        {
            for (i3 = 0; i3 < 16; ++i3)
            {
                for (i4 = 4; i4 < 8; ++i4)
                {
                    if (aflag[(i2 * 16 + i3) * 8 + i4] && world.getBlockId(i + i2, j + i4 - 1, k + i3) == AetherBlocks.Dirt.id && world.method_164(LightType.field_2757, i + i2, j + i4, k + i3) > 0)
                    {
                        world.setBlockInChunk(i + i2, j + i4 - 1, k + i3, AetherBlocks.Grass.id);
                    }
                }
            }
        }

        if (Block.BY_ID[this.field_15235_a].material == Material.LAVA)
        {
            for (i2 = 0; i2 < 16; ++i2)
            {
                for (i3 = 0; i3 < 16; ++i3)
                {
                    for (i4 = 0; i4 < 8; ++i4)
                    {
                        flag1 = !aflag[(i2 * 16 + i3) * 8 + i4] && (i2 < 15 && aflag[((i2 + 1) * 16 + i3) * 8 + i4] || i2 > 0 && aflag[((i2 - 1) * 16 + i3) * 8 + i4] || i3 < 15 && aflag[(i2 * 16 + i3 + 1) * 8 + i4] || i3 > 0 && aflag[(i2 * 16 + (i3 - 1)) * 8 + i4] || i4 < 7 && aflag[(i2 * 16 + i3) * 8 + i4 + 1] || i4 > 0 && aflag[(i2 * 16 + i3) * 8 + (i4 - 1)]);
                        if (flag1 && (i4 < 4 || random.nextInt(2) != 0) && world.getMaterial(i + i2, j + i4, k + i3).isSolid())
                        {
                            world.setBlockWithMetadata(i + i2, j + i4, k + i3, AetherBlocks.Holystone.id, 0);
                        }
                    }
                }
            }
        }

        return true;
    }
}
