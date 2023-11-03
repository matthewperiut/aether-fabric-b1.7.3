package com.matthewperiut.aether.gen.dim;


import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.NetherTeleporter;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class AetherTravelAgent extends NetherTeleporter {
    private final Random rand = new Random();

    public AetherTravelAgent() {
    }

    public void teleport(World world, Entity entity) {
        if (!this.tryTeleport(world, entity)) {
            this.placeNetherPortal(world, entity);
            this.tryTeleport(world, entity);
        }
    }

    public boolean tryTeleport(World world, Entity entity) {
        char c = 128;
        double d = -1.0;
        int i = 0;
        int j = 0;
        int k = 0;
        int l = MathHelper.floor(entity.x);
        int i1 = MathHelper.floor(entity.z);

        double d6;
        for (int j1 = l - c; j1 <= l + c; ++j1) {
            double d1 = (double) j1 + 0.5 - entity.x;

            for (int j2 = i1 - c; j2 <= i1 + c; ++j2) {
                double d3 = (double) j2 + 0.5 - entity.z;

                for (int k2 = 127; k2 >= 0; --k2) {
                    if (world.getBlockId(j1, k2, j2) == AetherBlocks.Portal.id) {
                        while (world.getBlockId(j1, k2 - 1, j2) == AetherBlocks.Portal.id) {
                            --k2;
                        }

                        d6 = (double) k2 + 0.5 - entity.y;
                        double d7 = d1 * d1 + d6 * d6 + d3 * d3;
                        if (d < 0.0 || d7 < d) {
                            d = d7;
                            i = j1;
                            j = k2;
                            k = j2;
                        }
                    }
                }
            }
        }

        if (d >= 0.0) {
            double d2 = (double) i + 0.5;
            double d4 = (double) j + 0.5;
            d6 = (double) k + 0.5;
            if (world.getBlockId(i - 1, j, k) == AetherBlocks.Portal.id) {
                d2 -= 0.5;
            }

            if (world.getBlockId(i + 1, j, k) == AetherBlocks.Portal.id) {
                d2 += 0.5;
            }

            if (world.getBlockId(i, j, k - 1) == AetherBlocks.Portal.id) {
                d6 -= 0.5;
            }

            if (world.getBlockId(i, j, k + 1) == AetherBlocks.Portal.id) {
                d6 += 0.5;
            }

            entity.setPositionAndAngles(d2, d4, d6, entity.yaw, 0.0F);
            entity.xVelocity = entity.yVelocity = entity.zVelocity = 0.0;
            return true;
        } else {
            return false;
        }
    }

    public boolean placeNetherPortal(World world, Entity entity) {
        byte byte0 = 16;
        double d = -1.0;
        int i = MathHelper.floor(entity.x);
        int j = MathHelper.floor(entity.y);
        int k = MathHelper.floor(entity.z);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.rand.nextInt(4);

        int j2;
        double d2;
        int k3;
        double d4;
        int l4;
        int l5;
        int i7;
        int j8;
        int k9;
        int l10;
        int i12;
        int k12;
        int i13;
        double d6;
        double d8;
        for (j2 = i - byte0; j2 <= i + byte0; ++j2) {
            d2 = (double) j2 + 0.5 - entity.x;

            for (k3 = k - byte0; k3 <= k + byte0; ++k3) {
                d4 = (double) k3 + 0.5 - entity.z;

                label296:
                for (l4 = 127; l4 >= 0; --l4) {
                    if (world.isAir(j2, l4, k3)) {
                        while (l4 > 0 && world.isAir(j2, l4 - 1, k3)) {
                            --l4;
                        }

                        for (l5 = l1; l5 < l1 + 4; ++l5) {
                            i7 = l5 % 2;
                            j8 = 1 - i7;
                            if (l5 % 4 >= 2) {
                                i7 = -i7;
                                j8 = -j8;
                            }

                            for (k9 = 0; k9 < 3; ++k9) {
                                for (l10 = 0; l10 < 4; ++l10) {
                                    for (i12 = -1; i12 < 4; ++i12) {
                                        k12 = j2 + (l10 - 1) * i7 + k9 * j8;
                                        i13 = l4 + i12;
                                        int j13 = k3 + (l10 - 1) * j8 - k9 * i7;
                                        if (i12 < 0 && !this.blockIsGood(world.getBlockId(k12, i13, j13), world.getBlockMeta(k12, i13, j13)) || i12 >= 0 && !world.isAir(k12, i13, j13)) {
                                            continue label296;
                                        }
                                    }
                                }
                            }

                            d6 = (double) l4 + 0.5 - entity.y;
                            d8 = d2 * d2 + d6 * d6 + d4 * d4;
                            if (d < 0.0 || d8 < d) {
                                d = d8;
                                l = j2;
                                i1 = l4;
                                j1 = k3;
                                k1 = l5 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d < 0.0) {
            for (j2 = i - byte0; j2 <= i + byte0; ++j2) {
                d2 = (double) j2 + 0.5 - entity.x;

                for (k3 = k - byte0; k3 <= k + byte0; ++k3) {
                    d4 = (double) k3 + 0.5 - entity.z;

                    label234:
                    for (l4 = 127; l4 >= 0; --l4) {
                        if (world.isAir(j2, l4, k3)) {
                            while (world.isAir(j2, l4 - 1, k3) && l4 > 0) {
                                --l4;
                            }

                            for (l5 = l1; l5 < l1 + 2; ++l5) {
                                i7 = l5 % 2;
                                j8 = 1 - i7;

                                for (k9 = 0; k9 < 4; ++k9) {
                                    for (l10 = -1; l10 < 4; ++l10) {
                                        i12 = j2 + (k9 - 1) * i7;
                                        k12 = l4 + l10;
                                        i13 = k3 + (k9 - 1) * j8;
                                        if (l10 < 0 && !this.blockIsGood(world.getBlockId(i12, k12, i13), world.getBlockMeta(i12, k12, i13)) || l10 >= 0 && !world.isAir(i12, k12, i13)) {
                                            continue label234;
                                        }
                                    }
                                }

                                d6 = (double) l4 + 0.5 - entity.y;
                                d8 = d2 * d2 + d6 * d6 + d4 * d4;
                                if (d < 0.0 || d8 < d) {
                                    d = d8;
                                    l = j2;
                                    i1 = l4;
                                    j1 = k3;
                                    k1 = l5 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int l2 = l;
        int i3 = i1;
        k3 = j1;
        int i4 = k1 % 2;
        int j4 = 1 - i4;
        if (k1 % 4 >= 2) {
            i4 = -i4;
            j4 = -j4;
        }

        boolean flag;
        if (d < 0.0) {
            if (i1 < 70) {
                i1 = 70;
            }

            if (i1 > 118) {
                i1 = 118;
            }

            i3 = i1;

            for (l4 = -1; l4 <= 1; ++l4) {
                for (l5 = 1; l5 < 3; ++l5) {
                    for (i7 = -1; i7 < 3; ++i7) {
                        j8 = l2 + (l5 - 1) * i4 + l4 * j4;
                        k9 = i3 + i7;
                        l10 = k3 + (l5 - 1) * j4 - l4 * i4;
                        flag = i7 < 0;
                        world.setBlock(j8, k9, l10, flag ? Block.GLOWSTONE.id : 0);
                    }
                }
            }
        }

        for (l4 = 0; l4 < 4; ++l4) {
            world.stopPhysics = true;

            for (l5 = 0; l5 < 4; ++l5) {
                for (i7 = -1; i7 < 4; ++i7) {
                    j8 = l2 + (l5 - 1) * i4;
                    k9 = i3 + i7;
                    l10 = k3 + (l5 - 1) * j4;
                    flag = l5 == 0 || l5 == 3 || i7 == -1 || i7 == 3;
                    world.setBlock(j8, k9, l10, flag ? Block.GLOWSTONE.id : AetherBlocks.Portal.id);
                }
            }

            world.stopPhysics = false;

            for (l5 = 0; l5 < 4; ++l5) {
                for (i7 = -1; i7 < 4; ++i7) {
                    j8 = l2 + (l5 - 1) * i4;
                    k9 = i3 + i7;
                    l10 = k3 + (l5 - 1) * j4;
                    world.updateNeighbors(j8, k9, l10, world.getBlockId(j8, k9, l10));
                }
            }
        }

        return true;
    }

    public boolean blockIsGood(int block, int meta) {
        if (block == 0) {
            return false;
        } else if (!Block.BY_ID[block].material.isSolid()) {
            return false;
        } else {
            return block != AetherBlocks.Aercloud.id || meta != 0;
        }
    }
}
