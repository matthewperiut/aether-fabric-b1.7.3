package com.matthewperiut.aether.item.tool;

import net.minecraft.item.ShovelItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class ItemZaniteShovel extends ShovelItem {
    public ItemZaniteShovel(ToolMaterial material) {
        super(RetroItemAccess.allocateId(), material);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return super.getMiningSpeedMultiplier(itemstack, block) * (2.0F * (float) itemstack.getDamage() / (float) itemstack.getItem().getMaxDamage() + 0.5F);
    }
}
