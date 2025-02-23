package com.matthewperiut.aether.item.tool;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ItemVampireBlade extends TemplateItem {
    private static final Random random = new Random();
    private final int weaponDamage;

    public ItemVampireBlade(Identifier i) {
        super(i);
        this.maxCount = 1;
        this.setMaxDamage(ToolMaterial.DIAMOND.getDurability());
        this.weaponDamage = 4 + ToolMaterial.DIAMOND.getAttackDamage() * 2;
    }

    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        PlayerEntity player = (PlayerEntity) entityliving1;
        if (player.health < 20 + ((PlayerExtraHP) player).getExtraHP()) {
            ++player.health;
        }

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
}
