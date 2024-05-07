package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.block.UtilSkyroot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(World arg) {
        super(arg);
    }

    @Shadow
    protected abstract void getDrops();

    @Redirect(method = "onKilledBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDrops()V"))
    void killedByGetDrops(LivingEntity entity) {

        PlayerEntity player = world.getClosestPlayer(x, y, z, 10);
        if (player != null)
            if (UtilSkyroot.sword(player)) {
                getDrops();
            }
        getDrops();
    }
}
