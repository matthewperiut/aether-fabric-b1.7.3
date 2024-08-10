package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.poison.ClientPoison;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudPoisonDisplay {
    @Shadow
    private Minecraft minecraft;

    // for future me migrating to new biny
    // method_693 -> getArmorStack
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;method_693(I)Lnet/minecraft/item/ItemStack;"))
    void injectPoisonRender(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        ClientPoison.tickRender(minecraft);
    }
}
