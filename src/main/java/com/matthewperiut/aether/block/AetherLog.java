package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class AetherLog extends TemplateBlockBase implements MetaNamedBlockItemProvider
{
    private static final Random rand = new Random();
    public static int sprTop;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogTop.png");
    public static int sprSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogSide.png");
    public static int sprGoldenSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/GoldenOak.png");

    public AetherLog(Identifier identifier)
    {
        super(identifier, sprSide, Material.WOOD);
    }

    public int getTextureForSide(int i, int j)
    {
        if (i <= 1 && j <= 3)
        {
            return sprTop;
        }
        else
        {
            return j <= 1 ? sprSide : sprGoldenSide;
        }
    }

    public int getDropCount(Random random)
    {
        return 0;
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int x, int y, int z, int meta)
    {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        ItemStack stack = new ItemStack(this.id, 1, 0);
        if (UtilSkyroot.axe(entityplayer) && meta != 1)
        {
            stack.count *= 2;
        }

        world.spawnEntity(new ItemEntity(world, x, y, z, stack));
        ItemStack itemstack = entityplayer.inventory.getHeldItem();
        /* todo: items
        if (itemstack != null && (itemstack.itemId == AetherItems.AxeZanite.id || itemstack.itemId == AetherItems.AxeGravitite.id || meta < 2)) {
            if (meta > 1 && rand.nextBoolean()) {
                stack = new ItemStack(AetherItems.GoldenAmber.id, 2 + rand.nextInt(2), 0);
                world.spawnEntity(new ItemEntity(world, x, y, z, stack));
            }

        }*/
    }

    @Override
    public void onBlockPlaced(World arg, int i, int j, int k)
    {
        arg.setBlockMeta(i, j, k, 1);
    }

    public int[] getValidMetas()
    {
        return new int[]{0, 1};
    }
}