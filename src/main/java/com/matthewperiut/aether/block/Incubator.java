package com.matthewperiut.aether.block;

import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.container.ContainerIncubator;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

import static com.matthewperiut.aether.blockentity.AetherBlockEntities.MOD_ID;

public class Incubator extends TemplateBlockWithEntity {
    public static int sideTexture;
    private final Random IncubatorRand;

    protected Incubator(Identifier blockID) {
        super(blockID, Material.STONE);
        this.IncubatorRand = new Random();
    }

    public static void updateIncubatorBlockState(boolean flag, World world, int i, int j, int k) {
        int l = world.getBlockMeta(i, j, k);
        BlockEntity tileentity = world.getBlockEntity(i, j, k);
        world.setBlockMeta(i, j, k, l);
        world.setBlockEntity(i, j, k, tileentity);
    }

    public void onPlaced(World world, int i, int j, int k) {
        super.onPlaced(world, i, j, k);
        this.setDefaultDirection(world, i, j, k);
    }

    private void setDefaultDirection(World world, int i, int j, int k) {
        if (!world.isRemote) {
            int l = world.getBlockId(i, j, k - 1);
            int i1 = world.getBlockId(i, j, k + 1);
            int j1 = world.getBlockId(i - 1, j, k);
            int k1 = world.getBlockId(i + 1, j, k);
            byte byte0 = 3;
            if (Block.BLOCKS_OPAQUE[l] && !Block.BLOCKS_OPAQUE[i1]) {
                byte0 = 3;
            }

            if (Block.BLOCKS_OPAQUE[i1] && !Block.BLOCKS_OPAQUE[l]) {
                byte0 = 2;
            }

            if (Block.BLOCKS_OPAQUE[j1] && !Block.BLOCKS_OPAQUE[k1]) {
                byte0 = 5;
            }

            if (Block.BLOCKS_OPAQUE[k1] && !Block.BLOCKS_OPAQUE[j1]) {
                byte0 = 4;
            }

            world.setBlockMeta(i, j, k, byte0);
        }
    }

    public int getTexture(int i) {
        if (i == 1) {
            return this.textureId;
        } else {
            return i == 0 ? this.textureId : sideTexture;
        }
    }

    public boolean onUse(World world, int i, int j, int k, PlayerEntity player) {
        if (world.isRemote) {
            return true;
        } else {
            BlockEntityIncubator tileentityIncubator = (BlockEntityIncubator) world.getBlockEntity(i, j, k);
            GuiHelper.openGUI(player, MOD_ID.id("incubator"), tileentityIncubator, new ContainerIncubator(player.inventory, tileentityIncubator));
            return true;
        }
    }

    protected BlockEntity createBlockEntity() {
        return new BlockEntityIncubator();
    }

    public void onPlaced(World world, int i, int j, int k, LivingEntity entityliving) {
        int l = MathHelper.floor((double) (entityliving.yaw * 4.0F / 360.0F) + 0.5) & 3;
        if (l == 0) {
            world.setBlockMeta(i, j, k, 2);
        }

        if (l == 1) {
            world.setBlockMeta(i, j, k, 5);
        }

        if (l == 2) {
            world.setBlockMeta(i, j, k, 3);
        }

        if (l == 3) {
            world.setBlockMeta(i, j, k, 4);
        }

    }

    public void onBreak(World world, int i, int j, int k) {
        BlockEntityIncubator tileentityIncubator = (BlockEntityIncubator) world.getBlockEntity(i, j, k);

        for (int l = 0; l < tileentityIncubator.size(); ++l) {
            ItemStack itemstack = tileentityIncubator.getStack(l);
            if (itemstack != null) {
                float f = this.IncubatorRand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.IncubatorRand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.IncubatorRand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.count > 0) {
                    int i1 = this.IncubatorRand.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }

                    itemstack.count -= i1;
                    ItemEntity entityitem = new ItemEntity(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemId, i1, itemstack.getDamage()));
                    float f3 = 0.05F;
                    entityitem.velocityX = (float) this.IncubatorRand.nextGaussian() * f3;
                    entityitem.velocityY = (float) this.IncubatorRand.nextGaussian() * f3 + 0.2F;
                    entityitem.velocityZ = (float) this.IncubatorRand.nextGaussian() * f3;
                    world.spawnEntity(entityitem);
                }
            }
        }

        super.onBreak(world, i, j, k);
    }
}
