package com.matthewperiut.aether.gen.feature;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.TreasureChest;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.block.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class AetherGenDungeonSilver extends AetherGenBuildings
{
    private int baseMeta1;
    private int baseMeta2;
    private int lockedBlockID1;
    private int lockedBlockID2;
    private int wallBlockID1;
    private int wallBlockID2;
    private int baseID1;
    private int baseID2;
    private int columnID;
    private int[][][] rooms = new int[3][3][3];

    public AetherGenDungeonSilver(int i, int j, int k, int l, int m, int m1, int o, int o1, int p)
    {
        this.lockedBlockID1 = i;
        this.lockedBlockID2 = j;
        this.wallBlockID1 = k;
        this.wallBlockID2 = l;
        this.baseID1 = m;
        this.baseMeta1 = m1;
        this.baseID2 = o;
        this.baseMeta2 = o1;
        this.columnID = p;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        this.replaceAir = true;
        if (!this.isBoxEmpty(world, i, j, k, 55, 20, 30))
        {
            return false;
        }
        else
        {
            int row;
            int x;
            int y;
            int z;
            if (world.getBlockId(i, j - 5, k) == 0 || world.getBlockId(i + 55, j - 5, k) == 0 || world.getBlockId(i, j - 5, k + 30) == 0 || world.getBlockId(i + 55, j - 5, k + 30) == 0)
            {
                for (row = 0; row < 100; ++row)
                {
                    x = i - 11 + random.nextInt(77);
                    y = j - 7;
                    z = k - 10 + random.nextInt(50);
                    (new AetherGenClouds(AetherBlocks.Aercloud.id, 0, 10, false)).generate(world, random, x, y, z);
                }
            }

            this.replaceSolid = true;
            this.setBlocks(this.baseID2, this.baseID1, 30);
            this.setMetadata(this.baseMeta2, this.baseMeta1);
            this.addSolidBox(world, random, i, j - 5, k, 55, 5, 30);

            for (row = i; row < i + 55; row += 4)
            {
                this.addColumn(world, random, row, j, k, 14);
                this.addColumn(world, random, row, j, k + 27, 14);
            }

            for (row = k; row < k + 12; row += 4)
            {
                this.addColumn(world, random, i, j, row, 14);
                this.addColumn(world, random, i + 52, j, row, 14);
            }

            for (row = k + 19; row < k + 30; row += 4)
            {
                this.addColumn(world, random, i, j, row, 14);
                this.addColumn(world, random, i + 52, j, row, 14);
            }

            this.setBlocks(this.lockedBlockID1, this.lockedBlockID2, 20);
            this.setMetadata(1, 1);
            this.addHollowBox(world, random, i + 4, j - 1, k + 4, 47, 16, 22);
            this.addPlaneX(world, random, i + 11, j, k + 5, 15, 20);
            this.addPlaneX(world, random, i + 18, j, k + 5, 15, 20);
            this.addPlaneX(world, random, i + 25, j, k + 5, 15, 20);
            this.addPlaneZ(world, random, i + 5, j, k + 11, 20, 15);
            this.addPlaneZ(world, random, i + 5, j, k + 18, 20, 15);
            this.setBlocks(this.lockedBlockID1, AetherBlocks.Trap.id, 15);
            this.setMetadata(1, 1);
            this.addPlaneY(world, random, i + 5, j + 4, k + 5, 20, 20);
            this.addPlaneY(world, random, i + 5, j + 9, k + 5, 20, 20);

            for (row = j; row < j + 2; ++row)
            {
                for (x = k + 14; x < k + 16; ++x)
                {
                    world.setBlockInChunk(i + 4, row, x, 0);
                }
            }

            this.setBlocks(0, 0, 1);
            this.addSolidBox(world, random, i, j - 4, k + 14, 1, 4, 2);
            this.addSolidBox(world, random, i + 1, j - 3, k + 14, 1, 3, 2);
            this.addSolidBox(world, random, i + 2, j - 2, k + 14, 1, 2, 2);
            this.addSolidBox(world, random, i + 3, j - 1, k + 14, 1, 1, 2);
            this.setBlocks(this.lockedBlockID1, this.lockedBlockID2, 20);
            this.setMetadata(1, 1);

            for (row = 0; row < 7; ++row)
            {
                this.addPlaneY(world, random, i - 1, j + 15 + row, k - 1 + 2 * row, 57, 32 - 4 * row);
            }

            row = random.nextInt(3);
            this.addStaircase(world, random, i + 19, j, k + 5 + row * 7, 10);
            this.rooms[2][0][row] = 2;
            this.rooms[2][1][row] = 2;
            this.rooms[2][2][row] = 1;
            x = i + 25;

            for (y = j; y < j + 2; ++y)
            {
                for (z = k + 7 + 7 * row; z < k + 9 + 7 * row; ++z)
                {
                    world.setBlockInChunk(x, y, z, 0);
                }
            }

            row = random.nextInt(3);
            this.addStaircase(world, random, i + 12, j, k + 5 + row * 7, 5);
            this.rooms[1][0][row] = 1;
            this.rooms[1][1][row] = 1;
            row = random.nextInt(3);
            this.addStaircase(world, random, i + 5, j + 5, k + 5 + row * 7, 5);
            this.rooms[0][0][row] = 1;
            this.rooms[0][1][row] = 1;

            int p;
            int r;
            for (p = 0; p < 3; ++p)
            {
                for (int q = 0; q < 3; ++q)
                {
                    label298:
                    for (r = 0; r < 3; ++r)
                    {
                        int type = this.rooms[p][q][r];
                        int roomType;
                        if (p + 1 < 3 && (type == 0 || type == 1 || random.nextBoolean()) && type != 2)
                        {
                            roomType = this.rooms[p + 1][q][r];
                            if (roomType != 2 && (roomType != 1 || type != 1))
                            {
                                this.rooms[p][q][r] = 3;
                                type = 3;
                                x = i + 11 + 7 * p;

                                for (y = j + 5 * q; y < j + 2 + 5 * q; ++y)
                                {
                                    for (z = k + 7 + 7 * r; z < k + 9 + 7 * r; ++z)
                                    {
                                        world.setBlockInChunk(x, y, z, 0);
                                    }
                                }
                            }
                        }

                        if (p - 1 > 0 && (type == 0 || type == 1 || random.nextBoolean()) && type != 2)
                        {
                            roomType = this.rooms[p - 1][q][r];
                            if (roomType != 2 && (roomType != 1 || type != 1))
                            {
                                this.rooms[p][q][r] = 4;
                                type = 4;
                                x = i + 4 + 7 * p;

                                for (y = j + 5 * q; y < j + 2 + 5 * q; ++y)
                                {
                                    for (z = k + 7 + 7 * r; z < k + 9 + 7 * r; ++z)
                                    {
                                        world.setBlockInChunk(x, y, z, 0);
                                    }
                                }
                            }
                        }

                        if (r + 1 < 3 && (type == 0 || type == 1 || random.nextBoolean()) && type != 2)
                        {
                            roomType = this.rooms[p][q][r + 1];
                            if (roomType != 2 && (roomType != 1 || type != 1))
                            {
                                this.rooms[p][q][r] = 5;
                                type = 5;
                                z = k + 11 + 7 * r;

                                for (y = j + 5 * q; y < j + 2 + 5 * q; ++y)
                                {
                                    for (x = i + 7 + 7 * p; x < i + 9 + 7 * p; ++x)
                                    {
                                        world.setBlockInChunk(x, y, z, 0);
                                    }
                                }
                            }
                        }

                        if (r - 1 > 0 && (type == 0 || type == 1 || random.nextBoolean()) && type != 2)
                        {
                            roomType = this.rooms[p][q][r - 1];
                            if (roomType != 2 && (roomType != 1 || type != 1))
                            {
                                this.rooms[p][q][r] = 6;
                                type = 6;
                                z = k + 4 + 7 * r;

                                for (y = j + 5 * q; y < j + 2 + 5 * q; ++y)
                                {
                                    for (x = i + 7 + 7 * p; x < i + 9 + 7 * p; ++x)
                                    {
                                        world.setBlockInChunk(x, y, z, 0);
                                    }
                                }
                            }
                        }

                        roomType = random.nextInt(3);
                        if (type >= 3)
                        {
                            switch (roomType)
                            {
                                case 0:
                                    world.setBlockWithMetadata(i + 7 + p * 7, j - 1 + q * 5, k + 7 + r * 7, AetherBlocks.Trap.id, 1);
                                    break;
                                case 1:
                                    this.addPlaneY(world, random, i + 7 + 7 * p, j + 5 * q, k + 7 + 7 * r, 2, 2);
                                    int u = i + 7 + 7 * p + random.nextInt(2);
                                    int v = k + 7 + 7 * r + random.nextInt(2);
                                    if (world.getBlockId(u, j + 5 * q + 1, v) != 0)
                                    {
                                        break;
                                    }

                                    world.setBlock(u, j + 5 * q + 1, v, Block.CHEST.id);
                                    ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(u, j + 5 * q + 1, v);
                                    u = 0;

                                    while (true)
                                    {
                                        if (u >= 3 + random.nextInt(3))
                                        {
                                            continue label298;
                                        }

                                        ItemStack item = this.getNormalLoot(random);
                                        chest.setInventoryItem(random.nextInt(chest.getInventorySize()), item);
                                        ++u;
                                    }
                                case 2:
                                    this.addPlaneY(world, random, i + 7 + 7 * p, j + 5 * q, k + 7 + 7 * r, 2, 2);
                                    world.setBlock(i + 7 + 7 * p + random.nextInt(2), j + 5 * q + 1, k + 7 + 7 * r + random.nextInt(2), AetherBlocks.ChestMimic.id);
                                    world.setBlock(i + 7 + 7 * p + random.nextInt(2), j + 5 * q + 1, k + 7 + 7 * r + random.nextInt(2), AetherBlocks.ChestMimic.id);
                            }
                        }
                    }
                }
            }

            for (x = 0; x < 24; ++x)
            {
                for (z = 0; z < 20; ++z)
                {
                    p = (int) (Math.sqrt((double) (x * x + (z - 7) * (z - 7))) + Math.sqrt((double) (x * x + (z - 12) * (z - 12))));
                    if (p == 21)
                    {
                        world.setBlockWithMetadata(i + 26 + x, j, k + 5 + z, this.lockedBlockID2, 1);
                    }
                    else if (p > 21)
                    {
                        world.setBlockWithMetadata(i + 26 + x, j, k + 5 + z, this.lockedBlockID1, 1);
                    }
                }
            }

            this.setBlocks(this.lockedBlockID1, this.lockedBlockID1, 1);
            this.setMetadata(1, 1);
            this.addPlaneY(world, random, i + 44, j + 1, k + 11, 6, 8);
            this.addSolidBox(world, random, i + 46, j + 2, k + 13, 4, 2, 4);
            this.addLineX(world, random, i + 46, j + 4, k + 13, 4);
            this.addLineX(world, random, i + 46, j + 4, k + 16, 4);
            this.addPlaneX(world, random, i + 49, j + 4, k + 13, 4, 4);
            this.setBlocks(Block.WOOL.id, Block.WOOL.id, 1);
            this.setMetadata(11, 11);
            this.addPlaneY(world, random, i + 47, j + 3, k + 14, 2, 2);

            for (x = 0; x < 2; ++x)
            {
                for (z = 0; z < 2; ++z)
                {
                    world.setBlockInChunk(i + 44 + x * 5, j + 2, k + 11 + z * 7, AetherBlocks.AmbrosiumTorch.id);
                }
            }

            this.setBlocks(Block.STILL_WATER.id, Block.STILL_WATER.id, 1);
            this.setMetadata(0, 0);
            this.addPlaneY(world, random, i + 35, j + 1, k + 5, 6, 3);
            this.addPlaneY(world, random, i + 35, j + 1, k + 22, 6, 3);
            this.setBlocks(this.lockedBlockID1, this.lockedBlockID1, 1);
            this.setMetadata(1, 1);
            this.addLineZ(world, random, i + 34, j + 1, k + 5, 2);
            this.addLineZ(world, random, i + 41, j + 1, k + 5, 2);
            this.addLineX(world, random, i + 36, j + 1, k + 8, 4);
            world.setBlockWithMetadata(i + 35, j + 1, k + 7, this.lockedBlockID1, 1);
            world.setBlockWithMetadata(i + 40, j + 1, k + 7, this.lockedBlockID1, 1);
            this.addLineZ(world, random, i + 34, j + 1, k + 23, 2);
            this.addLineZ(world, random, i + 41, j + 1, k + 23, 2);
            this.addLineX(world, random, i + 36, j + 1, k + 21, 4);
            world.setBlockWithMetadata(i + 35, j + 1, k + 22, this.lockedBlockID1, 1);
            world.setBlockWithMetadata(i + 40, j + 1, k + 22, this.lockedBlockID1, 1);

            for (x = i + 36; x < i + 40; x += 3)
            {
                for (z = k + 8; z < k + 22; z += 13)
                {
                    world.setBlockInChunk(x, j + 2, z, AetherBlocks.AmbrosiumTorch.id);
                }
            }

            this.addChandelier(world, i + 28, j, k + 10, 8);
            this.addChandelier(world, i + 43, j, k + 10, 8);
            this.addChandelier(world, i + 43, j, k + 19, 8);
            this.addChandelier(world, i + 28, j, k + 19, 8);
            this.addSapling(world, random, i + 45, j + 1, k + 6);
            this.addSapling(world, random, i + 45, j + 1, k + 21);
            // todo: entity EntityValkyrie valk = new EntityValkyrie(world, (double)i + 40.0, (double)j + 1.5, (double)k + 15.0, true);
            // valk.setPosition((double)(i + 40), (double)(j + 2), (double)(k + 15));
            // valk.setDungeon(i + 26, j, k + 5);
            // world.spawnEntity(valk);
            this.setBlocks(this.lockedBlockID1, this.lockedBlockID1, 1);
            this.setMetadata(1, 1);
            this.addHollowBox(world, random, i + 41, j - 2, k + 13, 4, 4, 4);
            x = i + 42 + random.nextInt(2);
            z = k + 14 + random.nextInt(2);
            TreasureChest.PlaceTreasureChest(world, x, y, z, 2);
            world.method_223(x, j - 1, z, 2);
            return true;
        }
    }

    private void addSapling(World world, Random random, int i, int j, int k)
    {
        this.setBlocks(this.lockedBlockID1, this.lockedBlockID1, 1);
        this.setMetadata(1, 1);
        this.addPlaneY(world, random, i, j, k, 3, 3);
        world.setBlockInChunk(i + 1, j, k + 1, AetherBlocks.Dirt.id);
        world.setBlockInChunk(i + 1, j + 1, k + 1, AetherBlocks.GoldenOakSapling.id);

        for (int x = i; x < i + 3; x += 2)
        {
            for (int z = k; z < k + 3; z += 2)
            {
                world.setBlockInChunk(x, j + 1, z, AetherBlocks.AmbrosiumTorch.id);
            }
        }

    }

    private void addChandelier(World world, int i, int j, int k, int h)
    {
        int z;
        for (z = j + h + 3; z < j + h + 6; ++z)
        {
            world.setBlockInChunk(i, z, k, Block.FENCE.id);
        }

        for (z = i - 1; z < i + 2; ++z)
        {
            world.setBlockInChunk(z, j + h + 1, k, Block.GLOWSTONE.id);
        }

        for (z = j + h; z < j + h + 3; ++z)
        {
            world.setBlockInChunk(i, z, k, Block.GLOWSTONE.id);
        }

        for (z = k - 1; z < k + 2; ++z)
        {
            world.setBlockInChunk(i, j + h + 1, z, Block.GLOWSTONE.id);
        }

    }

    private void addColumn(World world, Random random, int i, int j, int k, int h)
    {
        this.setBlocks(this.wallBlockID1, this.wallBlockID2, 20);
        this.setMetadata(1, 1);
        this.addPlaneY(world, random, i, j, k, 3, 3);
        this.addPlaneY(world, random, i, j + h, k, 3, 3);
        this.setBlocks(this.columnID, this.columnID, 1);
        this.setMetadata(0, 0);
        this.addLineY(world, random, i + 1, j, k + 1, h - 1);
        world.setBlockWithMetadata(i + 1, j + h - 1, k + 1, this.columnID, 1);
    }

    private void addStaircase(World world, Random random, int i, int j, int k, int h)
    {
        this.setBlocks(0, 0, 1);
        this.addSolidBox(world, random, i + 1, j, k + 1, 4, h, 4);
        this.setBlocks(this.lockedBlockID1, this.lockedBlockID2, 5);
        this.setMetadata(1, 1);
        this.addSolidBox(world, random, i + 2, j, k + 2, 2, h + 4, 2);
        world.setBlockInChunk(i + 1, j + 0, k + 1, Block.STONE_SLAB.id);
        world.setBlockInChunk(i + 2, j + 0, k + 1, Block.DOUBLE_STONE_SLAB.id);
        world.setBlockInChunk(i + 3, j + 1, k + 1, Block.STONE_SLAB.id);
        world.setBlockInChunk(i + 4, j + 1, k + 1, Block.DOUBLE_STONE_SLAB.id);
        world.setBlockInChunk(i + 4, j + 2, k + 2, Block.STONE_SLAB.id);
        world.setBlockInChunk(i + 4, j + 2, k + 3, Block.DOUBLE_STONE_SLAB.id);
        world.setBlockInChunk(i + 4, j + 3, k + 4, Block.STONE_SLAB.id);
        world.setBlockInChunk(i + 3, j + 3, k + 4, Block.DOUBLE_STONE_SLAB.id);
        world.setBlockInChunk(i + 2, j + 4, k + 4, Block.STONE_SLAB.id);
        world.setBlockInChunk(i + 1, j + 4, k + 4, Block.DOUBLE_STONE_SLAB.id);
        if (h != 5)
        {
            world.setBlockInChunk(i + 1, j + 5, k + 3, Block.STONE_SLAB.id);
            world.setBlockInChunk(i + 1, j + 5, k + 2, Block.DOUBLE_STONE_SLAB.id);
            world.setBlockInChunk(i + 1, j + 6, k + 1, Block.STONE_SLAB.id);
            world.setBlockInChunk(i + 2, j + 6, k + 1, Block.DOUBLE_STONE_SLAB.id);
            world.setBlockInChunk(i + 3, j + 7, k + 1, Block.STONE_SLAB.id);
            world.setBlockInChunk(i + 4, j + 7, k + 1, Block.DOUBLE_STONE_SLAB.id);
            world.setBlockInChunk(i + 4, j + 8, k + 2, Block.STONE_SLAB.id);
            world.setBlockInChunk(i + 4, j + 8, k + 3, Block.DOUBLE_STONE_SLAB.id);
            world.setBlockInChunk(i + 4, j + 9, k + 4, Block.STONE_SLAB.id);
            world.setBlockInChunk(i + 3, j + 9, k + 4, Block.DOUBLE_STONE_SLAB.id);
        }
    }

    private ItemStack getNormalLoot(Random random)
    {
        int item = random.nextInt(15);
        switch (item)
        {
            case 0:
                return new ItemStack(AetherItems.PickZanite);
            case 1:
                return new ItemStack(AetherItems.Bucket, 1, 2);
            case 2:
                return new ItemStack(AetherItems.DartShooter);
            case 3:
                return new ItemStack(AetherItems.MoaEgg, 1, 0);
            case 4:
                return new ItemStack(AetherItems.AmbrosiumShard, random.nextInt(10) + 1);
            case 5:
                return new ItemStack(AetherItems.Dart, random.nextInt(5) + 1, 0);
            case 6:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 1);
            case 7:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 2);
            case 8:
                if (random.nextInt(20) == 0)
                {
                    return new ItemStack(AetherItems.BlueMusicDisk);
                }
                break;
            case 9:
                return new ItemStack(AetherItems.Bucket);
            case 10:
                if (random.nextInt(10) == 0)
                {
                    return new ItemStack(Item.byId[Item.RECORD_13.id + random.nextInt(2)]);
                }
                break;
            case 11:
                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteBoots);
                }

                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteHelmet);
                }

                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteLeggings);
                }

                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteChestplate);
                }
                break;
            case 12:
                if (random.nextInt(4) == 0)
                {
                    return new ItemStack(AetherItems.IronPendant);
                }
            case 13:
                if (random.nextInt(10) == 0)
                {
                    return new ItemStack(AetherItems.GoldPendant);
                }
            case 14:
                if (random.nextInt(15) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteRing);
                }
        }

        return new ItemStack(AetherBlocks.AmbrosiumTorch, random.nextInt(5));
    }

    private ItemStack getSilverLoot(Random random)
    {
        int item = random.nextInt(9);
        switch (item)
        {
            case 0:
                return new ItemStack(AetherItems.GummieSwet, random.nextInt(16));
            case 1:
                return new ItemStack(AetherItems.SwordLightning);
            case 2:
                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.AxeValkyrie);
                }

                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.ShovelValkyrie);
                }

                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.PickValkyrie);
                }
                break;
            case 3:
                return new ItemStack(AetherItems.SwordHoly);
            case 4:
                return new ItemStack(AetherItems.GoldenFeather);
            case 5:
                return new ItemStack(AetherItems.RegenerationStone);
            case 6:
                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.NeptuneHelmet);
                }

                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.NeptuneLeggings);
                }

                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.NeptuneChestplate);
                }
                break;
            case 7:
                if (random.nextBoolean())
                {
                    return new ItemStack(AetherItems.NeptuneBoots);
                }

                return new ItemStack(AetherItems.NeptuneGlove);
            case 8:
                return new ItemStack(AetherItems.InvisibilityCloak);
        }

        return new ItemStack(AetherItems.ZanitePendant);
    }
}
