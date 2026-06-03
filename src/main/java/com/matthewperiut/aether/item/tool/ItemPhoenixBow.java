package com.matthewperiut.aether.item.tool;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.entity.projectile.EntityFlamingArrow;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPhoenixBow extends Item {
    public ItemPhoenixBow() {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (entityplayer.inventory.remove(Item.ARROW.id)) {
            world.playSound(entityplayer, "mob.ghast.fireball", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntity(new EntityFlamingArrow(world, entityplayer));
            }
        }

        return itemstack;
    }
}