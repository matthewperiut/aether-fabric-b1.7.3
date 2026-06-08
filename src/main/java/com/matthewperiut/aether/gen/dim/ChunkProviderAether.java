package com.matthewperiut.aether.gen.dim;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.gen.biome.AetherBiomes;
import com.matthewperiut.aether.gen.feature.*;
import net.minecraft.block.Block;
import net.minecraft.block.SandBlock;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakeFeature;

import java.util.Random;

public class ChunkProviderAether implements ChunkSource {
    public static int gumCount;
    private final Random random;
    private final OctavePerlinNoiseSampler minLimitPerlinNoise;
    private final OctavePerlinNoiseSampler maxLimitPerlinNoise;
    private final OctavePerlinNoiseSampler perlinNoise1;
    private final OctavePerlinNoiseSampler perlinNoise2;
    private final OctavePerlinNoiseSampler perlinNoise3;
    private final World world;
    private final Carver cave = new CaveCarver();
    public OctavePerlinNoiseSampler floatingIslandScale;
    public OctavePerlinNoiseSampler floatingIslandNoise;
    public OctavePerlinNoiseSampler forestNoise;

    // Noise buffers
    double[] perlinNoiseBuffer;
    double[] minLimitPerlinNoiseBuffer;
    double[] maxLimitPerlinNoiseBuffer;
    double[] scaleNoiseBuffer;
    double[] depthNoiseBuffer;
    int[][] waterDepths = new int[32][32];

    private double[] heightMap;
    private double[] sandBuffer = new double[256];
    private double[] gravelBuffer = new double[256];
    private double[] depthBuffer = new double[256];
    private Biome[] biomes;
    private double[] temperatures;

    public ChunkProviderAether(World world, long seed) {
        this.world = world;
        this.random = new Random(seed);
        this.minLimitPerlinNoise = new OctavePerlinNoiseSampler(this.random, 16);
        this.maxLimitPerlinNoise = new OctavePerlinNoiseSampler(this.random, 16);
        this.perlinNoise1 = new OctavePerlinNoiseSampler(this.random, 8);
        this.perlinNoise2 = new OctavePerlinNoiseSampler(this.random, 4);
        this.perlinNoise3 = new OctavePerlinNoiseSampler(this.random, 4);
        this.floatingIslandScale = new OctavePerlinNoiseSampler(this.random, 10);
        this.floatingIslandNoise = new OctavePerlinNoiseSampler(this.random, 16);
        this.forestNoise = new OctavePerlinNoiseSampler(this.random, 8);
    }

    /** Vanilla chunk byte[] layout: index = (x*16 + z)*128 + y. We use an int[] so modded ids >255 fit. */
    private static int idx(int x, int y, int z) {
        return (x * 16 + z) * 128 + y;
    }

