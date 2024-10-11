package com.matthewperiut.aether.block;

import com.matthewperiut.aether.entity.living.EntityMimic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class MimicBlock extends TemplateBlock {
    protected MimicBlock(Identifier identifier) {
        super(identifier, Material.WOOD);
        this.textureId = 26;
    }

    public int getTextureId(BlockView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            return this.textureId - 1;
        } else if (l == 0) {
            return this.textureId - 1;
        } else {
            int i1 = iblockaccess.getBlockId(i, j, k - 1);
            int j1 = iblockaccess.getBlockId(i, j, k + 1);
            int k1 = iblockaccess.getBlockId(i - 1, j, k);
            int l1 = iblockaccess.getBlockId(i + 1, j, k);
            int j2;
            int l2;
            int j3;
            byte byte2;
            if (i1 != this.id && j1 != this.id) {
                if (k1 != this.id && l1 != this.id) {
                    byte byte0 = 3;
                    if (Block.BLOCKS_OPAQUE[i1] && !Block.BLOCKS_OPAQUE[j1]) {
                        byte0 = 3;
                    }

                    if (Block.BLOCKS_OPAQUE[j1] && !Block.BLOCKS_OPAQUE[i1]) {
                        byte0 = 2;
                    }

                    if (Block.BLOCKS_OPAQUE[k1] && !Block.BLOCKS_OPAQUE[l1]) {
                        byte0 = 5;
                    }

                    if (Block.BLOCKS_OPAQUE[l1] && !Block.BLOCKS_OPAQUE[k1]) {
                        byte0 = 4;
                    }

                    return l != byte0 ? this.textureId : this.textureId + 1;
                } else if (l != 4 && l != 5) {
                    j2 = 0;
                    if (k1 == this.id) {
                        j2 = -1;
                    }

                    l2 = iblockaccess.getBlockId(k1 != this.id ? i + 1 : i - 1, j, k - 1);
                    j3 = iblockaccess.getBlockId(k1 != this.id ? i + 1 : i - 1, j, k + 1);
                    if (l == 3) {
                        j2 = -1 - j2;
                    }

                    byte2 = 3;
                    if ((Block.BLOCKS_OPAQUE[i1] || Block.BLOCKS_OPAQUE[l2]) && !Block.BLOCKS_OPAQUE[j1] && !Block.BLOCKS_OPAQUE[j3]) {
                        byte2 = 3;
                    }

                    if ((Block.BLOCKS_OPAQUE[j1] || Block.BLOCKS_OPAQUE[j3]) && !Block.BLOCKS_OPAQUE[i1] && !Block.BLOCKS_OPAQUE[l2]) {
                        byte2 = 2;
                    }

                    return (l != byte2 ? this.textureId + 32 : this.textureId + 16) + j2;
                } else {
                    return this.textureId;
                }
            } else if (l != 2 && l != 3) {
                j2 = 0;
                if (i1 == this.id) {
                    j2 = -1;
                }

                l2 = iblockaccess.getBlockId(i - 1, j, i1 != this.id ? k + 1 : k - 1);
                j3 = iblockaccess.getBlockId(i + 1, j, i1 != this.id ? k + 1 : k - 1);
                if (l == 4) {
                    j2 = -1 - j2;
                }

                byte2 = 5;
                if ((Block.BLOCKS_OPAQUE[k1] || Block.BLOCKS_OPAQUE[l2]) && !Block.BLOCKS_OPAQUE[l1] && !Block.BLOCKS_OPAQUE[j3]) {
                    byte2 = 5;
                }

                if ((Block.BLOCKS_OPAQUE[l1] || Block.BLOCKS_OPAQUE[j3]) && !Block.BLOCKS_OPAQUE[k1] && !Block.BLOCKS_OPAQUE[l2]) {
                    byte2 = 4;
                }

                return (l != byte2 ? this.textureId + 32 : this.textureId + 16) + j2;
            } else {
                return this.textureId;
            }
        }
    }

    public int getTexture(int i) {
        if (i == 1) {
            return this.textureId - 1;
        } else if (i == 0) {
            return this.textureId - 1;
        } else {
            return i == 3 ? this.textureId + 1 : this.textureId;
        }
    }

    public boolean canPlaceAt(World world, int i, int j, int k) {
        int l = 0;
        if (world.getBlockId(i - 1, j, k) == this.id) {
            ++l;
        }

        if (world.getBlockId(i + 1, j, k) == this.id) {
            ++l;
        }

        if (world.getBlockId(i, j, k - 1) == this.id) {
            ++l;
        }

        if (world.getBlockId(i, j, k + 1) == this.id) {
            ++l;
        }

        if (l > 1) {
            return false;
        } else if (this.isThereANeighborChest(world, i - 1, j, k)) {
            return false;
        } else if (this.isThereANeighborChest(world, i + 1, j, k)) {
            return false;
        } else if (this.isThereANeighborChest(world, i, j, k - 1)) {
            return false;
        } else {
            return !this.isThereANeighborChest(world, i, j, k + 1);
        }
    }

    private boolean isThereANeighborChest(World world, int i, int j, int k) {
        if (world.getBlockId(i, j, k) != this.id) {
            return false;
        } else if (world.getBlockId(i - 1, j, k) == this.id) {
            return true;
        } else if (world.getBlockId(i + 1, j, k) == this.id) {
            return true;
        } else if (world.getBlockId(i, j, k - 1) == this.id) {
            return true;
        } else {
            return world.getBlockId(i, j, k + 1) == this.id;
        }
    }

    public void onBreak(World world, int i, int j, int k) {
        if (!world.isRemote) {
            world.setBlock(i, j, k, 0);
            EntityMimic mimic = new EntityMimic(world);
            mimic.setPosition((double) i + 0.5, (double) j + 1.5, (double) k + 0.5);
            world.spawnEntity(mimic);
        }
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity entityplayer) {
        if (!world.isRemote) {
            world.setBlock(i, j, k, 0);
        }
        return true;
    }

    public int getDroppedItemCount(Random random) {
        return 0;
    }
}
