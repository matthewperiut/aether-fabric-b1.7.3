package com.matthewperiut.aether.block;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.container.ContainerEnchanter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

import java.util.Random;

import static com.matthewperiut.aether.blockentity.AetherBlockEntities.MOD_ID;

public class Enchanter extends TemplateBlockWithEntity {
    public static int sideTexture;
    private final Random EnchanterRand;

    protected Enchanter(Identifier blockID) {
        super(blockID, Material.STONE);
        this.EnchanterRand = new Random();
    }

    public static void updateEnchanterBlockState(boolean flag, World world, int i, int j, int k) {
        int l = world.getBlockMeta(i, j, k);
        BlockEntity tileentity = world.getBlockEntity(i, j, k);
        world.setBlockMeta(i, j, k, l);
        world.setBlockEntity(i, j, k, tileentity);
    }

    public void onBlockPlaced(World world, int i, int j, int k) {
        super.onBlockPlaced(world, i, j, k);
        this.setDefaultDirection(world, i, j, k);
    }

    private void setDefaultDirection(World world, int i, int j, int k) {
        if (!world.isClient) {
            int l = world.getBlockId(i, j, k - 1);
            int i1 = world.getBlockId(i, j, k + 1);
            int j1 = world.getBlockId(i - 1, j, k);
            int k1 = world.getBlockId(i + 1, j, k);
            byte byte0 = 3;
            if (Block.FULL_OPAQUE[l] && !Block.FULL_OPAQUE[i1]) {
                byte0 = 3;
            }

            if (Block.FULL_OPAQUE[i1] && !Block.FULL_OPAQUE[l]) {
                byte0 = 2;
            }

            if (Block.FULL_OPAQUE[j1] && !Block.FULL_OPAQUE[k1]) {
                byte0 = 5;
            }

            if (Block.FULL_OPAQUE[k1] && !Block.FULL_OPAQUE[j1]) {
                byte0 = 4;
            }

            world.setBlockMeta(i, j, k, byte0);
        }
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (world.isClient) {
            BlockEntityEnchanter tileentity = (BlockEntityEnchanter) world.getBlockEntity(i, j, k);
            if (tileentity.isBurning()) {
                float f = (float) i + 0.5F;
                float f1 = (float) j + 1.0F + random.nextFloat() * 6.0F / 16.0F;
                float f2 = (float) k + 0.5F;
                world.addParticle("smoke", f, f1, f2, 0.0, 0.0, 0.0);
                world.addParticle("flame", f, f1, f2, 0.0, 0.0, 0.0);
            }
        } else {
            super.randomDisplayTick(world, i, j, k, random);
        }
    }

    public int getTextureForSide(int i) {
        if (i == 1) {
            return this.texture;
        } else {
            return i == 0 ? this.texture : sideTexture;
        }
    }

    public boolean canUse(World world, int i, int j, int k, PlayerEntity player) {
        if (world.isClient) {
            return true;
        } else {
            BlockEntityEnchanter tileentityEnchanter = (BlockEntityEnchanter) world.getBlockEntity(i, j, k);
            GuiHelper.openGUI(player, MOD_ID.id("enchanter"), tileentityEnchanter, new ContainerEnchanter(player.inventory, tileentityEnchanter));
            return true;
        }
    }

    protected BlockEntity createBlockEntity() {
        return new BlockEntityEnchanter();
    }

    public void afterPlaced(World world, int i, int j, int k, LivingEntity entityliving) {
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

    public void onBlockRemoved(World world, int i, int j, int k) {
        BlockEntityEnchanter tileentityEnchanter = (BlockEntityEnchanter) world.getBlockEntity(i, j, k);

        for (int l = 0; l < tileentityEnchanter.getInventorySize(); ++l) {
            ItemStack itemstack = tileentityEnchanter.getInventoryItem(l);
            if (itemstack != null) {
                float f = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;

                while (itemstack.count > 0) {
                    int i1 = this.EnchanterRand.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }

                    itemstack.count -= i1;
                    ItemEntity entityitem = new ItemEntity(world, (float) i + f, (float) j + f1, (float) k + f2, new ItemStack(itemstack.itemId, i1, itemstack.getMeta()));
                    float f3 = 0.05F;
                    entityitem.xVelocity = (float) this.EnchanterRand.nextGaussian() * f3;
                    entityitem.yVelocity = (float) this.EnchanterRand.nextGaussian() * f3 + 0.2F;
                    entityitem.zVelocity = (float) this.EnchanterRand.nextGaussian() * f3;
                    world.spawnEntity(entityitem);
                }
            }
        }

        super.onBlockRemoved(world, i, j, k);
    }
}
