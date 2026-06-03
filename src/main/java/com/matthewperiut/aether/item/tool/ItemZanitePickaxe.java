package com.matthewperiut.aether.item.tool;

import net.minecraft.item.PickaxeItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class ItemZanitePickaxe extends PickaxeItem {
    public ItemZanitePickaxe(ToolMaterial material) {
        super(RetroItemAccess.allocateId(), material);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return super.getMiningSpeedMultiplier(itemstack, block) * (2.0F * (float) itemstack.getDamage() / (float) itemstack.getItem().getMaxDamage() + 0.5F);
    }
}
