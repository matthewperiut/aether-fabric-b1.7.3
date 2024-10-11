package com.matthewperiut.aether.block;

import com.matthewperiut.aether.gen.feature.AetherGenGoldenOak;
import com.matthewperiut.aether.gen.feature.AetherGenSkyroot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AetherSapling extends TemplatePlantBlock {
    public static int sprSkyroot;
    public static int sprGoldenOak;
    public boolean golden;

    public AetherSapling(Identifier identifier, boolean golden) {
        super(identifier, golden ? sprGoldenOak : sprSkyroot);
        this.golden = golden;
        float f = 0.4F;
        this.setBoundingBox(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }

    public void onTick(World world, int i, int j, int k, Random random) {
        if (!world.isRemote) {
            super.onTick(world, i, j, k, random);
            if (world.getLightLevel(i, j + 1, k) >= 9 && random.nextInt(30) == 0) {
                this.growTree(world, i, j, k, random);
            }

        }
    }

    public int getTexture(int i, int j) {
        return golden ? sprGoldenOak : sprSkyroot;
    }

    public boolean canPlaceAt(World world, int i, int j, int k) {
        return super.canPlaceAt(world, i, j, k) && this.canPlantOnTop(world.getBlockId(i, j - 1, k));
    }

    protected boolean canPlantOnTop(int i) {
        return i == AetherBlocks.Grass.id || i == AetherBlocks.Dirt.id;
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityPlayer) {
        if (world.isRemote) {
            return false;
        } else if (entityPlayer == null) {
            return false;
        } else {
            ItemStack itemStack = entityPlayer.getHand();
            if (itemStack == null) {
                return false;
            } else if (itemStack.itemId != Item.DYE.id) {
                return false;
            } else if (itemStack.getDamage() != 15) {
                return false;
            } else {
                this.growTree(world, i, j, k, world.random);
                --itemStack.count;
                return true;
            }
        }
    }

    public void growTree(World world, int i, int j, int k, Random random) {
        world.setBlockWithoutNotifyingNeighbors(i, j, k, 0);
        Object obj = null;
        if (this.id == AetherBlocks.GoldenOakSapling.id) {
            obj = new AetherGenGoldenOak();
        } else {
            obj = new AetherGenSkyroot();
        }

        if (!((Feature) obj).generate(world, random, i, j, k)) {
            world.setBlockWithoutNotifyingNeighbors(i, j, k, this.id);
        }

    }
}