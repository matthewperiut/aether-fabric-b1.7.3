package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.BlockEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class TreasureChest extends TemplateBlockBase { // todo: tileentity extends TemplateBlockWithEntity {
    public static int sideTexture;
    private final Random random = new Random();

    protected TreasureChest(Identifier i) {
        super(i, Material.STONE);
    }

    public int getDropCount(Random random) {
        return 0;
    }

    public int getTextureForSide(BlockView iblockaccess, int i, int j, int k, int l) {
        if (l == 1) {
            return 62;
        } else if (l == 0) {
            return 62;
        } else {
            int i1 = iblockaccess.getBlockId(i, j, k - 1);
            int j1 = iblockaccess.getBlockId(i, j, k + 1);
            int k1 = iblockaccess.getBlockId(i - 1, j, k);
            int l1 = iblockaccess.getBlockId(i + 1, j, k);
            byte byte0 = 3;
            if (Block.FULL_OPAQUE[i1] && !Block.FULL_OPAQUE[j1]) {
                byte0 = 3;
            }

            if (Block.FULL_OPAQUE[j1] && !Block.FULL_OPAQUE[i1]) {
                byte0 = 2;
            }

            if (Block.FULL_OPAQUE[k1] && !Block.FULL_OPAQUE[l1]) {
                byte0 = 5;
            }

            if (Block.FULL_OPAQUE[l1] && !Block.FULL_OPAQUE[k1]) {
                byte0 = 4;
            }

            return l != byte0 ? sideTexture : this.texture;
        }
    }

    public int getTextureForSide(int i) {
        if (i == 1) {
            return 62;
        } else if (i == 0) {
            return 62;
        } else {
            return i == 3 ? this.texture : sideTexture;
        }
    }

    public boolean canPlaceAt(World world, int i, int j, int k) {
        return false;
    }

    /* todo: tileentity
    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityplayer) {
        if (world.isClient) {
            return true;
        } else {
            int meta = world.getBlockMeta(i, j, k);
            ChestBlockEntity chest = (ChestBlockEntity)world.getBlockEntity(i, j, k);
            if (meta % 2 == 1) {
                // todo: gui ModLoader.OpenGUI(entityplayer, new GuiTreasureChest(entityplayer.inventory, chest, meta));
                return true;
            } else {
                ItemStack itemstack = entityplayer.inventory.getHeldItem();
                if (itemstack != null && itemstack.itemId == AetherItems.Key.id && itemstack.getMeta() == 0 && meta == 0) {
                    entityplayer.inventory.takeInventoryItem(entityplayer.inventory.selectedHotBarSlot, 1);
                    world.method_223(i, j, k, meta + 1);
                    world.setBlockEntity(i, j, k, chest);
                    return true;
                } else if (itemstack != null && itemstack.itemId == AetherItems.Key.id && itemstack.getMeta() == 1 && meta == 2) {
                    entityplayer.inventory.takeInventoryItem(entityplayer.inventory.selectedHotBarSlot, 1);
                    world.method_223(i, j, k, meta + 1);
                    world.setBlockEntity(i, j, k, chest);
                    return true;
                } else if (itemstack != null && itemstack.itemId == AetherItems.Key.id && itemstack.getMeta() == 2 && meta == 4) {
                    entityplayer.inventory.takeInventoryItem(entityplayer.inventory.selectedHotBarSlot, 1);
                    world.method_223(i, j, k, meta + 1);
                    world.setBlockEntity(i, j, k, chest);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }*/

    protected BlockEntity createBlockEntity() {
        // todo: tileentity return new ChestBlockEntity();
        return null;
    }
}
