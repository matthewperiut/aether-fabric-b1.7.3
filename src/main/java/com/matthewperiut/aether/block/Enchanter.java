package com.matthewperiut.aether.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class Enchanter extends TemplateBlockBase { // todo: tileentity TemplateBlockWithEntity {
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
        // todo: tileentity TileEntityEnchanter tileentity = (TileEntityEnchanter)world.getBlockEntity(i, j, k);
        //if (tileentity.isBurning())
        {
            float f = (float) i + 0.5F;
            float f1 = (float) j + 1.0F + random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float) k + 0.5F;
            world.addParticle("smoke", f, f1, f2, 0.0, 0.0, 0.0);
            world.addParticle("flame", f, f1, f2, 0.0, 0.0, 0.0);
        }

    }

    public int getTextureForSide(int i) {
        if (i == 1) {
            return this.texture;
        } else {
            return i == 0 ? this.texture : sideTexture;
        }
    }

    public boolean canUse(World world, int i, int j, int k, PlayerEntity entityplayer) {
        if (world.isClient) {
            return true;
        } else {
            // todo: tileentity TileEntityEnchanter tileentityEnchanter = (TileEntityEnchanter)world.getBlockEntity(i, j, k);
            // todo: gui ModLoader.OpenGUI(entityplayer, new GuiEnchanter(entityplayer.inventory, tileentityEnchanter));
            return true;
        }
    }

    protected BlockEntity createBlockEntity() {
        // todo: tileentity return new TileEntityEnchanter();
        return null;
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

    /* todo: tileentity
    public void onBlockRemoved(World world, int i, int j, int k) {
        TileEntityEnchanter tileentityEnchanter = (TileEntityEnchanter)world.getBlockEntity(i, j, k);

        for(int l = 0; l < tileentityEnchanter.getInventorySize(); ++l) {
            ItemStack itemstack = tileentityEnchanter.getInventoryItem(l);
            if (itemstack != null) {
                float f = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.EnchanterRand.nextFloat() * 0.8F + 0.1F;

                while(itemstack.count > 0) {
                    int i1 = this.EnchanterRand.nextInt(21) + 10;
                    if (i1 > itemstack.count) {
                        i1 = itemstack.count;
                    }

                    itemstack.count -= i1;
                    ItemEntity entityitem = new ItemEntity(world, (double)((float)i + f), (double)((float)j + f1), (double)((float)k + f2), new ItemStack(itemstack.itemId, i1, itemstack.getMeta()));
                    float f3 = 0.05F;
                    entityitem.xVelocity = (double)((float)this.EnchanterRand.nextGaussian() * f3);
                    entityitem.yVelocity = (double)((float)this.EnchanterRand.nextGaussian() * f3 + 0.2F);
                    entityitem.zVelocity = (double)((float)this.EnchanterRand.nextGaussian() * f3);
                    world.spawnEntity(entityitem);
                }
            }
        }

        super.onBlockRemoved(world, i, j, k);
    }*/
}
