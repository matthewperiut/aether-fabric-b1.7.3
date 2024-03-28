package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.poison.ClientPoison;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudPoison {
    @Shadow
    private Minecraft client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/PlayerInventory;getArmorItem(I)Lnet/minecraft/item/ItemStack;"))
    void injectPoisonRender(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        ClientPoison.tickRender(client);
    }
}
