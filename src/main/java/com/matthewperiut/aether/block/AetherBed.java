package com.matthewperiut.aether.block;

import com.matthewperiut.aether.mixin.LivingEntityAccessor;
import com.matthewperiut.aether.mixin.PlayerEntityAccessor;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.number.BedMagicNumbers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SleepStatus;
import net.minecraft.util.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Iterator;
import java.util.Random;

public class AetherBed extends TemplateBlockBase {
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
                    if (world.canSuffocate(l2, j - 1, i3) && world.isAir(l2, j, i3) && world.isAir(l2, j + 1, i3)) {
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

    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityplayer) {
        if (world.isClient) {
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
                    if (entityplayer2.isLyingOnBed()) {
                        Vec3i chunkcoordinates = entityplayer2.bedPosition;
                        if (chunkcoordinates.x == i && chunkcoordinates.y == j && chunkcoordinates.z == k) {
                            entityplayer1 = entityplayer2;
                        }
                    }
                }

                if (entityplayer1 != null) {
                    entityplayer.sendMessage("tile.bed.occupied");
                    return true;
                }

                setBedOccupied(world, i, j, k, false);
            }

            SleepStatus enumstatus = this.sleepInBedAt(entityplayer, i, j, k);
            if (enumstatus == SleepStatus.field_2660) {
                setBedOccupied(world, i, j, k, true);
                return true;
            } else {
                if (enumstatus == SleepStatus.DAY_TIME) {
                    entityplayer.sendMessage("tile.bed.noSleep");
                }

                return true;
            }
        }
    }

    public SleepStatus sleepInBedAt(PlayerEntity player, int i, int j, int k) {
        World worldObj = player.world;
        if (!worldObj.isClient) {
            label49:
            {
                if (!player.isLyingOnBed() && player.isAlive()) {
                    if (worldObj.isDaylight()) {
                        return SleepStatus.DAY_TIME;
                    }

                    if (!(Math.abs(player.x - (double) i) > 3.0) && !(Math.abs(player.y - (double) j) > 2.0) && !(Math.abs(player.z - (double) k) > 3.0)) {
                        break label49;
                    }

                    return SleepStatus.field_2663;
                }

                return SleepStatus.YOU_SLEEPING_OR_DEAD;
            }
        }

        ((LivingEntityAccessor) player).invokeSetSize(0.2F, 0.2F);
        player.standingEyeHeight = 0.2F;
        if (worldObj.isBlockLoaded(i, j, k)) {
            int l = worldObj.getBlockMeta(i, j, k);
            int i1 = BedBlock.orientationOnly(l);
            float f = 0.5F;
            float f1 = 0.5F;
            switch (i1) {
                case 0:
                    f1 = 0.9F;
                    break;
                case 1:
                    f = 0.1F;
                    break;
                case 2:
                    f1 = 0.1F;
                    break;
                case 3:
                    f = 0.9F;
            }

            this.func_22052_e(player, i1);
            player.setPosition((float) i + f, (float) j + 0.9375F, (float) k + f1);
        } else {
            player.setPosition((float) i + 0.5F, (float) j + 0.9375F, (float) k + 0.5F);
        }

        ((PlayerEntityAccessor) player).setLyingOnBed(true);
        player.xVelocity = player.zVelocity = player.yVelocity = 0.0;
        if (!worldObj.isClient) {
            worldObj.onPlayerDisconnect();
        }

        return SleepStatus.field_2660;
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

    public int getTextureForSide(int i, int j) {
        if (i == 0) {
            return Block.WOOD.texture;
        } else {
            int k = getDirectionFromMetadata(j);
            int l = BedMagicNumbers.field_794[k][i];
            if (isBlockFootOfBed(j)) {
                if (l == 2) {
                    return this.texture + 2 + 16;
                } else {
                    return l != 5 && l != 4 ? this.texture + 1 : this.texture + 1 + 16;
                }
            } else if (l == 3) {
                return this.texture - 1 + 16;
            } else {
                return l != 5 && l != 4 ? this.texture : this.texture + 16;
            }
        }
    }

    public int getRenderType() {
        return 14;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean isFullOpaque() {
        return false;
    }

    public void updateBoundingBox(BlockView iblockaccess, int i, int j, int k) {
        this.setBounds();
    }

    public void onAdjacentBlockUpdate(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMeta(i, j, k);
        int j1 = getDirectionFromMetadata(i1);
        if (isBlockFootOfBed(i1)) {
            if (world.getBlockId(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != this.id) {
                world.setBlock(i, j, k, 0);
            }
        } else if (world.getBlockId(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != this.id) {
            world.setBlock(i, j, k, 0);
            if (!world.isClient) {
                this.drop(world, i, j, k, i1);
            }
        }

    }

    public int getDropId(int i, Random random) {
        return isBlockFootOfBed(i) ? 0 : Item.BED.id;
    }

    private void setBounds() {
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public void beforeDestroyedByExplosion(World world, int i, int j, int k, int l, float f) {
        if (!isBlockFootOfBed(l)) {
            super.beforeDestroyedByExplosion(world, i, j, k, l, f);
        }

    }

    public int getPistonPushMode() {
        return 1;
    }
}
