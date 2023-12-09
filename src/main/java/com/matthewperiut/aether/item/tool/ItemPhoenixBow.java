package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.entity.EntityFlamingArrow;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItem;

public class ItemPhoenixBow extends TemplateItem {
    public ItemPhoenixBow(Identifier i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (entityplayer.inventory.removeItem(Item.ARROW.id)) {
            world.playSound(entityplayer, "mob.ghast.fireball", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                world.spawnEntity(new EntityFlamingArrow(world, entityplayer));
            }
        }

        return itemstack;
    }
}