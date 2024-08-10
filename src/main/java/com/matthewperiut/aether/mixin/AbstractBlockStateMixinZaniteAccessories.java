package com.matthewperiut.aether.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.block.AbstractBlockState;
import net.modificationstation.stationapi.api.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.matthewperiut.aether.player.ZaniteMiningController.modifyMiningSpeed;

@Mixin(AbstractBlockState.class)
public abstract class AbstractBlockStateMixinZaniteAccessories {
    @Shadow
    public abstract Block getBlock();

    @Inject(at = @At("RETURN"), method = "calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
    private void inject(PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
            cir.setReturnValue(modifyMiningSpeed(cir.getReturnValueF(), player));
        }
    }
