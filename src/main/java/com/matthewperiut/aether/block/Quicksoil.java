package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Quicksoil extends TemplateBlock {
    public Quicksoil(Identifier blockID) {
        super(blockID, Material.SAND);
        this.slipperiness = 1.1F;
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        if (l == 1 && UtilSkyroot.shovel(entityplayer)) {
            this.drop(world, i, j, k, l);
        }

        this.drop(world, i, j, k, l);
    }
}
