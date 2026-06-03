package com.matthewperiut.aether.item.tool;

import net.minecraft.item.PickaxeItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class ItemGravititePickaxe extends PickaxeItem {
    public ItemGravititePickaxe(ToolMaterial material) {
        super(RetroItemAccess.allocateId(), material);
    }

    @Override
    public boolean useOnBlock(ItemStack item, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (!world.isRemote) {
            Block b = Block.BLOCKS[world.getBlockId(x, y, z)];
            if (b != null && this.isSuitableFor(b)) {
                final int blockID = world.getBlockId(x, y, z);
                if (blockID != 52) {
                    final int metadata = world.getBlockMeta(x, y, z);
                    final EntityFloatingBlock floating = new EntityFloatingBlock(world, x + 0.5f, y + 0.5f, z + 0.5f, blockID, metadata);
                    world.spawnEntity(floating);
                    item.damage(1, player);
                }
            }

            return super.useOnBlock(item, player, world, x, y, z, side);
        }
        return false;
    }
}
