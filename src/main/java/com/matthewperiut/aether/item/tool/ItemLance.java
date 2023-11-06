package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.HitType;
import net.modificationstation.stationapi.api.item.CustomReachProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemLance extends TemplateItemBase implements CustomReachProvider
{
    private final int weaponDamage;

    public ItemLance(Identifier i, ToolMaterial enumtoolmaterial)
    {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(enumtoolmaterial.getDurability());
        this.weaponDamage = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    public float getStrengthOnBlock(ItemStack itemstack, Block block)
    {
        return block.id != Block.COBWEB.id ? 1.5F : 15.0F;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1)
    {
        itemstack.applyDamage(1, entityliving1);
        return true;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving)
    {
        itemstack.applyDamage(2, entityliving);
        return true;
    }

    public int getAttackDamage(Entity entity)
    {
        return this.weaponDamage;
    }

    public boolean isRendered3d()
    {
        return true;
    }

    public boolean isEffectiveOn(Block block)
    {
        return block.id == Block.COBWEB.id;
    }

    public boolean reachItemMatches(ItemStack itemstack)
    {
        if (itemstack == null)
        {
            return false;
        }
        else
        {
            return itemstack.itemId == AetherItems.Lance.id;
        }
    }

    @Override
    public double getReach(ItemStack itemStack, PlayerEntity playerEntity, HitType hitType, double v)
    {
        return 10.0F;
    }
}
