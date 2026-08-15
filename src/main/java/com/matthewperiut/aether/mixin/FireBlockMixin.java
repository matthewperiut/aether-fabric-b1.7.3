package com.matthewperiut.aether.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.matthewperiut.aether.gen.dim.AetherDimension;
import net.minecraft.block.FireBlock;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FireBlock.class)
public abstract class FireBlockMixin {
    @WrapMethod(method = "onPlaced")
    private void onBlockPlaced(World world, int x, int y, int z, Operation<Void> original) {
        if (world.dimension instanceof AetherDimension) {
            BlockState below = world.getBlockState(x, y - 1, z);
            boolean infiniteBurn = below != null && below.isIn(TagKey.of(BlockRegistry.INSTANCE.getKey(), Identifier.of("minecraft:infiniburn")));
            if (!infiniteBurn) {
                world.setBlock(x, y, z, 0);
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
                return;
            }
        }
        original.call(world, x, y, z);
    }
}