package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateTranslucentBlock;

import java.util.Random;

public class Trap extends TemplateTranslucentBlock {
    public Trap(Identifier blockID) {
        super(blockID, DungeonBlock.sprBronze, Material.STONE, false);
        this.setTicksRandomly(true);
    }

    public boolean isFullOpaque() {
        return true;
    }

    public int getRenderPass() {
        return 1;
    }

    public int getTextureForSide(int i, int j) {
        if (j == 2) {
            return DungeonBlock.sprGold;
        } else {
            return j == 1 ? DungeonBlock.sprSilver : DungeonBlock.sprBronze;
        }
    }

    public int getDropCount(Random random) {
        return 1;
    }

    public void onSteppedOn(World world, int i, int j, int k, Entity entity) {
        if (!world.isClient) {
        /*  todo: entity
            if (entity instanceof PlayerEntity) {
                world.playSound((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "aether:other.dungeontrap.activateTrap", 1.0F, 1.0F);
                int x = MathHelper.floor((double)i);
                int y = MathHelper.floor((double)j);
                int z = MathHelper.floor((double)k);
                switch (world.getBlockMeta(i, j, k)) {
                    case 0:
                        EntitySentry entitysentry = new EntitySentry(world);
                        entitysentry.setPosition((double)x + 0.5, (double)y + 1.5, (double)z + 0.5);
                        world.spawnEntity(entitysentry);
                        break;
                    case 1:
                        EntityValkyrie entityvalk = new EntityValkyrie(world);
                        entityvalk.setPosition((double)x + 0.5, (double)y + 1.5, (double)z + 0.5);
                        world.spawnEntity(entityvalk);
                }

                world.placeBlockWithMetaData(i, j, k, AetherBlocks.LockedDungeonStone.id, world.getBlockMeta(i, j, k));
            }
*/
        }
    }

    protected int droppedMeta(int i) {
        return i;
    }
}