    // Build the base terrain using noise generation
    public void buildTerrain(int chunkX, int chunkZ, int[] blocks, Biome[] biomes, double[] temperatures) {
        byte sizeXZ = 2;  // chunk subdivision size
        int sizeXZPlus1 = sizeXZ + 1;
        byte sizeY = 33;  // vertical size
        int sizeYPlus1 = sizeXZ + 1;
        this.heightMap = this.generateHeightMap(this.heightMap, chunkX * sizeXZ, 0, chunkZ * sizeXZ, sizeXZPlus1, sizeY, sizeYPlus1);

        for (int xSection = 0; xSection < sizeXZ; ++xSection) {
            for (int zSection = 0; zSection < sizeXZ; ++zSection) {
                for (int yChunk = 0; yChunk < 32; ++yChunk) {
                    double yLerpAmount = 0.25;
                    double density00 = this.heightMap[((xSection) * sizeYPlus1 + zSection) * sizeY + yChunk];
                    double density01 = this.heightMap[((xSection) * sizeYPlus1 + zSection + 1) * sizeY + yChunk];
                    double density10 = this.heightMap[((xSection + 1) * sizeYPlus1 + zSection) * sizeY + yChunk];
                    double density11 = this.heightMap[((xSection + 1) * sizeYPlus1 + zSection + 1) * sizeY + yChunk];
                    double yDelta00 = (this.heightMap[((xSection) * sizeYPlus1 + zSection) * sizeY + yChunk + 1] - density00) * yLerpAmount;
                    double yDelta01 = (this.heightMap[((xSection) * sizeYPlus1 + zSection + 1) * sizeY + yChunk + 1] - density01) * yLerpAmount;
                    double yDelta10 = (this.heightMap[((xSection + 1) * sizeYPlus1 + zSection) * sizeY + yChunk + 1] - density10) * yLerpAmount;
                    double yDelta11 = (this.heightMap[((xSection + 1) * sizeYPlus1 + zSection + 1) * sizeY + yChunk + 1] - density11) * yLerpAmount;

                    for (int ySubChunk = 0; ySubChunk < 4; ++ySubChunk) {
                        double xLerpAmount = 0.125;
                        double density0 = density00;
                        double density1 = density01;
                        double xDelta0 = (density10 - density00) * xLerpAmount;
                        double xDelta1 = (density11 - density01) * xLerpAmount;

                        for (int xSubChunk = 0; xSubChunk < 8; ++xSubChunk) {
                            double zLerpAmount = 0.125;
                            double density = density0;
                            double zDelta = (density1 - density0) * zLerpAmount;

                            for (int zSubChunk = 0; zSubChunk < 8; ++zSubChunk) {
                                // Generate Holystone where density is positive
                                if (density > 0.0) {
                                    int x = xSubChunk + xSection * 8;
                                    int y = yChunk * 4 + ySubChunk;
                                    int z = zSubChunk + zSection * 8;
                                    blocks[idx(x, y, z)] = AetherBlocks.Holystone.id;
                                }

                                density += zDelta;
                            }

                            density0 += xDelta0;
                            density1 += xDelta1;
                        }

                        density00 += yDelta00;
                        density01 += yDelta01;
                        density10 += yDelta10;
                        density11 += yDelta11;
                    }
                }
            }
        }
    }

