package com.matthewperiut.aether.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.matthewperiut.aether.player.ZaniteMiningController.modifyMiningSpeed;

/**
 * Zanite accessories speed up mining. Hooks vanilla's per-player block breaking delta
 * (was StationAPI's AbstractBlockState.calcBlockBreakingDelta before the RetroAPI migration).
 */
@Mixin(Block.class)
public abstract class AbstractBlockStateMixinZaniteAccessories {

    @Inject(at = @At("RETURN"), method = "getHardness(Lnet/minecraft/entity/player/PlayerEntity;)F", cancellable = true)
    private void aether$zaniteMiningSpeed(PlayerEntity player, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(modifyMiningSpeed(cir.getReturnValueF(), player));
    }
}
