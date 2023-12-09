package com.matthewperiut.aether.item.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateAxeItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemZaniteAxe extends TemplateAxeItem {
    public ItemZaniteAxe(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public float getStrengthOnBlock(ItemStack itemstack, Block block) {
        return super.getStrengthOnBlock(itemstack, block) * (2.0F * (float) itemstack.getMeta() / (float) itemstack.getItem().getDurability() + 0.5F);
    }
}
