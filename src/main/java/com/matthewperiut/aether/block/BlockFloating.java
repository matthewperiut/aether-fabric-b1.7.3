package com.matthewperiut.aether.block;

import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class BlockFloating extends TemplateBlock {
    public static boolean fallInstantly = false;
    private final boolean enchanted;

    public BlockFloating(Identifier i, int j, boolean bool) {
        super(i, j, Material.STONE);
        this.enchanted = bool;
    }

    public BlockFloating(Identifier i, boolean bool) {
        super(i, Material.STONE);
        this.enchanted = bool;
    }

    public static boolean canFallAbove(World world, int i, int j, int k) {
        int l = world.getBlockId(i, j, k);
        if (l == 0) {
            return true;
        } else if (l == Block.FIRE.id) {
            return true;
        } else {
            Material material = Block.BY_ID[l].material;
            if (material == Material.WATER) {
                return true;
            } else {
                return material == Material.LAVA;
            }
        }
    }

    public void onBlockPlaced(World world, int i, int j, int k) {
        world.method_216(i, j, k, this.id, this.getTickrate());
    }

    public void onAdjacentBlockUpdate(World world, int i, int j, int k, int l) {
        world.method_216(i, j, k, this.id, this.getTickrate());
    }

    public void onScheduledTick(World world, int i, int j, int k, Random random) {
        if (!this.enchanted || this.enchanted && world.method_263(i, j, k)) {
            this.tryToFall(world, i, j, k);
        }

    }

    private void tryToFall(World world, int i, int j, int k) {
        if (canFallAbove(world, i, j + 1, k) && j < 128) {
            byte byte0 = 32;
            if (!fallInstantly && world.method_155(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0)) {
                EntityFloatingBlock floating = new EntityFloatingBlock(world, (double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), this.id);
                world.spawnEntity(floating);
            } else {
                world.setBlock(i, j, k, 0);

                while (canFallAbove(world, i, j + 1, k) && j < 128) {
                    ++j;
                }

                if (j > 0) {
                    world.setBlock(i, j, k, this.id);
                }
            }
        }

    }

    public int getTickrate() {
        return 3;
    }
}