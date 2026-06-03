package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.item.util.CustomReach;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.InteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResultType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Extends client-side interaction reach when holding a CustomReach item (lance, valkyrie
 * tools). Plain mixin replacement for StationAPI's CustomReachProvider.
 */
@Mixin(InteractionManager.class)
public class InteractionManagerReachMixin {

    @Inject(method = "getReachDistance", at = @At("RETURN"), cancellable = true)
    private void aether$customReach(CallbackInfoReturnable<Float> cir) {
        Minecraft minecraft = (Minecraft) FabricLoader.getInstance().getGameInstance();
        PlayerEntity player = minecraft != null ? minecraft.player : null;
        if (player == null) return;
        ItemStack held = player.inventory.getSelectedItem();
        if (held != null && held.getItem() instanceof CustomReach custom) {
            cir.setReturnValue((float) custom.getReach(held, player, HitResultType.BLOCK, cir.getReturnValueF()));
        }
    }
}
