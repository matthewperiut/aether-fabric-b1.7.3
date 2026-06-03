package com.matthewperiut.aether.network;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.entity.living.EntityAerbunny;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import net.ornithemc.osl.networking.api.ChannelIdentifiers;
import net.ornithemc.osl.networking.api.ChannelRegistry;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;

/**
 * Syncs player jumping state to server for Aerbunny slow-fall mechanic.
 * The Aerbunny rides the player, so we need the player's jump input on the server.
 */
public class AerbunnyJumpPacket {
    public static final NamespacedIdentifier ID =
            ChannelRegistry.register(ChannelIdentifiers.from(Aether.MOD_ID, "aerbunny_jump"), false, true);

    public static void register() {
        ServerPlayNetworking.registerListener(ID, (ctx, buffer) -> {
            boolean jumping = buffer.readBoolean();
            ctx.ensureOnMainThread();
            PlayerEntity player = ctx.player();
            if (player == null || player.passenger == null) return;
            if (player.passenger instanceof EntityAerbunny bunny) {
                bunny.vehicleJumping = jumping;
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendJumping(boolean jumping) {
        ClientPlayNetworking.send(ID, buffer -> buffer.writeBoolean(jumping));
    }
}
