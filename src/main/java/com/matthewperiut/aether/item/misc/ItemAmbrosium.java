package com.matthewperiut.aether.item.misc;

import net.minecraft.item.FoodItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmbrosium extends FoodItem {
    private final int healAmount;

    public ItemAmbrosium(int healAmount, boolean isWolfFood) {
        super(RetroItemAccess.allocateId(),healAmount,isWolfFood);
        this.healAmount = healAmount;
        this.maxCount = 64;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        --itemstack.count;
        entityplayer.heal(this.healAmount);
        return itemstack;
    }

    public int getHealAmount() {
        return this.healAmount;
    }
}
