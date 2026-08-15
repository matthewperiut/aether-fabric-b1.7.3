package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.gen.dim.AetherDimension;
import net.minecraft.block.LiquidBlock;
import net.minecraft.block.StillLiquidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StillLiquidBlock.class)
public abstract class LavaBlockMixin extends LiquidBlock {
    public LavaBlockMixin(int id, Material material) {
        super(id, material);
    }
    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (world.dimension instanceof AetherDimension) {
            if (material == Material.LAVA) {
                world.setBlock(x, y, z, AetherBlocks.Aerogel.id);
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
            }
        }
    }
}