package com.matthewperiut.aether.block;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Aercloud extends TemplateBlock implements MetaNamedBlockItemProvider {
    public static final int bouncingMeta = 1;

    public Aercloud(Identifier identifier) {
        super(identifier, Material.ICE);
    }

    public void onEntityCollision(World world, int i, int j, int k, Entity entity) {
        ((EntityAccessor) entity).setFallDistance(0.0F);
        if (!entity.isSneaking()) {
            if (world.getBlockMeta(i, j, k) == 1) {
                entity.velocityY = 2.0;
                if (entity instanceof PlayerEntity player) {
                        AetherAchievements.giveAchievement(AetherAchievements.blueCloud, player);
                }
            }
        }
        if (entity.velocityY < 0.0) {
            entity.velocityY *= 0.005;
        }
    }

    public boolean isOpaque() {
        return false;
    }

    public int getRenderLayer() {
        return 1;
    }

    protected int getDroppedItemMeta(int i) {
        return i;
    }

    public int getColor(int i) {
        if (i == 1) {
            return 3714284;
        } else {
            return i == 2 ? 16777088 : 16777215;
        }
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getColor(iblockaccess.getBlockMeta(i, j, k));
    }

    public boolean isSideVisible(BlockView iblockaccess, int i, int j, int k, int l) {
        return super.isSideVisible(iblockaccess, i, j, k, 1 - l);
    }

    public Box getCollisionShape(World world, int i, int j, int k) {
        return Box.createCached(i, j, k, i + 1, j, k + 1);
    }

    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
