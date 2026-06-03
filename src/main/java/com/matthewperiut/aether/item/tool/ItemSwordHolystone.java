package com.matthewperiut.aether.item.tool;

import net.minecraft.item.SwordItem;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

import java.util.Random;

public class ItemSwordHolystone extends SwordItem {
    Random random = new Random();

    public ItemSwordHolystone(ToolMaterial mat) {
        super(RetroItemAccess.allocateId(), mat);
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (random.nextInt(25) == 0 && entityliving1 instanceof PlayerEntity && (entityliving.hurtTime > 0 || entityliving.deathTime > 0)) {
            entityliving.dropItem(AetherItems.AmbrosiumShard.id, 1, 0.0F);
            itemstack.damage(1, entityliving1);
        }

        itemstack.damage(1, entityliving1);
        return true;
    }
}