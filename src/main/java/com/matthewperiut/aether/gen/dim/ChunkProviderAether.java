package com.matthewperiut.aether.gen.dim;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.gen.biome.AetherBiomes;
import com.matthewperiut.aether.gen.feature.*;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.noise.PerlinOctaveNoise;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.feature.Feature;
import net.minecraft.world.feature.LakeFeature;
import net.minecraft.world.gen.Carver;
import net.minecraft.world.gen.OverworldCarver;
import net.minecraft.world.source.WorldSource;
import net.modificationstation.stationapi.impl.level.chunk.FlattenedChunk;

import java.util.Random;

public class ChunkProviderAether implements WorldSource {
    public static int gumCount;
    private final Random random;
    private final PerlinOctaveNoise noiseGenerator1;
    private final PerlinOctaveNoise noiseGenerator2;
    private final PerlinOctaveNoise noiseGenerator3;
    private final PerlinOctaveNoise noiseGenerator4;
    private final PerlinOctaveNoise noiseGenerator5;
    private final World worldObj;
    private final Carver mapGenCaves = new OverworldCarver();
    public PerlinOctaveNoise noiseGenerator6;
    public PerlinOctaveNoise noiseGenerator7;
    public PerlinOctaveNoise noiseGenerator8;
    public byte topAetherBlock;
    public byte fillerAetherBlock;
    double[] field_28093_d;
    double[] field_28092_e;
    double[] field_28091_f;
    double[] field_28090_g;
    double[] field_28089_h;
    int[][] field_28088_i = new int[32][32];
    private double[] field_28080_q;
    private double[] field_28079_r = new double[256];
    private double[] field_28078_s = new double[256];
    private double[] field_28077_t = new double[256];
    private Biome[] field_28075_v;
    private double[] field_28074_w;

    public ChunkProviderAether(World world, long l) {
        this.worldObj = world;
        this.random = new Random(l);
        this.noiseGenerator1 = new PerlinOctaveNoise(this.random, 16);
        this.noiseGenerator2 = new PerlinOctaveNoise(this.random, 16);
        this.noiseGenerator3 = new PerlinOctaveNoise(this.random, 8);
        this.noiseGenerator4 = new PerlinOctaveNoise(this.random, 4);
        this.noiseGenerator5 = new PerlinOctaveNoise(this.random, 4);
        this.noiseGenerator6 = new PerlinOctaveNoise(this.random, 10);
        this.noiseGenerator7 = new PerlinOctaveNoise(this.random, 16);
        this.noiseGenerator8 = new PerlinOctaveNoise(this.random, 8);
    }

    public void func_28071_a(int i, int j, byte[] abyte0, Biome[] abiomegenbase, double[] ad) {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        this.field_28080_q = this.func_28073_a(this.field_28080_q, i * byte0, 0, j * byte0, k, byte1, l);

        for (int i1 = 0; i1 < byte0; ++i1) {
            for (int j1 = 0; j1 < byte0; ++j1) {
                for (int k1 = 0; k1 < 32; ++k1) {
                    double d = 0.25;
                    double d1 = this.field_28080_q[((i1) * l + j1) * byte1 + k1];
                    double d2 = this.field_28080_q[((i1) * l + j1 + 1) * byte1 + k1];
                    double d3 = this.field_28080_q[((i1 + 1) * l + j1) * byte1 + k1];
                    double d4 = this.field_28080_q[((i1 + 1) * l + j1 + 1) * byte1 + k1];
                    double d5 = (this.field_28080_q[((i1) * l + j1) * byte1 + k1 + 1] - d1) * d;
                    double d6 = (this.field_28080_q[((i1) * l + j1 + 1) * byte1 + k1 + 1] - d2) * d;
                    double d7 = (this.field_28080_q[((i1 + 1) * l + j1) * byte1 + k1 + 1] - d3) * d;
                    double d8 = (this.field_28080_q[((i1 + 1) * l + j1 + 1) * byte1 + k1 + 1] - d4) * d;

                    for (int l1 = 0; l1 < 4; ++l1) {
                        double d9 = 0.125;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 8; ++i2) {
                            int j2 = i2 + i1 * 8 << 11 | j1 * 8 << 7 | k1 * 4 + l1;
                            char c = 128;
                            double d14 = 0.125;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 8; ++k2) {
                                int l2 = 0;
                                if (d15 > 0.0) {
                                    l2 = AetherBlocks.Holystone.id;
                                }

                                abyte0[j2] = (byte) l2;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }

    }

    public void func_28072_a(int i, int j, byte[] abyte0, Biome[] abiomegenbase) {
        double d = 0.03125;
        this.field_28079_r = this.noiseGenerator4.sample(this.field_28079_r, i * 16, j * 16, 0.0, 16, 16, 1, d, d, 1.0);
        this.field_28078_s = this.noiseGenerator4.sample(this.field_28078_s, i * 16, 109.0134, j * 16, 16, 1, 16, d, 1.0, d);
        this.field_28077_t = this.noiseGenerator5.sample(this.field_28077_t, i * 16, j * 16, 0.0, 16, 16, 1, d * 2.0, d * 2.0, d * 2.0);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome biomegenbase = AetherBiomes.AETHER;
                int i1 = (int) (this.field_28077_t[k + l * 16] / 3.0 + 3.0 + this.random.nextDouble() * 0.25);
                int j1 = -1;
                this.topAetherBlock = (byte) AetherBlocks.Grass.id;
                this.fillerAetherBlock = (byte) AetherBlocks.Dirt.id;
                byte byte0 = this.topAetherBlock;
                byte byte1 = this.fillerAetherBlock;
                byte stone = (byte) AetherBlocks.Holystone.id;
                if (byte0 < 0) {
                    byte0 = byte0;
                }

                if (byte1 < 0) {
                    byte1 = byte1;
                }

                if (stone < 0) {
                    stone = stone;
                }

                for (int k1 = 127; k1 >= 0; --k1) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte2 = abyte0[l1];
                    if (byte2 == 0) {
                        j1 = -1;
                    } else if (byte2 == stone) {
                        if (j1 == -1) {
                            if (i1 <= 0) {
                                byte0 = 0;
                                byte1 = stone;
                            }

                            j1 = i1;
                            if (k1 >= 0) {
                                abyte0[l1] = byte0;
                            } else {
                                abyte0[l1] = byte1;
                            }
                        } else if (j1 > 0) {
                            --j1;
                            abyte0[l1] = byte1;
                        }
                    }
                }
            }
        }

    }

