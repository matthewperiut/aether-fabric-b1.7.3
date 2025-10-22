package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AetherGrass extends TemplateBlock {
    public static int sprTop;
    public static int sprSide;

    public AetherGrass(Identifier identifier) {
        super(identifier, Material.SOIL);
        setTickRandomly(true);
        textureId = sprTop;
    }

    @Override
    public int getTexture(int side, int j) {
        switch (side) {
            case 0:
                return AetherBlocks.Dirt.textureId;
            case 1:
                return sprTop;
            default:
                return sprSide;
        }
    }

    public int getTextureId(BlockView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            return sprTop;
        } else {
            return l == 0 ? AetherBlocks.Dirt.textureId : sprSide;
        }
    }

    public void onTick(World world, int i, int j, int k, Random random) {
        if (!world.isRemote) {
            if (world.getLightLevel(i, j + 1, k) < 4 && world.getMaterial(i, j + 1, k).blocksVision()) {
                if (random.nextInt(4) != 0) {
                    return;
                }

                world.setBlock(i, j, k, AetherBlocks.Dirt.id);
            } else if (world.getLightLevel(i, j + 1, k) >= 9) {
                int l = i + random.nextInt(3) - 1;
                int i1 = j + random.nextInt(5) - 3;
                int j1 = k + random.nextInt(3) - 1;
                if (world.getBlockId(l, i1, j1) == AetherBlocks.Dirt.id && world.getLightLevel(l, i1 + 1, j1) >= 4 && !world.getMaterial(l, i1 + 1, j1).blocksVision()) {
                    int meta = world.getBlockMeta(l, i1, j1);
                    world.setBlock(l, i1, j1, AetherBlocks.Grass.id);
                    world.setBlockMeta(l, i1, j1, meta);
                }
            }

        }
    }

    public int getDroppedItemId(int i, Random random) {
        return AetherBlocks.Dirt.getDroppedItemId(0, random);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        //entityplayer.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        if (UtilSkyroot.shovel(entityplayer) && l == 0) {
            this.dropStacks(world, i, j, k, l);
        }

        this.dropStacks(world, i, j, k, l);
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
                --itemStack.count;
                int iSpawned = 0;

                label54:
                for (int var9 = 0; var9 < 64; ++var9) {
                    int var10 = i;
                    int var11 = j + 1;
                    int var12 = k;

                    for (int var13 = 0; var13 < var9 / 16; ++var13) {
                        var10 += world.random.nextInt(3) - 1;
                        var11 += (world.random.nextInt(3) - 1) * world.random.nextInt(3) / 2;
                        var12 += world.random.nextInt(3) - 1;
                        if (world.getBlockId(var10, var11 - 1, var12) != this.id || world.shouldSuffocate(var10, var11, var12)) {
                            continue label54;
                        }
                    }

                    if (world.getBlockId(var10, var11, var12) == 0) {
                        if (world.random.nextInt(20 + 10 * iSpawned) == 0) {
                            world.setBlock(var10, var11, var12, AetherBlocks.WhiteFlower.id);
                            ++iSpawned;
                        } else if (world.random.nextInt(10 + 2 * iSpawned) <= 2) {
                            world.setBlock(var10, var11, var12, AetherBlocks.PurpleFlower.id);
                            ++iSpawned;
                        }
                    }
                }

                return true;
            }
        }
    }

    @Override
    public void onPlaced(World arg, int i, int j, int k) {
        super.onPlaced(arg, i, j, k);
        arg.setBlockMeta(i, j, k, 1);
    }
}
