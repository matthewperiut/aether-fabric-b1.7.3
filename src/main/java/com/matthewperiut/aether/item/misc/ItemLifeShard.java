package com.matthewperiut.aether.item.misc;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class ItemLifeShard extends TemplateItem {
    public ItemLifeShard(Identifier i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (world.isClient)
            return itemstack;
        if (itemstack.count > 0) {
            --itemstack.count;
            ((PlayerExtraHP) entityplayer).setExtraHP(((PlayerExtraHP) entityplayer).getExtraHP() + 2);
            entityplayer.addHealth(2);
        }
        return itemstack;
    }
}
