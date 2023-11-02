package com.matthewperiut.aether.item.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class Ambrosium extends TemplateItemBase {
    private final int healAmount;

    public Ambrosium(Identifier identifier, int healAmount) {
        super(identifier);
        this.healAmount = healAmount;
        this.maxStackSize = 64;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        --itemstack.count;
        entityplayer.addHealth(this.healAmount);
        return itemstack;
    }

    public int getHealAmount() {
        return this.healAmount;
    }
}
