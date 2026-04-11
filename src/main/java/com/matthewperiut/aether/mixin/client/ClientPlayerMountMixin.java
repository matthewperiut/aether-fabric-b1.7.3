package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.entity.MountInput;
import com.matthewperiut.aether.entity.living.EntityAerbunny;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import com.matthewperiut.aether.network.AerbunnyJumpPacket;
import com.matthewperiut.aether.network.MountInputPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class ClientPlayerMountMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void sendMountInput(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity) (Object) this;

        // Send mount steering input
        if (self.vehicle instanceof MountInput) {
            MountInputPacket.sendInput(self);
        }

        // Aerbunny riding: send jump state + apply slow-fall locally
        if (self.passenger instanceof EntityAerbunny bunny) {
            boolean jumping = ((LivingEntityAccessor) self).getJumping();

            // Send jump state to server
            MessagePacket packet = new MessagePacket(AerbunnyJumpPacket.ID);
            packet.booleans = new boolean[]{jumping};
            PacketHelper.send(packet);

            // Apply slow-fall and jump-boost physics locally (mirrors server Aerbunny.tickLiving)
            if (!self.onGround) {
                ((EntityAccessor) self).setFallDistance(0.0F);
                self.velocityY += 0.05000000074505806;
                if (self.velocityY < -0.22499999403953552 && jumping) {
                    self.velocityY = 0.125;
                    bunny.cloudPoop();
                    bunny.puffiness = 1.15F;
                }
            }
        }
    }
}