    // Build the surface layers (grass, dirt, etc.)
    public void buildSurfaces(int chunkX, int chunkZ, int[] blocks, Biome[] biomes) {
        double noiseScale = 0.03125;
        this.sandBuffer = this.perlinNoise2.create(this.sandBuffer, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, noiseScale, noiseScale, 1.0);
        this.gravelBuffer = this.perlinNoise2.create(this.gravelBuffer, chunkX * 16, 109.0134, chunkZ * 16, 16, 1, 16, noiseScale, 1.0, noiseScale);
        this.depthBuffer = this.perlinNoise3.create(this.depthBuffer, chunkX * 16, chunkZ * 16, 0.0, 16, 16, 1, noiseScale * 2.0, noiseScale * 2.0, noiseScale * 2.0);

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int surfaceDepth = (int) (this.depthBuffer[x + z * 16] / 3.0 + 3.0 + this.random.nextDouble() * 0.25);
                int runDepth = -1;

                int topBlock = AetherBlocks.Grass.id;
                int fillerBlock = AetherBlocks.Dirt.id;
                int stoneBlock = AetherBlocks.Holystone.id;

                for (int y = 127; y >= 0; --y) {
                    // (z, x) order mirrors the original section access
                    int current = blocks[idx(z, y, x)];

                    if (current == 0) { // Air
                        runDepth = -1;
                    } else if (current == stoneBlock) {
                        if (runDepth == -1) {
                            if (surfaceDepth <= 0) {
                                topBlock = 0; // Air
                                fillerBlock = stoneBlock;
                            } else {
                                topBlock = AetherBlocks.Grass.id;
                                fillerBlock = AetherBlocks.Dirt.id;
                            }

                            runDepth = surfaceDepth;

                            // Set top block
                            if (y >= 0 && topBlock != 0 && topBlock != current) {
                                blocks[idx(z, y, x)] = topBlock;
                            }
                        } else if (runDepth > 0) {
                            --runDepth;
                            // Set filler block
                            if (fillerBlock != current) {
                                blocks[idx(z, y, x)] = fillerBlock;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return false;
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.getChunk(chunkX, chunkZ);
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        this.random.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);

        // Generate into an int[] working buffer (vanilla byte[] layout, but holds ids >255)
        int[] blocks = new int[32768];

        // Initialize biome arrays with Aether biome
        if (this.biomes == null || this.biomes.length < 256) {
            this.biomes = new Biome[256];
        }
        for (int b = 0; b < 256; b++) {
            this.biomes[b] = AetherBiomes.AETHER;
        }

        if (this.temperatures == null || this.temperatures.length < 256) {
            this.temperatures = new double[256];
        }
        for (int t = 0; t < 256; t++) {
            this.temperatures[t] = 0.5; // Default temperature
        }

        this.buildTerrain(chunkX, chunkZ, blocks, this.biomes, this.temperatures);
        this.buildSurfaces(chunkX, chunkZ, blocks, this.biomes);

        // Cave carving: the vanilla carver expects a byte array, and Aether blocks have
        // ids >255 — map them to placeholder bytes, carve, then apply the carve-outs.
        byte[] tempBlocks = new byte[32768];
        int holystoneId = AetherBlocks.Holystone.id;
        for (int i = 0; i < 32768; i++) {
            int id = blocks[i];
            if (id == holystoneId) {
                tempBlocks[i] = 1; // stone placeholder: carveable
            } else if (id != 0) {
                tempBlocks[i] = 2; // other blocks (grass, dirt) - don't carve
            }
        }

        this.cave.carve(this, this.world, chunkX, chunkZ, tempBlocks);

        for (int i = 0; i < 32768; i++) {
            if (tempBlocks[i] == 0 && blocks[i] == holystoneId) {
                blocks[i] = 0;
            }
        }

        // Build the vanilla chunk: ids <256 go in the byte array; extended ids are applied
        // through Chunk.setBlock, which RetroAPI routes into the extended-block overlay.
        byte[] vanillaBlocks = new byte[32768];
        for (int i = 0; i < 32768; i++) {
            int id = blocks[i];
            if (id < 256) {
                vanillaBlocks[i] = (byte) id;
            }
        }

        Chunk chunk = com.periut.retroapi.world.RetroWorldGen.createChunk(this.world, vanillaBlocks, chunkX, chunkZ);

        // Extended (>255) ids go through the generation-safe path: Chunk.setBlock would fire
        // Block.onPlaced mid-generation and recursively re-generate this chunk.
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    int id = blocks[idx(x, y, z)];
                    if (id >= 256) {
                        com.periut.retroapi.world.RetroWorldGen.setBlockInChunk(chunk, x, y, z, id, 0);
                    }
                }
            }
        }

        chunk.populateHeightMap();
        return chunk;
    }

