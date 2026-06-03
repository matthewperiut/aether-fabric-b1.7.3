package com.matthewperiut.aether.item.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResultType;

/**
 * Replacement for StationAPI's CustomReachProvider, consumed by Aether's own reach mixins.
 * Items implementing this extend the player's block/entity interaction reach while held.
 */
public interface CustomReach {
    double getReach(ItemStack itemStack, PlayerEntity playerEntity, HitResultType hitType, double defaultReach);
}
