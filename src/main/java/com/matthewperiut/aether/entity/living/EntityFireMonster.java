package com.matthewperiut.aether.entity.living;

import com.periut.retroapi.entity.spawn.RetroMobSpawnData;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import com.periut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.entity.projectile.EntityFiroBall;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import com.matthewperiut.aether.util.NameGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;


public class EntityFireMonster extends FlyingEntity implements RetroMobSpawnData {
    private static final int TRACKED_TEXTURE_STATE = 16;

    public int wideness;
    public int orgX;
    public int orgY;
    public int orgZ;
    public int motionTimer;
    public int entCount;
    public int flameCount;
    public int ballCount;
    public int chatLog;
    public int chatCount;
    public int hurtness;
    public int direction;
    public double rotary;
    public double speedness;
    public Entity target;
    public boolean gotTarget;
    public String bossName;
    public static final float jimz = 57.295773F;
    int areaOfEffect = 50;

    public EntityFireMonster(World world) {
        super(world);
        this.texture = "aether:textures/mobs/firemonster.png";
        this.setBoundingBoxSpacing(2.25F, 2.5F);
        this.noClip = true;
        this.orgX = MathHelper.floor(this.x);
        this.orgY = MathHelper.floor(this.boundingBox.minY) + 1;
        this.orgZ = MathHelper.floor(this.z);
        this.wideness = 10;
        this.health = 50;
        this.speedness = 0.5 - (double) this.health / 70.0 * 0.2;
        this.direction = 0;
        this.entCount = this.random.nextInt(6);
        this.bossName = NameGen.gen();
    }

