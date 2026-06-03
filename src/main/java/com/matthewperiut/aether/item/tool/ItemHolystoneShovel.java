package com.matthewperiut.aether.item.tool;

import net.minecraft.item.ShovelItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.Random;

public class ItemHolystoneShovel extends ShovelItem {
    Random random = new Random();

    public ItemHolystoneShovel(ToolMaterial material) {
        super(RetroItemAccess.allocateId(), material);
    }

    @Override
    public boolean postMine(ItemStack arg, int i, int j, int k, int l, LivingEntity arg2) {
        if (random.nextInt(50) == 0) {
            arg2.dropItem(AetherItems.AmbrosiumShard.id, 1, 0.0F);
        }
        return super.postMine(arg, i, j, k, l, arg2);
    }
}
