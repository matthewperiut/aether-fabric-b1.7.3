package com.matthewperiut.aether.item.misc;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.periut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLifeShard extends Item {
    public ItemLifeShard() {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (world.isRemote)
            return itemstack;
        if (itemstack.count > 0) {
            --itemstack.count;
            ((PlayerExtraHP) entityplayer).setExtraHP(((PlayerExtraHP) entityplayer).getExtraHP() + 2);
            entityplayer.heal(2);
        }
        return itemstack;
    }
}
