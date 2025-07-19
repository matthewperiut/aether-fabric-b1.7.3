package com.matthewperiut.aether.entity.living;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntitySwet extends EntityAetherAnimal implements MobSpawnDataProvider {
    public int ticker;
    public int flutter;
    public int hops;
    public int textureNum;
    public boolean textureSet;
    public boolean gotrider;
    public boolean kickoff;
    public boolean friendly;

    public EntitySwet(World world) {
        super(world);
        this.health = 25;
        if (!this.textureSet) {
            if (this.random.nextInt(2) == 0) {
                this.textureNum = 2;
                this.textureSet = true;
            } else {
                this.textureNum = 1;
                this.textureSet = true;
            }
        }

        if (this.textureNum == 1) {
            this.texture = "aether:stationapi/textures/mobs/swets.png";
            this.movementSpeed = 1.5F;
        } else {
            this.texture = "aether:stationapi/textures/mobs/goldswets.png";
            this.movementSpeed = 3.0F;
        }

        this.setBoundingBoxSpacing(0.8F, 0.8F);
        this.setPosition(this.x, this.y, this.z);
        this.hops = 0;
        this.gotrider = false;
        this.flutter = 0;
        this.ticker = 0;
    }

    public EntitySwet(World world, int color) {
        super(world);
        this.health = 25;
        if (!this.textureSet) {
            if (color == 0) {
                this.textureNum = 2;
                this.textureSet = true;
            } else {
                this.textureNum = 1;
                this.textureSet = true;
            }
        }

        if (this.textureNum == 1) {
            this.texture = "aether:stationapi/textures/mobs/swets.png";
            this.movementSpeed = 1.5F;
        } else {
            this.texture = "aether:stationapi/textures/mobs/goldswets.png";
            this.movementSpeed = 3.0F;
        }

        this.setBoundingBoxSpacing(0.8F, 0.8F);
        this.setPosition(this.x, this.y, this.z);
        this.hops = 0;
        this.gotrider = false;
        this.flutter = 0;
        this.ticker = 0;
    }

    public void tickRiding() {
        super.tickRiding();
        if (this.passenger != null && this.kickoff) {
            this.passenger.setVehicle(this);
            this.kickoff = false;
        }

    }

    public void updatePassengerPosition() {
        this.passenger.setPosition(this.x, this.boundingBox.minY - 0.30000001192092896 + (double) this.passenger.standingEyeHeight, this.z);
    }

    public void tick() {
        if (this.target != null) {
            for (int i = 0; i < 3; ++i) {
                float f = 0.01745278F;
                double d = (double) ((float) this.x + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F);
                double d1 = (double) ((float) this.y + this.height);
                double d2 = (double) ((float) this.z + (this.random.nextFloat() - this.random.nextFloat()) * 0.3F);
                this.world.addParticle("splash", d, d1 - 0.25, d2, 0.0, 0.0, 0.0);
            }
        }

        super.tick();
        if (this.gotrider) {
            if (this.passenger != null) {
                return;
            }

            List list = this.world.getEntities(this, this.boundingBox.expand(0.5, 0.75, 0.5));
            int j = 0;
            if (j < list.size()) {
                Entity entity = (Entity) list.get(j);
                this.capturePrey(entity);
            }

            this.gotrider = false;
        }

        if (this.checkWaterCollisions()) {
            this.dissolve();
        }

    }

    protected boolean canDespawn() {
        return true;
    }

    public void onLanding(float f) {
        if (!this.friendly) {
            super.onLanding(f);
            if (this.hops >= 3 && this.health > 0) {
                this.dissolve();
            }

        }
    }

    public void applyKnockback(Entity entity, int i, double d, double d1) {
        if (this.passenger == null || entity != this.passenger) {
            super.applyKnockback(entity, i, d, d1);
        }
    }

    public void dissolve() {
        for (int i = 0; i < 50; ++i) {
            float f = this.random.nextFloat() * 3.141593F * 2.0F;
            float f1 = this.random.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;
            this.world.addParticle("splash", this.x + (double) f2, this.boundingBox.minY + 1.25, this.z + (double) f3, (double) f2 * 1.5 + this.velocityX, 4.0, (double) f3 * 1.5 + this.velocityZ);
        }

        if (this.passenger != null) {
            Entity var10000 = this.passenger;
            var10000.y += (double) (this.passenger.standingEyeHeight - 0.3F);
            this.passenger.setVehicle(this);
        }

        this.markDead();
    }

    public void capturePrey(Entity entity) {
        this.splorch();
        this.prevX = this.x = entity.x;
        this.prevY = this.y = entity.y + 0.009999999776482582;
        this.prevZ = this.z = entity.z;
        this.prevYaw = this.yaw = entity.yaw;
        this.prevPitch = this.pitch = entity.pitch;
        this.velocityX = entity.velocityX;
        this.velocityY = entity.velocityY;
        this.velocityZ = entity.velocityZ;
        this.setBoundingBoxSpacing(entity.width, entity.height);
        this.setPosition(this.x, this.y, this.z);
        entity.setVehicle(this);
        this.yaw = this.random.nextFloat() * 360.0F;
    }

    public boolean damage(Entity entity, int i) {
        if (this.hops == 3 && entity == null && this.health > 1) {
            this.health = 1;
        }

        boolean flag = super.damage(entity, i);
        if (flag && this.passenger != null && this.passenger instanceof LivingEntity) {
            if (entity != null && this.passenger == entity) {
                if (this.random.nextInt(3) == 0) {
                    this.kickoff = true;
                }
            } else {
                this.passenger.damage((Entity) null, i);
                if (this.health <= 0) {
                    this.kickoff = true;
                }
            }
        }

        if (flag && this.health <= 0) {
            this.dissolve();
        } else if (flag && entity instanceof LivingEntity) {
            LivingEntity entityliving = (LivingEntity) entity;
            if (entityliving.health > 0 && (this.passenger == null || entityliving != this.passenger)) {
                this.target = entity;
                this.lookAt(entity, 180.0F, 180.0F);
                this.kickoff = true;
            }
        }

        if (this.friendly && this.target instanceof PlayerEntity) {
            this.target = null;
        }

        return flag;
    }

    public void d_2() {
        if (this.passenger != null && this.passenger instanceof LivingEntity) {
            this.forwardSpeed = 0.0F;
            this.sidewaysSpeed = 0.0F;
            this.jumping = false;
            ((EntityAccessor) this.passenger).setFallDistance(0.0F);
            this.prevYaw = this.yaw = this.passenger.yaw;
            this.prevPitch = this.pitch = 0.0F;
            LivingEntity entityliving = (LivingEntity) this.passenger;
            float f = 3.141593F;
            float f1 = f / 180.0F;
            float f2 = entityliving.yaw * f1;
            if (!this.onGround) {
                if (((LivingEntityAccessor) entityliving).getForwardVelocity() > 0.1F) {
                    if (this.textureNum == 1) {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f2) * 0.125;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f2) * 0.125;
                    } else {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f2) * 0.325;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f2) * 0.125;
                    }
                } else if (((LivingEntityAccessor) entityliving).getForwardVelocity() < -0.1F) {
                    if (this.textureNum == 1) {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f2) * 0.125;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f2) * 0.125;
                    } else {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * -Math.sin((double) f2) * 0.325;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getForwardVelocity() * Math.cos((double) f2) * 0.125;
                    }
                }

                if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() > 0.1F) {
                    if (this.textureNum == 1) {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f2) * 0.125;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f2) * 0.125;
                    } else {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f2) * 0.325;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f2) * 0.125;
                    }
                } else if (((LivingEntityAccessor) entityliving).getHorizontalVelocity() < -0.1F) {
                    if (this.textureNum == 1) {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f2) * 0.125;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f2) * 0.125;
                    } else {
                        this.velocityX += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.cos((double) f2) * 0.325;
                        this.velocityZ += (double) ((LivingEntityAccessor) entityliving).getHorizontalVelocity() * Math.sin((double) f2) * 0.125;
                    }
                }

                if (this.velocityY < 0.05000000074505806 && this.flutter > 0 && ((LivingEntityAccessor) entityliving).getJumping()) {
                    this.velocityY += 0.07000000029802322;
                    --this.flutter;
                }
            } else {
                if (((LivingEntityAccessor) entityliving).getJumping()) {
                    if (this.hops == 0) {
                        this.onGround = false;
                        this.velocityY = 0.8500000238418579;
                        this.hops = 1;
                        this.flutter = 5;
                    } else if (this.hops == 1) {
                        this.onGround = false;
                        this.velocityY = 1.0499999523162842;
                        this.hops = 2;
                        this.flutter = 5;
                    } else if (this.hops == 2) {
                        this.onGround = false;
                        this.velocityY = 1.25;
                        this.flutter = 5;
                    }
                } else if (!(((LivingEntityAccessor) entityliving).getForwardVelocity() > 0.125F) && !(((LivingEntityAccessor) entityliving).getForwardVelocity() < -0.125F) && !(((LivingEntityAccessor) entityliving).getHorizontalVelocity() > 0.125F) && !(((LivingEntityAccessor) entityliving).getHorizontalVelocity() < -0.125F)) {
                    if (this.hops > 0) {
                        this.hops = 0;
                    }
                } else {
                    this.onGround = false;
                    this.velocityY = 0.3499999940395355;
                    this.hops = 0;
                    this.flutter = 0;
                }

                ((LivingEntityAccessor) entityliving).setForwardVelocity(0.0F);
                ((LivingEntityAccessor) entityliving).setHorizontalVelocity(0.0F);
            }

            double d = Math.abs(Math.sqrt(this.velocityX * this.velocityX + this.velocityZ * this.velocityZ));
            if (d > 0.2750000059604645) {
                double d1 = 0.275 / d;
                this.velocityX *= d1;
                this.velocityZ *= d1;
            }
        }

    }

    public void tickLiving() {
        ++this.despawnCounter;
        this.tryDespawn();
        if (this.friendly && this.passenger != null) {
            this.d_2();
        } else {
            if (!this.onGround && this.jumping) {
                this.jumping = false;
            } else if (this.onGround) {
                if (this.forwardSpeed > 0.05F) {
                    this.forwardSpeed *= 0.75F;
                } else {
                    this.forwardSpeed = 0.0F;
                }
            }

            if (this.target != null && this.passenger == null && this.health > 0) {
                this.lookAt(this.target, 10.0F, 10.0F);
            }

            if (this.target != null && this.target.dead) {
                this.target = null;
            }

            if (!this.onGround && this.velocityY < 0.05000000074505806 && this.flutter > 0) {
                this.velocityY += 0.07000000029802322;
                --this.flutter;
            }

            if (this.ticker < 4) {
                ++this.ticker;
            } else {
                if (this.onGround && this.passenger == null && this.hops != 0 && this.hops != 3) {
                    this.hops = 0;
                }

                if (this.target == null && this.passenger == null) {
                    Entity entity = this.getPrey();
                    if (entity != null) {
                        this.target = entity;
                    }
                } else if (this.target != null && this.passenger == null) {
                    if (this.getDistance(this.target) <= 9.0F) {
                        if (this.onGround && this.canSee(this.target)) {
                            this.splotch();
                            this.flutter = 10;
                            this.jumping = true;
                            this.forwardSpeed = 1.0F;
                            this.yaw += 5.0F * (this.random.nextFloat() - this.random.nextFloat());
                        }
                    } else {
                        this.target = null;
                        this.jumping = false;
                        this.forwardSpeed = 0.0F;
                    }
                } else if (this.passenger != null && this.onGround) {
                    if (this.hops == 0) {
                        this.splotch();
                        this.onGround = false;
                        this.velocityY = 0.3499999940395355;
                        this.forwardSpeed = 0.8F;
                        this.hops = 1;
                        this.flutter = 5;
                        this.yaw += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    } else if (this.hops == 1) {
                        this.splotch();
                        this.onGround = false;
                        this.velocityY = 0.44999998807907104;
                        this.forwardSpeed = 0.9F;
                        this.hops = 2;
                        this.flutter = 5;
                        this.yaw += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    } else if (this.hops == 2) {
                        this.splotch();
                        this.onGround = false;
                        this.velocityY = 1.25;
                        this.forwardSpeed = 1.25F;
                        this.hops = 3;
                        this.flutter = 5;
                        this.yaw += 20.0F * (this.random.nextFloat() - this.random.nextFloat());
                    }
                }

                this.ticker = 0;
            }

            if (this.onGround && this.hops >= 3) {
                this.dissolve();
            }

        }
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("Hops", (short) this.hops);
        nbttagcompound.putShort("Flutter", (short) this.flutter);
        if (this.passenger != null) {
            this.gotrider = true;
        }

        nbttagcompound.putBoolean("GotRider", this.gotrider);
        nbttagcompound.putBoolean("Friendly", this.friendly);
        nbttagcompound.putBoolean("textureSet", this.textureSet);
        nbttagcompound.putShort("textureNum", (short) this.textureNum);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.hops = nbttagcompound.getShort("Hops");
        this.flutter = nbttagcompound.getShort("Flutter");
        this.gotrider = nbttagcompound.getBoolean("GotRider");
        this.friendly = nbttagcompound.getBoolean("Friendly");
        this.textureSet = nbttagcompound.getBoolean("textureSet");
        this.textureNum = nbttagcompound.getShort("textureNum");
        if (this.textureNum == 1) {
            this.texture = "aether:stationapi/textures/mobs/swets.png";
            this.movementSpeed = 1.5F;
        } else {
            this.texture = "aether:stationapi/textures/mobs/goldswets.png";
            this.movementSpeed = 3.0F;
        }

    }

    public void splorch() {
        this.world.playSound(this, "mob.slimeattack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    public void splotch() {
        this.world.playSound(this, "mob.slime", 0.5F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    protected String getHurtSound() {
        return "mob.slime";
    }

    protected String getDeathSound() {
        return "mob.slime";
    }

    public void onCollision(Entity entity) {
        if (this.hops == 0 && this.passenger == null && this.target != null && entity != null && entity == this.target && (entity.vehicle == null || !(entity.vehicle instanceof EntitySwet))) {
            if (entity.passenger != null) {
                entity.passenger.setVehicle(entity);
            }

            this.capturePrey(entity);
        }

        super.onCollision(entity);
    }

    public boolean interact(PlayerEntity entityplayer) {
        if (!this.world.isRemote) {
            if (!this.friendly) {
                this.friendly = true;
                this.target = null;
                return true;
            }

            if (this.friendly && this.passenger == null || this.passenger == entityplayer) {
                this.capturePrey(entityplayer);
            }
        }

        return true;
    }

    protected Entity getPrey() {
        List list = this.world.getEntities(this, this.boundingBox.expand(6.0, 6.0, 6.0));
        int i = 0;

        Entity entity;
        while (true) {
            if (i >= list.size()) {
                return null;
            }

            entity = (Entity) list.get(i);
            if (entity instanceof LivingEntity && !(entity instanceof EntitySwet)) {
                if (this.friendly) {
                    if (!(entity instanceof PlayerEntity)) {
                        break;
                    }
                } else if (!(entity instanceof MonsterEntity)) {
                    break;
                }
            }

            ++i;
        }

        return entity;
    }

    protected void dropItems() {
        ItemStack stack = new ItemStack(this.textureNum == 1 ? AetherBlocks.Aercloud.asItem().id : Block.GLOWSTONE.asItem().id, 3, this.textureNum == 1 ? 1 : 0);

        if (UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10))) {
            stack.count *= 2;
        }

        this.dropItem(stack, 0.0F);
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Swet");
    }
}
