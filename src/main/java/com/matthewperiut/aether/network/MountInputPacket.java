package com.matthewperiut.aether.network;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.entity.MountInput;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import net.ornithemc.osl.networking.api.ChannelIdentifiers;
import net.ornithemc.osl.networking.api.ChannelRegistry;
import net.ornithemc.osl.networking.api.client.ClientPlayNetworking;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;

/**
 * Syncs rider input (forward, strafe, jump) from client to server for Aether mounts.
 * Called every client tick when the player is riding a MountInput entity.
 */
public class MountInputPacket {
    public static final NamespacedIdentifier ID =
            ChannelRegistry.register(ChannelIdentifiers.from(Aether.MOD_ID, "mount_input"), false, true);

    public static void register() {
        ServerPlayNetworking.registerListener(ID, (ctx, buffer) -> {
            float forward = buffer.readFloat();
            float strafe = buffer.readFloat();
            float yaw = buffer.readFloat();
            float pitch = buffer.readFloat();
            boolean jump = buffer.readBoolean();
            ctx.ensureOnMainThread();
            PlayerEntity player = ctx.player();
            if (player == null) return;
            Entity vehicle = player.vehicle;
            if (vehicle instanceof MountInput mount) {
                mount.setMountInput(forward, strafe, jump, yaw, pitch);
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendInput(LivingEntity rider) {
        if (rider.vehicle instanceof MountInput) {
            LivingEntityAccessor accessor = (LivingEntityAccessor) rider;
            ClientPlayNetworking.send(ID, buffer -> {
                buffer.writeFloat(accessor.getForwardVelocity());
                buffer.writeFloat(accessor.getHorizontalVelocity());
                buffer.writeFloat(rider.yaw);
                buffer.writeFloat(rider.pitch);
                buffer.writeBoolean(accessor.getJumping());
            });
        }
    }
}
