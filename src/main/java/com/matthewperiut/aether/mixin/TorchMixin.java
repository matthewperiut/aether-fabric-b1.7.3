package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.gen.dim.AetherDimension;
import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TorchBlock.class)
public class TorchMixin {
    @Redirect(method = "onPlaced(Lnet/minecraft/world/World;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockMeta(IIII)V"))
    void setBlockMetaReplace(World world, int x, int y, int z, int meta) {
        if (world.dimension instanceof AetherDimension && world.getBlockId(x, y, z) == Block.TORCH.id) {
            world.setBlock(x, y, z, AetherBlocks.BurntTorch.id);
            world.setBlockMeta(x, y, z, meta);
        } else {
            world.setBlockMeta(x, y, z, meta);
        }
    }
}
