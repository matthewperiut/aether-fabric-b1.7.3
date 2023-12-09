package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateShovelItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ItemHolystoneShovel extends TemplateShovelItem {
    Random random = new Random();

    public ItemHolystoneShovel(Identifier identifier, ToolMaterial material) {
        super(identifier, material);
    }

    @Override
    public boolean postMine(ItemStack arg, int i, int j, int k, int l, LivingEntity arg2) {
        if (random.nextInt(50) == 0) {
            arg2.dropItem(AetherItems.AmbrosiumShard.id, 1, 0.0F);
        }
        return super.postMine(arg, i, j, k, l, arg2);
    }
}
