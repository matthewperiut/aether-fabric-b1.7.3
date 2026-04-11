package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.entity.MountInput;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the rider's camera from being rotated by the mount's yaw/pitch changes
 * when riding Aether MountInput entities (Phyg, FlyingCow, Moa).
 * Saves yaw/pitch before tickRiding processes mount deltas, restores after.
 */
@Mixin(Entity.class)
public class MountRidingMixin {
    @Shadow
    public Entity vehicle;

    @Shadow
    public float yaw;

    @Shadow
    public float pitch;

    @Unique
    private float aether_savedYaw;

    @Unique
    private float aether_savedPitch;

    @Inject(method = "tickRiding", at = @At("HEAD"))
    private void saveLookBeforeRiding(CallbackInfo ci) {
        if (this.vehicle instanceof MountInput) {
            this.aether_savedYaw = this.yaw;
            this.aether_savedPitch = this.pitch;
        }
    }

    @Inject(method = "tickRiding", at = @At("TAIL"))
    private void restoreLookAfterRiding(CallbackInfo ci) {
        if (this.vehicle instanceof MountInput) {
            this.yaw = this.aether_savedYaw;
            this.pitch = this.aether_savedPitch;
        }
    }
}
