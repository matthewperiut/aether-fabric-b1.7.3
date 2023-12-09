package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.UtilSkyroot;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityAechorPlant extends EntityAetherAnimal implements MobSpawnDataProvider {
    public LivingEntity target;
    public int size;
    public int attTime;
    public int smokeTime;
    public boolean seeprey;
    public boolean grounded;
    public boolean noDespawn;
    public float sinage;
    private int poisonLeft;

    public EntityAechorPlant(World world1) {
        super(world1);
        this.texture = "aether:stationapi/textures/mobs/aechorplant.png";
        this.size = this.rand.nextInt(4) + 1;
        this.health = 10 + this.size * 2;
        this.sinage = this.rand.nextFloat() * 6.0F;
        this.smokeTime = this.attTime = 0;
        this.seeprey = false;
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPosition(this.x, this.y, this.z);
        this.poisonLeft = 2;
    }

    public int getLimitPerChunk() {
        return 3;
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getBlockId(i, j - 1, k) == AetherBlocks.Grass.id && this.world.placeBlock(i, j, k) > 8 && super.canSpawn();
    }

    public void updateDespawnCounter() {
        if (this.health > 0 && this.grounded) {
            ++this.despawnCounter;
            this.tryDespawn();
        } else {
            super.updateDespawnCounter();
            if (this.health <= 0) {
                return;
            }
        }

        if (this.onGround) {
            this.grounded = true;
        }

        if (this.hurtTime > 0) {
            this.sinage += 0.9F;
        } else if (this.seeprey) {
            this.sinage += 0.3F;
        } else {
            this.sinage += 0.1F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
        }

        int j;
        if (this.target == null) {
            label107:
            {
                List list = this.world.getEntities(this, this.boundingBox.expand(10.0, 10.0, 10.0));
                j = 0;

                Entity entity1;
                while (true) {
                    if (j >= list.size()) {
                        break label107;
                    }

                    entity1 = (Entity) list.get(j);
                    if (entity1 instanceof LivingEntity && !(entity1 instanceof EntityAechorPlant) && !(entity1 instanceof CreeperEntity)) {
                        if (!(entity1 instanceof PlayerEntity)) {
                            break;
                        }

                        PlayerEntity player1 = (PlayerEntity) entity1;
                        boolean flag = false;
                        if (!flag) {
                            break;
                        }
                    }

                    ++j;
                }

                this.target = (LivingEntity) entity1;
            }
        }

        if (this.target != null) {
            if (!this.target.removed && !((double) this.target.distanceTo(this) > 12.0)) {
                if (this.target instanceof PlayerEntity) {
                    PlayerEntity player1 = (PlayerEntity) this.target;
                    boolean flag = false;
                    if (flag) {
                        this.target = null;
                        this.attTime = 0;
                    }
                }
            } else {
                this.target = null;
                this.attTime = 0;
            }

            if (this.target != null && this.attTime >= 20 && this.method_928(this.target) && (double) this.target.distanceTo(this) < 5.5 + (double) this.size / 2.0) {
                this.shootTarget();
                this.attTime = -10;
            }

            if (this.attTime < 20) {
                ++this.attTime;
            }
        }

        ++this.smokeTime;
        if (this.smokeTime >= (this.seeprey ? 3 : 8)) {
            this.smokeTime = 0;
            int i = MathHelper.floor(this.x);
            j = MathHelper.floor(this.boundingBox.minY);
            int k = MathHelper.floor(this.z);
            if (this.world.getBlockId(i, j - 1, k) != AetherBlocks.Grass.id && this.grounded) {
                this.removed = true;
            }
        }

        this.seeprey = this.target != null;
    }

    public void remove() {
        if (!this.noDespawn || this.health <= 0) {
            super.remove();
        }

    }

    public void shootTarget() {
        if (this.world.difficulty != 0 && !this.world.isClient) {
            double d1 = this.target.x - this.x;
            double d2 = this.target.z - this.z;
            double d3 = 1.5 / Math.sqrt(d1 * d1 + d2 * d2 + 0.1);
            double d4 = 0.1 + Math.sqrt(d1 * d1 + d2 * d2 + 0.1) * 0.5 + (this.y - this.target.y) * 0.25;
            d1 *= d3;
            d2 *= d3;
            EntityPoisonNeedle entityarrow = new EntityPoisonNeedle(this.world, this);
            entityarrow.y = this.y + 0.5;
            this.world.playSound(this, "aether:other.dartshooter.shootDart", 2.0F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(entityarrow);
            entityarrow.setArrowHeading(d1, d4, d2, 0.285F + (float) d4 * 0.05F, 1.0F);
        }
    }

    protected String getHurtSound() {
        return "damage.hurtflesh";
    }

    protected String getDeathSound() {
        return "damage.fallbig";
    }

    public void method_925(Entity entity, int ii, double dd, double dd1) {
        for (int i = 0; i < 8; ++i) {
            double d1 = this.x + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5;
            double d2 = this.y + 0.25 + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5;
            double d3 = this.z + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5;
            double d4 = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5;
            double d5 = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5;
            this.world.addParticle("portal", d1, d2, d3, d4, 0.25, d5);
        }

        if (this.health <= 0) {
            super.method_925(entity, ii, dd, dd1);
        }
    }

    public boolean interact(PlayerEntity entityplayer) {
        boolean flag = false;
        ItemStack stack = entityplayer.inventory.getHeldItem();
        if (stack != null && stack.itemId == AetherItems.Bucket.id && this.poisonLeft > 0) {
            --this.poisonLeft;
            entityplayer.inventory.setInventoryItem(entityplayer.inventory.selectedHotBarSlot, (ItemStack) null);
            entityplayer.inventory.setInventoryItem(entityplayer.inventory.selectedHotBarSlot, new ItemStack(AetherItems.Bucket, 1, 2));
            return true;
        } else {
            if (flag) {
                this.noDespawn = true;
                String s = "heart";

                for (int i = 0; i < 7; ++i) {
                    double d = this.rand.nextGaussian() * 0.02;
                    double d1 = this.rand.nextGaussian() * 0.02;
                    double d2 = this.rand.nextGaussian() * 0.02;
                    this.world.addParticle(s, this.x + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.y + 0.5 + (double) (this.rand.nextFloat() * this.height), this.z + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d, d1, d2);
                }
            }

            return false;
        }
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Grounded", this.grounded);
        nbttagcompound.put("NoDespawn", this.noDespawn);
        nbttagcompound.put("AttTime", (short) this.attTime);
        nbttagcompound.put("Size", (short) this.size);
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.grounded = nbttagcompound.getBoolean("Grounded");
        this.noDespawn = nbttagcompound.getBoolean("NoDespawn");
        this.attTime = nbttagcompound.getShort("AttTime");
        this.size = nbttagcompound.getShort("Size");
        this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
        this.setPosition(this.x, this.y, this.z);
    }

    protected void getDrops() {
        this.dropItem(AetherItems.AechorPetal.id, 2 * (UtilSkyroot.sword(world.getClosestPlayer(x, y, z, 10)) ? 2 : 1));
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("AechorPlant");
    }
}