    public void decorate(ChunkSource chunkSource, int chunkX, int chunkZ) {
        SandBlock.fallInstantly = true;
        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;
        Biome biome = AetherBiomes.AETHER; // Use Aether biome directly
        this.random.setSeed(this.world.getSeed());
        long seedModifier1 = this.random.nextLong() / 2L * 2L + 1L;
        long seedModifier2 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long) chunkX * seedModifier1 + (long) chunkZ * seedModifier2 ^ this.world.getSeed());
        double treeNoiseScale = 0.5;

        // Water lakes
        if (this.random.nextInt(4) == 0) {
            int x = worldX + this.random.nextInt(16) + 8;
            int y = this.random.nextInt(128);
            int z = worldZ + this.random.nextInt(16) + 8;
            (new LakeFeature(Block.WATER.id)).generate(this.world, this.random, x, y, z);
        }

        // Dirt patches
        for (int i = 0; i < 20; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(128);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.Dirt.id, 32)).generate(this.world, this.random, x, y, z);
        }

        // White flowers
        for (int i = 0; i < 2; ++i) {
            int x = worldX + this.random.nextInt(16) + 8;
            int y = this.random.nextInt(128);
            int z = worldZ + this.random.nextInt(16) + 8;
            (new AetherGenFlowers(AetherBlocks.WhiteFlower.id)).generate(this.world, this.random, x, y, z);
        }

        // Purple flowers
        for (int i = 0; i < 2; ++i) {
            if (this.random.nextInt(2) == 0) {
                int x = worldX + this.random.nextInt(16) + 8;
                int y = this.random.nextInt(128);
                int z = worldZ + this.random.nextInt(16) + 8;
                (new AetherGenFlowers(AetherBlocks.PurpleFlower.id)).generate(this.world, this.random, x, y, z);
            }
        }

        // Icestone ore
        for (int i = 0; i < 10; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(128);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.Icestone.id, 32)).generate(this.world, this.random, x, y, z);
        }

        // Ambrosium ore
        for (int i = 0; i < 20; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(128);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.AmbrosiumOre.id, 16)).generate(this.world, this.random, x, y, z);
        }

        // Zanite ore
        for (int i = 0; i < 15; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(64);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.ZaniteOre.id, 8)).generate(this.world, this.random, x, y, z);
        }

        // Gravitite ore
        for (int i = 0; i < 8; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(32);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenMinable(AetherBlocks.GravititeOre.id, 7)).generate(this.world, this.random, x, y, z);
        }

        // Cold clouds (high altitude)
        if (this.random.nextInt(50) == 0) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(32) + 96;
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 2, 4, false)).generate(this.world, this.random, x, y, z);
        }

        // Blue clouds (mid altitude)
        if (this.random.nextInt(13) == 0) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(64) + 32;
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 1, 8, false)).generate(this.world, this.random, x, y, z);
        }

        // Regular clouds
        if (this.random.nextInt(7) == 0) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(64) + 32;
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 0, 16, false)).generate(this.world, this.random, x, y, z);
        }

        // Golden clouds (low altitude, dense)
        if (this.random.nextInt(50) == 0) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(32);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenClouds(AetherBlocks.Aercloud.id, 0, 64, true)).generate(this.world, this.random, x, y, z);
        }

        // Bronze dungeons
        for (int i = 0; i < 2; ++i) {
            int x = worldX + this.random.nextInt(16);
            int y = 32 + this.random.nextInt(64);
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenDungeonBronze(AetherBlocks.LockedDungeonStone.id, AetherBlocks.LockedLightDungeonStone.id, AetherBlocks.DungeonStone.id, AetherBlocks.LightDungeonStone.id, AetherBlocks.Holystone.id, 2, AetherBlocks.Holystone.id, 0, 16, true)).generate(this.world, this.random, x, y, z);
        }

        // Silver dungeon (rare)
        if (this.random.nextInt(500) == 0) {
            int x = worldX + this.random.nextInt(16);
            int y = this.random.nextInt(32) + 64;
            int z = worldZ + this.random.nextInt(16);
            (new AetherGenDungeonSilver(AetherBlocks.LockedDungeonStone.id, AetherBlocks.LockedLightDungeonStone.id, AetherBlocks.DungeonStone.id, AetherBlocks.LightDungeonStone.id, AetherBlocks.Holystone.id, 2, AetherBlocks.Holystone.id, 0, AetherBlocks.Pillar.id)).generate(this.world, this.random, x, y, z);
        }

        // Gold dungeon (very rare, cooldown-gated)
        if (gumCount < 800) {
            gumCount++;
        } else if (this.random.nextInt(32) == 0) {
            int x = worldX + this.random.nextInt(16) + 8;
            int y = this.random.nextInt(64) + 32;
            int z = worldZ + this.random.nextInt(16) + 8;
            boolean generated = (new AetherGenGumdrop()).generate(this.world, this.random, x, y, z);
            if (generated) {
                gumCount = 0;
            }
        }

        // Quicksoil patches
        if (this.random.nextInt(5) == 0) {
            for (int x = worldX; x < worldX + 16; ++x) {
                for (int z = worldZ; z < worldZ + 16; ++z) {
                    for (int y = 0; y < 48; ++y) {
                        if (this.world.getBlockId(x, y, z) == 0 && this.world.getBlockId(x, y + 1, z) == AetherBlocks.Grass.id && this.world.getBlockId(x, y + 2, z) == 0) {
                            (new AetherGenQuicksoil(AetherBlocks.Quicksoil.id)).generate(this.world, this.random, x, y, z);
                            y = 128;
                        }
                    }
                }
            }
        }

        // Tree generation
        int treeCount = (int) ((this.forestNoise.sample((double) worldX * treeNoiseScale, (double) worldZ * treeNoiseScale) / 8.0 + this.random.nextDouble() * 4.0 + 4.0) / 3.0);
        int extraTrees = 0;
        if (this.random.nextInt(10) == 0) {
            ++extraTrees;
        }

        if (biome == Biome.FOREST) {
            extraTrees += treeCount + 5;
        }

        if (biome == Biome.RAINFOREST) {
            extraTrees += treeCount + 5;
        }

        if (biome == Biome.SEASONAL_FOREST) {
            extraTrees += treeCount + 2;
        }

        if (biome == Biome.TAIGA) {
            extraTrees += treeCount + 5;
        }

        if (biome == Biome.DESERT) {
            extraTrees -= 20;
        }

        if (biome == Biome.TUNDRA) {
            extraTrees -= 20;
        }

        if (biome == Biome.PLAINS) {
            extraTrees -= 20;
        }

        extraTrees += treeCount;

        for (int i = 0; i < extraTrees; ++i) {
            int x = worldX + this.random.nextInt(16) + 8;
            int z = worldZ + this.random.nextInt(16) + 8;
            Feature treeGenerator = random.nextInt(100) == 0 ? new AetherGenGoldenOak() : new AetherGenSkyroot();
            treeGenerator.prepare(1.0, 1.0, 1.0);
            treeGenerator.generate(this.world, this.random, x, this.world.getTopY(x, z), z);
        }

        // Water springs
        for (int i = 0; i < 50; ++i) {
            int x = worldX + this.random.nextInt(16) + 8;
            int y = this.random.nextInt(this.random.nextInt(120) + 8);
            int z = worldZ + this.random.nextInt(16) + 8;
            (new AetherGenLiquids(Block.FLOWING_WATER.id)).generate(this.world, this.random, x, y, z);
        }

        SandBlock.fallInstantly = false;
    }

    // Generate the heightmap using Perlin noise
    private double[] generateHeightMap(double[] buffer, int xStart, int yStart, int zStart, int xSize, int ySize, int zSize) {
        if (buffer == null) {
            buffer = new double[xSize * ySize * zSize];
        }

        if (this.depthNoiseBuffer == null) {
            this.depthNoiseBuffer = new double[xSize * ySize * zSize];
            this.minLimitPerlinNoiseBuffer = new double[xSize * ySize * zSize];
            this.maxLimitPerlinNoiseBuffer = new double[xSize * ySize * zSize];
            this.scaleNoiseBuffer = new double[xSize * ySize * zSize];
            this.perlinNoiseBuffer = new double[xSize * ySize * zSize];
        }

        double xzScale = 684.412;
        double yScale = 684.412;
        this.scaleNoiseBuffer = this.floatingIslandScale.create(this.scaleNoiseBuffer, xStart, zStart, xSize, zSize, 1.121, 1.121, 0.5);
        this.perlinNoiseBuffer = this.floatingIslandNoise.create(this.perlinNoiseBuffer, xStart, zStart, xSize, zSize, 200.0, 200.0, 0.5);
        this.depthNoiseBuffer = this.perlinNoise1.create(this.depthNoiseBuffer, xStart, yStart, zStart, xSize, ySize, zSize, xzScale / 80.0, yScale / 160.0, xzScale / 80.0);
        this.minLimitPerlinNoiseBuffer = this.minLimitPerlinNoise.create(this.minLimitPerlinNoiseBuffer, xStart, yStart, zStart, xSize, ySize, zSize, xzScale, yScale, xzScale);
        this.maxLimitPerlinNoiseBuffer = this.maxLimitPerlinNoise.create(this.maxLimitPerlinNoiseBuffer, xStart, yStart, zStart, xSize, ySize, zSize, xzScale, yScale, xzScale);
        int index = 0;
        int scaleIndex = 0;

        for (int x = 0; x < xSize; ++x) {
            for (int z = 0; z < zSize; ++z) {
                double scale = (this.scaleNoiseBuffer[scaleIndex] + 256.0) / 512.0;
                if (scale > 1.0) {
                    scale = 1.0;
                }

                double floatingIslandFactor = this.perlinNoiseBuffer[scaleIndex] / 8000.0;
                if (floatingIslandFactor < 0.0) {
                    floatingIslandFactor = -floatingIslandFactor * 0.3;
                }

                floatingIslandFactor = floatingIslandFactor * 3.0 - 2.0;
                if (floatingIslandFactor > 1.0) {
                    floatingIslandFactor = 1.0;
                }

                floatingIslandFactor /= 8.0;
                floatingIslandFactor = 0.0;
                if (scale < 0.0) {
                    scale = 0.0;
                }

                scale += 0.5;
                floatingIslandFactor = floatingIslandFactor * (double) ySize / 16.0;
                ++scaleIndex;
                double center = (double) ySize / 2.0;

                for (int y = 0; y < ySize; ++y) {
                    double density = 0.0;
                    double yDistanceFromCenter = ((double) y - center) * 8.0 / scale;
                    if (yDistanceFromCenter < 0.0) {
                        yDistanceFromCenter *= -1.0;
                    }

                    double minLimit = this.minLimitPerlinNoiseBuffer[index] / 512.0;
                    double maxLimit = this.maxLimitPerlinNoiseBuffer[index] / 512.0;
                    double depthNoise = (this.depthNoiseBuffer[index] / 10.0 + 1.0) / 2.0;
                    if (depthNoise < 0.0) {
                        density = minLimit;
                    } else if (depthNoise > 1.0) {
                        density = maxLimit;
                    } else {
                        density = minLimit + (maxLimit - minLimit) * depthNoise;
                    }

                    density -= 8.0;
                    int topFadeStart = 32;
                    if (y > ySize - topFadeStart) {
                        double topFade = (float) (y - (ySize - topFadeStart)) / ((float) topFadeStart - 1.0F);
                        density = density * (1.0 - topFade) + -30.0 * topFade;
                    }

                    topFadeStart = 8;
                    if (y < topFadeStart) {
                        double bottomFade = (float) (topFadeStart - y) / ((float) topFadeStart - 1.0F);
                        density = density * (1.0 - bottomFade) + -30.0 * bottomFade;
                    }

                    buffer[index] = density;
                    ++index;
                }
            }
        }

        return buffer;
    }

    public boolean save(boolean saveEntities, LoadingDisplay display) {
        return true;
    }

    public boolean func_532_a() {
        return false;
    }

    public boolean func_536_b() {
        return true;
    }

    public String getDebugInfo() {
        return "RandomLevelSource";
    }

    public boolean tick() {
        return false;
    }

    public boolean canSave() {
        return true;
    }
}
