package com.matthewperiut.aether.block;

import com.matthewperiut.aether.gen.GenGoldenOak;
import com.matthewperiut.aether.gen.GenSkyroot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplatePlant;

import java.util.Random;

public class AetherSapling extends TemplatePlant {
    public static int sprSkyroot;
    public static int sprGoldenOak;
    public boolean golden;

    public AetherSapling(Identifier identifier, boolean golden) {
        super(identifier, golden ? sprGoldenOak : sprSkyroot);
        this.golden = golden;
        float f = 0.4F;
        this.setBoundingBox(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void onScheduledTick(World world, int i, int j, int k, Random random) {
        if (!world.isClient) {
            super.onScheduledTick(world, i, j, k, random);
            if (world.placeBlock(i, j + 1, k) >= 9 && random.nextInt(30) == 0) {
                this.growTree(world, i, j, k, random);
            }

        }
    }

    public int getTextureForSide(int i, int j) {
        return golden ? sprGoldenOak : sprSkyroot;
    }

    public boolean canPlaceAt(World world, int i, int j, int k) {
        return super.canPlaceAt(world, i, j, k) && this.canPlantOnTopOf(world.getBlockId(i, j - 1, k));
    }

    protected boolean canPlantOnTopOf(int i) {
        return i == AetherBlocks.Grass.id || i == AetherBlocks.Dirt.id;
    }

    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityPlayer) {
        if (world.isClient) {
            return false;
        } else if (entityPlayer == null) {
            return false;
        } else {
            ItemStack itemStack = entityPlayer.getHeldItem();
            if (itemStack == null) {
                return false;
            } else if (itemStack.itemId != Item.DYE_POWDER.id) {
                return false;
            } else if (itemStack.getMeta() != 15) {
                return false;
            } else {
                this.growTree(world, i, j, k, world.rand);
                --itemStack.count;
                return true;
            }
        }
    }

    public void growTree(World world, int i, int j, int k, Random random) {
        world.setBlockInChunk(i, j, k, 0);
        Object obj = null;
        if (this.id == AetherBlocks.GoldenOakSapling.id) {
            obj = new GenGoldenOak();
        } else {
            obj = new GenSkyroot();
        }

        if (!((Feature) obj).generate(world, random, i, j, k)) {
            world.setBlockInChunk(i, j, k, this.id);
        }

    }
}