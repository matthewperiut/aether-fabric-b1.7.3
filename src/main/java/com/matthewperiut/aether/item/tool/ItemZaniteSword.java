package com.matthewperiut.aether.item.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemZaniteSword extends TemplateSwordItem {
    public ItemZaniteSword(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, Block block) {// just for cobwebs
        return super.getMiningSpeedMultiplier(stack, block) * (2.0F * (float) stack.getDamage() / (float) stack.getItem().getMaxDamage() + 0.5F);
    }
}
