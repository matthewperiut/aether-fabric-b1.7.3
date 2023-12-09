package com.matthewperiut.aether.item.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ItemSwordGravitite extends TemplateSwordItem {
    public ItemSwordGravitite(final @NotNull Identifier identifier, final ToolMaterial mat) {
        super(identifier, mat);
    }

    @Override
    public boolean postHit(final ItemStack itemstack, final LivingEntity damageSource, final LivingEntity damageTarget) {
        if (damageTarget != null && damageTarget instanceof PlayerEntity && (damageSource.hurtTime > 0 || damageSource.deathTime > 0)) {
            ++damageSource.yVelocity;
            itemstack.applyDamage(1, damageTarget);
        }
        return true;
    }
}