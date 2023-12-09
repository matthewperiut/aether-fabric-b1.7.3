package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.EntityPath;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityAerbunny extends EntityAetherAnimal implements MobSpawnDataProvider {
    public int age;
    public int mate;
    public boolean grab;
    public boolean fear;
    public boolean gotrider;
    public Entity runFrom;
    public float puffiness;

    public EntityAerbunny(World world) {
        super(world);
        this.movementSpeed = 2.5F;
        this.texture = "aether:stationapi/textures/mobs/aerbunny.png";
        this.standingEyeHeight = -0.16F;
        this.setSize(0.4F, 0.4F);
        this.health = 6;
        if (this.renderDistanceMultiplier < 5.0) {
            this.renderDistanceMultiplier = 5.0;
        }

        this.age = this.rand.nextInt(64);
        this.mate = 0;
    }

    public void tick() {
        if (this.gotrider) {
            this.gotrider = false;
            if (this.vehicle == null) {
                PlayerEntity entityplayer = (PlayerEntity) this.findPlayerToRunFrom();
                if (entityplayer != null && this.distanceTo(entityplayer) < 2.0F && entityplayer.passenger == null) {
                    this.startRiding(entityplayer);
                }
            }
        }

        if (this.age < 1023) {
            ++this.age;
        } else if (this.mate < 127) {
            ++this.mate;
        } else {
            int i = 0;
            List list = this.world.getEntities(this, this.boundingBox.expand(16.0, 16.0, 16.0));

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof EntityAerbunny) {
                    ++i;
                }
            }

            if (i > 12) {
                this.proceed();
                return;
            }

            List list1 = this.world.getEntities(this, this.boundingBox.expand(1.0, 1.0, 1.0));
            boolean flag = false;

            for (int k = 0; k < list.size(); ++k) {
                Entity entity1 = (Entity) list1.get(k);
                if (entity1 instanceof EntityAerbunny && entity1 != this) {
                    EntityAerbunny entitybunny = (EntityAerbunny) entity1;
                    if (entitybunny.vehicle == null && entitybunny.age >= 1023) {
                        EntityAerbunny entitybunny1 = new EntityAerbunny(this.world);
                        entitybunny1.setPosition(this.x, this.y, this.z);
                        this.world.spawnEntity(entitybunny1);
                        this.world.playSound(this, "mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                        this.proceed();
                        entitybunny.proceed();
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag) {
                this.mate = this.rand.nextInt(16);
            }
        }

        if (this.puffiness > 0.0F) {
            this.puffiness -= 0.1F;
        } else {
            this.puffiness = 0.0F;
        }

        super.tick();
    }

    protected void handleFallDamage(float f) {
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Fear", this.fear);
        if (this.passenger != null) {
            this.gotrider = true;
        }

        nbttagcompound.put("GotRider", this.gotrider);
        nbttagcompound.put("RepAge", (short) this.age);
        nbttagcompound.put("RepMate", (short) this.mate);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.fear = nbttagcompound.getBoolean("Fear");
        this.gotrider = nbttagcompound.getBoolean("GotRider");
        this.age = nbttagcompound.getShort("RepAge");
        this.mate = nbttagcompound.getShort("RepMate");
    }

    protected void tickHandSwing() {
        int i;
        if (this.onGround) {
            if (this.forwardVelocity != 0.0F) {
                this.jump();
            }
        } else if (this.vehicle != null) {
            if (this.vehicle.removed) {
                this.startRiding(this.vehicle);
            } else if (!this.vehicle.onGround && !this.vehicle.method_1393()) {
                ((EntityAccessor) this.vehicle).setFallDistance(0.0F);
                Entity var10000 = this.vehicle;
                var10000.yVelocity += 0.05000000074505806;
                if (this.vehicle.yVelocity < -0.22499999403953552 && this.vehicle instanceof LivingEntity && ((LivingEntityAccessor) this.vehicle).getJumping()) {
                    this.vehicle.yVelocity = 0.125;
                    this.cloudPoop();
                    this.puffiness = 1.15F;
                }
            }
        } else if (!this.grab) {
            if (this.forwardVelocity != 0.0F) {
                int j = MathHelper.floor(this.x);
                i = MathHelper.floor(this.boundingBox.minY);
                int k1 = MathHelper.floor(this.boundingBox.minY - 0.5);
                int l1 = MathHelper.floor(this.z);
                if ((this.world.getBlockId(j, i - 1, l1) != 0 || this.world.getBlockId(j, k1 - 1, l1) != 0) && this.world.getBlockId(j, i + 2, l1) == 0 && this.world.getBlockId(j, i + 1, l1) == 0) {
                    if (this.yVelocity < 0.0) {
                        this.cloudPoop();
                        this.puffiness = 0.9F;
                    }

                    this.yVelocity = 0.2;
                }
            }

            if (this.yVelocity < -0.1) {
                this.yVelocity = -0.1;
            }
        }

        if (!this.grab) {
            super.tickHandSwing();
            if (this.fear && this.rand.nextInt(4) == 0) {
                if (this.runFrom != null) {
                    this.runLikeHell();
                    this.world.addParticle("splash", this.x, this.y, this.z, 0.0, 0.0, 0.0);
                    if (!this.method_633()) {
                        this.lookAt(this.runFrom, 30.0F, 30.0F);
                    }

                    if (this.runFrom.removed || this.distanceTo(this.runFrom) > 16.0F) {
                        this.runFrom = null;
                    }
                } else {
                    this.runFrom = this.findPlayerToRunFrom();
                }
            }
        } else if (this.onGround) {
            this.grab = false;
            this.world.playSound(this, "aether:mobs.aerbunny.aerbunnyLand", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            List list = this.world.getEntities(this, this.boundingBox.expand(12.0, 12.0, 12.0));

            for (i = 0; i < list.size(); ++i) {
                Entity entity = (Entity) list.get(i);
                if (entity instanceof MonsterEntity) {
                    MonsterEntity entitymobs = (MonsterEntity) entity;
                    entitymobs.method_636(this);
                }
            }
        }

        if (this.method_1393()) {
            this.jump();
        }

    }

    public void cloudPoop() {
        double a = (double) (this.rand.nextFloat() - 0.5F);
        double c = (double) (this.rand.nextFloat() - 0.5F);
        double d = this.x + a * 0.4000000059604645;
        double e = this.boundingBox.minY;
        double f = this.z + a * 0.4000000059604645;
        this.world.addParticle("explode", d, e, f, 0.0, -0.07500000298023224, 0.0);
    }

    public boolean damage(Entity e, int i) {
        boolean flag = super.damage(e, i);
        if (flag && e instanceof PlayerEntity) {
            this.fear = true;
        }

        return flag;
    }

    public boolean method_932() {
        return this.forwardVelocity != 0.0F;
    }

    protected Entity findPlayerToRunFrom() {
        PlayerEntity entityplayer = this.world.getClosestPlayerTo(this, 12.0);
        return entityplayer != null && this.method_928(entityplayer) ? entityplayer : null;
    }

    public void runLikeHell() {
        double a = this.x - this.runFrom.x;
        double b = this.z - this.runFrom.z;
        double crazy = Math.atan2(a, b);
        crazy += (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.75;
        double c = this.x + Math.sin(crazy) * 8.0;
        double d = this.z + Math.cos(crazy) * 8.0;
        int x = MathHelper.floor(c);
        int y = MathHelper.floor(this.boundingBox.minY);
        int z = MathHelper.floor(d);

        for (int q = 0; q < 16; ++q) {
            int i = x + this.rand.nextInt(4) - this.rand.nextInt(4);
            int j = y + this.rand.nextInt(4) - this.rand.nextInt(4) - 1;
            int k = z + this.rand.nextInt(4) - this.rand.nextInt(4);
            if (j > 4 && (this.world.getBlockId(i, j, k) == 0 || this.world.getBlockId(i, j, k) == Block.SNOW.id) && this.world.getBlockId(i, j - 1, k) != 0) {
                EntityPath dogs = this.world.method_189(this, i, j, k, 16.0F);
                this.setTarget(dogs);
                break;
            }
        }

    }

    public boolean interact(PlayerEntity entityplayer) {
        this.yaw = entityplayer.yaw;
        if (this.vehicle != null) {
            this.field_1012 = this.vehicle.yaw;
            this.yaw = this.vehicle.yaw;
        }

        this.startRiding(entityplayer);
        if (this.vehicle == null) {
            this.grab = true;
        } else {
            this.world.playSound(this, "aether:mobs.aerbunny.aerbunnylift", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }

        this.jumping = false;
        this.forwardVelocity = 0.0F;
        this.horizontalVelocity = 0.0F;
        this.setTarget((EntityPath) null);
        this.xVelocity = entityplayer.xVelocity * 5.0;
        this.yVelocity = entityplayer.yVelocity / 2.0 + 0.5;
        this.zVelocity = entityplayer.zVelocity * 5.0;
        return true;
    }

    public double getHeightOffset() {
        return this.vehicle instanceof PlayerEntity ? (double) (this.standingEyeHeight - 1.15F) : (double) this.standingEyeHeight;
    }

    protected String getAmbientSound() {
        return null;
    }

    protected void getDrops() {
        if (UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10))) {
            this.dropItem(Item.STRING.id, 2);
        } else {
            this.dropItem(Item.STRING.id, 1);
        }

    }

    public void proceed() {
        this.mate = 0;
        this.age = this.rand.nextInt(64);
    }

    protected boolean canClimb() {
        return this.onGround;
    }

    protected String getHurtSound() {
        return "aether:mobs.aerbunny.aerbunnyhurt";
    }

    protected String getDeathSound() {
        return "aether:mobs.aerbunny.aerbunnydeath";
    }

    public boolean canSpawn() {
        return super.canSpawn();
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Aerbunny");
    }
}
