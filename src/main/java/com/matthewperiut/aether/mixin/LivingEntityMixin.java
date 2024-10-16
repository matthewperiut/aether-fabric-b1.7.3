package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.UtilSkyroot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(World arg) {
        super(arg);
    }

    @Shadow
    protected abstract void dropItems();

    @Redirect(method = "onKilledBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropItems()V"))
    void killedByGetDrops(LivingEntity entity) {
        PlayerEntity player = world.getClosestPlayer(x, y, z, 10);
        if (player != null)
            if (UtilSkyroot.sword(player)) {
                dropItems();
            }
        dropItems();
    }

    @Inject(method = "onLanding", at = @At("HEAD"), cancellable = true)
    void shouldStopDmg(float par1, CallbackInfo ci) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (world.getBlockId((int) x + i, (int) (y - 1 + j), (int) z + k) == AetherBlocks.Aercloud.id) {
                        ci.cancel();
                    }
                }
            }
        }

    }
}
