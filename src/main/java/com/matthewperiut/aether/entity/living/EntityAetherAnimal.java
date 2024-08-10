package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends AnimalEntity {
    public EntityAetherAnimal(World world) {
        super(world);
    }

    @Override
    protected float getPathfindingFavor(int i, int j, int k) {
        return this.world.getBlockId(i, j - 1, k) == AetherBlocks.Grass.id ? 10.0F : this.world.method_1782(i, j, k) - 0.5F;
    }

    @Override
    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
    }

    @Override
    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
    }

    @Override
    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.canSpawnEntity(this.boundingBox) && this.world.getEntityCollisions(this, this.boundingBox).isEmpty() && !this.world.isBoxSubmergedInFluid(this.boundingBox) && this.world.getBlockId(i, j - 1, k) == AetherBlocks.Grass.id && this.world.getLightLevel(i, j, k) > 8 && this.getPathfindingFavor(i, j, k) >= 0.0F;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 120;
    }
}
