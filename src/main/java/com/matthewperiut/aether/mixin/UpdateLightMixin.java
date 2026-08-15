package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.gen.dim.AetherDimension;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = World.class)
public abstract class UpdateLightMixin {

    @Shadow
    public abstract boolean isAir(int x, int y, int z);
    @Shadow
    public abstract int getBrightness(int x, int y, int z);
    @Shadow @Final public Dimension dimension;

    @Inject(method = "updateLight", at = @At("HEAD"), cancellable = true)
    private void skipIfNoLightChange(LightType lightType, int x, int y, int z, int i, CallbackInfo ci) {
        if (!(dimension instanceof AetherDimension)) return;
        if (this.isAir(x,y,z) && this.getBrightness(x,y,z) == 15) {
            ci.cancel();
        }
    }
}
