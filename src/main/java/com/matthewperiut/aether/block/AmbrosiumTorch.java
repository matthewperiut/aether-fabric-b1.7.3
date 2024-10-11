package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AmbrosiumTorch extends TemplateBlock {
    public AmbrosiumTorch(Identifier identifier) {
        super(identifier, Material.PISTON_BREAKABLE);
        this.setTickRandomly(true);
    }

    public Box getCollisionShape(World world, int i, int j, int k) {
        return null;
    }

    public boolean isOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public int getRenderType() {
        return 2;
    }

    public boolean canPlaceAt(World world, int i, int j, int k) {
        if (world.method_1783(i - 1, j, k)) {
            return true;
        } else if (world.method_1783(i + 1, j, k)) {
            return true;
        } else if (world.method_1783(i, j, k - 1)) {
            return true;
        } else {
            return world.method_1783(i, j, k + 1) || world.method_1783(i, j - 1, k);
        }
    }

    public void onPlaced(World world, int i, int j, int k, int l) {
        int i1 = world.getBlockMeta(i, j, k);
        if (l == 1 && world.method_1783(i, j - 1, k)) {
            i1 = 5;
        }

        if (l == 2 && world.method_1783(i, j, k + 1)) {
            i1 = 4;
        }

        if (l == 3 && world.method_1783(i, j, k - 1)) {
            i1 = 3;
        }

        if (l == 4 && world.method_1783(i + 1, j, k)) {
            i1 = 2;
        }

        if (l == 5 && world.method_1783(i - 1, j, k)) {
            i1 = 1;
        }

        world.setBlockMeta(i, j, k, i1);
    }

    public void onTick(World world, int i, int j, int k, Random random) {
        super.onTick(world, i, j, k, random);
        if (world.getBlockMeta(i, j, k) == 0) {
            this.onPlaced(world, i, j, k);
        }

    }

    public void onPlaced(World world, int i, int j, int k) {
        if (world.method_1783(i - 1, j, k)) {
            world.setBlockMeta(i, j, k, 1);
        } else if (world.method_1783(i + 1, j, k)) {
            world.setBlockMeta(i, j, k, 2);
        } else if (world.method_1783(i, j, k - 1)) {
            world.setBlockMeta(i, j, k, 3);
        } else if (world.method_1783(i, j, k + 1)) {
            world.setBlockMeta(i, j, k, 4);
        } else if (world.method_1783(i, j - 1, k)) {
            world.setBlockMeta(i, j, k, 5);
        }

        this.dropTorchIfCantStay(world, i, j, k);
    }

    public void neighborUpdate(World world, int i, int j, int k, int l) {
        if (this.dropTorchIfCantStay(world, i, j, k)) {
            int i1 = world.getBlockMeta(i, j, k);
            boolean flag = !world.method_1783(i - 1, j, k) && i1 == 1;

            if (!world.method_1783(i + 1, j, k) && i1 == 2) {
                flag = true;
            }

            if (!world.method_1783(i, j, k - 1) && i1 == 3) {
                flag = true;
            }

            if (!world.method_1783(i, j, k + 1) && i1 == 4) {
                flag = true;
            }

            if (!world.method_1783(i, j - 1, k) && i1 == 5) {
                flag = true;
            }

            if (flag) {
                this.dropStacks(world, i, j, k, world.getBlockMeta(i, j, k));
                world.setBlock(i, j, k, 0);
            }
        }

    }

    private boolean dropTorchIfCantStay(World world, int i, int j, int k) {
        if (!this.canPlaceAt(world, i, j, k)) {
            this.dropStacks(world, i, j, k, world.getBlockMeta(i, j, k));
            world.setBlock(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public HitResult raycast(World world, int i, int j, int k, Vec3d vec3d, Vec3d vec3d1) {
        int l = world.getBlockMeta(i, j, k) & 7;
        float f = 0.15F;
        if (l == 1) {
            this.setBoundingBox(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 2) {
            this.setBoundingBox(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 3) {
            this.setBoundingBox(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 4) {
            this.setBoundingBox(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else {
            float f1 = 0.1F;
            this.setBoundingBox(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, 0.6F, 0.5F + f1);
        }

        return super.raycast(world, i, j, k, vec3d, vec3d1);
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        int l = world.getBlockMeta(i, j, k);
        double d = (float) i + 0.5F;
        double d1 = (float) j + 0.7F;
        double d2 = (float) k + 0.5F;
        double d3 = 0.2199999988079071;
        double d4 = 0.27000001072883606;
        if (l == 1) {
            world.addParticle("flame", d - d4, d1 + d3, d2, 0.0, 0.0, 0.0);
        } else if (l == 2) {
            world.addParticle("flame", d + d4, d1 + d3, d2, 0.0, 0.0, 0.0);
        } else if (l == 3) {
            world.addParticle("flame", d, d1 + d3, d2 - d4, 0.0, 0.0, 0.0);
        } else if (l == 4) {
            world.addParticle("flame", d, d1 + d3, d2 + d4, 0.0, 0.0, 0.0);
        } else {
            world.addParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
        }

    }
}
