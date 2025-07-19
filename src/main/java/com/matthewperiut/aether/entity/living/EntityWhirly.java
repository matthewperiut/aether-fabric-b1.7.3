package com.matthewperiut.aether.entity.living;

import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityWhirly extends EntityAetherAnimal implements MobSpawnDataProvider {
    public int entcount = 0;
    public int Life;
    public List<Particle> fluffies;
    public static final float pie = 3.141593F;
    public static final float pia = 0.01745329F;
    public float Angle;
    public float Speed;
    public float Curve;
    public boolean evil;

    public EntityWhirly(World world) {
        super(world);
        this.setBoundingBoxSpacing(0.6F, 0.8F);
        this.setPosition(this.x, this.y, this.z);
        this.movementSpeed = 0.6F;
        this.Angle = this.random.nextFloat() * 360.0F;
        this.Speed = this.random.nextFloat() * 0.025F + 0.025F;
        this.Curve = (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
        this.Life = this.random.nextInt(512) + 512;
        if (this.random.nextInt(10) == 0 && !this.shouldStopEvil()) {
            this.evil = true;
            this.Life /= 2;
        }

        this.fluffies = new ArrayList<>();
    }

    public boolean bypassesSteppingEffects() {
        return false;
    }

    public boolean shouldStopEvil() {
        /* will do later
        if (!(this.world.dimensionData instanceof DimensionFile)) {
            return false;
        } else {
            File file = new File(((DimensionFile)this.world.dimensionData).getParentFolder(), "stopevil.txt");
            return file.exists();
        }*/
        return false;
    }

    public void tickLiving() {
        boolean flag = false;
        if (this.evil) {
            PlayerEntity entityplayer = (PlayerEntity) this.getPlayer();
            if (entityplayer != null && entityplayer.onGround) {
                this.target = entityplayer;
                flag = true;
            }
        }

        if (this.target == null) {
            this.velocityX = Math.cos((double) (0.01745329F * this.Angle)) * (double) this.Speed;
            this.velocityZ = -Math.sin((double) (0.01745329F * this.Angle)) * (double) this.Speed;
            this.Angle += this.Curve;
        } else {
            super.tickLiving();
        }

        List list = this.world.getEntities(this, this.boundingBox.expand(2.5, 2.5, 2.5));
        if (this.Life-- <= 0 || this.checkWaterCollisions()) {
            this.markDead();
        }

        if (this.getPlayer() != null) {
            ++this.entcount;
        }

        int i;
        if (this.entcount >= 128) {
            if (this.evil && this.target != null) {
                CreeperEntity entitycreeper = new CreeperEntity(this.world);
                entitycreeper.setPositionAndAnglesKeepPrevAngles(this.x, this.y - 0.75, this.z, this.random.nextFloat() * 360.0F, 0.0F);
                entitycreeper.velocityX = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
                entitycreeper.velocityZ = (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.125;
                this.world.spawnEntity(entitycreeper);
                this.entcount = 0;
            } else {
                i = this.loot();
                if (i != 0) {
                    this.dropItem(i, 1);
                    this.entcount = 0;
                }
            }
        }

        int j1 = MathHelper.floor(this.x);
        int k1 = MathHelper.floor(this.y);
        int l1 = MathHelper.floor(this.z);
        if (this.world.getBlockId(j1, k1 + 1, l1) != 0) {
            this.Life -= 50;
        }

        int i2 = j1 - 1 + this.random.nextInt(3);
        int j2 = k1 + this.random.nextInt(5);
        int k2 = l1 - 1 + this.random.nextInt(3);
        if (this.world.getBlockId(i2, j2, k2) == Block.LEAVES.id) {
            this.world.setBlock(i2, j2, k2, 0);
        }
    }

    public int loot() {
        int i = this.random.nextInt(100) + 1;
        if (i == 100) {
            return Item.DIAMOND.id;
        } else if (i >= 96) {
            return Item.IRON_INGOT.id;
        } else if (i >= 91) {
            return Item.GOLD_INGOT.id;
        } else if (i >= 82) {
            return Item.COAL.id;
        } else if (i >= 75) {
            return Block.GRAVEL.asItem().id;
        } else if (i >= 64) {
            return Block.CLAY.asItem().id;
        } else if (i >= 52) {
            return Item.STICK.id;
        } else if (i >= 38) {
            return Item.FLINT.id;
        } else {
            return i > 20 ? Block.LOG.asItem().id : Block.SAND.asItem().id;
        }
    }

    public void markDead() {
        if (this.fluffies.size() > 0) {
            for (int i = 0; i < this.fluffies.size(); ++i) {
                Particle entityfx = (Particle) this.fluffies.get(i);
                entityfx.velocityX *= 0.5;
                entityfx.velocityY *= 0.75;
                entityfx.velocityZ *= 0.5;
                this.fluffies.remove(entityfx);
            }
        }

        super.markDead();
    }

    public boolean canSpawn() {
        if (this.y < 64.0) {
            this.y += 64.0;
        }

        if (this.world.canSpawnEntity(this.boundingBox) && this.world.getEntityCollisions(this, this.boundingBox).size() == 0 && !this.world.isBoxSubmergedInFluid(this.boundingBox)) {
            int i = MathHelper.floor(this.x);
            int j = MathHelper.floor(this.boundingBox.minY);
            int k = MathHelper.floor(this.z);
            boolean flag = true;

            for (int l = 1; l < 20 && l + j < 125; ++l) {
                if (this.world.getLightLevel(i, j + l, k) <= 12 || this.world.getBlockId(i, j + l, k) != 0) {
                    flag = false;
                    break;
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    public Entity getPlayer() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 16.0);
        return entityplayer != null && this.canSee(entityplayer) ? entityplayer : null;
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putFloat("Angle", this.Angle);
        nbttagcompound.putFloat("Speed", this.Speed);
        nbttagcompound.putFloat("Curve", this.Curve);
        nbttagcompound.putShort("Life", (short) this.Life);
        nbttagcompound.putShort("Counter", (short) this.entcount);
        nbttagcompound.putBoolean("Evil", this.evil);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.Angle = nbttagcompound.getFloat("Angle");
        this.Speed = nbttagcompound.getFloat("Speed");
        this.Curve = nbttagcompound.getFloat("Curve");
        this.Life = nbttagcompound.getShort("Life");
        this.entcount = nbttagcompound.getShort("Counter");
        this.evil = nbttagcompound.getBoolean("Evil");
    }

    public boolean damage(Entity entity, int i) {
        return false;
    }

    public void onCollision(Entity entity) {
    }

    public int getLimitPerChunk() {
        return 1;
    }

    public boolean isOnLadder() {
        return this.horizontalCollision;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Whirlwind");
    }
}