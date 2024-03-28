package com.matthewperiut.aether.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract public class PlayerMixin extends Entity {
    public PlayerMixin(World arg) {
        super(arg);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void initDataTrackerPoisonTime(CallbackInfo ci) {
        dataTracker.startTracking(29, (int) 0);
    }
}
