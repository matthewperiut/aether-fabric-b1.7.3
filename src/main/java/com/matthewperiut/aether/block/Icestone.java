package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class Icestone extends TemplateBlockBase
{
    Icestone(Identifier blockID)
    {
        super(blockID, Material.STONE);
    }

    public void onBlockPlaced(World world, int i, int j, int k, int l)
    {
        world.setBlock(i, j, k, this.id);
        world.setBlockMeta(i, j, k, 1);

        for (int x = i - 3; x < i + 4; ++x)
        {
            for (int y = j - 1; y < j + 2; ++y)
            {
                for (int z = k - 3; z < k + 4; ++z)
                {
                    if ((x - i) * (x - i) + (y - j) * (y - j) + (z - k) * (z - k) < 8 && world.getBlockId(x, y, z) == Block.STILL_WATER.id)
                    {
                        world.setBlock(x, y, z, Block.ICE.id);
                    }

                    if ((x - i) * (x - i) + (y - j) * (y - j) + (z - k) * (z - k) < 8 && world.getBlockId(x, y, z) == Block.STILL_LAVA.id)
                    {
                        world.setBlock(x, y, z, Block.OBSIDIAN.id);
                    }
                }
            }
        }

    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l)
    {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        if (l == 0 && UtilSkyroot.pickaxe(entityplayer))
        {
            this.drop(world, i, j, k, l);
        }

        this.drop(world, i, j, k, l);
    }
}
