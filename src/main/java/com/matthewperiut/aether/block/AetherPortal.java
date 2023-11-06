package com.matthewperiut.aether.block;

import com.matthewperiut.aether.gen.dim.AetherDimensions;
import com.matthewperiut.aether.gen.dim.AetherTravelAgent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NetherTeleporter;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplatePortal;

import java.util.Random;

public class AetherPortal extends TemplatePortal implements CustomPortal {
    public static int spr;

    public AetherPortal(Identifier identifier) {
        super(identifier, spr);
    }

    @Override
    public int getTextureForSide(int i, int j) {
        return spr;
    }

    public boolean method_736(World world, int i, int j, int k) {
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

            world.stopPhysics = true;

            for (k1 = 0; k1 < 2; ++k1) {
                for (i2 = 0; i2 < 3; ++i2) {
                    world.setBlock(i + l * k1, j + i2, k + i1 * k1, this.id);
                }
            }

            world.stopPhysics = false;
            return true;
        }
    }

    public void onAdjacentBlockUpdate(World world, int i, int j, int k, int l) {
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

            /* todo: particle
            ParticleEntity obj = new EntityAetherPortalFX(world, d, d1, d2, d3, d4, d5);
            ((Minecraft)FabricLoader.getInstance().getGameInstance()).particleManager.addParticle(obj);
             */
        }

    }

    @Override
    public Identifier getDimension(PlayerEntity player) {
        return AetherDimensions.THE_AETHER;
    }

    public NetherTeleporter getTravelAgent(PlayerEntity player) {
        return new AetherTravelAgent();
    }
}
