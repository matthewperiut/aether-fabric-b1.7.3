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

public class AetherGenDungeonBronze extends AetherGenBuildings {
    private final int corridorMeta1;
    private final int corridorMeta2;
    private final int lockedBlockID1;
    private final int lockedBlockID2;
    private final int wallBlockID1;
    private final int wallBlockID2;
    private final int corridorBlockID1;
    private final int corridorBlockID2;
    private final int numRooms;
    private final boolean flat;
    private int n;
    private boolean finished;

    public AetherGenDungeonBronze(int i, int j, int k, int l, int m, int m1, int o, int o1, int p, boolean flag) {
        this.lockedBlockID1 = i;
        this.lockedBlockID2 = j;
        this.wallBlockID1 = k;
        this.wallBlockID2 = l;
        this.corridorBlockID1 = m;
        this.corridorMeta1 = m1;
        this.corridorBlockID2 = o;
        this.corridorMeta2 = o1;
        this.numRooms = p;
        this.flat = flag;
        this.finished = false;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        this.replaceAir = true;
        this.replaceSolid = true;
        this.n = 0;
        if (this.isBoxSolid(world, i, j, k, 16, 12, 16) && this.isBoxSolid(world, i + 20, j, k + 2, 12, 12, 12)) {
            this.setBlocks(this.lockedBlockID1, this.lockedBlockID2, 20);
            this.addHollowBox(world, random, i, j, k, 16, 12, 16);
            this.addHollowBox(world, random, i + 6, j - 2, k + 6, 4, 4, 4);
            // todo: entity EntitySlider slider = new EntitySlider(world);
            // slider.setPosition((double)(i + 8), (double)(j + 2), (double)(k + 8));
            // slider.setDungeon(i, j, k);
            // world.spawnEntity(slider);
            int x = i + 7 + random.nextInt(2);
            int y = j - 1;
            int z = k + 7 + random.nextInt(2);
            TreasureChest.PlaceTreasureChest(world, x, y, z, 1);
            int p;

            x = i + 20;
            z = k + 2;
            if (!this.isBoxSolid(world, x, j, z, 12, 12, 12)) {
                return true;
            } else {
                this.setBlocks(this.wallBlockID1, this.wallBlockID2, 20);
                this.addHollowBox(world, random, x, j, z, 12, 12, 12);
                this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                this.setMetadata(this.corridorMeta2, this.corridorMeta1);
                this.addSquareTube(world, random, x - 5, j, z + 3, 6, 6, 6, 0);

                for (p = x + 2; p < x + 10; p += 3) {
                    for (int q = z + 2; q < z + 10; q += 3) {
                        world.setBlockWithMetadata(p, j, q, AetherBlocks.Trap.id, 0);
                    }
                }

                ++this.n;
                this.generateNextRoom(world, random, x, j, z);
                this.generateNextRoom(world, random, x, j, z);
                if (this.n > this.numRooms || !this.finished) {
                    this.endCorridor(world, random, x, j, z);
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public boolean generateNextRoom(World world, Random random, int i, int j, int k) {
        if (this.n > this.numRooms && !this.finished) {
            this.endCorridor(world, random, i, j, k);
            return false;
        } else {
            int dir = random.nextInt(4);
            int x = i;
            int y = j;
            int z = k;
            if (dir == 0) {
                x = i + 16;
                z = k;
            }

            if (dir == 1) {
                x += 0;
                z += 16;
            }

            if (dir == 2) {
                x -= 16;
                z += 0;
            }

            if (dir == 3) {
                x += 0;
                z -= 16;
            }

            if (!this.isBoxSolid(world, x, j, z, 12, 8, 12)) {
                return false;
            } else {
                this.setBlocks(this.wallBlockID1, this.wallBlockID2, 20);
                this.setMetadata(0, 0);
                this.addHollowBox(world, random, x, j, z, 12, 8, 12);

                int p;
                int q;
                for (p = x; p < x + 12; ++p) {
                    for (p = y; p < y + 8; ++p) {
                        for (q = z; q < z + 12; ++q) {
                            if (world.getBlockId(p, p, q) == this.wallBlockID1 && random.nextInt(100) == 0) {
                                world.setBlockInChunk(p, p, q, AetherBlocks.Trap.id);
                            }
                        }
                    }
                }

                for (p = x + 2; p < x + 10; p += 7) {
                    for (p = z + 2; p < z + 10; p += 7) {
                        world.setBlockWithMetadata(p, j, p, AetherBlocks.Trap.id, 0);
                    }
                }

                this.addPlaneY(world, random, x + 4, y + 1, z + 4, 4, 4);
                p = random.nextInt(2);
                p = x + 5 + random.nextInt(2);
                q = z + 5 + random.nextInt(2);
                switch (p) {
                    case 0:
                        world.setBlock(p, y + 2, q, AetherBlocks.ChestMimic.id);
                        break;
                    case 1:
                        if (world.getBlockId(p, y + 2, q) == 0) {
                            world.setBlock(p, y + 2, q, Block.CHEST.id);
                            ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(p, y + 2, q);

                            for (p = 0; p < 3 + random.nextInt(3); ++p) {
                                ItemStack item = this.getNormalLoot(random);
                                chest.setInventoryItem(random.nextInt(chest.getInventorySize()), item);
                            }
                        }
                }

                this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                this.setMetadata(this.corridorMeta2, this.corridorMeta1);
                switch (dir) {
                    case 0:
                        this.addSquareTube(world, random, x - 5, y, z + 3, 6, 6, 6, 0);
                        break;
                    case 1:
                        this.addSquareTube(world, random, x + 3, y, z - 5, 6, 6, 6, 2);
                        break;
                    case 2:
                        this.addSquareTube(world, random, x + 11, y, z + 3, 6, 6, 6, 0);
                        break;
                    case 3:
                        this.addSquareTube(world, random, x + 3, y, z + 11, 6, 6, 6, 2);
                }

                ++this.n;
                if (!this.generateNextRoom(world, random, x, y, z)) {
                    return false;
                } else {
                    return this.generateNextRoom(world, random, x, y, z);
                }
            }
        }
    }

    public void endCorridor(World world, Random random, int i, int j, int k) {
        this.replaceAir = false;
        boolean tunnelling = true;
        int dir = random.nextInt(3);
        int x = i;
        int y = j;
        int z = k;
        boolean flag;
        if (dir == 0) {
            x = i + 11;

            for (z = k + 3; tunnelling; ++x) {
                if (this.isBoxEmpty(world, x, y, z, 1, 8, 6) || x - i > 100) {
                    tunnelling = false;
                }

                flag = true;

                while (true) {
                    while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                        if (world.getBlockId(x + 1, y, z) != this.wallBlockID1 && world.getBlockId(x + 1, y, z) != this.wallBlockID2 && world.getBlockId(x + 1, y, z) != this.lockedBlockID1 && world.getBlockId(x + 1, y, z) != this.lockedBlockID2) {
                            flag = false;
                        } else {
                            ++x;
                        }
                    }

                    this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                    this.setMetadata(this.corridorMeta2, this.corridorMeta1);
                    this.addPlaneX(world, random, x, y, z, 8, 6);
                    this.setBlocks(0, 0, 1);
                    this.addPlaneX(world, random, x, y + 1, z + 1, 6, 4);
                    break;
                }
            }
        }

        if (dir == 1) {
            x += 3;

            for (z += 11; tunnelling; ++z) {
                if (this.isBoxEmpty(world, x, y, z, 6, 8, 1) || z - k > 100) {
                    tunnelling = false;
                }

                flag = true;

                while (true) {
                    while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                        if (world.getBlockId(x, y, z + 1) != this.wallBlockID1 && world.getBlockId(x, y, z + 1) != this.wallBlockID2 && world.getBlockId(x, y, z + 1) != this.lockedBlockID1 && world.getBlockId(x, y, z + 1) != this.lockedBlockID2) {
                            flag = false;
                        } else {
                            ++z;
                        }
                    }

                    this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                    this.setMetadata(this.corridorMeta2, this.corridorMeta1);
                    this.addPlaneZ(world, random, x, y, z, 6, 8);
                    this.setBlocks(0, 0, 1);
                    this.addPlaneZ(world, random, x + 1, y + 1, z, 4, 6);
                    break;
                }
            }
        }

        if (dir == 2) {
            x += 3;

            for (z += 0; tunnelling; --z) {
                if (this.isBoxEmpty(world, x, y, z, 6, 8, 1) || j - z > 100) {
                    tunnelling = false;
                }

                flag = true;

                while (true) {
                    while (flag && (world.getBlockId(x, y, z) == this.wallBlockID1 || world.getBlockId(x, y, z) == this.wallBlockID2 || world.getBlockId(x, y, z) == this.lockedBlockID1 || world.getBlockId(x, y, z) == this.lockedBlockID2)) {
                        if (world.getBlockId(x, y, z - 1) != this.wallBlockID1 && world.getBlockId(x, y, z - 1) != this.wallBlockID2 && world.getBlockId(x, y, z - 1) != this.lockedBlockID1 && world.getBlockId(x, y, z - 1) != this.lockedBlockID2) {
                            flag = false;
                        } else {
                            --z;
                        }
                    }

                    this.setBlocks(this.corridorBlockID2, this.corridorBlockID1, 5);
                    this.setMetadata(this.corridorMeta2, this.corridorMeta1);
                    this.addPlaneZ(world, random, x, y, z, 6, 8);
                    this.setBlocks(0, 0, 1);
                    this.addPlaneZ(world, random, x + 1, y + 1, z, 4, 6);
                    break;
                }
            }
        }

        this.finished = true;
    }

    private ItemStack getNormalLoot(Random random) {
        int item = random.nextInt(14);
        switch (item) {
            case 0:
                return new ItemStack(AetherItems.PickZanite);
            case 1:
                return new ItemStack(AetherItems.AxeZanite);
            case 2:
                return new ItemStack(AetherItems.SwordZanite);
            case 3:
                return new ItemStack(AetherItems.ShovelZanite);
            case 4:
                return new ItemStack(AetherItems.AgilityCape);
            case 5:
                return new ItemStack(AetherItems.AmbrosiumShard, random.nextInt(10) + 1);
            case 6:
                return new ItemStack(AetherItems.Dart, random.nextInt(5) + 1, 0);
            case 7:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 1);
            case 8:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 2);
            case 9:
                if (random.nextInt(20) == 0) {
                    return new ItemStack(AetherItems.BlueMusicDisk);
                }
                break;
            case 10:
                return new ItemStack(AetherItems.Bucket);
            case 11:
                if (random.nextInt(10) == 0) {
                    return new ItemStack(Item.byId[Item.RECORD_13.id + random.nextInt(2)]);
                }
                break;
            case 12:
                if (random.nextInt(4) == 0) {
                    return new ItemStack(AetherItems.IronRing);
                }
                break;
            case 13:
                if (random.nextInt(10) == 0) {
                    return new ItemStack(AetherItems.GoldRing);
                }
        }

        return new ItemStack(AetherBlocks.AmbrosiumTorch);
    }

    private ItemStack getBronzeLoot(Random random) {
        int item = random.nextInt(7);
        switch (item) {
            case 0:
                return new ItemStack(AetherItems.GummieSwet, random.nextInt(8), random.nextInt(2));
            case 1:
                return new ItemStack(AetherItems.PhoenixBow);
            case 2:
                return new ItemStack(AetherItems.SwordFire);
            case 3:
                return new ItemStack(AetherItems.HammerNotch);
            case 4:
                return new ItemStack(AetherItems.LightningKnife, random.nextInt(16));
            case 5:
                return new ItemStack(AetherItems.Lance);
            case 6:
                return new ItemStack(AetherItems.AgilityCape);
            default:
                return new ItemStack(AetherItems.Stick);
        }
    }
}
