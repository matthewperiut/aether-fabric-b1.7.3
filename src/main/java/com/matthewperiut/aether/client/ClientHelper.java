package com.matthewperiut.aether.client;

import com.matthewperiut.aether.client.gui.GuiLore;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;

/**
 * Client-only helpers invoked from common code behind environment guards.
 *
 * <p>Common classes must never reference client-only classes directly (field types, casts,
 * instanceof, extends) — those constructs resolve at class verification, and the production
 * server jar does not contain client classes. A static call into this class links lazily, so
 * it is safe as long as it is only EXECUTED on the client.</p>
 */
public final class ClientHelper {
    private ClientHelper() {}

    private static Minecraft mc() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    /** The entity under the crosshair, or null. */
    public static Entity crosshairEntity() {
        HitResult target = mc().crosshairTarget;
        return target == null ? null : target.entity;
    }

    public static boolean isLocalPlayer(PlayerEntity player) {
        return player.equals(mc().player);
    }

    public static void setRecordOverlay(String message) {
        mc().inGameHud.setRecordPlayingOverlay(message);
    }

    public static void openLoreScreen(PlayerEntity player, int damage) {
        mc().setScreen(new GuiLore(player.inventory, damage));
    }
}
