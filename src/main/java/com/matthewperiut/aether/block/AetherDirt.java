package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class AetherDirt extends Block {

    public AetherDirt() {
        super(RetroBlockAccess.allocateId(), Material.SOIL);
    }

    public boolean isOpaque() {
        return true;
    }

    public void onPlaced(World world, int i, int j, int k, int l) {
        world.setBlock(i, j, k, this.id);
        world.setBlockMeta(i, j, k, 1);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        //entityplayer.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        if (l == 0 && UtilSkyroot.shovel(entityplayer)) {
            this.dropStacks(world, i, j, k, l);
        }

        this.dropStacks(world, i, j, k, l);
    }
}
