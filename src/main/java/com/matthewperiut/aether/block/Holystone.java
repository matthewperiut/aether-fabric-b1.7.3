package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;

public class Holystone extends TemplateBlock implements MetaNamedBlockItemProvider {
    public static int sprNormal;
    public static int sprMossy;

    protected Holystone(Identifier blockID) {
        super(blockID, sprNormal, Material.STONE);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int x, int y, int z, int meta) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        ItemStack stack = new ItemStack(this.id, 1, meta <= 1 ? 1 : 3);
        if (UtilSkyroot.pickaxe(entityplayer) && (meta == 0 || meta == 2)) {
            stack.count *= 2;
        }

        world.spawnEntity(new ItemEntity(world, x, y, z, stack));
    }

    public int getTextureForSide(int i, int j) {
        return j > 1 ? sprMossy : sprNormal;
    }

    public int[] getValidMetas() {
        return new int[]{1, 3};
    }
}
