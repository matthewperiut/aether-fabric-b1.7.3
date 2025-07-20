package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.WoolBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntitySheepuff extends EntityAetherAnimal implements MobSpawnDataProvider {
    public static final float[][] fleeceColorTable = new float[][]{{1.0F, 1.0F, 1.0F}, {0.975F, 0.85F, 0.6F}, {0.95F, 0.75F, 0.925F}, {0.8F, 0.85F, 0.975F}, {0.95F, 0.95F, 0.6F}, {0.75F, 0.9F, 0.55F}, {0.975F, 0.85F, 0.9F}, {0.65F, 0.65F, 0.65F}, {0.8F, 0.8F, 0.8F}, {0.65F, 0.8F, 0.85F}, {0.85F, 0.7F, 0.95F}, {0.6F, 0.7F, 0.9F}, {0.75F, 0.7F, 0.65F}, {0.7F, 0.75F, 0.6F}, {0.9F, 0.65F, 0.65F}, {0.55F, 0.55F, 0.55F}};
    private int amountEaten;

    public EntitySheepuff(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/sheepuff.png";
        this.setBoundingBoxSpacing(0.9F, 1.3F);
        this.setFleeceColor(getRandomFleeceColor(this.random));
        this.amountEaten = 0;
    }

    public EntitySheepuff(World world, int color) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/sheepuff.png";
        this.setBoundingBoxSpacing(0.9F, 1.3F);
        this.setFleeceColor(color);
        this.amountEaten = 0;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte) 0);
    }

    protected void dropItems() {
        if (!this.getSheared()) {
            this.dropItem(new ItemStack(Block.WOOL.asItem().id, 1 + this.random.nextInt(2), this.getFleeceColor()), 0.0F);
        }

    }

    public boolean interact(PlayerEntity entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getSelectedItem();
        int colour;
        if (itemstack != null && itemstack.itemId == Item.SHEARS.id && !this.getSheared()) {
            if (!this.world.isRemote) {
                int j;
                ItemEntity entityitem;
                if (this.getPuffed()) {
                    this.setPuffed(false);
                    colour = 2 + this.random.nextInt(3);

                    for (j = 0; j < colour; ++j) {
                        entityitem = this.dropItem(new ItemStack(Block.WOOL.asItem().id, 1, this.getFleeceColor()), 1.0F);
                        entityitem.velocityY += (double) (this.random.nextFloat() * 0.05F);
                        entityitem.velocityX += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                        entityitem.velocityZ += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    }
                } else {
                    this.setSheared(true);
                    colour = 2 + this.random.nextInt(3);

                    for (j = 0; j < colour; ++j) {
                        entityitem = this.dropItem(new ItemStack(Block.WOOL.asItem().id, 1, this.getFleeceColor()), 1.0F);
                        entityitem.velocityY += (double) (this.random.nextFloat() * 0.05F);
                        entityitem.velocityX += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                        entityitem.velocityZ += (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    }
                }
            }

            itemstack.damage(1, entityplayer);
        }

        if (itemstack != null && itemstack.itemId == Item.DYE.id && !this.getSheared()) {
            colour = WoolBlock.getBlockMeta(itemstack.getDamage());
            if (this.getFleeceColor() != colour) {
                if (this.getPuffed() && itemstack.count >= 2) {
                    this.setFleeceColor(colour);
                    itemstack.count -= 2;
                } else if (!this.getPuffed()) {
                    this.setFleeceColor(colour);
                    --itemstack.count;
                }
            }
        }

        return false;
    }

    protected void jump() {
        if (this.getPuffed()) {
            this.velocityY = 1.8;
            this.velocityX += this.random.nextGaussian() * 0.5;
            this.velocityZ += this.random.nextGaussian() * 0.5;
        } else {
            this.velocityY = 0.41999998688697815;
        }

    }

    public void tick() {
        super.tick();
        if (this.getPuffed()) {
            this.fallDistance = 0.0F;
            if (this.velocityY < -0.05) {
                this.velocityY = -0.05;
            }
        }

        if (this.random.nextInt(100) == 0) {
            int x = MathHelper.floor(this.x);
            int y = MathHelper.floor(this.y);
            int z = MathHelper.floor(this.z);
            if (this.world.getBlockId(x, y - 1, z) == AetherBlocks.Grass.id) {
                this.world.setBlock(x, y - 1, z, AetherBlocks.Dirt.id);
                ++this.amountEaten;
            }
        }

        if (this.amountEaten == 5 && !this.getSheared() && !this.getPuffed()) {
            this.setPuffed(true);
            this.amountEaten = 0;
        }

        if (this.amountEaten == 10 && this.getSheared() && !this.getPuffed()) {
            this.setSheared(false);
            this.setFleeceColor(0);
            this.amountEaten = 0;
        }

    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putBoolean("Sheared", this.getSheared());
        nbttagcompound.putBoolean("Puffed", this.getPuffed());
        nbttagcompound.putByte("Color", (byte) this.getFleeceColor());
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.setSheared(nbttagcompound.getBoolean("Sheared"));
        this.setPuffed(nbttagcompound.getBoolean("Puffed"));
        this.setFleeceColor(nbttagcompound.getByte("Color"));
    }

    protected String getRandomSound() {
        return "mob.sheep";
    }

    protected String getHurtSound() {
        return "mob.sheep";
    }

    protected String getDeathSound() {
        return "mob.sheep";
    }

    public int getFleeceColor() {
        return this.dataTracker.getByte(16) & 15;
    }

    public void setFleeceColor(int i) {
        byte byte0 = this.dataTracker.getByte(16);
        this.dataTracker.set(16, (byte) (byte0 & 240 | i & 15));
    }

    public boolean getSheared() {
        return (this.dataTracker.getByte(16) & 16) != 0;
    }

    public void setSheared(boolean flag) {
        byte byte0 = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte) (byte0 | 16));
        } else {
            this.dataTracker.set(16, (byte) (byte0 & -17));
        }

    }

    public boolean getPuffed() {
        return (this.dataTracker.getByte(16) & 32) != 0;
    }

    public void setPuffed(boolean flag) {
        byte byte0 = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte) (byte0 | 32));
        } else {
            this.dataTracker.set(16, (byte) (byte0 & -33));
        }

    }

    public static int getRandomFleeceColor(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return 3;
        } else if (i < 10) {
            return 9;
        } else if (i < 15) {
            return 5;
        } else if (i < 18) {
            return 6;
        } else {
            return random.nextInt(500) != 0 ? 0 : 10;
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Sheepuff");
    }
}
