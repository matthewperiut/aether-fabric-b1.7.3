package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.entity.AbstractAnimalEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends AbstractAnimalEntity {
    public EntityAetherAnimal(World world) {
        super(world);
    }

    protected float getPathfindingFavour(int i, int j, int k) {
        return this.world.getBlockId(i, j - 1, k) == AetherBlocks.Grass.id ? 10.0F : this.world.method_1782(i, j, k) - 0.5F;
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.canSpawnEntity(this.boundingBox) && this.world.method_190(this, this.boundingBox).size() == 0 && !this.world.method_218(this.boundingBox) && this.world.getBlockId(i, j - 1, k) == AetherBlocks.Grass.id && this.world.getLightLevel(i, j, k) > 8 && this.getPathfindingFavour(i, j, k) >= 0.0F;
    }

    public int method_936() {
        return 120;
    }
}
