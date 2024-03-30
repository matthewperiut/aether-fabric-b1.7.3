package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplatePickaxeItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemGravititePickaxe extends TemplatePickaxeItem {
    public ItemGravititePickaxe(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public boolean useOnBlock(ItemStack item, PlayerEntity player, World world, int x, int y, int z, int side) {
        if (!world.isClient) {
            BlockState b = world.getBlockState(x, y, z);

            TagKey<Block> tag = this.getEffectiveBlocks(item);
            if (b.isIn(tag)) {
                final int blockID = world.getBlockId(x, y, z);
                final int metadata = world.getBlockMeta(x, y, z);
                final EntityFloatingBlock floating = new EntityFloatingBlock(world, x + 0.5f, y + 0.5f, z + 0.5f, blockID, metadata);
                world.spawnEntity(floating);
                item.applyDamage(1, player);
            }

            return super.useOnBlock(item, player, world, x, y, z, side);
        }
        return false;
    }
}
