package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;

public class UtilSkyroot {
    public static boolean sword(PlayerEntity player) {
        if (player == null)
            return false;
        if (player.inventory == null)
            return false;
        if (player.inventory.getHeldItem() != null) {
            System.out.println(player.inventory.getHeldItem().getItem().id == AetherItems.SwordSkyroot.id);
            return player.inventory.getHeldItem().getItem().id == AetherItems.SwordSkyroot.id;
        }
        return false;
    }

    public static boolean pickaxe(PlayerEntity player) {
        if (player == null)
            return false;
        if (player.inventory == null)
            return false;
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.PickSkyroot;
        }
        return false;
    }

    public static boolean axe(PlayerEntity player) {
        if (player == null)
            return false;
        if (player.inventory == null)
            return false;
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.AxeSkyroot;
        }
        return false;
    }

    public static boolean shovel(PlayerEntity player) {
        if (player == null)
            return false;
        if (player.inventory == null)
            return false;
        if (player.inventory.getHeldItem() != null) {
            return player.inventory.getHeldItem().getItem() == AetherItems.ShovelSkyroot;
        }
        return false;
    }
}
