package com.matthewperiut.aether.block;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.class_213;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.SleepAttemptResult;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Iterator;
import java.util.Random;

import static net.minecraft.block.BedBlock.method_1659;

public class AetherBed extends TemplateBlock {
    public static final int[][] headBlockToFootBlockMap = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

    public AetherBed(Identifier identifier) {
        super(identifier, 134, Material.WOOL);
        this.setBounds();
    }

    public static int getDirectionFromMetadata(int i) {
        return i & 3;
    }

    public static boolean isBlockFootOfBed(int i) {
        return (i & 8) != 0;
    }

    public static boolean isBedOccupied(int i) {
        return (i & 4) != 0;
    }

    public static void setBedOccupied(World world, int i, int j, int k, boolean flag) {
        int l = world.getBlockMeta(i, j, k);
        if (flag) {
            l |= 4;
        } else {
            l &= -5;
        }

        world.setBlockMeta(i, j, k, l);
    }

    public static Vec3i getNearestEmptyChunkCoordinates(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMeta(i, j, k);
        int j1 = getDirectionFromMetadata(i1);

        for (int k1 = 0; k1 <= 1; ++k1) {
            int l1 = i - headBlockToFootBlockMap[j1][0] * k1 - 1;
            int i2 = k - headBlockToFootBlockMap[j1][1] * k1 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;

            for (int l2 = l1; l2 <= j2; ++l2) {
                for (int i3 = i2; i3 <= k2; ++i3) {
                    if (world.shouldSuffocate(l2, j - 1, i3) && world.isAir(l2, j, i3) && world.isAir(l2, j + 1, i3)) {
                        if (l <= 0) {
                            return new Vec3i(l2, j, i3);
                        }

                        --l;
                    }
                }
            }
        }

        return null;
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer) {
        if (world.isRemote) {
            return true;
        } else {
            int l = world.getBlockMeta(i, j, k);
            if (!isBlockFootOfBed(l)) {
                int i1 = getDirectionFromMetadata(l);
                i += headBlockToFootBlockMap[i1][0];
                k += headBlockToFootBlockMap[i1][1];
                if (world.getBlockId(i, j, k) != this.id) {
                    return true;
                }

                l = world.getBlockMeta(i, j, k);
            }

            if (isBedOccupied(l)) {
                PlayerEntity entityplayer1 = null;
                Iterator iterator = world.players.iterator();

                while (iterator.hasNext()) {
                    PlayerEntity entityplayer2 = (PlayerEntity) iterator.next();
                    if (entityplayer2.isSleeping()) {
                        Vec3i chunkcoordinates = entityplayer2.spawnPos;
                        if (chunkcoordinates.x == i && chunkcoordinates.y == j && chunkcoordinates.z == k) {
                            entityplayer1 = entityplayer2;
                        }
                    }
                }

                if (entityplayer1 != null) {
                    // method_490 -> sendMessage
                    entityplayer.method_490("tile.bed.occupied");
                    return true;
                }

                setBedOccupied(world, i, j, k, false);
            }

            SleepAttemptResult enumstatus = entityplayer.method_495(i, j, k);//this.sleepInBedAt(entityplayer, i, j, k);
            if (enumstatus == SleepAttemptResult.OK) {
                setBedOccupied(world, i, j, k, true);
                return true;
            } else {
                if (enumstatus == SleepAttemptResult.NOT_POSSIBLE_NOW) {
                    // method_490 -> sendMessage
                    entityplayer.method_490("tile.bed.noSleep");
                }

                return true;
            }
        }
    }

    private void func_22052_e(PlayerEntity player, int i) {
        player.field_509 = 0.0F;
        player.field_510 = 0.0F;
        switch (i) {
            case 0:
                player.field_510 = -1.8F;
                break;
            case 1:
                player.field_509 = 1.8F;
                break;
            case 2:
                player.field_510 = 1.8F;
                break;
            case 3:
                player.field_509 = -1.8F;
        }

    }

    @Override
    public int getTexture(int i, int j) {
        if (i == 0) {
            return Block.PLANKS.textureId;
        } else {
            int k = getDirectionFromMetadata(j);
            int l = class_213.field_794[k][i];
            if (isBlockFootOfBed(j)) {
                if (l == 2) {
                    return this.textureId + 2 + 16;
                } else {
                    return l != 5 && l != 4 ? this.textureId + 1 : this.textureId + 1 + 16;
                }
            } else if (l == 3) {
                return this.textureId - 1 + 16;
            } else {
                return l != 5 && l != 4 ? this.textureId : this.textureId + 16;
            }
        }
    }

    public int getRenderType() {
        return 14;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isOpaque() {
        return false;
    }

    public void updateBoundingBox(BlockView iblockaccess, int i, int j, int k) {
        this.setBounds();
    }

    public void neighborUpdate(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMeta(i, j, k);
        int j1 = getDirectionFromMetadata(i1);
        if (isBlockFootOfBed(i1)) {
            if (world.getBlockId(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != this.id) {
                world.setBlock(i, j, k, 0);
            }
        } else if (world.getBlockId(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != this.id) {
            world.setBlock(i, j, k, 0);
            if (!world.isRemote) {
                this.dropStacks(world, i, j, k, i1);
            }
        }

    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return isBlockFootOfBed(blockMeta) ? 0 : Item.BED.id;
    }

    private void setBounds() {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }


    public void dropStacks(World world, int x, int y, int z, int l, float f) {
        if (!isBlockFootOfBed(l)) {
            super.dropStacks(world, x, y, z, l, f);
        }

    }

    public int getPistonBehavior() {
        return 1;
    }
}
