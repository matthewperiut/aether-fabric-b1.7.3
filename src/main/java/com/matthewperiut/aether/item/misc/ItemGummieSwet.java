package com.matthewperiut.aether.item.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemGummieSwet extends TemplateItemBase {
    private final int healAmount;
    private final boolean damZero;
    private final boolean damOne;

    public ItemGummieSwet(Identifier i) {
        super(i);
        this.maxStackSize = 64;
        this.damZero = false;
        this.damOne = false;
        this.healAmount = 20;
        this.setHasSubItems(true);
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        --itemstack.count;
        entityplayer.addHealth(this.healAmount);
        return itemstack;
    }

    public int getHealAmount() {
        return this.healAmount;
    }

    public int getNameColor(int damage) {
        return damage == 1 ? 16777087 : 8765927;
    }

    public String getTranslationKey(ItemStack stack) {
        int i = stack.getMeta();
        if (i > 1) {
            i = 1;
        }

        return this.getTranslationKey() + i;
    }
}
