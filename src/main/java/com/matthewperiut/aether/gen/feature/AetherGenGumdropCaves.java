package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.feature.Feature;

import java.util.Random;

public class AetherGenGumdropCaves extends Feature {
    private final int field_100003_a;
    private final int field_100002_b;

    public AetherGenGumdropCaves(int i, int j) {
        this.field_100003_a = i;
        this.field_100002_b = j;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        float f = random.nextFloat() * 3.141593F;
        double d = (float) (i + 8) + MathHelper.sin(f) * (float) this.field_100002_b / 8.0F;
        double d1 = (float) (i + 8) - MathHelper.sin(f) * (float) this.field_100002_b / 8.0F;
        double d2 = (float) (k + 8) + MathHelper.cos(f) * (float) this.field_100002_b / 8.0F;
        double d3 = (float) (k + 8) - MathHelper.cos(f) * (float) this.field_100002_b / 8.0F;
        double d4 = j + random.nextInt(3) + 2;
        double d5 = j + random.nextInt(3) + 2;

        for (int l = 0; l <= this.field_100002_b; ++l) {
            double d6 = d + (d1 - d) * (double) l / (double) this.field_100002_b;
            double d7 = d4 + (d5 - d4) * (double) l / (double) this.field_100002_b;
            double d8 = d2 + (d3 - d2) * (double) l / (double) this.field_100002_b;
            double d9 = random.nextDouble() * (double) this.field_100002_b / 16.0;
            double d10 = (double) (MathHelper.sin((float) l * 3.141593F / (float) this.field_100002_b) + 1.0F) * d9 + 1.0;
            double d11 = (double) (MathHelper.sin((float) l * 3.141593F / (float) this.field_100002_b) + 1.0F) * d9 + 1.0;
            int i1 = (int) (d6 - d10 / 2.0);
            int j1 = (int) (d7 - d11 / 2.0);
            int k1 = (int) (d8 - d10 / 2.0);
            int l1 = (int) (d6 + d10 / 2.0);
            int i2 = (int) (d7 + d11 / 2.0);
            int j2 = (int) (d8 + d10 / 2.0);

            for (int k2 = i1; k2 <= l1; ++k2) {
                double d12 = ((double) k2 + 0.5 - d6) / (d10 / 2.0);
                if (d12 * d12 < 1.0) {
                    for (int l2 = j1; l2 <= i2; ++l2) {
                        double d13 = ((double) l2 + 0.5 - d7) / (d11 / 2.0);
                        if (d12 * d12 + d13 * d13 < 1.0) {
                            for (int i3 = k1; i3 <= j2; ++i3) {
                                double d14 = ((double) i3 + 0.5 - d8) / (d10 / 2.0);
                                int bID = world.getBlockId(k2, l2, i3);
                                int meta = world.getBlockMeta(k2, l2, i3);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && (bID == AetherBlocks.Holystone.id && meta <= 1 || bID == AetherBlocks.Grass.id || bID == AetherBlocks.Dirt.id)) {
                                    world.setBlock(k2, l2, i3, this.field_100003_a);
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
