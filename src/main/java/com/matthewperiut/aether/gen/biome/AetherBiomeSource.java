package com.matthewperiut.aether.gen.biome;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Arrays;

public class AetherBiomeSource extends BiomeSource {

    private final Biome biome;
    private final double temperature;

    public AetherBiomeSource(double temperature) {
        biome = AetherBiomes.AETHER;
        this.temperature = temperature;
    }

    @Override
    public Biome method_1789(ChunkPos arg) {
        // method_1789 -> getBiome
        return super.method_1789(arg);
    }

    @Override
    public Biome getBiome(int x, int z) {
        return biome;
    }

    @Override
    public double getTemperature(int x, int z) {
        return temperature;
    }

    // method_1790 > getTemperatures
    @Override
    public double[] method_1790(double[] temperatures, int x, int z, int xSize, int zSize) {
        if (temperatures == null || temperatures.length < xSize * zSize)
            temperatures = new double[xSize * zSize];
        Arrays.fill(temperatures, 0, xSize * zSize, temperature);
        return temperatures;
    }

    @Override // method_1791 > getBiomes
    public Biome[] method_1791(Biome[] biomes, int x, int z, int xSize, int zSize) {

        // field_2235 > temperatureNoises
        if (biomes == null || biomes.length < xSize * zSize)
            biomes = new Biome[xSize * zSize];
        if (field_2235 == null || field_2235.length < xSize * zSize) {
            field_2235 = new double[xSize * zSize];
            field_2236 = new double[xSize * zSize];
        }
        Arrays.fill(biomes, 0, xSize * zSize, biome);
        return biomes;
    }
}
