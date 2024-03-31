//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.entity.living;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.entity.projectile.EntityHomeShot;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.util.NameGen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.EntityPath;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.ServerPlayerEntity;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.DoubleTag;
import net.minecraft.util.io.ListTag;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityValkyrie extends EntityDungeonMob implements BossLivingEntity, MobSpawnDataProvider {
    public boolean isSwinging;
    public boolean boss;
    public boolean duel;
    public boolean hasDungeon;
    public int teleTimer;
    public int angerLevel;
    public int timeLeft;
    public int chatTime;
    public int dungeonX;
    public int dungeonY;
    public int dungeonZ;
    public int dungeonEntranceZ;
    public double safeX;
    public double safeY;
    public double safeZ;
    public float sinage;
    public double lastMotionY;
    public String bossName;
    boolean isBoss = false;
    public int areaOfEffect = 50;

    public EntityValkyrie(World world) {
        super(world);
        this.setSize(0.8F, 1.6F);
        this.texture = "aether:stationapi/textures/mobs/valkyrie.png";
        this.teleTimer = this.rand.nextInt(250);
        this.health = 50;
        this.movementSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.safeX = this.x;
        this.safeY = this.y;
        this.safeZ = this.z;
    }

    public EntityValkyrie(World world, double x, double y, double z, boolean flag) {
        super(world);
        this.setSize(0.8F, 1.6F);
        this.bossName = NameGen.gen();
        this.texture = "aether:stationapi/textures/mobs/valkyrie.png";
        if (flag) {
            this.texture = "aether:stationapi/textures/mobs/valkyrie2.png";
            this.health = 500;
            this.boss = true;
        } else {
            this.health = 50;
        }

        this.teleTimer = this.rand.nextInt(250);
        this.movementSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.safeX = this.x = x;
        this.safeY = this.y = y;
        this.safeZ = this.z = z;
        this.hasDungeon = false;
    }

    public void handleFallDamage(float f) {
    }

    public void tick() {
        this.lastMotionY = this.yVelocity;
        super.tick();
        if (!this.onGround && this.entity != null && this.lastMotionY >= 0.0 && this.yVelocity < 0.0 && this.distanceTo(this.entity) <= 16.0F && this.method_928(this.entity)) {
            double a = this.entity.x - this.x;
            double b = this.entity.z - this.z;
            double angle = Math.atan2(a, b);
            this.xVelocity = Math.sin(angle) * 0.25;
            this.zVelocity = Math.cos(angle) * 0.25;
        }

        if (!this.onGround && !this.method_932() && Math.abs(this.yVelocity - this.lastMotionY) > 0.07 && Math.abs(this.yVelocity - this.lastMotionY) < 0.09) {
            this.yVelocity += 0.054999999701976776;
            if (this.yVelocity < -0.2750000059604645) {
                this.yVelocity = -0.2750000059604645;
            }
        }

        this.movementSpeed = this.entity == null ? 0.5F : 1.0F;
        if (this.world.difficulty <= 0 && (this.entity != null || this.angerLevel > 0)) {
            this.angerLevel = 0;
            this.entity = null;
        }

        if (this.isSwinging) {
            this.lastHandSwingProgress += 0.15F;
            this.handSwingProgress += 0.15F;
            if (this.lastHandSwingProgress > 1.0F || this.handSwingProgress > 1.0F) {
                this.isSwinging = false;
                this.lastHandSwingProgress = 0.0F;
                this.handSwingProgress = 0.0F;
            }
        }

        if (!this.onGround) {
            this.sinage += 0.75F;
        } else {
            this.sinage += 0.15F;
        }

        if (this.sinage > 6.283186F) {
            this.sinage -= 6.283186F;
        }

        if (!this.otherDimension()) {
            --this.timeLeft;
            if (this.timeLeft <= 0) {
                this.removed = true;
                this.onSpawnedFromSpawner();
            }
        }

    }

    public boolean otherDimension() {
        return true;
    }

    public void teleport(double x, double y, double z, int rad) {
        int a = this.rand.nextInt(rad + 1);
        int b = this.rand.nextInt(rad / 2);
        int c = rad - a;
        a *= this.rand.nextInt(2) * 2 - 1;
        b *= this.rand.nextInt(2) * 2 - 1;
        c *= this.rand.nextInt(2) * 2 - 1;
        x += (double) a;
        y += (double) b;
        z += (double) c;
        int newX = (int) Math.floor(x - 0.5);
        int newY = (int) Math.floor(y - 0.5);
        int newZ = (int) Math.floor(z - 0.5);
        boolean flag = false;

        for (int q = 0; q < 32 && !flag; ++q) {
            int i = newX + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int j = newY + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int k = newZ + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            if (j <= 124 && j >= 5 && this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k) && !this.isAirySpace(i, j - 1, k) && (!this.hasDungeon || i > this.dungeonX && i < this.dungeonX + 20 && j > this.dungeonY && j < this.dungeonY + 12 && k > this.dungeonZ && k < this.dungeonZ + 20)) {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag) {
            this.teleFail();
        } else {
            this.onSpawnedFromSpawner();
            this.setPosition((double) newX + 0.5, (double) newY + 0.5, (double) newZ + 0.5);
            this.xVelocity = 0.0;
            this.yVelocity = 0.0;
            this.zVelocity = 0.0;
            this.forwardVelocity = 0.0F;
            this.horizontalVelocity = 0.0F;
            this.jumping = false;
            this.pitch = 0.0F;
            this.yaw = 0.0F;
            this.setTarget((EntityPath) null);
            this.field_1012 = this.rand.nextFloat() * 360.0F;
            this.onSpawnedFromSpawner();
            this.teleTimer = this.rand.nextInt(40);
        }

    }

    public boolean isAirySpace(int x, int y, int z) {
        int p = this.world.getBlockId(x, y, z);
        return p == 0 || Block.BY_ID[p].getCollisionShape(this.world, x, y, z) == null;
    }

    public boolean canDespawn() {
        return !this.boss;
    }

    public boolean interact(PlayerEntity entityplayer) {
        this.lookAt(entityplayer, 180.0F, 180.0F);
        ItemStack itemstack;
        if (!this.boss) {
            if (this.timeLeft >= 1200) {
                itemstack = entityplayer.getHeldItem();
                if (itemstack != null && itemstack.itemId == AetherItems.VictoryMedal.id && itemstack.count >= 0) {
                    if (itemstack.count >= 10) {
                        this.chatItUp("Umm... that's a nice pile of medallions you have there...");
                    } else if (itemstack.count >= 5) {
                        this.chatItUp("That's pretty impressive, but you won't defeat me.");
                    } else {
                        this.chatItUp("You think you're a tough guy, eh? Well, bring it on!");
                    }
                } else {
                    int pokey = this.rand.nextInt(3);
                    if (pokey == 2) {
                        this.chatItUp("What's that? You want to fight? Aww, what a cute little human.");
                    } else if (pokey == 1) {
                        this.chatItUp("You're not thinking of fighting a big, strong Valkyrie are you?");
                    } else {
                        this.chatItUp("I don't think you should bother me, you could get really hurt.");
                    }
                }
            }
        } else if (this.duel) {
            this.chatItUp("If you wish to challenge me, strike at any time.");
        } else if (!this.duel) {
            itemstack = entityplayer.getHeldItem();
            if (itemstack != null && itemstack.itemId == AetherItems.VictoryMedal.id && itemstack.count >= 10) {
                itemstack.count -= 10;
                if (itemstack.count <= 0) {
                    itemstack.unusedEmptyMethod1(entityplayer);
                    entityplayer.breakHeldItem();
                    this.chatTime = 0;
                    this.chatItUp("Very well, attack me when you wish to begin.");
                    this.duel = true;
                }
            } else {
                this.chatItUp("Show me 10 victory medals, and I will fight you.");
            }
        }

        return true;
    }

    private void chatItUp(String s) {
        if (this.chatTime <= 0) {
            chatLine(s);
            this.chatTime = 60;
        }
    }

    public void chatLine(String s) {
        SideUtil.run(() -> chatLineClient(s), () -> chatLineServer(s));
    }

    @Environment(EnvType.CLIENT)
    public void chatLineClient(String s) {
        Minecraft mc = ((Minecraft) FabricLoader.getInstance().getGameInstance());
        mc.overlay.addChatMessage(s);
    }

    @Environment(EnvType.SERVER)
    public void chatLineServer(String s) {
        MinecraftServer mc = ((MinecraftServer) FabricLoader.getInstance().getGameInstance());
        List<PlayerEntity> playersNearby = world.getEntities(PlayerEntity.class, AxixAlignedBoundingBox.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            ((ServerPlayerEntity) player).sendMessage(s);
        }
    }

    public void makeHomeShot(int shots, LivingEntity ep) {
        for (int i = 0; i < shots; ++i) {
            EntityHomeShot e1 = new EntityHomeShot(this.world, this.x - this.xVelocity / 2.0, this.y, this.z - this.zVelocity / 2.0, ep);
            this.world.spawnEntity(e1);
        }

    }

    protected void getDrops() {
        if (this.boss) {
            this.dropItem(new ItemStack(AetherItems.Key, 1, 1), 0.0F);
            this.dropItem(Item.GOLD_SWORD.id, 1);
        } else {
            this.dropItem(AetherItems.VictoryMedal.id, 1);
        }

    }

    public void tickHandSwing() {
        super.tickHandSwing();
        ++this.teleTimer;
        if (this.teleTimer >= 450) {
            if (this.entity != null) {
                if (this.boss && this.onGround && this.rand.nextInt(2) == 0 && this.entity != null && this.entity instanceof LivingEntity) {
                    this.makeHomeShot(1, (LivingEntity) this.entity);
                    this.teleTimer = -100;
                } else {
                    this.teleport(this.entity.x, this.entity.y, this.entity.z, 7);
                }
            } else if (this.onGround && !this.boss) {
                this.teleport(this.x, this.y, this.z, 12 + this.rand.nextInt(12));
            } else {
                this.teleport(this.safeX, this.safeY, this.safeZ, 6);
            }
        } else if (this.teleTimer >= 446 || !(this.y <= 0.0) && !(this.y <= this.safeY - 16.0)) {
            if (this.teleTimer % 5 == 0 && this.entity != null && !this.method_928(this.entity)) {
                this.teleTimer += 100;
            }
        } else {
            this.teleTimer = 446;
        }

        if (this.onGround && this.teleTimer % 10 == 0 && !this.boss) {
            this.safeX = this.x;
            this.safeY = this.y;
            this.safeZ = this.z;
        }

        if (this.entity != null && this.entity.removed) {
            this.entity = null;
            if (this.boss) {
                this.unlockDoor();
                isBoss = false;
            }

            this.angerLevel = 0;
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }

    }

    public void swingArm() {
        if (!this.isSwinging) {
            this.isSwinging = true;
            this.lastHandSwingProgress = 0.0F;
            this.handSwingProgress = 0.0F;
        }

    }

    public void teleFail() {
        this.teleTimer -= this.rand.nextInt(40) + 40;
        if (this.y <= 0.0) {
            this.teleTimer = 446;
        }

    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getLightLevel(i, j, k) > 8 && this.world.canSpawnEntity(this.boundingBox) && this.world.method_190(this, this.boundingBox).size() == 0 && !this.world.method_218(this.boundingBox);
    }

    public void writeAdditional(CompoundTag nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.put("Anger", (short) this.angerLevel);
        nbttagcompound.put("TeleTimer", (short) this.teleTimer);
        nbttagcompound.put("TimeLeft", (short) this.timeLeft);
        nbttagcompound.put("Boss", this.boss);
        nbttagcompound.put("Duel", this.duel);
        nbttagcompound.put("DungeonX", this.dungeonX);
        nbttagcompound.put("DungeonY", this.dungeonY);
        nbttagcompound.put("DungeonZ", this.dungeonZ);
        nbttagcompound.put("DungeonEntranceZ", this.dungeonEntranceZ);
        nbttagcompound.put("SafePos", this.toListTag(new double[]{this.safeX, this.safeY, this.safeZ}));
        nbttagcompound.put("IsCurrentBoss", isBoss);
        if (isBoss) {
            if (!bossName.isEmpty()) {
                nbttagcompound.put("BossName", this.bossName);
            }
        }
    }

    public void readAdditional(CompoundTag nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
        this.teleTimer = nbttagcompound.getShort("TeleTimer");
        this.timeLeft = nbttagcompound.getShort("TimeLeft");
        this.duel = nbttagcompound.getBoolean("Duel");
        this.boss = nbttagcompound.getBoolean("Boss");
        this.dungeonX = nbttagcompound.getInt("DungeonX");
        this.dungeonY = nbttagcompound.getInt("DungeonY");
        this.dungeonZ = nbttagcompound.getInt("DungeonZ");
        this.dungeonEntranceZ = nbttagcompound.getInt("DungeonEntranceZ");
        if (this.boss) {
            this.texture = "aether:stationapi/textures/mobs/valkyrie2.png";
        }

        ListTag nbttaglist = nbttagcompound.getListTag("SafePos");
        this.safeX = ((DoubleTag) nbttaglist.get(0)).data;
        this.safeY = ((DoubleTag) nbttaglist.get(1)).data;
        this.safeZ = ((DoubleTag) nbttaglist.get(2)).data;
        if (nbttagcompound.getBoolean("IsCurrentBoss")) {
            isBoss = true;
        }
        if (isBoss) {
            this.bossName = nbttagcompound.getString("BossName");
        }
    }

    protected Entity getAttackTarget() {
        return !this.otherDimension() || this.world.difficulty > 0 && (!this.boss || this.duel) && this.angerLevel > 0 ? super.getAttackTarget() : null;
    }

    public boolean damage(Entity entity, int i) {
        if (entity instanceof PlayerEntity && this.world.difficulty > 0) {
            int pokey;
            if (this.boss && (!this.duel || this.world.difficulty <= 0)) {
                this.onSpawnedFromSpawner();
                pokey = this.rand.nextInt(2);
                if (pokey == 2) {
                    this.chatItUp("Sorry, I don't fight with weaklings.");
                } else {
                    this.chatItUp("Try defeating some weaker valkyries first.");
                }

                return false;
            } else {
                if (this.boss) {
                    if (this.entity == null) {
                        isBoss = true;
                        this.chatTime = 0;
                        this.chatItUp("This will be your final battle!");
                    } else {
                        this.teleTimer += 60;
                    }
                } else if (this.entity == null) {
                    this.chatTime = 0;
                    pokey = this.rand.nextInt(3);
                    if (pokey == 2) {
                        this.chatItUp("I'm not going easy on you!");
                    } else if (pokey == 1) {
                        this.chatItUp("You're gonna regret that!");
                    } else {
                        this.chatItUp("Now you're in for it!");
                    }
                } else {
                    this.teleTimer -= 10;
                }

                this.becomeAngryAt(entity);
                boolean flag = super.damage(entity, i);
                if (flag && this.health <= 0) {
                    pokey = this.rand.nextInt(3);
                    this.removed = true;
                    if (this.boss) {
                        this.removed = false;
                        this.unlockDoor();
                        this.unlockTreasure();
                        this.chatItUp("You are truly... a mighty warrior...");
                        isBoss = false;
                    } else if (pokey == 2) {
                        this.chatItUp("Alright, alright! You win!");
                    } else if (pokey == 1) {
                        this.chatItUp("Okay, I give up! Geez!");
                    } else {
                        this.chatItUp("Oww! Fine, here's your medal...");
                    }

                    this.onSpawnedFromSpawner();
                }

                return flag;
            }
        } else {
            this.teleport(this.x, this.y, this.z, 8);
            this.fireTicks = 0;
            return false;
        }
    }

    protected void tryAttack(Entity entity, float f) {
        if (this.attackTime <= 0 && f < 2.75F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.swingArm();
            entity.damage(this, this.attackStrength);
            if (entity != null && this.entity != null && entity == this.entity && entity instanceof LivingEntity) {
                LivingEntity e1 = (LivingEntity) entity;
                if (e1.health <= 0) {
                    this.entity = null;
                    this.angerLevel = 0;
                    int pokey = this.rand.nextInt(3);
                    this.chatTime = 0;
                    if (this.boss) {
                        this.chatItUp("As expected of a human.");
                        this.unlockDoor();
                        isBoss = false;
                    } else if (pokey == 2) {
                        this.chatItUp("You want a medallion? Try being less pathetic.");
                    } else if (pokey == 1 && e1 instanceof PlayerEntity) {
                        PlayerEntity ep = (PlayerEntity) e1;
                        String s = ep.name;
                        this.chatItUp("Maybe some day, " + s + "... maybe some day.");
                    } else {
                        this.chatItUp("Humans aren't nearly as cute when they're dead.");
                    }
                }
            }
        }

    }

    private void becomeAngryAt(Entity entity) {
        this.entity = entity;
        this.angerLevel = 200 + this.rand.nextInt(200);
        if (this.boss) {
            for (int k = this.dungeonZ + 2; k < this.dungeonZ + 23; k += 7) {
                if (this.world.getBlockId(this.dungeonX - 1, this.dungeonY, k) == 0) {
                    this.dungeonEntranceZ = k;
                    this.world.setBlockWithMetadata(this.dungeonX - 1, this.dungeonY, k, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithMetadata(this.dungeonX - 1, this.dungeonY, k + 1, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithMetadata(this.dungeonX - 1, this.dungeonY + 1, k + 1, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithMetadata(this.dungeonX - 1, this.dungeonY + 1, k, AetherBlocks.LockedDungeonStone.id, 1);
                    return;
                }
            }
        }

    }

    private void unlockDoor() {
        this.world.setBlockInChunk(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ, 0);
        this.world.setBlockInChunk(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ + 1, 0);
        this.world.setBlockInChunk(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ + 1, 0);
        this.world.setBlockInChunk(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ, 0);
    }

    private void unlockTreasure() {
        this.world.setBlockWithMetadata(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 9, Block.TRAPDOOR.id, 3);
        this.world.setBlockWithMetadata(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 9, Block.TRAPDOOR.id, 2);
        this.world.setBlockWithMetadata(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 10, Block.TRAPDOOR.id, 3);
        this.world.setBlockWithMetadata(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 10, Block.TRAPDOOR.id, 2);

        List<PlayerEntity> playersNearby = world.getEntities(PlayerEntity.class, AxixAlignedBoundingBox.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            AetherAchievements.giveAchievement(AetherAchievements.defeatSilver, player);
        }

        for (int x = this.dungeonX - 26; x < this.dungeonX + 29; ++x) {
            for (int y = this.dungeonY - 1; y < this.dungeonY + 22; ++y) {
                for (int z = this.dungeonZ - 5; z < this.dungeonZ + 25; ++z) {
                    int id = this.world.getBlockId(x, y, z);
                    if (id == AetherBlocks.LockedDungeonStone.id) {
                        this.world.setBlockWithMetadata(x, y, z, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }

                    if (id == AetherBlocks.Trap.id) {
                        this.world.setBlockWithMetadata(x, y, z, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }

                    if (id == AetherBlocks.LockedLightDungeonStone.id) {
                        this.world.setBlockWithMetadata(x, y, z, AetherBlocks.LightDungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }
                }
            }
        }

    }

    public void setDungeon(int i, int j, int k) {
        this.hasDungeon = true;
        this.dungeonX = i;
        this.dungeonY = j;
        this.dungeonZ = k;
    }

    @Override
    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    @Override
    public boolean isBoss() {
        return isBoss;
    }

    @Override
    public int getHP() {
        return health;
    }

    @Override
    public int getMaxHP() {
        return 500;
    }

    @Override
    public String getName() {
        return this.bossName + ", the Valkyrie Queen";
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Valkyrie");
    }
}
