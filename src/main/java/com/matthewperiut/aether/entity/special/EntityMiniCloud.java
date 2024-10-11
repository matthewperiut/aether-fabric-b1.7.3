package com.matthewperiut.aether.entity.special;

import com.matthewperiut.aether.entity.projectile.EntityFiroBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityMiniCloud extends FlyingEntity implements MobSpawnDataProvider {
    public int shotTimer;
    public int lifeSpan;
    public boolean gotPlayer;
    public boolean toLeft;
    public LivingEntity dude;
    public double targetX;
    public double targetY;
    public double targetZ;

    public EntityMiniCloud(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/minicloud.png";
        this.setBoundingBoxSpacing(0.0F, 0.0F);
        this.noClip = true;
        this.pushSpeedReduction = 1.75F;
    }

    public EntityMiniCloud(World world, PlayerEntity ep, boolean flag) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/minicloud.png";
        this.setBoundingBoxSpacing(0.5F, 0.45F);
        this.dude = ep;
        this.toLeft = flag;
        this.lifeSpan = 3600;
        this.getTargetPos();
        this.setPosition(this.targetX, this.targetY, this.targetZ);
        this.pitch = this.dude.pitch;
        this.yaw = this.dude.yaw;
        this.pushSpeedReduction = 1.75F;
        this.animateSpawn();
    }

    public boolean shouldRender(double d) {
        return true;
    }

    public void getTargetPos() {
        if (this.getDistance(this.dude) > 2.0F) {
            this.targetX = this.dude.x;
            this.targetY = this.dude.y - 0.10000000149011612;
            this.targetZ = this.dude.z;
        } else {
            double angle = (double) this.dude.yaw;
            if (this.toLeft) {
                angle -= 90.0;
            } else {
                angle += 90.0;
            }

            angle /= -57.29577319531843;
            this.targetX = this.dude.x + Math.sin(angle) * 1.05;
            this.targetY = this.dude.y - 0.10000000149011612;
            this.targetZ = this.dude.z + Math.cos(angle) * 1.05;
        }

    }

    public boolean atShoulder() {
        double a = this.x - this.targetX;
        double b = this.y - this.targetY;
        double c = this.z - this.targetZ;
        return Math.sqrt(a * a + b * b + c * c) < 0.3;
    }

    public void approachTarget() {
        double a = this.targetX - this.x;
        double b = this.targetY - this.y;
        double c = this.targetZ - this.z;
        double d = Math.sqrt(a * a + b * b + c * c) * 3.25;
        this.velocityX = (this.velocityX + a / d) / 2.0;
        this.velocityY = (this.velocityY + b / d) / 2.0;
        this.velocityZ = (this.velocityZ + c / d) / 2.0;
        Math.atan2(a, c);
    }

    protected Entity findPlayer() {
        return this.world.getClosestPlayer(this, 16.0);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("LifeSpan", (short) this.lifeSpan);
        nbttagcompound.putShort("ShotTimer", (short) this.shotTimer);
        this.gotPlayer = this.dude != null;
        nbttagcompound.putBoolean("GotPlayer", this.gotPlayer);
        nbttagcompound.putBoolean("ToLeft", this.toLeft);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.lifeSpan = nbttagcompound.getShort("LifeSpan");
        this.shotTimer = nbttagcompound.getShort("ShotTimer");
        this.gotPlayer = nbttagcompound.getBoolean("GotPlayer");
        this.toLeft = nbttagcompound.getBoolean("ToLeft");
    }

    public void tickLiving() {
        super.tickLiving();
        --this.lifeSpan;
        if (this.lifeSpan <= 0) {
            this.animateSpawn();
            this.dead = true;
        } else {
            if (this.shotTimer > 0) {
                --this.shotTimer;
            }

            if (this.gotPlayer && this.dude == null) {
                this.gotPlayer = false;
                this.dude = (LivingEntity) this.findPlayer();
            }

            if (this.dude != null && this.dude.isAlive()) {
                this.getTargetPos();
                if (this.atShoulder()) {
                    this.velocityX *= 0.65;
                    this.velocityY *= 0.65;
                    this.velocityZ *= 0.65;
                    this.yaw = this.dude.yaw + (this.toLeft ? 1.0F : -1.0F);
                    this.pitch = this.dude.pitch;
                    if (this.shotTimer <= 0 && this.dude instanceof PlayerEntity && ((PlayerEntity) this.dude).handSwinging) {
                        float spanish = this.yaw - (this.toLeft ? 1.0F : -1.0F);
                        double a = this.x + Math.sin((double) spanish / -57.29577319531843) * 1.6;
                        double b = this.y - 0.25;
                        double c = this.z + Math.cos((double) spanish / -57.29577319531843) * 1.6;
                        EntityFiroBall eh = new EntityFiroBall(this.world, a, b, c, true, true);
                        this.world.spawnEntity(eh);
                        Vec3d vec3d = this.getLookVector();
                        if (vec3d != null) {
                            eh.smotionX = vec3d.x * 1.5;
                            eh.smotionY = vec3d.y * 1.5;
                            eh.smotionZ = vec3d.z * 1.5;
                        }

                        eh.smacked = true;
                        this.world.playSound(this, "aether:mob.zephyr.zephyrshoot", 0.75F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                        this.shotTimer = 40;
                    }
                } else {
                    this.approachTarget();
                }

            } else {
                this.animateSpawn();
                this.dead = true;
            }
        }
    }

    public boolean damage(Entity e, int i) {
        return e != null && e == this.dude ? false : super.damage(e, i);
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("MiniCloud");
    }
}
