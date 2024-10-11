package com.matthewperiut.aether.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import static com.matthewperiut.aether.item.AetherItems.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityHomeShot extends FlyingEntity implements MobSpawnDataProvider {
    public float[] sinage;
    public LivingEntity target;
    public boolean firstRun;
    public int life;
    public int lifeSpan;
    private static final double topSpeed = 0.125;
    private static final float sponge = 57.295773F;

    public EntityHomeShot(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/electroball.png";
        this.lifeSpan = 200;
        this.life = this.lifeSpan;
        this.setBoundingBoxSpacing(0.7F, 0.7F);
        this.firstRun = true;
        this.sinage = new float[3];
        this.fireImmune = true;

        for (int i = 0; i < 3; ++i) {
            this.sinage[i] = this.random.nextFloat() * 6.0F;
        }

    }

    public EntityHomeShot(World world, double x, double y, double z, LivingEntity ep) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/electroball.png";
        this.lifeSpan = 200;
        this.life = this.lifeSpan;
        this.setBoundingBoxSpacing(0.7F, 0.7F);
        this.setPosition(x, y, z);
        this.target = ep;
        this.sinage = new float[3];
        this.fireImmune = true;

        for (int i = 0; i < 3; ++i) {
            this.sinage[i] = this.random.nextFloat() * 6.0F;
        }

    }

    public void tick() {
        super.tick();
        --this.life;
        if (this.firstRun && this.target == null) {
            this.target = (LivingEntity) this.findPlayerToAttack();
            this.firstRun = false;
        }

        if (this.target != null && this.target.isAlive()) {
            if (this.life <= 0) {
                LightningEntity thunder = new LightningEntity(this.world, this.x, this.y, this.z);
                this.world.spawnEntity(thunder);
                this.dead = true;
            } else {
                this.updateAnims();
                this.faceIt();
                this.moveIt(this.target, 0.02);
            }
        } else {
            this.dead = true;
        }

    }

    public void moveIt(Entity e1, double sped) {
        double angle1 = (double) (this.yaw / 57.295773F);
        this.velocityX -= Math.sin(angle1) * sped;
        this.velocityZ += Math.cos(angle1) * sped;
        double a = e1.y - 0.75;
        if (a < this.boundingBox.minY - 0.5) {
            this.velocityY -= sped / 2.0;
        } else if (a > this.boundingBox.minY + 0.5) {
            this.velocityY += sped / 2.0;
        } else {
            this.velocityY += (a - this.boundingBox.minY) * (sped / 2.0);
        }

        if (this.onGround) {
            this.onGround = false;
            this.velocityY = 0.10000000149011612;
        }

    }

    public void faceIt() {
        this.lookAt(this.target, 10.0F, 10.0F);
    }

    public void updateAnims() {
        for (int i = 0; i < 3; ++i) {
            float[] var10000 = this.sinage;
            var10000[i] += 0.3F + (float) i * 0.13F;
            if (this.sinage[i] > 6.283186F) {
                var10000 = this.sinage;
                var10000[i] -= 6.283186F;
            }
        }

    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("LifeLeft", (short) this.life);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.life = nbttagcompound.getShort("LifeLeft");
    }

    public void checkOverLimit() {
        double a = this.target.x - this.x;
        double b = this.target.y - this.y;
        double c = this.target.z - this.z;
        double d = Math.sqrt(a * a + b * b + c * c);
        if (d > 0.125) {
            double e = 0.125 / d;
            this.velocityX *= e;
            this.velocityY *= e;
            this.velocityZ *= e;
        }

    }

    public Entity findPlayerToAttack() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 16.0);
        return entityplayer != null && this.canSee(entityplayer) ? entityplayer : null;
    }

    public void onCollision(Entity entity) {
        super.onCollision(entity);
        if (entity != null && this.target != null && entity == this.target) {
            boolean flag = entity.damage(this, 1);
            if (flag) {
                this.moveIt(entity, -0.1);
            }
        }

    }

    public boolean damage(Entity entity, int i) {
        if (entity != null) {
            this.moveIt(entity, -0.15 - (double) i / 8.0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("HomeShot");
    }
}
