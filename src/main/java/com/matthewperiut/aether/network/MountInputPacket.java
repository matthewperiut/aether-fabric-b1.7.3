package com.matthewperiut.aether.network;

import com.matthewperiut.aether.entity.MountInput;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.registry.MessageListenerRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

/**
 * Syncs rider input (forward, strafe, jump) from client to server for Aether mounts.
 * Called every client tick when the player is riding a MountInput entity.
 */
public class MountInputPacket {
    public static final Identifier ID = MOD_ID.id("mount_input");

    public static void register() {
        Registry.register(MessageListenerRegistry.INSTANCE, ID, MountInputPacket::handle);
    }

    private static void handle(PlayerEntity player, MessagePacket packet) {
        if (player == null) return;
        Entity vehicle = player.vehicle;
        if (vehicle instanceof MountInput mount) {
            float forward = packet.floats != null && packet.floats.length > 0 ? packet.floats[0] : 0;
            float strafe = packet.floats != null && packet.floats.length > 1 ? packet.floats[1] : 0;
            float yaw = packet.floats != null && packet.floats.length > 2 ? packet.floats[2] : 0;
            float pitch = packet.floats != null && packet.floats.length > 3 ? packet.floats[3] : 0;
            boolean jump = packet.booleans != null && packet.booleans.length > 0 && packet.booleans[0];
            mount.setMountInput(forward, strafe, jump, yaw, pitch);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void sendInput(LivingEntity rider) {
        if (rider.vehicle instanceof MountInput) {
            MessagePacket packet = new MessagePacket(ID);
            LivingEntityAccessor accessor = (LivingEntityAccessor) rider;
            packet.floats = new float[]{accessor.getForwardVelocity(), accessor.getHorizontalVelocity(), rider.yaw, rider.pitch};
            packet.booleans = new boolean[]{accessor.getJumping()};
            PacketHelper.send(packet);
        }
    }
}
