package com.matthewperiut.aether.item.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateSword;

public class ItemZaniteSword extends TemplateSword {
    public ItemZaniteSword(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public float getStrengthOnBlock(ItemStack itemstack, Block block) { // just for cobwebs
        return super.getStrengthOnBlock(itemstack, block) * (2.0F * (float) itemstack.getMeta() / (float) itemstack.getItem().getDurability() + 0.5F);
    }
}
