package com.matthewperiut.aether.player;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ZaniteAccessoryController {
    private static class PlayerData {
        public PlayerEntity player;
        public int ticks;
        public float multiplier = 0;

        PlayerData(PlayerEntity player, int ticks, float multiplier) {
            this.player = player;
            this.ticks = ticks;
            this.multiplier = multiplier;
        }
    }

    static ArrayList<PlayerData> data = new ArrayList<>();

    private static float getMultiplier(PlayerEntity player) {
        float multiplier = 0;
        ItemStack[] list = AccessoryAccess.getAccessories(player);
        for (ItemStack l : list) {
            if (l != null) {
                if (l.itemId == AetherItems.ZaniteRing.id || l.itemId == AetherItems.ZanitePendant.id) {
                    l.applyDamage(1, player);
                    System.out.println(l.getDamage() + " / " + l.getDurability());
                    float additive = ((float) l.getDamage() / l.getDurability());
                    multiplier += additive;
                }
            }
        }
        return multiplier;
    }

    public static float modifyMiningSpeed(float miningSpeed, PlayerEntity player) {
        boolean found = false;
        for (PlayerData d : data) {
            if (d.player.equals(player)) {
                if (d.ticks == 120) {
                    d.ticks = 0;
                    // calculate
                    d.multiplier = getMultiplier(player);
                }
                found = true;
                d.ticks++;
                miningSpeed *= (1.0f + d.multiplier);
                System.out.println(d.multiplier);
            }
        }
        if (!found) {
            data.add(new PlayerData(player, 0, getMultiplier(player)));
        }


        return miningSpeed;
    }
}
