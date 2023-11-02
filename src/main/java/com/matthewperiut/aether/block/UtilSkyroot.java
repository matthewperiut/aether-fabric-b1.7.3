package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;

public class UtilSkyroot {
    public static boolean sword(PlayerEntity player) {
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.SwordSkyroot;
        }
        return false;
    }

    public static boolean pickaxe(PlayerEntity player) {
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.PickSkyroot;
        }
        return false;
    }

    public static boolean axe(PlayerEntity player) {
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.AxeSkyroot;
        }
        return false;
    }

    public static boolean shovel(PlayerEntity player) {
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.ShovelSkyroot;
        }
        return false;
    }
}
