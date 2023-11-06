package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.gen.biome.AetherBiomes;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getSkyColor(float par1, CallbackInfoReturnable<Integer> cir) {
        if (this.equals(AetherBiomes.AETHER))
            cir.setReturnValue(0xc0c0ff);
    }
}
