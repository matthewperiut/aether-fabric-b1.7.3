package com.matthewperiut.aether.server;

import com.matthewperiut.aether.entity.MountInput;
import com.matthewperiut.aether.entity.living.EntityAerbunny;
import com.matthewperiut.aether.network.AerbunnyJumpPacket;
import com.matthewperiut.aether.network.MountInputPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.ornithemc.osl.entrypoints.api.server.ServerModInitializer;
import net.ornithemc.osl.networking.api.server.ServerPlayNetworking;

/**
 * Dedicated-server entrypoint. The C2S packet handlers live here (not in the packet classes)
 * because they touch ServerPlayerEntity — referencing it from common code crashes the client
 * with "Cannot load class ... in environment type CLIENT" (environment isolation).
 */
public class AetherServer implements ServerModInitializer {

    @Override
    public void initServer() {
        ServerPlayNetworking.registerListener(MountInputPacket.ID, (ctx, buffer) -> {
            float forward = buffer.readFloat();
            float strafe = buffer.readFloat();
            float yaw = buffer.readFloat();
            float pitch = buffer.readFloat();
            boolean jump = buffer.readBoolean();
            ctx.ensureOnMainThread();
            ServerPlayerEntity player = ctx.player();
            if (player == null) return;
            Entity vehicle = player.vehicle;
            if (vehicle instanceof MountInput mount) {
                mount.setMountInput(forward, strafe, jump, yaw, pitch);
            }
        });

        ServerPlayNetworking.registerListener(AerbunnyJumpPacket.ID, (ctx, buffer) -> {
            boolean jumping = buffer.readBoolean();
            ctx.ensureOnMainThread();
            ServerPlayerEntity player = ctx.player();
            if (player == null || player.passenger == null) return;
            if (player.passenger instanceof EntityAerbunny bunny) {
                bunny.vehicleJumping = jumping;
            }
        });
    }
}
