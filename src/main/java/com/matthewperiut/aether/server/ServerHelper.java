package com.matthewperiut.aether.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityVehicleSetS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

/**
 * Server-only helpers invoked from common code behind ServerPlayerEntity class-name guards.
 *
 * <p>Common classes must never reference ServerPlayerEntity directly (casts/instanceof resolve
 * at class verification, and the production client jar does not contain server classes). A
 * static call into this class links lazily, so it is safe as long as it is only EXECUTED on a
 * dedicated server. Guard call sites with {@code ServerHelper-style class-name checks} done
 * inline (a call to isServerPlayer would itself load this class).</p>
 */
public final class ServerHelper {
    private ServerHelper() {}

    public static void teleport(PlayerEntity player, double x, double y, double z, float yaw, float pitch) {
        ((ServerPlayerEntity) player).networkHandler.teleport(x, y, z, yaw, pitch);
    }

    public static void sendVelocity(PlayerEntity player) {
        ServerPlayerEntity sp = (ServerPlayerEntity) player;
        sp.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(sp.id, sp.velocityX, sp.velocityY, sp.velocityZ));
    }

    public static void sendMountPacket(PlayerEntity player, Entity rider, Entity vehicle) {
        ((ServerPlayerEntity) player).networkHandler.sendPacket(new EntityVehicleSetS2CPacket(rider, vehicle));
    }
}
