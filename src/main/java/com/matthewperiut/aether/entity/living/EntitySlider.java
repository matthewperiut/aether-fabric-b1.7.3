package com.matthewperiut.aether.entity.living;

import com.matthewperiut.accessoryapi.api.BossLivingEntity;
import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.util.NameGen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntitySlider extends FlyingEntity implements BossLivingEntity, MobSpawnDataProvider {
    public int moveTimer;
    public int dennis;
    public int rennis;
    public int chatTime;
    public Entity target;
    public boolean awake;
    public boolean gotMovement;
    public boolean crushed;
    public float speedy;
    public float harvey;
    public int direction;
    private int dungeonX;
    private int dungeonY;
    private int dungeonZ;
    public String bossName;
    public boolean isBoss = false;
    public int areaOfEffect = 50;

    public EntitySlider(World world) {
        super(world);
        this.yaw = 0.0F;
        this.pitch = 0.0F;
        this.setBoundingBoxSpacing(2.0F, 2.0F);
        this.health = 500;
        this.dennis = 1;
        this.texture = "aether:stationapi/textures/mobs/sliderSleep.png";
        this.chatTime = 60;
        this.bossName = NameGen.gen();
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.x = Math.floor(this.x + 0.5);
        this.y = Math.floor(this.y + 0.5);
        this.z = Math.floor(this.z + 0.5);
    }

    public boolean canDespawn() {
        return false;
    }

    protected String getRandomSound() {
        return "ambient.cave.cave";
    }

    protected String getHurtSound() {
        return "step.stone";
    }

    protected String getDeathSound() {
        return "aether:bosses.slider.sliderdeath";
    }

    public void writeNbt(NbtCompound nbttagcompound) {
        super.writeNbt(nbttagcompound);
        nbttagcompound.putFloat("Speedy", this.speedy);
        nbttagcompound.putShort("MoveTimer", (short) this.moveTimer);
        nbttagcompound.putShort("Direction", (short) this.direction);
        nbttagcompound.putBoolean("GotMovement", this.gotMovement);
        nbttagcompound.putBoolean("Awake", this.awake);
        nbttagcompound.putInt("DungeonX", this.dungeonX);
        nbttagcompound.putInt("DungeonY", this.dungeonY);
        nbttagcompound.putInt("DungeonZ", this.dungeonZ);
        nbttagcompound.putBoolean("IsCurrentBoss", isBoss);
        nbttagcompound.putString("BossName", this.bossName);
    }

    public void readNbt(NbtCompound nbttagcompound) {
        super.readNbt(nbttagcompound);
        this.speedy = nbttagcompound.getFloat("Speedy");
        this.moveTimer = nbttagcompound.getShort("MoveTimer");
        this.direction = nbttagcompound.getShort("Direction");
        this.gotMovement = nbttagcompound.getBoolean("GotMovement");
        this.awake = nbttagcompound.getBoolean("Awake");
        this.dungeonX = nbttagcompound.getInt("DungeonX");
        this.dungeonY = nbttagcompound.getInt("DungeonY");
        this.dungeonZ = nbttagcompound.getInt("DungeonZ");
        if (nbttagcompound.getBoolean("IsCurrentBoss")) {
            isBoss = true;
        }

        this.bossName = nbttagcompound.getString("BossName");
        if (this.awake) {
            if (this.criticalCondition()) {
                this.texture = "aether:stationapi/textures/mobs/sliderAwake_red.png";
            } else {
                this.texture = "aether:stationapi/textures/mobs/sliderAwake.png";
            }
        }

    }

    public boolean criticalCondition() {
        return this.health <= 125;
    }

    public void tick() {
        super.tick();
        this.bodyYaw = this.pitch = this.yaw = 0.0F;
        if (this.awake) {
            if (this.target != null && this.target instanceof LivingEntity) {
                LivingEntity e1 = (LivingEntity) this.target;
                if (e1.health <= 0) {
                    this.awake = false;
                    isBoss = false;
                    this.target = null;
                    this.texture = "aether:stationapi/textures/mobs/sliderSleep.png";
                    this.stop();
                    this.openDoor();
                    this.moveTimer = 0;
                    return;
                }
            } else {
                if (this.target != null && this.target.dead) {
                    this.awake = false;
                    isBoss = false;
                    this.target = null;
                    this.texture = "aether:stationapi/textures/mobs/sliderSleep.png";
                    this.stop();
                    this.openDoor();
                    this.moveTimer = 0;
                    return;
                }

                if (this.target == null) {
                    this.target = this.world.getClosestPlayer(this, -1.0);
                    if (this.target == null) {
                        this.awake = false;
                        isBoss = false;
                        this.target = null;
                        this.texture = "aether:stationapi/textures/mobs/sliderSleep.png";
                        this.stop();
                        this.openDoor();
                        this.moveTimer = 0;
                        return;
                    }
                }
            }

            double y;
            double z;
            double x;
            if (this.gotMovement) {
                if (this.hasCollided) {
                    x = this.x - 0.5;
                    y = this.boundingBox.minY + 0.75;
                    z = this.z - 0.5;
                    this.crushed = false;
                    if (y < 124.0 && y > 4.0) {
                        int i;
                        double a;
                        double b;
                        if (this.direction == 0) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x + a), (int) (y + 1.5), (int) (z + b));
                            }
                        } else if (this.direction == 1) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x + a), (int) (y - 1.5), (int) (z + b));
                            }
                        } else if (this.direction == 2) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x + 1.5), (int) (y + a), (int) (z + b));
                            }
                        } else if (this.direction == 3) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x - 1.5), (int) (y + a), (int) (z + b));
                            }
                        } else if (this.direction == 4) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x + a), (int) (y + b), (int) (z + 1.5));
                            }
                        } else if (this.direction == 5) {
                            for (i = 0; i < 25; ++i) {
                                a = (double) (i / 5 - 2) * 0.75;
                                b = (double) (i % 5 - 2) * 0.75;
                                this.blockCrush((int) (x + a), (int) (y + b), (int) (z - 1.5));
                            }
                        }
                    }

                    if (this.crushed) {
                        this.world.playSound(this.x, this.y, this.z, "random.explode", 3.0F, (0.625F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
                        this.world.playSound(this, "aether:bosses.slider.slidercollide", 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                    }

                    this.stop();
                } else {
                    if (this.speedy < 2.0F) {
                        this.speedy += this.criticalCondition() ? 0.0325F : 0.025F;
                    }

                    this.velocityX = 0.0;
                    this.velocityY = 0.0;
                    this.velocityZ = 0.0;
                    if (this.direction == 0) {
                        this.velocityY = (double) this.speedy;
                        if (this.boundingBox.minY > this.target.boundingBox.minY + 0.35) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    } else if (this.direction == 1) {
                        this.velocityY = (double) (-this.speedy);
                        if (this.boundingBox.minY < this.target.boundingBox.minY - 0.25) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    } else if (this.direction == 2) {
                        this.velocityX = (double) this.speedy;
                        if (this.x > this.target.x + 0.125) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    } else if (this.direction == 3) {
                        this.velocityX = (double) (-this.speedy);
                        if (this.x < this.target.x - 0.125) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    } else if (this.direction == 4) {
                        this.velocityZ = (double) this.speedy;
                        if (this.z > this.target.z + 0.125) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    } else if (this.direction == 5) {
                        this.velocityZ = (double) (-this.speedy);
                        if (this.z < this.target.z - 0.125) {
                            this.stop();
                            this.moveTimer = 8;
                        }
                    }
                }
            } else if (this.moveTimer > 0) {
                --this.moveTimer;
                if (this.criticalCondition() && this.random.nextInt(2) == 0) {
                    --this.moveTimer;
                }

                this.velocityX = 0.0;
                this.velocityY = 0.0;
                this.velocityZ = 0.0;
            } else {
                x = Math.abs(this.x - this.target.x);
                y = Math.abs(this.boundingBox.minY - this.target.boundingBox.minY);
                z = Math.abs(this.z - this.target.z);
                if (x > z) {
                    this.direction = 2;
                    if (this.x > this.target.x) {
                        this.direction = 3;
                    }
                } else {
                    this.direction = 4;
                    if (this.z > this.target.z) {
                        this.direction = 5;
                    }
                }

                if (y > x && y > z || y > 0.25 && this.random.nextInt(5) == 0) {
                    this.direction = 0;
                    if (this.y > this.target.y) {
                        this.direction = 1;
                    }
                }

                this.world.playSound(this, "aether:bosses.slider.slidermove", 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                this.gotMovement = true;
            }
        }

        if (this.harvey > 0.01F) {
            this.harvey *= 0.8F;
        }

        if (this.chatTime > 0) {
            --this.chatTime;
        }

    }

    private void openDoor() {
        int x = this.dungeonX + 15;

        for (int y = this.dungeonY + 1; y < this.dungeonY + 5; ++y) {
            for (int z = this.dungeonZ + 6; z < this.dungeonZ + 10; ++z) {
                this.world.setBlockWithoutNotifyingNeighbors(x, y, z, 0);
            }
        }

    }

    public void onCollision(Entity entity) {
        if (this.awake && this.gotMovement) {
            boolean flag = entity.damage(this, 6);
            if (flag && entity instanceof LivingEntity) {
                this.world.playSound(this, "aether:bosses.slider.slidercollide", 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                if (entity instanceof MobEntity || entity instanceof PlayerEntity) {
                    LivingEntity ek = (LivingEntity) entity;
                    ek.velocityY += 0.35;
                    ek.velocityX *= 2.0;
                    ek.velocityZ *= 2.0;
                }

                this.stop();
            }
        }

    }

    protected void dropItems() {
        for (int i = 0; i < 7 + this.random.nextInt(3); ++i) {
            this.dropItem(AetherBlocks.DungeonStone.id, 1);
        }

        this.dropItem(new ItemStack(AetherItems.Key, 1, 0), 0.0F);
    }

    public boolean canSpawn() {
        int i = MathHelper.floor(this.x);
        int j = MathHelper.floor(this.boundingBox.minY);
        int k = MathHelper.floor(this.z);
        return this.world.getBlockId(i, j - 1, k) == Block.GRASS_BLOCK.id && this.world.getBrightness(i, j, k) > 8 && super.canSpawn();
    }

    public void stop() {
        this.gotMovement = false;
        this.moveTimer = 12;
        this.direction = 0;
        this.speedy = 0.0F;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.velocityZ = 0.0;
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

    public boolean damage(Entity e1, int i) {
        if (e1 != null && e1 instanceof PlayerEntity) {
            PlayerEntity p1 = (PlayerEntity) e1;
            ItemStack stack = p1.getHand();
            if (stack != null && stack.getItem() != null) {
                if (stack.getItem() instanceof ToolItem) {
                    ToolItem tool = (ToolItem) stack.getItem();
                    if (!tool.isSuitableFor(Block.STONE)) {
                        this.chatItUp("Hmm. Perhaps I need to attack it with a Pickaxe?");
                        return false;
                    } else {
                        boolean flag = super.damage(e1, Math.max(0, i));
                        if (flag) {
                            int x;
                            for (x = 0; x < (this.health <= 0 ? 16 : 48); ++x) {
                                double a = this.x + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
                                double b = this.boundingBox.minY + 1.75 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
                                double c = this.z + (double) (this.random.nextFloat() - this.random.nextFloat()) * 1.5;
                                if (this.health <= 0) {
                                    this.world.addParticle("explode", a, b, c, 0.0, 0.0, 0.0);
                                }
                            }

                            if (this.health <= 0) {
                                this.dead = true;
                                this.openDoor();
                                this.unlockBlock(this.dungeonX, this.dungeonY, this.dungeonZ);
                                this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 7, this.dungeonY + 1, this.dungeonZ + 7, Block.TRAPDOOR.id, 3);
                                this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 8, this.dungeonY + 1, this.dungeonZ + 7, Block.TRAPDOOR.id, 2);
                                this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 7, this.dungeonY + 1, this.dungeonZ + 8, Block.TRAPDOOR.id, 3);
                                this.world.setBlockWithoutNotifyingNeighbors(this.dungeonX + 8, this.dungeonY + 1, this.dungeonZ + 8, Block.TRAPDOOR.id, 2);

                                List<PlayerEntity> playersNearby = world.collectEntitiesByClass(PlayerEntity.class, Box.create(this.x - areaOfEffect, this.y - areaOfEffect, z - areaOfEffect, this.x + areaOfEffect, this.y + areaOfEffect, z + areaOfEffect));
                                for (PlayerEntity player : playersNearby) {
                                    AetherAchievements.giveAchievement(AetherAchievements.defeatBronze, player);
                                }

                                isBoss = false;
                            } else if (this.awake) {
                                if (this.gotMovement) {
                                    this.speedy *= 0.75F;
                                }
                            } else {
                                this.world.playSound(this, "aether:bosses.slider.sliderawaken", 2.5F, 1.0F / (this.random.nextFloat() * 0.2F + 0.9F));
                                this.awake = true;
                                this.target = e1;
                                this.texture = "aether:stationapi/textures/mobs/sliderAwake.png";
                                x = this.dungeonX + 15;
                                int y = this.dungeonY + 1;

                                while (true) {
                                    if (y >= this.dungeonY + 8) {
                                        isBoss = true;
                                        break;
                                    }

                                    for (int z = this.dungeonZ + 5; z < this.dungeonZ + 11; ++z) {
                                        this.world.setBlockWithoutNotifyingNeighbors(x, y, z, AetherBlocks.LockedDungeonStone.id);
                                    }

                                    ++y;
                                }
                            }

                            double a = Math.abs(this.x - e1.x);
                            double c = Math.abs(this.z - e1.z);
                            if (a > c) {
                                this.dennis = 1;
                                this.rennis = 0;
                                if (this.x > e1.x) {
                                    this.dennis = -1;
                                }
                            } else {
                                this.rennis = 1;
                                this.dennis = 0;
                                if (this.z > e1.z) {
                                    this.rennis = -1;
                                }
                            }

                            this.harvey = 0.7F - (float) this.health / 875.0F;
                            if (this.criticalCondition()) {
                                this.texture = "aether:stationapi/textures/mobs/sliderAwake_red.png";
                            } else {
                                this.texture = "aether:stationapi/textures/mobs/sliderAwake.png";
                            }
                        }

                        return flag;
                    }
                } else {
                    this.chatItUp("Hmm. Perhaps I need to attack it with a Pickaxe?");
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void unlockBlock(int i, int j, int k) {
        int id = this.world.getBlockId(i, j, k);
        if (id == AetherBlocks.LockedDungeonStone.id) {
            this.world.setBlockWithoutNotifyingNeighbors(i, j, k, AetherBlocks.DungeonStone.id, this.world.getBlockMeta(i, j, k));
            this.unlockBlock(i + 1, j, k);
            this.unlockBlock(i - 1, j, k);
            this.unlockBlock(i, j + 1, k);
            this.unlockBlock(i, j - 1, k);
            this.unlockBlock(i, j, k + 1);
            this.unlockBlock(i, j, k - 1);
        }

        if (id == AetherBlocks.LockedLightDungeonStone.id) {
            this.world.setBlockWithoutNotifyingNeighbors(i, j, k, AetherBlocks.LightDungeonStone.id, this.world.getBlockMeta(i, j, k));
            this.unlockBlock(i + 1, j, k);
            this.unlockBlock(i - 1, j, k);
            this.unlockBlock(i, j + 1, k);
            this.unlockBlock(i, j - 1, k);
            this.unlockBlock(i, j, k + 1);
            this.unlockBlock(i, j, k - 1);
        }

    }

    public void addVelocity(double d, double d1, double d2) {
    }

    public void applyKnockback(Entity entity, int i, double d, double d1) {
    }

    public void blockCrush(int x, int y, int z) {
        int a = this.world.getBlockId(x, y, z);
        int b = this.world.getBlockMeta(x, y, z);
        if (a != 0 && a != AetherBlocks.LockedDungeonStone.id && a != AetherBlocks.LockedLightDungeonStone.id) {
            //ModLoader.getMinecraftInstance().particleManager.addBlockBreakParticles(x, y, z, a, b);
            Block.BLOCKS[a].onBreak(this.world, x, y, z);
            Block.BLOCKS[a].dropStacks(this.world, x, y, z, b);
            this.world.setBlock(x, y, z, 0);
            this.crushed = true;
            this.addSquirrelButts(x, y, z);
        }
    }

    public void addSquirrelButts(int x, int y, int z) {
        double a = (double) x + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        double b = (double) y + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        double c = (double) z + 0.5 + (double) (this.random.nextFloat() - this.random.nextFloat()) * 0.375;
        this.world.addParticle("explode", a, b, c, 0.0, 0.0, 0.0);
    }

    public void setDungeon(int i, int j, int k) {
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
        return this.bossName + ", the Slider";
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("Slider");
    }
}
