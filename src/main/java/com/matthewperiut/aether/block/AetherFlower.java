package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

import java.util.Random;

public class AetherFlower extends TemplateBlockBase {
    public AetherFlower(Identifier identifier) {
        super(identifier, Material.PLANT);
        this.setTicksRandomly(true);
        float var3 = 0.2F;
        this.setBoundingBox(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
    }

    public boolean canPlaceAt(World var1, int var2, int var3, int var4) {
        return super.canPlaceAt(var1, var2, var3, var4) && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
    }

    protected boolean canThisPlantGrowOnThisBlockID(int var1) {
        return var1 == AetherBlocks.Grass.id || var1 == AetherBlocks.Dirt.id;
    }

    public void onAdjacentBlockUpdate(World var1, int var2, int var3, int var4, int var5) {
        super.onAdjacentBlockUpdate(var1, var2, var3, var4, var5);
        this.func_268_h(var1, var2, var3, var4);
    }

    public void onScheduledTick(World var1, int var2, int var3, int var4, Random var5) {
        this.func_268_h(var1, var2, var3, var4);
    }

    protected final void func_268_h(World var1, int var2, int var3, int var4) {
        if (!this.canGrow(var1, var2, var3, var4)) {
            this.drop(var1, var2, var3, var4, var1.getBlockMeta(var2, var3, var4));
            var1.setBlock(var2, var3, var4, 0);
        }

    }

    public boolean canGrow(World var1, int var2, int var3, int var4) {
        return (var1.getLightLevel(var2, var3, var4) >= 8 || var1.isAboveGroundCached(var2, var3, var4)) && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
    }

    public AxixAlignedBoundingBox getCollisionShape(World var1, int var2, int var3, int var4) {
        return null;
    }

    public boolean isFullOpaque() {
        return false;
    }

    public boolean isFullCube() {
        return false;
    }

    public int getRenderType() {
        return 1;
    }
}
