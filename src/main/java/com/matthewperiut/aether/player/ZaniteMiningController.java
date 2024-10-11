package com.matthewperiut.aether.player;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ZaniteMiningController {
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
        for (int i = 0; i < list.length; i++) {
            ItemStack l = list[i];
            if (l != null) {
                if (l.itemId == AetherItems.ZaniteRing.id || l.itemId == AetherItems.ZanitePendant.id) {
                    l.damage(1, player);
                    if (l.getDamage2() > l.getMaxDamage() - 2) {
                        for (int j = 0; j < player.inventory.armor.length; j++) {
                            if (player.inventory.armor[j] == null)
                                continue;
                            if (player.inventory.armor[j].equals(l)) {
                                player.inventory.armor[j] = null;
                            }
                        }
                    }
                    else {
                        float additive = ((float) l.getDamage2() / l.getMaxDamage());
                        multiplier += additive;
                    }
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

                float tempExtraMultiplier = d.multiplier;
                // temporary until stapi fix
                ItemStack heldItem = d.player.getHand();
                if (heldItem != null) {
                    if (heldItem.getItem().id == AetherItems.PickZanite.id ||
                            heldItem.getItem().id == AetherItems.AxeZanite.id ||
                            heldItem.getItem().id == AetherItems.ShovelZanite.id ||
                            heldItem.getItem().id == AetherItems.SwordZanite.id) {
                        float additive = (2.f * (float) heldItem.getDamage2() / heldItem.getMaxDamage()) - 0.5f;
                        tempExtraMultiplier += additive;
                    }
                }

                miningSpeed *= (1.0f + tempExtraMultiplier);
            }
        }
        if (!found) {
            data.add(new PlayerData(player, 0, getMultiplier(player)));
        }

        return miningSpeed;
    }
}
