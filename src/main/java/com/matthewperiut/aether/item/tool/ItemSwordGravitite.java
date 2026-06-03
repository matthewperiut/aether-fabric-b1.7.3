package com.matthewperiut.aether.item.tool;

import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.item.SwordItem;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;

public class ItemSwordGravitite extends SwordItem {
    public ItemSwordGravitite(final ToolMaterial mat) {
        super(RetroItemAccess.allocateId(), mat);
    }

    @Override
    public boolean postHit(final ItemStack itemstack, final LivingEntity damageSource, final LivingEntity damageTarget) {
        if (damageTarget != null && damageTarget instanceof PlayerEntity && (damageSource.hurtTime > 0 || damageSource.deathTime > 0)) {
            ++damageSource.velocityY;
            itemstack.damage(1, damageTarget);
        }
        return true;
    }
}