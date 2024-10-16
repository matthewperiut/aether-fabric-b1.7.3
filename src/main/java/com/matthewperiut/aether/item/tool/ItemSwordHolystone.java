package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ItemSwordHolystone extends TemplateSwordItem {
    Random random = new Random();

    public ItemSwordHolystone(Identifier itemID, ToolMaterial mat) {
        super(itemID, mat);
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