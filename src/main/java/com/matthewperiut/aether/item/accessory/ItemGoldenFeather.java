package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemGoldenFeather extends ItemMoreArmor {
    public ItemGoldenFeather(int j, int k, int l) {
        super(j, k, l);
    }

    public static void slowFall(PlayerEntity player) {
        if (player.velocityY < 0) {
            if (player.isSneaking()) // isSneaking
                player.velocityY *= 0.9;
            else player.velocityY *= 0.6;

            ((EntityAccessor) (player)).setFallDistance(-1.0F);
        }
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity playerBase, ItemStack itemStack) {
        if (!playerBase.onGround) {
            slowFall(playerBase);
            if (playerBase.age % 120 == 0) {
                itemStack.damage(1, playerBase);
                if (itemStack.getDamage2() > itemStack.getMaxDamage() - 2) {
                    itemStack = null;
                }
            }
        }

        return itemStack;
    }
}
