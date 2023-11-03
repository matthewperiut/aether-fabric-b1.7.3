package com.matthewperiut.aether.item.misc;

import com.matthewperiut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import org.jetbrains.annotations.NotNull;

public class ItemLifeShard extends TemplateItemBase {
    public ItemLifeShard(Identifier i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        --itemstack.count;
        ((PlayerExtraHP)entityplayer).setExtraHP(((PlayerExtraHP)entityplayer).getExtraHP() + 2);
        entityplayer.health += 2;
        return itemstack;
    }
}
