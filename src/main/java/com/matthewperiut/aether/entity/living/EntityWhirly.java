package com.matthewperiut.aether.entity.living;

import net.minecraft.block.Block;
import net.minecraft.client.entity.particle.ParticleEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;
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
    public List<ParticleEntity> fluffies;
    public static final float pie = 3.141593F;
    public static final float pia = 0.01745329F;
    public float Angle;
    public float Speed;
    public float Curve;
    public boolean evil;

    public EntityWhirly(World world) {
        super(world);
        this.setSize(0.6F, 0.8F);
        this.setPosition(this.x, this.y, this.z);
        this.movementSpeed = 0.6F;
        this.Angle = this.rand.nextFloat() * 360.0F;
        this.Speed = this.rand.nextFloat() * 0.025F + 0.025F;
        this.Curve = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
        this.Life = this.rand.nextInt(512) + 512;
        if (this.rand.nextInt(10) == 0 && !this.shouldStopEvil()) {
            this.evil = true;
            this.Life /= 2;
        }

        this.fluffies = new ArrayList<>();
    }

    public boolean canClimb() {
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

    public void tickHandSwing() {
        boolean flag = false;
        if (this.evil) {
            PlayerEntity entityplayer = (PlayerEntity) this.getPlayer();
            if (entityplayer != null && entityplayer.onGround) {
                this.entity = entityplayer;
                flag = true;
            }
        }

        if (this.entity == null) {
            this.xVelocity = Math.cos((double) (0.01745329F * this.Angle)) * (double) this.Speed;
            this.zVelocity = -Math.sin((double) (0.01745329F * this.Angle)) * (double) this.Speed;
            this.Angle += this.Curve;
        } else {
            super.tickHandSwing();
        }

        List list = this.world.getEntities(this, this.boundingBox.expand(2.5, 2.5, 2.5));
        if (this.Life-- <= 0 || this.method_1393()) {
            this.remove();
        }

        if (this.getPlayer() != null) {
            ++this.entcount;
        }

        int i;
        if (this.entcount >= 128) {
            if (this.evil && this.entity != null) {
                CreeperEntity entitycreeper = new CreeperEntity(this.world);
                entitycreeper.setPositionAndAngles(this.x, this.y - 0.75, this.z, this.rand.nextFloat() * 360.0F, 0.0F);
                entitycreeper.xVelocity = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125;
                entitycreeper.zVelocity = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125;
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

        int i2 = j1 - 1 + this.rand.nextInt(3);
        int j2 = k1 + this.rand.nextInt(5);
        int k2 = l1 - 1 + this.rand.nextInt(3);
        if (this.world.getBlockId(i2, j2, k2) == Block.LEAVES.id) {
            this.world.setBlock(i2, j2, k2, 0);
        }
    }

    public int loot() {
        int i = this.rand.nextInt(100) + 1;
        if (i == 100) {
            return Item.DIAMOND.id;
        } else if (i >= 96) {
            return Item.IRON_INGOT.id;
        } else if (i >= 91) {
            return Item.GOLD_INGOT.id;
        } else if (i >= 82) {
            return Item.COAL.id;
        } else if (i >= 75) {
            return Block.GRAVEL.id;
        } else if (i >= 64) {
            return Block.CLAY.id;
        } else if (i >= 52) {
            return Item.STICK.id;
        } else if (i >= 38) {
            return Item.FLINT.id;
        } else {
            return i > 20 ? Block.LOG.id : Block.SAND.id;
        }
    }

    public void remove() {
        if (this.fluffies.size() > 0) {
            for (int i = 0; i < this.fluffies.size(); ++i) {
                ParticleEntity entityfx = (ParticleEntity) this.fluffies.get(i);
                entityfx.xVelocity *= 0.5;
                entityfx.yVelocity *= 0.75;
                entityfx.zVelocity *= 0.5;
                this.fluffies.remove(entityfx);
            }
        }

        super.remove();
    }

    public boolean canSpawn() {
        if (this.y < 64.0) {
            this.y += 64.0;
        }

        if (this.world.canSpawnEntity(this.boundingBox) && this.world.method_190(this, this.boundingBox).size() == 0 && !this.world.method_218(this.boundingBox)) {
            int i = MathHelper.floor(this.x);
            int j = MathHelper.floor(this.boundingBox.minY);
            int k = MathHelper.floor(this.z);
            boolean flag = true;

            for (int l = 1; l < 20 && l + j < 125; ++l) {
                if (this.world.placeBlock(i, j + l, k) <= 12 || this.world.getBlockId(i, j + l, k) != 0) {
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
        PlayerEntity entityplayer = this.world.getClosestPlayerTo(this, 16.0);
        return entityplayer != null && this.method_928(entityplayer) ? entityplayer : null;
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Angle", this.Angle);
        nbttagcompound.put("Speed", this.Speed);
        nbttagcompound.put("Curve", this.Curve);
        nbttagcompound.put("Life", (short) this.Life);
        nbttagcompound.put("Counter", (short) this.entcount);
        nbttagcompound.put("Evil", this.evil);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
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

    public void method_1353(Entity entity) {
    }

    public int getLimitPerChunk() {
        return 1;
    }

    public boolean method_932() {
        return this.field_1624;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Whirlwind");
    }
}