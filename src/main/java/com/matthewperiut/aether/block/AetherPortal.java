package com.matthewperiut.aether.block;

import com.matthewperiut.aether.gen.dim.AetherDimensions;
import com.matthewperiut.aether.gen.dim.AetherTravelAgent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.entity.HasTeleportationManager;
import net.modificationstation.stationapi.api.template.block.BlockTemplate;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.dimension.TeleportationManager;

import java.util.Random;

public class AetherPortal extends TranslucentBlock implements BlockTemplate, CustomPortal, TeleportationManager {
    public static int spr;

    public AetherPortal(Identifier identifier) {
        super(BlockTemplate.getNextId(), spr, Material.NETHER_PORTAL, false);
        BlockTemplate.onConstructor(this, identifier);
    }

    @Override
    public int getTexture(int i, int j) {
        return spr;
    }

    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    public void updateBoundingBox(BlockView blockView, int x, int y, int z) {
        if (blockView.getBlockId(x - 1, y, z) != this.id && blockView.getBlockId(x + 1, y, z) != this.id) {
            float var7 = 0.125F;
            float var8 = 0.5F;
            this.setBoundingBox(0.5F - var7, 0.0F, 0.5F - var8, 0.5F + var7, 1.0F, 0.5F + var8);
        } else {
            float var5 = 0.5F;
            float var6 = 0.125F;
            this.setBoundingBox(0.5F - var5, 0.0F, 0.5F - var6, 0.5F + var5, 1.0F, 0.5F + var6);
        }
    }

    public boolean isOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean create(World world, int i, int j, int k) {
        int l = 0;
        int i1 = 0;
        if (world.getBlockId(i - 1, j, k) == Block.GLOWSTONE.id || world.getBlockId(i + 1, j, k) == Block.GLOWSTONE.id) {
            l = 1;
        }

        if (world.getBlockId(i, j, k - 1) == Block.GLOWSTONE.id || world.getBlockId(i, j, k + 1) == Block.GLOWSTONE.id) {
            i1 = 1;
        }

        if (l == i1) {
            return false;
        } else {
            if (world.getBlockId(i - l, j, k - i1) == 0) {
                i -= l;
                k -= i1;
            }

            int k1;
            int i2;
            for (k1 = -1; k1 <= 2; ++k1) {
                for (i2 = -1; i2 <= 3; ++i2) {
                    boolean flag = k1 == -1 || k1 == 2 || i2 == -1 || i2 == 3;
                    if (k1 != -1 && k1 != 2 || i2 != -1 && i2 != 3) {
                        int j2 = world.getBlockId(i + l * k1, j + i2, k + i1 * k1);
                        if (flag) {
                            if (j2 != Block.GLOWSTONE.id) {
                                return false;
                            }
                        } else if (j2 != 0 && j2 != Block.FLOWING_WATER.id) {
                            return false;
                        }
                    }
                }
            }

            world.pauseTicking = true;

            for (k1 = 0; k1 < 2; ++k1) {
                for (i2 = 0; i2 < 3; ++i2) {
                    world.setBlock(i + l * k1, j + i2, k + i1 * k1, this.id);
                }
            }

            world.pauseTicking = false;
            return true;
        }
    }

    public void neighborUpdate(World world, int i, int j, int k, int l) {
        int i1 = 0;
        int j1 = 1;
        if (world.getBlockId(i - 1, j, k) == this.id || world.getBlockId(i + 1, j, k) == this.id) {
            i1 = 1;
            j1 = 0;
        }

        int k1;
        for (k1 = j; world.getBlockId(i, k1 - 1, k) == this.id; --k1) {
        }

        if (world.getBlockId(i, k1 - 1, k) != Block.GLOWSTONE.id) {
            world.setBlock(i, j, k, 0);
        } else {
            int l1;
            for (l1 = 1; l1 < 4 && world.getBlockId(i, k1 + l1, k) == this.id; ++l1) {
            }

            if (l1 == 3 && world.getBlockId(i, k1 + l1, k) == Block.GLOWSTONE.id) {
                boolean flag = world.getBlockId(i - 1, j, k) == this.id || world.getBlockId(i + 1, j, k) == this.id;
                boolean flag1 = world.getBlockId(i, j, k - 1) == this.id || world.getBlockId(i, j, k + 1) == this.id;
                if (flag && flag1) {
                    world.setBlock(i, j, k, 0);
                } else if ((world.getBlockId(i + i1, j, k + j1) != Block.GLOWSTONE.id || world.getBlockId(i - i1, j, k - j1) != this.id) && (world.getBlockId(i - i1, j, k - j1) != Block.GLOWSTONE.id || world.getBlockId(i + i1, j, k + j1) != this.id)) {
                    world.setBlock(i, j, k, 0);
                }
            } else {
                world.setBlock(i, j, k, 0);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        if (blockView.getBlockId(x, y, z) == this.id) {
            return false;
        } else {
            boolean var6 = blockView.getBlockId(x - 1, y, z) == this.id && blockView.getBlockId(x - 2, y, z) != this.id;
            boolean var7 = blockView.getBlockId(x + 1, y, z) == this.id && blockView.getBlockId(x + 2, y, z) != this.id;
            boolean var8 = blockView.getBlockId(x, y, z - 1) == this.id && blockView.getBlockId(x, y, z - 2) != this.id;
            boolean var9 = blockView.getBlockId(x, y, z + 1) == this.id && blockView.getBlockId(x, y, z + 2) != this.id;
            boolean var10 = var6 || var7;
            boolean var11 = var8 || var9;
            if (var10 && side == 4) {
                return true;
            } else if (var10 && side == 5) {
                return true;
            } else if (var11 && side == 2) {
                return true;
            } else {
                return var11 && side == 3;
            }
        }
    }

    public int getDroppedItemCount(Random random) {
        return 0;
    }

    @Environment(EnvType.CLIENT)
    public int getRenderLayer() {
        return 1;
    }

    public void onEntityCollision(World world, int x, int y, int z, Entity entity) {
        if (entity.vehicle == null && entity.passenger == null) {
            entity.tickPortalCooldown();

            // Handle the teleportation manager for StationAPI
            if (entity instanceof HasTeleportationManager manager) {
                manager.setTeleportationManager(this);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double) i + 0.5, (double) j + 0.5, (double) k + 0.5, "portal.portal", 1.0F, random.nextFloat() * 0.4F + 0.8F);
        }

        for (int l = 0; l < 4; ++l) {
            double d = (float) i + random.nextFloat();
            double d1 = (float) j + random.nextFloat();
            double d2 = (float) k + random.nextFloat();
            double d3 = 0.0;
            double d4 = 0.0;
            double d5 = 0.0;
            int i1 = random.nextInt(2) * 2 - 1;
            d3 = ((double) random.nextFloat() - 0.5) * 0.5;
            d4 = ((double) random.nextFloat() - 0.5) * 0.5;
            d5 = ((double) random.nextFloat() - 0.5) * 0.5;
            if (world.getBlockId(i - 1, j, k) != this.id && world.getBlockId(i + 1, j, k) != this.id) {
                d = (double) i + 0.5 + 0.25 * (double) i1;
                d3 = random.nextFloat() * 2.0F * (float) i1;
            } else {
                d2 = (double) k + 0.5 + 0.25 * (double) i1;
                d5 = random.nextFloat() * 2.0F * (float) i1;
            }

            world.addParticle("aether_portal", d, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public Identifier getDimension(PlayerEntity player) {
        return AetherDimensions.THE_AETHER;
    }

    public PortalForcer getTravelAgent(PlayerEntity player) {
        return new AetherTravelAgent();
    }
}