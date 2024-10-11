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
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
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
        this.setBoundingBoxSpacing(0.8F, 1.6F);
        this.texture = "aether:stationapi/textures/mobs/valkyrie.png";
        this.teleTimer = this.random.nextInt(250);
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
        this.setBoundingBoxSpacing(0.8F, 1.6F);
        this.bossName = NameGen.gen();
        this.texture = "aether:stationapi/textures/mobs/valkyrie.png";
        if (flag) {
            this.texture = "aether:stationapi/textures/mobs/valkyrie2.png";
            this.health = 500;
            this.boss = true;
        } else {
            this.health = 50;
        }

        this.teleTimer = this.random.nextInt(250);
        this.movementSpeed = 0.5F;
        this.timeLeft = 1200;
        this.attackStrength = 7;
        this.safeX = this.x = x;
        this.safeY = this.y = y;
        this.safeZ = this.z = z;
        this.hasDungeon = false;
    }

    public void onLanding(float f) {
    }

    public void tick() {
        this.lastMotionY = this.velocityY;
        super.tick();
        if (!this.onGround && this.target != null && this.lastMotionY >= 0.0 && this.velocityY < 0.0 && this.getDistance(this.target) <= 16.0F && this.canSee(this.target)) {
            double a = this.target.x - this.x;
            double b = this.target.z - this.z;
            double angle = Math.atan2(a, b);
            this.velocityX = Math.sin(angle) * 0.25;
            this.velocityZ = Math.cos(angle) * 0.25;
        }

        if (!this.onGround && !this.isOnLadder() && Math.abs(this.velocityY - this.lastMotionY) > 0.07 && Math.abs(this.velocityY - this.lastMotionY) < 0.09) {
            this.velocityY += 0.054999999701976776;
            if (this.velocityY < -0.2750000059604645) {
                this.velocityY = -0.2750000059604645;
            }
        }

        this.movementSpeed = this.target == null ? 0.5F : 1.0F;
        if (this.world.difficulty <= 0 && (this.target != null || this.angerLevel > 0)) {
            this.angerLevel = 0;
            this.target = null;
        }

        if (this.isSwinging) {
            this.lastSwingAnimationProgress += 0.15F;
            this.swingAnimationProgress += 0.15F;
            if (this.lastSwingAnimationProgress > 1.0F || this.swingAnimationProgress > 1.0F) {
                this.isSwinging = false;
                this.lastSwingAnimationProgress = 0.0F;
                this.swingAnimationProgress = 0.0F;
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
                this.dead = true;
                this.animateSpawn();
            }
        }

    }

    public boolean otherDimension() {
        return true;
    }

    public void teleport(double x, double y, double z, int rad) {
        int a = this.random.nextInt(rad + 1);
        int b = this.random.nextInt(rad / 2);
        int c = rad - a;
        a *= this.random.nextInt(2) * 2 - 1;
        b *= this.random.nextInt(2) * 2 - 1;
        c *= this.random.nextInt(2) * 2 - 1;
        x += (double) a;
        y += (double) b;
        z += (double) c;
        int newX = (int) Math.floor(x - 0.5);
        int newY = (int) Math.floor(y - 0.5);
        int newZ = (int) Math.floor(z - 0.5);
        boolean flag = false;

        for (int q = 0; q < 32 && !flag; ++q) {
            int i = newX + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
            int j = newY + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
            int k = newZ + (this.random.nextInt(rad / 2) - this.random.nextInt(rad / 2));
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
            this.animateSpawn();
            this.setPosition((double) newX + 0.5, (double) newY + 0.5, (double) newZ + 0.5);
            this.velocityX = 0.0;
            this.velocityY = 0.0;
            this.velocityZ = 0.0;
            this.forwardSpeed = 0.0F;
            this.sidewaysSpeed = 0.0F;
            this.jumping = false;
            this.pitch = 0.0F;
            this.yaw = 0.0F;
            this.setPath((Path) null);
            this.bodyYaw = this.random.nextFloat() * 360.0F;
            this.animateSpawn();
            this.teleTimer = this.random.nextInt(40);
        }

    }

    public boolean isAirySpace(int x, int y, int z) {
        int p = this.world.getBlockId(x, y, z);
        return p == 0 || Block.BLOCKS[p].getCollisionShape(this.world, x, y, z) == null;
    }

    public boolean canDespawn() {
        return !this.boss;
    }

    public boolean interact(PlayerEntity entityplayer) {
        this.lookAt(entityplayer, 180.0F, 180.0F);
        ItemStack itemstack;
        if (!this.boss) {
            if (this.timeLeft >= 1200) {
                itemstack = entityplayer.getHand();
                if (itemstack != null && itemstack.itemId == AetherItems.VictoryMedal.id && itemstack.count >= 0) {
                    if (itemstack.count >= 10) {
                        this.chatItUp("Umm... that's a nice pile of medallions you have there...");
                    } else if (itemstack.count >= 5) {
                        this.chatItUp("That's pretty impressive, but you won't defeat me.");
                    } else {
                        this.chatItUp("You think you're a tough guy, eh? Well, bring it on!");
                    }
                } else {
                    int pokey = this.random.nextInt(3);
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
            itemstack = entityplayer.getHand();
            if (itemstack != null && itemstack.itemId == AetherItems.VictoryMedal.id && itemstack.count >= 10) {
                itemstack.count -= 10;
                if (itemstack.count <= 0) {
                    itemstack.onRemoved(entityplayer);
                    entityplayer.clearStackInHand();
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
        mc.inGameHud.addChatMessage(s);
    }

    @Environment(EnvType.SERVER)
    public void chatLineServer(String s) {
        MinecraftServer mc = ((MinecraftServer) FabricLoader.getInstance().getGameInstance());
        List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            ((ServerPlayerEntity) player).sendMessage(s);
        }
    }

    public void makeHomeShot(int shots, LivingEntity ep) {
        for (int i = 0; i < shots; ++i) {
            EntityHomeShot e1 = new EntityHomeShot(this.world, this.x - this.velocityX / 2.0, this.y, this.z - this.velocityZ / 2.0, ep);
            this.world.spawnEntity(e1);
        }

    }

    protected void dropItems() {
        if (this.boss) {
            this.dropItem(new ItemStack(AetherItems.Key, 1, 1), 0.0F);
            this.dropItem(Item.GOLDEN_SWORD.id, 1);
        } else {
            this.dropItem(AetherItems.VictoryMedal.id, 1);
        }

    }

    public void tickLiving() {
        super.tickLiving();
        ++this.teleTimer;
        if (this.teleTimer >= 450) {
            if (this.target != null) {
                if (this.boss && this.onGround && this.random.nextInt(2) == 0 && this.target != null && this.target instanceof LivingEntity) {
                    this.makeHomeShot(1, (LivingEntity) this.target);
                    this.teleTimer = -100;
                } else {
                    this.teleport(this.target.x, this.target.y, this.target.z, 7);
                }
            } else if (this.onGround && !this.boss) {
                this.teleport(this.x, this.y, this.z, 12 + this.random.nextInt(12));
            } else {
                this.teleport(this.safeX, this.safeY, this.safeZ, 6);
            }
        } else if (this.teleTimer >= 446 || !(this.y <= 0.0) && !(this.y <= this.safeY - 16.0)) {
            if (this.teleTimer % 5 == 0 && this.target != null && !this.canSee(this.target)) {
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

        if (this.target != null && this.target.dead) {
            this.target = null;
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
            this.lastSwingAnimationProgress = 0.0F;
            this.swingAnimationProgress = 0.0F;
        }

    }

    public void teleFail() {
        this.teleTimer -= this.random.nextInt(40) + 40;
        if (this.y <= 0.0) {
            this.teleTimer = 446;
        }

    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getBrightness(i, j, k) > 8 && this.world.canSpawnEntity(this.boundingBox) && this.world.getEntityCollisions(this, this.boundingBox).size() == 0 && !this.world.isBoxSubmergedInFluid(this.boundingBox);
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putShort("Anger", (short) this.angerLevel);
        nbttagcompound.putShort("TeleTimer", (short) this.teleTimer);
        nbttagcompound.putShort("TimeLeft", (short) this.timeLeft);
        nbttagcompound.putBoolean("Boss", this.boss);
        nbttagcompound.putBoolean("Duel", this.duel);
        nbttagcompound.putInt("DungeonX", this.dungeonX);
        nbttagcompound.putInt("DungeonY", this.dungeonY);
        nbttagcompound.putInt("DungeonZ", this.dungeonZ);
        nbttagcompound.putInt("DungeonEntranceZ", this.dungeonEntranceZ);
        nbttagcompound.put("SafePos", this.toNbtList(new double[]{this.safeX, this.safeY, this.safeZ}));
        nbttagcompound.putBoolean("IsCurrentBoss", isBoss);
        if (isBoss) {
            if (bossName != null) {
                if (!bossName.isEmpty()) {
                    nbttagcompound.putString("BossName", this.bossName);
                }
            }
        }
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
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

        NbtList nbttaglist = nbttagcompound.getList("SafePos");
        this.safeX = ((NbtDouble) nbttaglist.get(0)).value;
        this.safeY = ((NbtDouble) nbttaglist.get(1)).value;
        this.safeZ = ((NbtDouble) nbttaglist.get(2)).value;
        if (nbttagcompound.getBoolean("IsCurrentBoss")) {
            isBoss = true;
        }
        if (isBoss) {
            this.bossName = nbttagcompound.getString("BossName");
            if (bossName == null) {
                bossName = NameGen.gen();
            }
        }
    }

    protected Entity getTargetInRange() {
        return !this.otherDimension() || this.world.difficulty > 0 && (!this.boss || this.duel) && this.angerLevel > 0 ? super.getTargetInRange() : null;
    }

    public boolean damage(Entity entity, int i) {
        if (entity instanceof PlayerEntity && this.world.difficulty > 0) {
            int pokey;
            if (this.boss && (!this.duel || this.world.difficulty <= 0)) {
                this.animateSpawn();
                pokey = this.random.nextInt(2);
                if (pokey == 2) {
                    this.chatItUp("Sorry, I don't fight with weaklings.");
                } else {
                    this.chatItUp("Try defeating some weaker valkyries first.");
                }

                return false;
            } else {
                if (this.boss) {
                    if (this.target == null) {
                        isBoss = true;
                        this.chatTime = 0;
                        this.chatItUp("This will be your final battle!");
                    } else {
                        this.teleTimer += 60;
                    }
                } else if (this.target == null) {
                    this.chatTime = 0;
                    pokey = this.random.nextInt(3);
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
                    pokey = this.random.nextInt(3);
                    this.dead = true;
                    if (this.boss) {
                        this.dead = false;
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

                    this.animateSpawn();
                }

                return flag;
            }
        } else {
            this.teleport(this.x, this.y, this.z, 8);
            this.fireTicks = 0;
            return false;
        }
    }

    protected void attack(Entity entity, float f) {
        if (this.attackCooldown <= 0 && f < 2.75F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackCooldown = 20;
            this.swingArm();
            entity.damage(this, this.attackStrength);
            if (entity != null && this.target != null && entity == this.target && entity instanceof LivingEntity) {
                LivingEntity e1 = (LivingEntity) entity;
                if (e1.health <= 0) {
                    this.target = null;
                    this.angerLevel = 0;
                    int pokey = this.random.nextInt(3);
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
        this.target = entity;
        this.angerLevel = 200 + this.random.nextInt(200);
        if (this.boss) {
            for (int k = this.dungeonZ + 2; k < this.dungeonZ + 23; k += 7) {
                if (this.world.getBlockId(this.dungeonX - 1, this.dungeonY, k) == 0) {
                    this.dungeonEntranceZ = k;
                    this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY, k, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY, k + 1, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY + 1, k + 1, AetherBlocks.LockedDungeonStone.id, 1);
                    this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY + 1, k, AetherBlocks.LockedDungeonStone.id, 1);
                    return;
                }
            }
        }

    }

    private void unlockDoor() {
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ, 0);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ + 1, 0);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ + 1, 0);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ, 0);
    }

    private void unlockTreasure() {
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 9, Block.TRAPDOOR.id, 3);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 9, Block.TRAPDOOR.id, 2);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 16, this.dungeonY + 1, this.dungeonZ + 10, Block.TRAPDOOR.id, 3);
        this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 17, this.dungeonY + 1, this.dungeonZ + 10, Block.TRAPDOOR.id, 2);

        List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
        for (PlayerEntity player : playersNearby) {
            AetherAchievements.giveAchievement(AetherAchievements.defeatSilver, player);
        }

        for (int x = this.dungeonX - 26; x < this.dungeonX + 29; ++x) {
            for (int y = this.dungeonY - 1; y < this.dungeonY + 22; ++y) {
                for (int z = this.dungeonZ - 5; z < this.dungeonZ + 25; ++z) {
                    int id = this.world.getBlockId(x, y, z);
                    if (id == AetherBlocks.LockedDungeonStone.id) {
                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }

                    if (id == AetherBlocks.Trap.id) {
                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(x, y, z));
                    }

                    if (id == AetherBlocks.LockedLightDungeonStone.id) {
                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.LightDungeonStone.id, this.world.getBlockMeta(x, y, z));
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
