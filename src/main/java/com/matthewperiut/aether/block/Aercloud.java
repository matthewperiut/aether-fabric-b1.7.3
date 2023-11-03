package com.matthewperiut.aether.block;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.mixin.EntityAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class Aercloud extends TemplateBlockBase implements MetaNamedBlockItemProvider {
    public static final int bouncingMeta = 1;

    public Aercloud(Identifier identifier) {
        super(identifier, Material.ICE);
    }

    public void onEntityCollision(World world, int i, int j, int k, Entity entity) {
        ((EntityAccessor) entity).setFallDistance(0.0F);
        if (world.getBlockMeta(i, j, k) == 1) {
            entity.yVelocity = 2.0;
            if (entity instanceof PlayerEntity player) {
                AetherAchievements.giveAchievement(AetherAchievements.blueCloud, player);
            }
        } else if (entity.yVelocity < 0.0) {
            entity.yVelocity *= 0.005;
        }
    }

    public boolean isFullOpaque() {
        return false;
    }

    public int getRenderPass() {
        return 1;
    }

    protected int droppedMeta(int i) {
        return i;
    }

    public int getBaseColor(int i) {
        if (i == 1) {
            return 3714284;
        } else {
            return i == 2 ? 16777088 : 16777215;
        }
    }

    public int getColorMultiplier(BlockView iblockaccess, int i, int j, int k) {
        return this.getBaseColor(iblockaccess.getBlockMeta(i, j, k));
    }

    public boolean isSideRendered(BlockView iblockaccess, int i, int j, int k, int l) {
        return super.isSideRendered(iblockaccess, i, j, k, 1 - l);
    }

    public AxixAlignedBoundingBox getCollisionShape(World world, int i, int j, int k) {
        return world.getBlockMeta(i, j, k) == 1 ? AxixAlignedBoundingBox.createAndAddToList(i, j, k, i, j, k) : AxixAlignedBoundingBox.createAndAddToList(i, j, k, i + 1, j, k + 1);
    }

    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
