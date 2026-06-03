package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.item.util.CustomReach;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.hit.HitResultType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Loosens the server's squared-distance interaction checks (36 = 6 blocks) when the player
 * holds a CustomReach item, so lance/valkyrie-tool reach is honored in multiplayer.
 * Plain mixin replacement for StationAPI's CustomReachProvider server side.
 */
@Mixin(ServerPlayNetworkHandler.class)
public class ServerReachMixin {

    @Shadow public ServerPlayerEntity player;

    @ModifyConstant(method = {"handleInteractEntity", "handlePlayerAction"}, constant = @Constant(doubleValue = 36.0), require = 0)
    private double aether$customReachSq(double vanillaSq) {
        ItemStack held = this.player != null ? this.player.inventory.getSelectedItem() : null;
        if (held != null && held.getItem() instanceof CustomReach custom) {
            double reach = custom.getReach(held, this.player, HitResultType.BLOCK, Math.sqrt(vanillaSq)) + 1.5;
            return reach * reach;
        }
        return vanillaSq;
    }
}
