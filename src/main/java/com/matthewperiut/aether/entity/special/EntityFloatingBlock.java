package com.matthewperiut.aether.entity.special;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.BlockFloating;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityFloatingBlock extends Entity implements EntitySpawnDataProvider {
    public int blockID;
    public int metadata;
    public int flytime;

    public EntityFloatingBlock(World world) {
        super(world);
        this.flytime = 0;
    }

    public EntityFloatingBlock(World world, double d, double d1, double d2, int i, int j) {
        super(world);
        this.flytime = 0;
        this.blockID = i;
        this.metadata = j;
        this.field_1593 = true;
        this.setSize(0.98F, 0.98F);
        this.standingEyeHeight = this.height / 2.0F;
        this.setPosition(d, d1, d2);
        this.xVelocity = 0.0;
        this.yVelocity = 0.0;
        this.zVelocity = 0.0;
        this.prevX = d;
        this.prevY = d1;
        this.prevZ = d2;
    }

    public EntityFloatingBlock(World world, double d, double d1, double d2, int i) {
        this(world, d, d1, d2, i, 0);
    }

    public EntityFloatingBlock(World world, Double d, Double d1, Double d2) {
        this(world, d, d1, d2, 1, 0);
    }

    protected boolean canClimb() {
        return false;
    }

    protected void initDataTracker() {
    }

    public boolean method_1356() {
        return !this.removed;
    }

    public void tick() {
        if (this.blockID == 0) {
            this.remove();
        } else {
            this.prevX = this.x;
            this.prevY = this.y;
            this.prevZ = this.z;
            ++this.flytime;
            this.yVelocity += 0.04;
            this.move(this.xVelocity, this.yVelocity, this.zVelocity);
            this.xVelocity *= 0.9800000190734863;
            this.yVelocity *= 0.9800000190734863;
            this.zVelocity *= 0.9800000190734863;
            int i = MathHelper.floor(this.x);
            int j = MathHelper.floor(this.y);
            int k = MathHelper.floor(this.z);
            if (this.world.getBlockId(i, j, k) == this.blockID || this.world.getBlockId(i, j, k) == AetherBlocks.Grass.id && this.blockID == AetherBlocks.Dirt.id) {
                this.world.setBlock(i, j, k, 0);
            }

            List list = this.world.getEntities(this, this.boundingBox.expand(0.0, 1.0, 0.0));

            for (int n = 0; n < list.size(); ++n) {
                if (list.get(n) instanceof FallingBlockEntity && this.world.canPlaceBlock(this.blockID, i, j, k, true, 1)) {
                    this.world.placeBlockWithMetaData(i, j, k, this.blockID, this.metadata);
                    this.remove();
                }
            }

            if (this.field_1625 && !this.onGround) {
                this.xVelocity *= 0.699999988079071;
                this.zVelocity *= 0.699999988079071;
                this.yVelocity *= -0.5;
                this.remove();
                if ((!this.world.canPlaceBlock(this.blockID, i, j, k, true, 1) || BlockFloating.canFallAbove(this.world, i, j + 1, k) || !this.world.placeBlockWithMetaData(i, j, k, this.blockID, this.metadata)) && !this.world.isClient) {
                }
            } else if (this.flytime > 100 && !this.world.isClient) {
                this.remove();
            }

        }
    }

    protected void writeAdditional(CompoundTag nbttagcompound) {
        nbttagcompound.put("Tile", (byte) this.blockID);
    }

    protected void readAdditional(CompoundTag nbttagcompound) {
        this.blockID = nbttagcompound.getByte("Tile") & 255;
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("FloatingBlock");
    }
}
