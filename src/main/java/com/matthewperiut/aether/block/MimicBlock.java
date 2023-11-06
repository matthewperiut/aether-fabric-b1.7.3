package com.matthewperiut.aether.block;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class MimicBlock extends TemplateBlockBase
{
    protected MimicBlock(Identifier identifier)
    {
        super(identifier, Material.WOOD);
        this.texture = 26;
    }

    public int getTextureForSide(BlockView iblockaccess, int i, int j, int k, int l)
    {
        if (l == 1)
        {
            return this.texture - 1;
        }
        else if (l == 0)
        {
            return this.texture - 1;
        }
        else
        {
            int i1 = iblockaccess.getBlockId(i, j, k - 1);
            int j1 = iblockaccess.getBlockId(i, j, k + 1);
            int k1 = iblockaccess.getBlockId(i - 1, j, k);
            int l1 = iblockaccess.getBlockId(i + 1, j, k);
            int j2;
            int l2;
            int j3;
            byte byte2;
            if (i1 != this.id && j1 != this.id)
            {
                if (k1 != this.id && l1 != this.id)
                {
                    byte byte0 = 3;
                    if (Block.FULL_OPAQUE[i1] && !Block.FULL_OPAQUE[j1])
                    {
                        byte0 = 3;
                    }

                    if (Block.FULL_OPAQUE[j1] && !Block.FULL_OPAQUE[i1])
                    {
                        byte0 = 2;
                    }

                    if (Block.FULL_OPAQUE[k1] && !Block.FULL_OPAQUE[l1])
                    {
                        byte0 = 5;
                    }

                    if (Block.FULL_OPAQUE[l1] && !Block.FULL_OPAQUE[k1])
                    {
                        byte0 = 4;
                    }

                    return l != byte0 ? this.texture : this.texture + 1;
                }
                else if (l != 4 && l != 5)
                {
                    j2 = 0;
                    if (k1 == this.id)
                    {
                        j2 = -1;
                    }

                    l2 = iblockaccess.getBlockId(k1 != this.id ? i + 1 : i - 1, j, k - 1);
                    j3 = iblockaccess.getBlockId(k1 != this.id ? i + 1 : i - 1, j, k + 1);
                    if (l == 3)
                    {
                        j2 = -1 - j2;
                    }

                    byte2 = 3;
                    if ((Block.FULL_OPAQUE[i1] || Block.FULL_OPAQUE[l2]) && !Block.FULL_OPAQUE[j1] && !Block.FULL_OPAQUE[j3])
                    {
                        byte2 = 3;
                    }

                    if ((Block.FULL_OPAQUE[j1] || Block.FULL_OPAQUE[j3]) && !Block.FULL_OPAQUE[i1] && !Block.FULL_OPAQUE[l2])
                    {
                        byte2 = 2;
                    }

                    return (l != byte2 ? this.texture + 32 : this.texture + 16) + j2;
                }
                else
                {
                    return this.texture;
                }
            }
            else if (l != 2 && l != 3)
            {
                j2 = 0;
                if (i1 == this.id)
                {
                    j2 = -1;
                }

                l2 = iblockaccess.getBlockId(i - 1, j, i1 != this.id ? k + 1 : k - 1);
                j3 = iblockaccess.getBlockId(i + 1, j, i1 != this.id ? k + 1 : k - 1);
                if (l == 4)
                {
                    j2 = -1 - j2;
                }

                byte2 = 5;
                if ((Block.FULL_OPAQUE[k1] || Block.FULL_OPAQUE[l2]) && !Block.FULL_OPAQUE[l1] && !Block.FULL_OPAQUE[j3])
                {
                    byte2 = 5;
                }

                if ((Block.FULL_OPAQUE[l1] || Block.FULL_OPAQUE[j3]) && !Block.FULL_OPAQUE[k1] && !Block.FULL_OPAQUE[l2])
                {
                    byte2 = 4;
                }

                return (l != byte2 ? this.texture + 32 : this.texture + 16) + j2;
            }
            else
            {
                return this.texture;
            }
        }
    }

    public int getTextureForSide(int i)
    {
        if (i == 1)
        {
            return this.texture - 1;
        }
        else if (i == 0)
        {
            return this.texture - 1;
        }
        else
        {
            return i == 3 ? this.texture + 1 : this.texture;
        }
    }

    public boolean canPlaceAt(World world, int i, int j, int k)
    {
        int l = 0;
        if (world.getBlockId(i - 1, j, k) == this.id)
        {
            ++l;
        }

        if (world.getBlockId(i + 1, j, k) == this.id)
        {
            ++l;
        }

        if (world.getBlockId(i, j, k - 1) == this.id)
        {
            ++l;
        }

        if (world.getBlockId(i, j, k + 1) == this.id)
        {
            ++l;
        }

        if (l > 1)
        {
            return false;
        }
        else if (this.isThereANeighborChest(world, i - 1, j, k))
        {
            return false;
        }
        else if (this.isThereANeighborChest(world, i + 1, j, k))
        {
            return false;
        }
        else if (this.isThereANeighborChest(world, i, j, k - 1))
        {
            return false;
        }
        else
        {
            return !this.isThereANeighborChest(world, i, j, k + 1);
        }
    }

    private boolean isThereANeighborChest(World world, int i, int j, int k)
    {
        if (world.getBlockId(i, j, k) != this.id)
        {
            return false;
        }
        else if (world.getBlockId(i - 1, j, k) == this.id)
        {
            return true;
        }
        else if (world.getBlockId(i + 1, j, k) == this.id)
        {
            return true;
        }
        else if (world.getBlockId(i, j, k - 1) == this.id)
        {
            return true;
        }
        else
        {
            return world.getBlockId(i, j, k + 1) == this.id;
        }
    }

    public void onBlockRemoved(World world, int i, int j, int k)
    {
        world.setBlock(i, j, k, 0);
        /* todo: entity
        EntityMimic mimic = new EntityMimic(world);
        mimic.setPosition((double)i + 0.5, (double)j + 1.5, (double)k + 0.5);
        world.spawnEntity(mimic);
         */
    }

    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityplayer)
    {
        world.setBlock(i, j, k, 0);
        return true;
    }

    public int getDropCount(Random random)
    {
        return 0;
    }
}
