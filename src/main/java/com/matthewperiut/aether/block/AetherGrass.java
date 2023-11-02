package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class AetherGrass extends TemplateBlockBase {
    public static int sprTop;
    public static int sprSide;

    public AetherGrass(Identifier identifier) {
        super(identifier, Material.DIRT);
        setTicksRandomly(true);
        texture = sprTop;
    }

    @Override
    public int getTextureForSide(int side, int j) {
        switch (side) {
            case 0:
                return AetherBlocks.Dirt.texture;
            case 1:
                return sprTop;
            default:
                return sprSide;
        }
    }

    public int getTextureForSide(BlockView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            return sprTop;
        } else {
            return l == 0 ? AetherBlocks.Dirt.texture : sprSide;
        }
    }

    public void onScheduledTick(World world, int i, int j, int k, Random random) {
        if (!world.isClient) {
            if (world.placeBlock(i, j + 1, k) < 4 && world.getMaterial(i, j + 1, k).canBlockGrass()) {
                if (random.nextInt(4) != 0) {
                    return;
                }

                world.setBlock(i, j, k, AetherBlocks.Dirt.id);
            } else if (world.placeBlock(i, j + 1, k) >= 9) {
                int l = i + random.nextInt(3) - 1;
                int i1 = j + random.nextInt(5) - 3;
                int j1 = k + random.nextInt(3) - 1;
                if (world.getBlockId(l, i1, j1) == AetherBlocks.Dirt.id && world.placeBlock(l, i1 + 1, j1) >= 4 && !world.getMaterial(l, i1 + 1, j1).canBlockGrass()) {
                    int meta = world.getBlockMeta(l, i1, j1);
                    world.setBlock(l, i1, j1, AetherBlocks.Grass.id);
                    world.setBlockMeta(l, i1, j1, meta);
                }
            }

        }
    }

    public int getDropId(int i, Random random) {
        return AetherBlocks.Dirt.getDropId(0, random);
    }

    public void afterBreak(World world, PlayerEntity entityplayer, int i, int j, int k, int l) {
        entityplayer.increaseStat(Stats.mineBlock[this.id], 1);
        if (UtilSkyroot.shovel(entityplayer) && l == 0) {
            this.drop(world, i, j, k, l);
        }

        this.drop(world, i, j, k, l);
    }

    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityPlayer) {
        if (world.isClient) {
            return false;
        } else if (entityPlayer == null) {
            return false;
        } else {
            ItemStack itemStack = entityPlayer.getHeldItem();
            if (itemStack == null) {
                return false;
            } else if (itemStack.itemId != Item.DYE_POWDER.id) {
                return false;
            } else if (itemStack.getMeta() != 15) {
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
                        var10 += world.rand.nextInt(3) - 1;
                        var11 += (world.rand.nextInt(3) - 1) * world.rand.nextInt(3) / 2;
                        var12 += world.rand.nextInt(3) - 1;
                        if (world.getBlockId(var10, var11 - 1, var12) != this.id || world.canSuffocate(var10, var11, var12)) {
                            continue label54;
                        }
                    }

                    if (world.getBlockId(var10, var11, var12) == 0) {
                        if (world.rand.nextInt(20 + 10 * iSpawned) == 0) {
                            world.setBlock(var10, var11, var12, AetherBlocks.WhiteFlower.id);
                            ++iSpawned;
                        } else if (world.rand.nextInt(10 + 2 * iSpawned) <= 2) {
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
    public void onBlockPlaced(World arg, int i, int j, int k) {
        super.onBlockPlaced(arg, i, j, k);
        arg.setBlockMeta(i, j, k, 1);
    }
}
