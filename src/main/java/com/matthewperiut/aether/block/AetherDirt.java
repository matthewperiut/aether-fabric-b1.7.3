package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class AetherDirt extends TemplateBlockBase {

    public AetherDirt(Identifier identifier) {
        super(identifier, Material.DIRT);
    }

    public boolean isFullOpaque() {
        return true;
    }

    public void onBlockPlaced(World world, int i, int j, int k, int l) {
        world.setBlock(i, j, k, this.id);
        world.setBlockMeta(i, j, k, 1);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        if (l == 0 && UtilSkyroot.shovel(entityplayer)) {
            this.drop(world, i, j, k, l);
        }

        this.drop(world, i, j, k, l);
    }
}
