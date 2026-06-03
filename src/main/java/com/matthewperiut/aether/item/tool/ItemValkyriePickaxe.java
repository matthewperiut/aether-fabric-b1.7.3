package com.matthewperiut.aether.item.tool;

import com.periut.retroapi.register.item.RetroItemAccess;

import net.minecraft.item.PickaxeItem;
import com.matthewperiut.aether.item.util.CustomReach;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.hit.HitResultType;
import org.jetbrains.annotations.NotNull;

public class ItemValkyriePickaxe extends PickaxeItem implements CustomReach {
    public ItemValkyriePickaxe(final ToolMaterial enumtoolmaterial) {
        super(RetroItemAccess.allocateId(), enumtoolmaterial);
    }

    @Override
    public double getReach(ItemStack itemInstance, PlayerEntity player, HitResultType type, double currentReach) {
        return 10.f;
    }
}
