package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.entity.AetherParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldEventRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldEventRenderer.class)
public class WorldEventRendererMixin {
    @Shadow
    private Minecraft client;

    @Inject(method = "addParticle", at = @At(value = "CONSTANT", args = "doubleValue=16.0"), cancellable = true)
    void inject(String particleId, double x, double y, double z, double xTo, double yTo, double zTo, CallbackInfo ci) {
        AetherParticles.addAetherParticle(client, particleId, x, y, z, xTo, yTo, zTo);
    }
}
