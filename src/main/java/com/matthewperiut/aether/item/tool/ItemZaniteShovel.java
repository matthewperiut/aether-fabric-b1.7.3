package com.matthewperiut.aether.item.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateShovelItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemZaniteShovel extends TemplateShovelItem {
    public ItemZaniteShovel(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return super.getMiningSpeedMultiplier(itemstack, block) * (2.0F * (float) itemstack.getDamage() / (float) itemstack.getItem().getMaxDamage() + 0.5F);
    }
}
