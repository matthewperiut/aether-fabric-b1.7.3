package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import java.util.Random;

public class AetherGenLiquids extends Feature {
    private final int liquidBlockId;

    public AetherGenLiquids(int i) {
        this.liquidBlockId = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        if (world.getBlockId(i, j + 1, k) == AetherBlocks.Holystone.id && world.getBlockMeta(i, j + 1, k) < 2) {
            if (world.getBlockId(i, j - 1, k) == AetherBlocks.Holystone.id && world.getBlockMeta(i, j - 1, k) < 2) {
                if (world.getBlockId(i, j, k) != 0 && (world.getBlockId(i, j, k) != AetherBlocks.Holystone.id || world.getBlockMeta(i, j, k) >= 2)) {
                    return false;
                } else {
                    int l = 0;
                    if (world.getBlockId(i - 1, j, k) == AetherBlocks.Holystone.id || world.getBlockMeta(i - 1, j, k) >= 2) {
                        ++l;
                    }

                    if (world.getBlockId(i + 1, j, k) == AetherBlocks.Holystone.id || world.getBlockMeta(i + 1, j, k) >= 2) {
                        ++l;
                    }

                    if (world.getBlockId(i, j, k - 1) == AetherBlocks.Holystone.id || world.getBlockMeta(i, j, k - 1) >= 2) {
                        ++l;
                    }

                    if (world.getBlockId(i, j, k + 1) == AetherBlocks.Holystone.id || world.getBlockMeta(i, j, k + 1) >= 2) {
                        ++l;
                    }

                    int i1 = 0;
                    if (world.isAir(i - 1, j, k)) {
                        ++i1;
                    }

                    if (world.isAir(i + 1, j, k)) {
                        ++i1;
                    }

                    if (world.isAir(i, j, k - 1)) {
                        ++i1;
                    }

                    if (world.isAir(i, j, k + 1)) {
                        ++i1;
                    }

                    if (l == 3 && i1 == 1) {
                        world.setBlock(i, j, k, this.liquidBlockId);
                        world.instantBlockUpdateEnabled = true;
                        Block.BLOCKS[this.liquidBlockId].onTick(world, i, j, k, random);
                        world.instantBlockUpdateEnabled = false;
                    }

                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
