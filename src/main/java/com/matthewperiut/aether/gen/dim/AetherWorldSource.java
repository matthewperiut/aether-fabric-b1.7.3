package com.matthewperiut.aether.gen.dim;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.noise.PerlinOctaveNoise;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Carver;
import net.minecraft.world.source.WorldSource;
import net.modificationstation.stationapi.impl.level.chunk.FlattenedChunk;

import java.util.Random;

public class AetherWorldSource implements WorldSource {
    private final Random random;
    private final PerlinOctaveNoise
            perlinOctaveNoise1,
            perlinOctaveNoise2,
            perlinOctaveNoise3,
            perlinOctaveNoise4,
            perlinOctaveNoise5,
            perlinOctaveNoise6,
            perlinOctaveNoise7,
            perlinOctaveNoise8;
    private final World world;
    private final Carver carver = new Carver();
    private double[]
            climateNoise,
            sandNoise = new double[256],
            gravelNoise = new double[256],
            stoneNoise = new double[256];
    private Biome[] biomes;
    private double[]
            noise3,
            noise1,
            noise2,
            noise6,
            noise7;
    private double[] temperatures;

    public AetherWorldSource(World level, long l) {
        random = new Random(l);
        perlinOctaveNoise1 = new PerlinOctaveNoise(random, 16);
        perlinOctaveNoise2 = new PerlinOctaveNoise(random, 16);
        perlinOctaveNoise3 = new PerlinOctaveNoise(random, 8);
        perlinOctaveNoise4 = new PerlinOctaveNoise(random, 4);
        perlinOctaveNoise5 = new PerlinOctaveNoise(random, 4);
        perlinOctaveNoise6 = new PerlinOctaveNoise(random, 10);
        perlinOctaveNoise7 = new PerlinOctaveNoise(random, 16);
        perlinOctaveNoise8 = new PerlinOctaveNoise(random, 8);
        this.world = level;
    }

