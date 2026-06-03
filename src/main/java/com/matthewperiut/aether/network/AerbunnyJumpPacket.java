package com.matthewperiut.aether.network;

import com.matthewperiut.aether.Aether;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import net.ornithemc.osl.networking.api.ChannelIdentifiers;
import net.ornithemc.osl.networking.api.ChannelRegistry;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;

/**
 * Syncs player jumping state to server for Aerbunny slow-fall mechanic.
 * The Aerbunny rides the player, so we need the player's jump input on the server.
 */
public class AerbunnyJumpPacket {
    public static final NamespacedIdentifier ID =
            ChannelRegistry.register(ChannelIdentifiers.from(Aether.MOD_ID, "aerbunny_jump"), false, true);

    /** Forces the static channel registration; the server listener lives in AetherServer
     * (environment isolation: its handler touches ServerPlayerEntity). */
    public static void registerChannel() {
    }

    @Environment(EnvType.CLIENT)
    public static void sendJumping(boolean jumping) {
        ClientPlayNetworking.send(ID, buffer -> buffer.writeBoolean(jumping));
    }
}
