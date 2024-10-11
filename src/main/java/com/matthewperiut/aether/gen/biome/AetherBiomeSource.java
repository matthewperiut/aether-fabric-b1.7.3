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
    public Biome getBiome(ChunkPos arg) {
        return super.getBiome(arg);
    }

    @Override
    public Biome getBiome(int x, int z) {
        return biome;
    }

    @Override
    public double getTemperature(int x, int z) {
        return temperature;
    }

    @Override
    public double[] create(double[] temperatures, int x, int z, int xSize, int zSize) {
        if (temperatures == null || temperatures.length < xSize * zSize)
            temperatures = new double[xSize * zSize];
        Arrays.fill(temperatures, 0, xSize * zSize, temperature);
        return temperatures;
    }

    @Override
    public Biome[] getBiomesInArea(Biome[] biomes, int x, int z, int xSize, int zSize) {
        if (biomes == null || biomes.length < xSize * zSize)
            biomes = new Biome[xSize * zSize];
        if (temperatureMap == null || temperatureMap.length < xSize * zSize) {
            temperatureMap = new double[xSize * zSize];
            downfallMap = new double[xSize * zSize];
        }
        Arrays.fill(biomes, 0, xSize * zSize, biome);
        return biomes;
    }
}
