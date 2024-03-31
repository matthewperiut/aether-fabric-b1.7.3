package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.util.JumpsDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixinJumps extends GuiElement {
    @Shadow
    private Minecraft client;

    @Inject(method = "render", at = @At(value = "TAIL"))
    void injectFeathers(float f, boolean bl, int i, int j, CallbackInfo ci) {
        JumpsDisplay.gui(client, (InGameHud) (Object) this);
    }
}
