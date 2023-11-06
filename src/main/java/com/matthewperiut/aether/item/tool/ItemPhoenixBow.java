package com.matthewperiut.aether.item.tool;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemPhoenixBow extends TemplateItemBase {
    public ItemPhoenixBow(Identifier i) {
        super(i);
        this.maxStackSize = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (entityplayer.inventory.removeItem(Item.ARROW.id)) {
            world.playSound(entityplayer, "mob.ghast.fireball", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                // todo: entity world.spawnEntity(new EntityFlamingArrow(world, entityplayer));
            }
        }

        return itemstack;
    }
}