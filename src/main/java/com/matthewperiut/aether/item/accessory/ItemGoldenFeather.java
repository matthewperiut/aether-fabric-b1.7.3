package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemGoldenFeather extends ItemMoreArmor {
    public ItemGoldenFeather(Identifier i, int j, int k, int l) {
        super(i, j, k, l);
    }

    public static void slowFall(PlayerEntity player) {
        if (player.yVelocity < 0) {
            if (player.method_1373()) // isSneaking
                player.yVelocity *= 0.9;
            else player.yVelocity *= 0.6;

            ((EntityAccessor) (player)).setFallDistance(-1.0F);
        }
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity playerBase, ItemStack itemStack) {
        if (!playerBase.onGround) {
            slowFall(playerBase);
            if (playerBase.field_1645 % 120 == 0) {
                itemStack.applyDamage(1, playerBase);
                if (itemStack.getDamage() > itemStack.getDurability() - 2) {
                    itemStack = null;
                }
            }
        }

        return itemStack;
    }
}
