package com.matthewperiut.aether.network;

import com.matthewperiut.aether.entity.living.EntityAerbunny;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.registry.MessageListenerRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

/**
 * Syncs player jumping state to server for Aerbunny slow-fall mechanic.
 * The Aerbunny rides the player, so we need the player's jump input on the server.
 */
public class AerbunnyJumpPacket {
    public static final Identifier ID = MOD_ID.id("aerbunny_jump");

    public static void register() {
        Registry.register(MessageListenerRegistry.INSTANCE, ID, AerbunnyJumpPacket::handle);
    }

    private static void handle(PlayerEntity player, MessagePacket packet) {
        if (player == null || player.passenger == null) return;
        if (player.passenger instanceof EntityAerbunny bunny) {
            boolean jumping = packet.booleans != null && packet.booleans.length > 0 && packet.booleans[0];
            bunny.vehicleJumping = jumping;
        }
    }
}
