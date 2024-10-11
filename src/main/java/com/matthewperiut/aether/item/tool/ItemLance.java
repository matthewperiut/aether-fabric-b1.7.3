package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.HitResultType;
import net.modificationstation.stationapi.api.item.CustomReachProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemLance extends TemplateItem implements CustomReachProvider {
    private final int weaponDamage;

    public ItemLance(Identifier i, ToolMaterial enumtoolmaterial) {
        super(i);
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
