package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.entity.living.EntityFireMinion;
import com.matthewperiut.aether.entity.living.EntityFireMonster;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityFiroBall extends FlyingEntity {
    public float[] sinage;
    public double smotionX;
    public double smotionY;
    public double smotionZ;
    public int life;
    public int lifeSpan;
    public boolean frosty;
    public boolean smacked;
    public boolean fromCloud;
    private static final double topSpeed = 0.125;
    private static final float sponge = 57.295773F;

    public EntityFiroBall(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/firoball.png";
        this.lifeSpan = 300;
        this.life = this.lifeSpan;
        this.setBoundingBoxSpacing(0.9F, 0.9F);
        this.sinage = new float[3];
        this.fireImmune = true;

        for (int i = 0; i < 3; ++i) {
            this.sinage[i] = this.random.nextFloat() * 6.0F;
        }
    }

    public EntityFiroBall(World world, double x, double y, double z, boolean flag) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/firoball.png";
        this.lifeSpan = 300;
        this.life = this.lifeSpan;
        this.setBoundingBoxSpacing(0.9F, 0.9F);
        this.setPositionAndAngles(x, y, z, this.yaw, this.pitch);
        this.sinage = new float[3];
        this.fireImmune = true;

        for (int i = 0; i < 3; ++i) {
            this.sinage[i] = this.random.nextFloat() * 6.0F;
        }

        this.smotionX = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        this.smotionY = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        this.smotionZ = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        if (flag) {
            this.frosty = true;
            this.texture = "aether:stationapi/textures/mobs/iceyball.png";
            this.smotionX /= 3.0;
            this.smotionY = 0.0;
            this.smotionZ /= 3.0;
        }

    }

    public EntityFiroBall(World world, double x, double y, double z) {
        this(world, x, y, z, false);
    }

    public EntityFiroBall(World world, double x, double y, double z, boolean flag, boolean flag2) {
        super(world);
        this.texture = "aether:stationapi/textures/firoball.png";
        this.lifeSpan = 300;
        this.life = this.lifeSpan;
        this.setBoundingBoxSpacing(0.9F, 0.9F);
        this.setPositionAndAngles(x, y, z, this.yaw, this.pitch);
        this.sinage = new float[3];
        this.fireImmune = true;

        for (int i = 0; i < 3; ++i) {
            this.sinage[i] = this.random.nextFloat() * 6.0F;
        }

        this.smotionX = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        this.smotionY = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        this.smotionZ = (0.2 + (double) this.random.nextFloat() * 0.15) * (this.random.nextInt(2) == 0 ? 1.0 : -1.0);
        if (flag) {
            this.frosty = true;
            this.texture = "aether:stationapi/textures/mobs/iceyball.png";
            this.smotionX /= 3.0;
            this.smotionY = 0.0;
            this.smotionZ /= 3.0;
        }

        this.fromCloud = flag2;
    }

    public void tick() {
        super.tick();
        --this.life;
        if (this.life <= 0) {
            this.fizzle();
            this.dead = true;
        }

    }

    public void fizzle() {
        if (this.frosty) {
            this.world.playSound(this, "random.glass", 2.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.2F);
        } else {
            this.world.playSound(this, "random.fizz", 2.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.2F);
        }

        for (int j = 0; j < 16; ++j) {
            double a = this.x + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.25;
            double b = this.y + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.25;
            double c = this.z + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.25;
            if (!this.frosty) {
                this.world.addParticle("largesmoke", a, b, c, 0.0, 0.0, 0.0);
            }
        }

    }

    public void splode() {
        if (this.frosty) {
            this.world.playSound(this, "random.glass", 2.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.2F);
        } else {
            this.world.playSound(this, "random.explode", 2.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.2F);
        }

        for (int j = 0; j < 40; ++j) {
            double a = (double) ((this.random.nextFloat() - 0.5F) * 0.5F);
            double b = (double) ((this.random.nextFloat() - 0.5F) * 0.5F);
            double c = (double) ((this.random.nextFloat() - 0.5F) * 0.5F);
            if (this.frosty) {
                a *= 0.5;
                b *= 0.5;
                c *= 0.5;
                this.world.addParticle("snowshovel", this.x, this.y, this.z, a, b + 0.125, c);
            } else {
                this.world.addParticle("flame", this.x, this.y, this.z, a, b, c);
            }
        }

    }

    public void updateAnims() {
        if (!this.frosty) {
            for (int i = 0; i < 3; ++i) {
                float[] var10000 = this.sinage;
                var10000[i] += 0.3F + (float) i * 0.13F;
                if (this.sinage[i] > 6.283186F) {
                    var10000 = this.sinage;
                    var10000[i] -= 6.283186F;
                }
            }
        }

    }

    public void tickLiving() {
        this.velocityX = this.smotionX;
        this.velocityY = this.smotionY;
        this.velocityZ = this.smotionZ;
        if (this.hasCollided) {
            if (this.frosty && this.smacked) {
                this.splode();
                this.fizzle();
                this.dead = true;
            } else {
                int i = MathHelper.floor(this.x);
                int j = MathHelper.floor(this.boundingBox.minY);
                int k = MathHelper.floor(this.z);
                if (this.smotionX > 0.0 && this.world.getBlockId(i + 1, j, k) != 0) {
                    this.velocityX = this.smotionX = -this.smotionX;
                } else if (this.smotionX < 0.0 && this.world.getBlockId(i - 1, j, k) != 0) {
                    this.velocityX = this.smotionX = -this.smotionX;
                }

                if (this.smotionY > 0.0 && this.world.getBlockId(i, j + 1, k) != 0) {
                    this.velocityY = this.smotionY = -this.smotionY;
                } else if (this.smotionY < 0.0 && this.world.getBlockId(i, j - 1, k) != 0) {
                    this.velocityY = this.smotionY = -this.smotionY;
                }

                if (this.smotionZ > 0.0 && this.world.getBlockId(i, j, k + 1) != 0) {
                    this.velocityZ = this.smotionZ = -this.smotionZ;
                } else if (this.smotionZ < 0.0 && this.world.getBlockId(i, j, k - 1) != 0) {
                    this.velocityZ = this.smotionZ = -this.smotionZ;
                }
            }
        }

        this.updateAnims();
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("LifeLeft", (short) this.life);
        nbttagcompound.put("SeriousKingVampire", this.toNbtList(new double[]{this.smotionX, this.smotionY, this.smotionZ}));
        nbttagcompound.putBoolean("Frosty", this.frosty);
        nbttagcompound.putBoolean("FromCloud", this.fromCloud);
        nbttagcompound.putBoolean("Smacked", this.smacked);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.life = nbttagcompound.getShort("LifeLeft");
        this.frosty = nbttagcompound.getBoolean("Frosty");
        this.fromCloud = nbttagcompound.getBoolean("FromCloud");
        if (this.frosty) {
            this.texture = "aether:stationapi/textures/mobs/iceyball.png";
        }

        this.smacked = nbttagcompound.getBoolean("Smacked");
        NbtList nbttaglist = nbttagcompound.getList("SeriousKingVampire");
        this.smotionX = (double) ((float) ((NbtDouble) nbttaglist.get(0)).value);
        this.smotionY = (double) ((float) ((NbtDouble) nbttaglist.get(1)).value);
        this.smotionZ = (double) ((float) ((NbtDouble) nbttaglist.get(2)).value);
    }

    public void onCollision(Entity entity) {
        super.onCollision(entity);
        boolean flag = false;
        if (entity != null && entity instanceof LivingEntity && !(entity instanceof EntityFiroBall)) {
            if (this.frosty && (!(entity instanceof EntityFireMonster) || this.smacked && !this.fromCloud) && !(entity instanceof EntityFireMinion)) {
                flag = entity.damage(this, 20);
            } else if (!this.frosty && !(entity instanceof EntityFireMonster) && !(entity instanceof EntityFireMinion)) {
                flag = entity.damage(this, 20);
                if (flag) {
                    entity.fireTicks = 100;
                }
            }
        }

        if (flag) {
            this.splode();
            this.fizzle();
            this.dead = true;
        }

    }

    public boolean damage(Entity entity, int i) {
        if (entity != null) {
            Vec3d vec3d = entity.getLookVector();
            if (vec3d != null) {
                this.smotionX = vec3d.x;
                this.smotionY = vec3d.y;
                this.smotionZ = vec3d.z;
            }

            this.smacked = true;
            return true;
        } else {
            return false;
        }
    }
}
