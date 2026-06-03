package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.player.AetherPlayerHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Applies Aether glove accessory bonuses (extra damage, knockback, fire) when the player
 * attacks. Plain mixin replacement for the StationAPI player-API hook.
 */
@Mixin(PlayerEntity.class)
public class PlayerAttackGlovesMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    private void aether$gloveBonuses(Entity target, CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity) (Object) this;
        new AetherPlayerHandler(self).attackTargetEntityBaseWithCurrentItem(target);
    }
}