    public Chunk loadChunk(int i, int j) {
        return this.getChunk(i, j);
    }

    public Chunk getChunk(int i, int j) {
        this.random.setSeed((long) i * 341873128712L + (long) j * 132897987541L);
        byte[] abyte0 = new byte['耀'];
        Chunk chunk = new Chunk(this.worldObj, abyte0, i, j);
        this.field_28075_v = this.worldObj.method_1781().getBiomes(this.field_28075_v, i * 16, j * 16, 16, 16);
        double[] ad = this.worldObj.method_1781().temperatureNoises;
        this.func_28071_a(i, j, abyte0, this.field_28075_v, ad);
        this.func_28072_a(i, j, abyte0, this.field_28075_v);
        this.mapGenCaves.generate(this, this.worldObj, i, j, abyte0);
        chunk.generateHeightmap();

        FlattenedChunk flattenedChunk = new FlattenedChunk(worldObj, i, j);
        flattenedChunk.fromLegacy(chunk.blocks);
        flattenedChunk.generateHeightmap();

        return flattenedChunk;
    }

    private double[] func_28073_a(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }

        double d = 684.412;
        double d1 = 684.412;
        double[] ad1 = this.worldObj.method_1781().temperatureNoises;
        double[] ad2 = this.worldObj.method_1781().rainfallNoises;
        this.field_28090_g = this.noiseGenerator6.sample(this.field_28090_g, i, k, l, j1, 1.121, 1.121, 0.5);
        this.field_28089_h = this.noiseGenerator7.sample(this.field_28089_h, i, k, l, j1, 200.0, 200.0, 0.5);
        d *= 2.0;
        this.field_28093_d = this.noiseGenerator3.sample(this.field_28093_d, i, j, k, l, i1, j1, d / 80.0, d1 / 160.0, d / 80.0);
        this.field_28092_e = this.noiseGenerator1.sample(this.field_28092_e, i, j, k, l, i1, j1, d, d1, d);
        this.field_28091_f = this.noiseGenerator2.sample(this.field_28091_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;

        for (int j2 = 0; j2 < l; ++j2) {
            int k2 = j2 * i2 + i2 / 2;

            for (int l2 = 0; l2 < j1; ++l2) {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = ad1[k2 * 16 + i3];
                double d3 = ad2[k2 * 16 + i3] * d2;
                double d4 = 1.0 - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1.0 - d4;
                double d5 = (this.field_28090_g[l1] + 256.0) / 512.0;
                d5 *= d4;
                if (d5 > 1.0) {
                    d5 = 1.0;
                }

                double d6 = this.field_28089_h[l1] / 8000.0;
                if (d6 < 0.0) {
                    d6 = -d6 * 0.3;
                }

                d6 = d6 * 3.0 - 2.0;
                if (d6 > 1.0) {
                    d6 = 1.0;
                }

                d6 /= 8.0;
                d6 = 0.0;
                if (d5 < 0.0) {
                    d5 = 0.0;
                }

                d5 += 0.5;
                d6 = d6 * (double) i1 / 16.0;
                ++l1;
                double d7 = (double) i1 / 2.0;

                for (int j3 = 0; j3 < i1; ++j3) {
                    double d8 = 0.0;
                    double d9 = ((double) j3 - d7) * 8.0 / d5;
                    if (d9 < 0.0) {
                        d9 *= -1.0;
                    }

                    double d10 = this.field_28092_e[k1] / 512.0;
                    double d11 = this.field_28091_f[k1] / 512.0;
                    double d12 = (this.field_28093_d[k1] / 10.0 + 1.0) / 2.0;
                    if (d12 < 0.0) {
                        d8 = d10;
                    } else if (d12 > 1.0) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }

                    d8 -= 8.0;
                    int k3 = 32;
                    double d14;
                    if (j3 > i1 - k3) {
                        d14 = (float) (j3 - (i1 - k3)) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0 - d14) + -30.0 * d14;
                    }

                    k3 = 8;
                    if (j3 < k3) {
                        d14 = (float) (k3 - j3) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0 - d14) + -30.0 * d14;
                    }

                    ad[k1] = d8;
                    ++k1;
                }
            }
        }

        return ad;
    }

    public boolean isChunkLoaded(int i, int j) {
        return true;
    }

    public void decorate(WorldSource ichunkprovider, int i, int j) {
        FallingBlock.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        Biome biomegenbase = this.worldObj.method_1781().getBiome(k + 16, l + 16);
        this.random.setSeed(this.worldObj.getSeed());
        long l1 = this.random.nextLong() / 2L * 2L + 1L;
        long l2 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long) i * l1 + (long) j * l2 ^ this.worldObj.getSeed());
        double d = 0.25;
        int l7;
        int k17;
        int j20;
        if (gumCount < 800) {
            ++gumCount;
        } else if (this.random.nextInt(32) == 0) {
            boolean flag = false;
            l7 = k + this.random.nextInt(16) + 8;
            k17 = this.random.nextInt(64) + 32;
            j20 = l + this.random.nextInt(16) + 8;
            flag = (new AetherGenGumdrop()).generate(this.worldObj, this.random, l7, k17, j20);
            if (flag) {
                gumCount = 0;
            }
        }

        int k4;
        if (this.random.nextInt(3) == 0) {
            k4 = k + this.random.nextInt(16) + 8;
            l7 = this.random.nextInt(128);
            k17 = l + this.random.nextInt(16) + 8;
            (new LakeFeature(Block.STILL_WATER.id)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        for (k4 = 0; k4 < 20; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = this.random.nextInt(128);
            j20 = l + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.Dirt.id, 32)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            l7 = k + this.random.nextInt(16) + 8;
            k17 = this.random.nextInt(128);
            j20 = l + this.random.nextInt(16) + 8;
            (new AetherGenFlowers(AetherBlocks.WhiteFlower.id)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            if (this.random.nextInt(2) == 0) {
                l7 = k + this.random.nextInt(16) + 8;
                k17 = this.random.nextInt(128);
                j20 = l + this.random.nextInt(16) + 8;
                (new AetherGenFlowers(AetherBlocks.PurpleFlower.id)).generate(this.worldObj, this.random, l7, k17, j20);
            }
        }

        for (k4 = 0; k4 < 10; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = this.random.nextInt(128);
            j20 = l + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.Icestone.id, 32)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        for (k4 = 0; k4 < 20; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = this.random.nextInt(128);
            j20 = l + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.AmbrosiumOre.id, 16)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        for (k4 = 0; k4 < 15; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = this.random.nextInt(64);
            j20 = l + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.ZaniteOre.id, 8)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        for (k4 = 0; k4 < 8; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = this.random.nextInt(32);
            j20 = l + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.GravititeOre.id, 7)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        if (this.random.nextInt(50) == 0) {
            k4 = k + this.random.nextInt(16);
            l7 = this.random.nextInt(32) + 96;
            k17 = l + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 2, 4, false)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        if (this.random.nextInt(13) == 0) {
            k4 = k + this.random.nextInt(16);
            l7 = this.random.nextInt(64) + 32;
            k17 = l + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 1, 8, false)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        if (this.random.nextInt(7) == 0) {
            k4 = k + this.random.nextInt(16);
            l7 = this.random.nextInt(64) + 32;
            k17 = l + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 0, 16, false)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        if (this.random.nextInt(50) == 0) {
            k4 = k + this.random.nextInt(16);
            l7 = this.random.nextInt(32);
            k17 = l + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 0, 64, true)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        for (k4 = 0; k4 < 2; ++k4) {
            l7 = k + this.random.nextInt(16);
            k17 = 32 + this.random.nextInt(64);
            j20 = l + this.random.nextInt(16);
            (new AetherGenDungeonBronze(AetherBlocks.LockedDungeonStone.id, AetherBlocks.LockedLightDungeonStone.id, AetherBlocks.DungeonStone.id, AetherBlocks.LightDungeonStone.id, AetherBlocks.Holystone.id, 2, AetherBlocks.Holystone.id, 0, 16, true)).generate(this.worldObj, this.random, l7, k17, j20);
        }

        if (this.random.nextInt(500) == 0) {
            k4 = k + this.random.nextInt(16);
            l7 = this.random.nextInt(32) + 64;
            k17 = l + this.random.nextInt(16);
            (new AetherGenDungeonSilver(AetherBlocks.LockedDungeonStone.id, AetherBlocks.LockedLightDungeonStone.id, AetherBlocks.DungeonStone.id, AetherBlocks.LightDungeonStone.id, AetherBlocks.Holystone.id, 2, AetherBlocks.Holystone.id, 0, AetherBlocks.Pillar.id)).generate(this.worldObj, this.random, k4, l7, k17);
        }

        if (this.random.nextInt(5) == 0) {
            for (k4 = k; k4 < k + 16; ++k4) {
                for (l7 = l; l7 < l + 16; ++l7) {
                    for (k17 = 0; k17 < 48; ++k17) {
                        if (this.worldObj.getBlockId(k4, k17, l7) == 0 && this.worldObj.getBlockId(k4, k17 + 1, l7) == AetherBlocks.Grass.id && this.worldObj.getBlockId(k4, k17 + 2, l7) == 0) {
                            (new AetherGenQuicksoil(AetherBlocks.Quicksoil.id)).generate(this.worldObj, this.random, k4, k17, l7);
                            k17 = 128;
                        }
                    }
                }
            }
        }

        d = 0.5;
        k4 = (int) ((this.noiseGenerator8.sample((double) k * d, (double) l * d) / 8.0 + this.random.nextDouble() * 4.0 + 4.0) / 3.0);
        l7 = 0;
        if (this.random.nextInt(10) == 0) {
            ++l7;
        }

        if (biomegenbase == Biome.FOREST) {
            l7 += k4 + 5;
        }

        if (biomegenbase == Biome.RAINFOREST) {
            l7 += k4 + 5;
        }

        if (biomegenbase == Biome.SEASONAL_FOREST) {
            l7 += k4 + 2;
        }

        if (biomegenbase == Biome.TAIGA) {
            l7 += k4 + 5;
        }

        if (biomegenbase == Biome.DESERT) {
            l7 -= 20;
        }

        if (biomegenbase == Biome.TUNDRA) {
            l7 -= 20;
        }

        if (biomegenbase == Biome.PLAINS) {
            l7 -= 20;
        }

        l7 += k4;

        int l21;
        for (k17 = 0; k17 < l7; ++k17) {
            j20 = k + this.random.nextInt(16) + 8;
            l21 = l + this.random.nextInt(16) + 8;
            Feature worldgenerator = random.nextInt(100) == 0 ? new AetherGenGoldenOak() : new AetherGenSkyroot();
            worldgenerator.setScale(1.0, 1.0, 1.0);
            worldgenerator.generate(this.worldObj, this.random, j20, this.worldObj.getHeight(j20, l21), l21);
        }

        for (k17 = 0; k17 < 50; ++k17) {
            j20 = k + this.random.nextInt(16) + 8;
            l21 = this.random.nextInt(this.random.nextInt(120) + 8);
            int l22 = l + this.random.nextInt(16) + 8;
            (new AetherGenLiquids(Block.FLOWING_WATER.id)).generate(this.worldObj, this.random, j20, l21, l22);
        }

        FallingBlock.fallInstantly = false;
    }

    public boolean saveChunks(boolean flag, ProgressListener iprogressupdate) {
        return true;
    }

    public boolean func_532_a() {
        return false;
    }

    public boolean func_536_b() {
        return true;
    }

    public String toString() {
        return "RandomLevelSource";
    }

    public boolean unloadOldestChunks() {
        return false;
    }

    public boolean isClean() {
        return true;
    }
}
