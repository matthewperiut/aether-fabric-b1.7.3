package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import com.periut.retroapi.register.block.RetroBlockAccess;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class Quicksoil extends Block {
    public Quicksoil() {
        super(RetroBlockAccess.allocateId(), Material.SAND);
        this.slipperiness = 1.1F;
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        //entityplayer.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        if (l == 0 && UtilSkyroot.shovel(entityplayer)) {
            this.dropStacks(world, i, j, k, l);
        }

        this.dropStacks(world, i, j, k, l);
    }

    @Override
    public void onPlaced(World arg, int i, int j, int k) {
        arg.setBlockMeta(i,j,k,1);
        super.onPlaced(arg, i, j, k);
    }
}
