package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.gen.biome.AetherBiomeSource;
import com.matthewperiut.aether.gen.dim.AetherDimension;
import net.minecraft.item.FlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteel.class)
public abstract class FlintAndSteelMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void evaporateFireInEvaporatingDims(ItemStack stack, PlayerEntity user, World world,
                                                int x, int y, int z, int side,
                                                CallbackInfoReturnable<Boolean> cir) {
        // compute the actual target block (matches the vanilla side offset logic)
        switch (side) {
            case 0: --y; break;
            case 1: ++y; break;
            case 2: --z; break;
            case 3: ++z; break;
            case 4: --x; break;
            case 5: ++x; break;
        }

        // If this dimension "evaporates water", make fire fizzle/smoke instead of placing
        if (world.dimension instanceof AetherDimension) {
            // Only intercept when the target is air (same condition vanilla checks before placing fire)
            if (world.getBlockId(x, y, z) == 0) {
                // Fizz sound (same feel as the water bucket-in-Nether behavior)
                world.playSound(
                        x + 0.5D, y + 0.5D, z + 0.5D,
                        "random.fizz",
                        0.5F,
                        2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F
                );

                // Smoke burst
                for (int i = 0; i < 8; ++i) {
                    world.addParticle(
                            "largesmoke",
                            x + Math.random(),
                            y + Math.random(),
                            z + Math.random(),
                            0.0D, 0.0D, 0.0D
                    );
                }

                // Still consume durability like vanilla
                stack.damage(1, user);

                // Cancel original method; treat as handled
                cir.setReturnValue(true);
            }
        }
    }
}