    public EntityFireMonster(World world, int x, int y, int z, int rad, int dir) {
        super(world);
        this.texture = "aether:textures/mobs/firemonster.png";
        this.setBoundingBoxSpacing(2.25F, 2.5F);
        this.setPosition((double) x + 0.5, (double) y, (double) z + 0.5);
        this.wideness = rad - 2;
        this.orgX = x;
        this.orgY = y;
        this.orgZ = z;
        this.noClip = true;
        this.rotary = (double) this.random.nextFloat() * 360.0;
        this.health = 50;
        this.speedness = 0.5 - (double) this.health / 70.0 * 0.2;
        this.direction = dir;
        this.entCount = this.random.nextInt(6);
        this.bossName = NameGen.gen();
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TRACKED_TEXTURE_STATE, (byte) 0);
        // Boss HP tracking (id 30) is owned by accessory-api's LivingEntity mixin.
    }

    private void setTextureState(byte state) {
        this.dataTracker.set(TRACKED_TEXTURE_STATE, state);
    }

    private void updateTextureFromState() {
        switch (this.dataTracker.getByte(TRACKED_TEXTURE_STATE)) {
            case 1:
                this.texture = "aether:textures/mobs/firemonsterHurt.png";
                break;
            default:
                this.texture = "aether:textures/mobs/firemonster.png";
                break;
        }
    }

    // accessory-api's BossLivingEntity mixin on LivingEntity owns boss HP tracking/sync.
    private BossLivingEntity bossApi() {
        return (BossLivingEntity) (Object) this;
    }

    public boolean canDespawn() {
        return false;
    }

    public void tick() {
        super.tick();
        if (this.health > 0) {
            double a = (double) (this.random.nextFloat() - 0.5F);
            double b = (double) this.random.nextFloat();
            double c = (double) (this.random.nextFloat() - 0.5F);
            double d = this.x + a * b;
            double e = this.boundingBox.minY + b - 0.5;
            double f = this.z + c * b;
            this.world.addParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0);

            if (!this.world.isRemote) {
                ++this.entCount;
                if (this.entCount >= 3) {
                    this.burnEntities();
                    this.evapWater();
                    this.entCount = 0;
                }
            }

            if (this.hurtness > 0) {
                --this.hurtness;
                if (this.hurtness == 0) {
                    setTextureState((byte) 0);
                }
            }
        }

        if (this.chatCount > 0) {
            --this.chatCount;
        }

        // Both sides: in singleplayer isRemote is false and the texture would never update.
        updateTextureFromState();
    }

    protected Entity findPlayerToAttack() {
        PlayerEntity entityplayer = this.world.getClosestPlayer(this, 32.0);
        return entityplayer != null && this.canSee(entityplayer) ? entityplayer : null;
    }

    public void tickLiving() {
        super.tickLiving();

        if (this.world.isRemote) return;

        if (this.gotTarget && this.target == null) {
            this.target = this.findPlayerToAttack();
            this.gotTarget = false;
        }

        if (this.target == null) {
            this.setPosition((double) this.orgX + 0.5, (double) this.orgY, (double) this.orgZ + 0.5);
            this.setDoor(0);
        } else {
            this.bodyYaw = this.yaw;
            this.setPosition(this.x, (double) this.orgY, this.z);
            this.velocityY = 0.0;
            boolean pool = false;
            if (this.velocityX > 0.0 && (int) Math.floor(this.x) > this.orgX + this.wideness) {
                this.rotary = 360.0 - this.rotary;
                pool = true;
            } else if (this.velocityX < 0.0 && (int) Math.floor(this.x) < this.orgX - this.wideness) {
                this.rotary = 360.0 - this.rotary;
                pool = true;
            }

            if (this.velocityZ > 0.0 && (int) Math.floor(this.z) > this.orgZ + this.wideness) {
                this.rotary = 180.0 - this.rotary;
                pool = true;
            } else if (this.velocityZ < 0.0 && (int) Math.floor(this.z) < this.orgZ - this.wideness) {
                this.rotary = 180.0 - this.rotary;
                pool = true;
            }

            if (this.rotary > 360.0) {
                this.rotary -= 360.0;
            } else if (this.rotary < 0.0) {
                this.rotary += 360.0;
            }

            if (this.target != null) {
                this.lookAt(this.target, 20.0F, 20.0F);
            }

            double crazy = this.rotary / 57.295772552490234;
            this.velocityX = Math.sin(crazy) * this.speedness;
            this.velocityZ = Math.cos(crazy) * this.speedness;
            ++this.motionTimer;
            if (this.motionTimer >= 20 || pool) {
                this.motionTimer = 0;
                if (this.random.nextInt(3) == 0) {
                    this.rotary += (double) (this.random.nextFloat() - this.random.nextFloat()) * 60.0;
                }
            }

            ++this.flameCount;
            if (this.flameCount == 40 && this.random.nextInt(2) == 0) {
                this.poopFire();
            } else if (this.flameCount >= 55 + this.health / 2 && this.target != null && this.target instanceof LivingEntity) {
                this.makeFireBall(1);
                this.flameCount = 0;
            }

            if (this.target != null && this.target.dead) {
                this.setPosition((double) this.orgX + 0.5, (double) this.orgY, (double) this.orgZ + 0.5);
                this.velocityX = 0.0;
                this.velocityY = 0.0;
                this.velocityZ = 0.0;
                this.target = null;
                this.chatLine("§cSuch is the fate of a being who opposes the might of the sun.");
                this.setDoor(0);
                bossApi().setBoss(false);
                this.gotTarget = false;
            }
        }
    }

    public void burnEntities() {
        if (this.world.isRemote) return;
        List list = this.world.getEntities(this, this.boundingBox.expand(0.0, 4.0, 0.0));

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);
            if (entity1 instanceof LivingEntity living && !((EntityAccessor) entity1).isImmuneToFire()) {
                entity1.damage(this, 10);
                entity1.fireTicks = 300;
            }
        }
    }

    public void evapWater() {
        if (this.world.isRemote) return;
        int x = MathHelper.floor(this.x);
        int z = MathHelper.floor(this.z);

        for (int i = 0; i < 8; ++i) {
            int b = this.orgY - 2 + i;
            if (this.world.getMaterial(x, b, z) == Material.WATER) {
                this.world.setBlock(x, b, z, 0);
                this.world.playSound((double) ((float) x + 0.5F), (double) ((float) b + 0.5F), (double) ((float) z + 0.5F), "random.fizz", 0.5F, 2.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.8F);

                for (int l = 0; l < 8; ++l) {
                    this.world.addParticle("largesmoke", (double) x + Math.random(), (double) b + 0.75, (double) z + Math.random(), 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public void makeFireBall(int shots) {
        if (this.world.isRemote) return;
        this.world.playSound(this, "mob.ghast.fireball", 5.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        boolean flag = false;
        ++this.ballCount;
        if (this.ballCount >= 3 + this.random.nextInt(3)) {
            flag = true;
            this.ballCount = 0;
        }

        for (int i = 0; i < shots; ++i) {
            EntityFiroBall e1 = new EntityFiroBall(this.world, this.x - this.velocityX / 2.0, this.y, this.z - this.velocityZ / 2.0, flag);
            this.world.spawnEntity(e1);
        }
    }

    public void poopFire() {
        if (this.world.isRemote) return;
        int x = MathHelper.floor(this.x);
        int z = MathHelper.floor(this.z);
        int b = this.orgY - 2;
        if (AetherBlocks.isGood(this.world.getBlockId(x, b, z), this.world.getBlockMeta(x, b, z))) {
            this.world.setBlock(x, b, z, Block.FIRE.id);
        }
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("OriginX", (short) this.orgX);
        nbttagcompound.putShort("OriginY", (short) this.orgY);
        nbttagcompound.putShort("OriginZ", (short) this.orgZ);
        nbttagcompound.putShort("Wideness", (short) this.wideness);
        nbttagcompound.putShort("FlameCount", (short) this.flameCount);
        nbttagcompound.putShort("BallCount", (short) this.ballCount);
        nbttagcompound.putShort("ChatLog", (short) this.chatLog);
        nbttagcompound.putFloat("Rotary", (float) this.rotary);
        this.gotTarget = this.target != null;
        nbttagcompound.putBoolean("GotTarget", this.gotTarget);
        nbttagcompound.putBoolean("IsCurrentBoss", bossApi().isBoss());
        nbttagcompound.putString("BossName", this.bossName);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.orgX = nbttagcompound.getShort("OriginX");
        this.orgY = nbttagcompound.getShort("OriginY");
        this.orgZ = nbttagcompound.getShort("OriginZ");
        this.wideness = nbttagcompound.getShort("Wideness");
        this.flameCount = nbttagcompound.getShort("FlameCount");
        this.ballCount = nbttagcompound.getShort("BallCount");
        this.chatLog = nbttagcompound.getShort("ChatLog");
        this.rotary = (double) nbttagcompound.getFloat("Rotary");
        this.gotTarget = nbttagcompound.getBoolean("GotTarget");
        this.speedness = 0.5 - (double) this.health / 70.0 * 0.2;
        if (nbttagcompound.getBoolean("IsCurrentBoss")) {
            bossApi().setBoss(true);
        }

        this.bossName = nbttagcompound.getString("BossName");
    }

    public void chatLine(String s) {
        if (!this.world.isRemote) {
            List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
            for (PlayerEntity player : playersNearby) {
                player.sendMessage(s);
            }
        }
    }

    public boolean chatWithMe() {
        if (this.chatCount <= 0) {
            if (this.chatLog == 0) {
                this.chatLine("§cYou are certainly a brave soul to have entered this chamber.");
                this.chatLog = 1;
                this.chatCount = 100;
            } else if (this.chatLog == 1) {
                this.chatLine("§cBegone human, you serve no purpose here.");
                this.chatLog = 2;
                this.chatCount = 100;
            } else if (this.chatLog == 2) {
                this.chatLine("§cYour presence annoys me. Do you not fear my burning aura?");
                this.chatLog = 3;
                this.chatCount = 100;
            } else if (this.chatLog == 3) {
                this.chatLine("§cI have nothing to offer you, fool. Leave me at peace.");
                this.chatLog = 4;
                this.chatCount = 100;
            } else if (this.chatLog == 4) {
                this.chatLine("§cPerhaps you are ignorant. Do you wish to know who I am?");
                this.chatLog = 5;
                this.chatCount = 100;
            } else if (this.chatLog == 5) {
                this.chatLine("§cI am a sun spirit, embodiment of Aether's eternal daylight.");
                this.chatLine("§cAs long as I am alive, the sun will never set on this world.");
                this.chatLog = 6;
                this.chatCount = 100;
            } else if (this.chatLog == 6) {
                this.chatLine("§cMy body burns with the anger of a thousand beasts.");
                this.chatLine("§cNo man, hero, or villain can harm me. You are no exception.");
                this.chatLog = 7;
                this.chatCount = 100;
            } else if (this.chatLog == 7) {
                this.chatLine("§cYou wish to challenge the might of the sun? You are mad.");
                this.chatLine("§cDo not further insult me or you will feel my wrath.");
                this.chatLog = 8;
                this.chatCount = 100;
            } else if (this.chatLog == 8) {
                this.chatLine("§cThis is your final warning. Leave now, or prepare to burn.");
                this.chatLog = 9;
                this.chatCount = 100;
            } else {
                if (this.chatLog == 9) {
                    this.chatLine("§6As you wish, your death will be slow and agonizing.");
                    this.chatLog = 10;
                    bossApi().setBoss(true);
                    return true;
                }

                if (this.chatLog == 10 && this.target == null) {
                    this.chatLine("§cDid your previous death not satisfy your curiosity, human?");
                    this.chatLog = 9;
                    this.chatCount = 100;
                }
            }
        }

        return false;
    }

    public boolean interact(PlayerEntity ep) {
        if (this.chatWithMe()) {
            this.rotary = 57.295772552490234 * Math.atan2(this.x - ep.x, this.z - ep.z);
            this.target = ep;
            if (!this.world.isRemote) {
                this.setDoor(AetherBlocks.LockedDungeonStone.id);
            }
            return true;
        } else {
            return false;
        }
    }

    public void addVelocity(double d, double d1, double d2) {
    }

    public void applyKnockback(Entity entity, int i, double d, double d1) {
    }

    public boolean damage(Entity e, int i) {
        if (e != null && e instanceof EntityFiroBall) {
            this.speedness = 0.5 - (double) this.health / 70.0 * 0.2;
            boolean flag = super.damage(e, i);
            if (flag) {
                // boss HP sync handled by accessory-api's tick mixin
                this.hurtness = 15;
                setTextureState((byte) 1);

                if (!this.world.isRemote) {
                    EntityFireMinion minion = new EntityFireMinion(this.world);
                    minion.setPositionAndAnglesKeepPrevAngles(this.x, this.y, this.z, this.yaw, 0.0F);
                    this.world.spawnEntity(minion);
                    this.world.spawnEntity(minion);
                    this.world.spawnEntity(minion);
                }

                if (this.health <= 0) {
                    bossApi().setBoss(false);
                    this.chatLine("§bSuch bitter cold... is this the feeling... of pain?");
                    if (!this.world.isRemote) {
                        this.setDoor(0);
                        this.unlockTreasure();
                    }
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    protected void dropItems() {
        this.dropItem(new ItemStack(AetherItems.Key, 1, 2), 0.0F);
    }

    private void setDoor(int ID) {
        if (this.world.isRemote) return;
        int y;
        int z;
        if (this.direction / 2 == 0) {
            for (y = this.orgY - 1; y < this.orgY + 2; ++y) {
                for (z = this.orgZ - 1; z < this.orgZ + 2; ++z) {
                    this.world.setBlockWithoutNotifyingNeighbors(this.orgX + (this.direction == 0 ? -11 : 11), y, z, ID, 2);
                }
            }
        } else {
            for (y = this.orgY - 1; y < this.orgY + 2; ++y) {
                for (z = this.orgX - 1; z < this.orgX + 2; ++z) {
                    this.world.setBlockWithoutNotifyingNeighbors(z, y, this.orgZ + (this.direction == 3 ? 11 : -11), ID, 2);
                }
            }
        }
    }

    private void unlockTreasure() {
        if (this.world.isRemote) return;
        int x;
        int y;
        if (this.direction / 2 == 0) {
            for (x = this.orgY - 1; x < this.orgY + 2; ++x) {
                for (y = this.orgZ - 1; y < this.orgZ + 2; ++y) {
                    this.world.setBlockWithoutNotifyingNeighbors(this.orgX + (this.direction == 0 ? 11 : -11), x, y, 0);
                }
            }
        } else {
            for (x = this.orgY - 1; x < this.orgY + 2; ++x) {
                for (y = this.orgX - 1; y < this.orgX + 2; ++y) {
                    this.world.setBlockWithoutNotifyingNeighbors(y, x, this.orgZ + (this.direction == 3 ? -11 : 11), 0);
                }
            }
        }

        List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            AetherAchievements.giveAchievement(AetherAchievements.defeatGold, player);
        }

        for (x = this.orgX - 20; x < this.orgX + 20; ++x) {
            for (y = this.orgY - 3; y < this.orgY + 6; ++y) {
                for (int z = this.orgZ - 20; z < this.orgZ + 20; ++z) {
                    int id = this.world.getBlockId(x, y, z);
                    if (id == AetherBlocks.LockedDungeonStone.id) {
                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }

                    if (id == AetherBlocks.LockedLightDungeonStone.id) {
                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.LightDungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }
                }
            }
        }
    }

    // setBoss/isBoss/getHP come from accessory-api's LivingEntity mixin at runtime.
    // getMaxHP/getName below override the mixin's defaults via virtual dispatch.
    public int getMaxHP() {
        return 50;
    }

    public String getName() {
        return this.bossName + ", the Sun Spirit";
    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("FireMonster");
    }
}
