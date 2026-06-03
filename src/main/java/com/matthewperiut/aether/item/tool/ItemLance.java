package com.matthewperiut.aether.item.tool;

import net.minecraft.item.Item;
import com.matthewperiut.aether.item.util.CustomReach;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.HitResultType;

public class ItemLance extends Item implements CustomReach {
    private final int weaponDamage;

    public ItemLance(ToolMaterial enumtoolmaterial) {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
        this.setMaxDamage(enumtoolmaterial.getDurability());
        this.weaponDamage = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return block.id != Block.COBWEB.id ? 1.5F : 15.0F;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        itemstack.damage(1, entityliving1);
        return true;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }

    public int getAttackDamage(Entity entity) {
        return this.weaponDamage;
    }

    public boolean isHandheld() {
        return true;
    }

    public boolean isSuitableFor(Block block) {
        return block.id == Block.COBWEB.id;
    }

    public boolean reachItemMatches(ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        } else {
            return itemstack.itemId == AetherItems.Lance.id;
        }
    }

    @Override
    public double getReach(ItemStack itemStack, PlayerEntity playerEntity, HitResultType hitType, double v) {
        return 10.0F;
    }
}
