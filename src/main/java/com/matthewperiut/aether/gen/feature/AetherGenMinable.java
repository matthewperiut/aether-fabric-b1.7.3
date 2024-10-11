package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import java.util.Random;

public class AetherGenMinable extends Feature {
    private final int minableBlockId;
    private final int numberOfBlocks;

    public AetherGenMinable(int i, int j) {
        this.minableBlockId = i;
        this.numberOfBlocks = j;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        float f = random.nextFloat() * 3.141593F;
        double d = (float) (i + 8) + MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double d1 = (float) (i + 8) - MathHelper.sin(f) * (float) this.numberOfBlocks / 8.0F;
        double d2 = (float) (k + 8) + MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double d3 = (float) (k + 8) - MathHelper.cos(f) * (float) this.numberOfBlocks / 8.0F;
        double d4 = j + random.nextInt(3) + 2;
        double d5 = j + random.nextInt(3) + 2;

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = d + (d1 - d) * (double) l / (double) this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double) l / (double) this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double) l / (double) this.numberOfBlocks;
            double d9 = random.nextDouble() * (double) this.numberOfBlocks / 16.0;
            double d10 = (double) (MathHelper.sin((float) l * 3.141593F / (float) this.numberOfBlocks) + 1.0F) * d9 + 1.0;
            double d11 = (double) (MathHelper.sin((float) l * 3.141593F / (float) this.numberOfBlocks) + 1.0F) * d9 + 1.0;
            int i1 = MathHelper.floor(d6 - d10 / 2.0);
            int j1 = MathHelper.floor(d7 - d11 / 2.0);
            int k1 = MathHelper.floor(d8 - d10 / 2.0);
            int l1 = MathHelper.floor(d6 + d10 / 2.0);
            int i2 = MathHelper.floor(d7 + d11 / 2.0);
            int j2 = MathHelper.floor(d8 + d10 / 2.0);

            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double) k2 + 0.5 - d6) / (d10 / 2.0);
                if (!(d12 * d12 >= 1.0)) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = ((double) l2 + 0.5 - d7) / (d11 / 2.0);
                        if (!(d12 * d12 + d13 * d13 >= 1.0)) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = ((double) i3 + 0.5 - d8) / (d10 / 2.0);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && world.getBlockId(k2, l2, i3) == AetherBlocks.Holystone.id && world.getBlockMeta(k2, l2, i3) <= 1) {
                                    world.setBlockWithoutNotifyingNeighbors(k2, l2, i3, this.minableBlockId);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
