package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.util.JumpsDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixinJumps extends DrawContext {
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At(value = "TAIL"))
    void injectFeathers(float f, boolean bl, int i, int j, CallbackInfo ci) {
        JumpsDisplay.gui(minecraft, (InGameHud) (Object) this);
    }
}