    private void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures) {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        climateNoise = calculateNoise(climateNoise, chunkX * byte0, 0, chunkZ * byte0, k, byte1, l);
        for (int i1 = 0; i1 < byte0; i1++)
            for (int j1 = 0; j1 < byte0; j1++)
                for (int k1 = 0; k1 < 32; k1++) {
                    double d = 0.25D;
                    double d1 = climateNoise[((i1) * l + (j1)) * byte1 + (k1)];
                    int a = ((i1) * l + (j1 + 1)) * byte1;
                    double d2 = climateNoise[a + (k1)];
                    int b = ((i1 + 1) * l + (j1)) * byte1;
                    double d3 = climateNoise[b + (k1)];
                    int e = ((i1 + 1) * l + (j1 + 1)) * byte1;
                    double d4 = climateNoise[e + (k1)];
                    double d5 = (climateNoise[((i1) * l + (j1)) * byte1 + (k1 + 1)] - d1) * d;
                    double d6 = (climateNoise[a + (k1 + 1)] - d2) * d;
                    double d7 = (climateNoise[b + (k1 + 1)] - d3) * d;
                    double d8 = (climateNoise[e + (k1 + 1)] - d4) * d;
                    for (int l1 = 0; l1 < 4; l1++) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i2 = 0; i2 < 8; i2++) {
                            int j2 = i2 + i1 * 8 << 11 | j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int k2 = 0; k2 < 8; k2++) {
                                int l2 = 0;
                                if (d15 > 0.0D)
                                    l2 = AetherBlocks.Holystone.id;
                                tiles[j2] = (byte) l2;
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

    public void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
        double d = 0.03125D;
        sandNoise = perlinOctaveNoise4.sample(sandNoise, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, d, d, 1.0D);
        gravelNoise = perlinOctaveNoise4.sample(gravelNoise, chunkX * 16, 109.0134D, chunkZ * 16, 16, 1, 16, d, 1.0D, d);
        stoneNoise = perlinOctaveNoise5.sample(stoneNoise, chunkX * 16, chunkZ * 16, 0.0D, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for (int k = 0; k < 16; k++)
            for (int l = 0; l < 16; l++) {
                int i1 = (int) (stoneNoise[k + l * 16] / 3D + 3D + random.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte0 = (byte) AetherBlocks.Grass.id;
                byte byte1 = (byte) AetherBlocks.Dirt.id;
                byte stone = (byte) AetherBlocks.Holystone.id;
                for (int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    byte byte2 = tiles[l1];
                    if (byte2 == 0) {
                        j1 = -1;
                        continue;
                    }
                    if (byte2 != stone)
                        continue;
                    if (j1 == -1) {
                        if (i1 <= 0) {
                            byte0 = 0;
                            byte1 = stone;
                        }
                        j1 = i1;
                        tiles[l1] = byte0;
                        continue;
                    }
                    if (j1 > 0) {
                        j1--;
                        tiles[l1] = byte1;
                    }
                }
            }
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        return loadChunk(chunkX, chunkZ);
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        random.setSeed((long) chunkX * 0x4f9939f508L + (long) chunkZ * 0x1ef1565bd5L);
        byte[] tiles = new byte[32768];
        Chunk chunk = new Chunk(world, tiles, chunkX, chunkZ);
        // world.method_1781 is world.getBiomeSource
        biomes = world.method_1781().getBiomes(biomes, chunkX * 16, chunkZ * 16, 16, 16);
        double[] temperatures = world.method_1781().temperatureNoises;
        shapeChunk(chunkX, chunkZ, tiles, biomes, temperatures);
        buildSurface(chunkX, chunkZ, tiles, biomes);
        carver.generate(this, world, chunkX, chunkZ, tiles);
        chunk.generateHeightmap();

        FlattenedChunk flattenedChunk = new FlattenedChunk(world, chunkX, chunkZ);
        flattenedChunk.fromLegacy(tiles);
        flattenedChunk.generateHeightmap();

        return flattenedChunk;
    }

    private double[] calculateNoise(double[] noises, int chunkX, int chunkY, int chunkZ, int noiseResolutionX, int noiseResolutionY, int noiseResolutionZ) {
        if (noises == null)
            noises = new double[noiseResolutionX * noiseResolutionY * noiseResolutionZ];
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        noise6 = perlinOctaveNoise6.sample(noise6, chunkX, chunkZ, noiseResolutionX, noiseResolutionZ, 1.121D, 1.121D, 0.5D);
        noise7 = perlinOctaveNoise7.sample(noise7, chunkX, chunkZ, noiseResolutionX, noiseResolutionZ, 200D, 200D, 0.5D);
        d *= 2D;
        noise3 = perlinOctaveNoise3.sample(noise3, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d / 80D, d1 / 160D, d / 80D);
        noise1 = perlinOctaveNoise1.sample(noise1, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d, d1, d);
        noise2 = perlinOctaveNoise2.sample(noise2, chunkX, chunkY, chunkZ, noiseResolutionX, noiseResolutionY, noiseResolutionZ, d, d1, d);
        int k1 = 0;
        for (int j2 = 0; j2 < noiseResolutionX; j2++)
            for (int l2 = 0; l2 < noiseResolutionZ; l2++)
                for (int j3 = 0; j3 < noiseResolutionY; j3++) {
                    double d8;
                    double d10 = noise1[k1] / 512D;
                    double d11 = noise2[k1] / 512D;
                    double d12 = (noise3[k1] / 10D + 1.0D) / 2D;
                    if (d12 < 0.0D)
                        d8 = d10;
                    else if (d12 > 1.0D)
                        d8 = d11;
                    else
                        d8 = d10 + (d11 - d10) * d12;
                    d8 -= 8D;
                    int k3 = 32;
                    if (j3 > noiseResolutionY - k3) {
                        double d13 = (float) (j3 - (noiseResolutionY - k3)) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if (j3 < k3) {
                        double d14 = (float) (k3 - j3) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }
                    noises[k1] = d8;
                    k1++;
                }
        return noises;
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public void decorate(WorldSource arg, int i, int j) {

    }

    @Override
    public boolean saveChunks(boolean bl, ProgressListener arg) {
        return true;
    }

    @Override
    public boolean unloadOldestChunks() {
        return false;
    }

    @Override
    public boolean isClean() {
        return true;
    }

    @Override
    public String toString() {
        return "RandomLevelSource";
    }
}
