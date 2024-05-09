package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AetherLog extends TemplateBlock implements MetaNamedBlockItemProvider {
    private static final Random rand = new Random();
    public static int sprTop;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogTop.png");
    public static int sprSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogSide.png");
    public static int sprGoldenSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/GoldenOak.png");

    public AetherLog(Identifier identifier) {
        super(identifier, sprSide, Material.WOOD);
    }

    public int getTextureForSide(int i, int j) {
        if (i <= 1 && j <= 3) {
            return sprTop;
        } else {
            return j <= 1 ? sprSide : sprGoldenSide;
        }
    }

    public int getDropCount(Random random) {
        return 0;
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int x, int y, int z, int meta) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        ItemStack stack = new ItemStack(this.id, 1, 0);
        if (UtilSkyroot.axe(entityplayer) && meta == 1 || meta == 3) {
            stack.count *= 2;
        }

        world.spawnEntity(new ItemEntity(world, x, y, z, stack));
        ItemStack itemstack = entityplayer.inventory.getHeldItem();
        if (itemstack != null && (itemstack.itemId == AetherItems.AxeZanite.id || itemstack.itemId == AetherItems.AxeGravitite.id)) {
            if (meta > 1) {
                stack = new ItemStack(AetherItems.GoldenAmber.id, rand.nextInt(4), 0);
                world.spawnEntity(new ItemEntity(world, x, y, z, stack));
            }

        }
    }

    @Override
    public void onBlockPlaced(World arg, int i, int j, int k) {
        int meta = arg.getBlockMeta(i,j,k);
        if (meta % 2 != 0)
            arg.setBlockMeta(i, j, k, meta+1);
        super.onBlockPlaced(arg, i, j, k);
    }

    public int[] getValidMetas() {
        return new int[]{0, 1};
    }
}