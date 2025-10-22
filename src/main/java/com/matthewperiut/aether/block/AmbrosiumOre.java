package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AmbrosiumOre extends TemplateBlock {
    public AmbrosiumOre(Identifier identifier) {
        super(identifier, Material.STONE);
    }

    public int getDroppedItemId(int i, Random random) {
        return AetherItems.AmbrosiumShard.id;
    }

    public void onPlaced(World world, int i, int j, int k, int l) {
        world.setBlock(i, j, k, this.id);
        world.setBlockMeta(i, j, k, 1);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        //entityplayer.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        if (l == 0 && UtilSkyroot.pickaxe(entityplayer)) {
            this.dropStacks(world, i, j, k, l);
        }

        this.dropStacks(world, i, j, k, l);
    }
}
