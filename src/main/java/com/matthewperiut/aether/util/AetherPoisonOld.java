package com.matthewperiut.aether.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.Random;

public class AetherPoisonOld {
    public static final float poisonRed = 1.0F;
    public static final float poisonBlue = 1.0F;
    public static final float poisonGreen = 0.0F;
    public static final float cureRed = 0.0F;
    public static final float cureBlue = 0.1F;
    public static final float cureGreen = 1.0F;
    public static final int poisonInterval = 50;
    public static final int poisonDmg = 1;
    public static final int poisonHurts = 10;
    public static final int maxPoisonTime = 500;
    public static long clock;
    public static int poisonTime;
    public static World world;
    //public static Minecraft mc = ModLoader.getMinecraftInstance();
    public static double rotDFac = 0.7853981633974483;
    public static double rotD;
    public static double rotTaper = 0.125;
    public static double motTaper = 0.2;
    public static double motD;
    public static double motDFac = 0.1;
    public static Random rand = new Random();

    public AetherPoisonOld() {
        //ModLoader.RegisterEntityID(EntityPoisonNeedle.class, "PoisonNeedle", ModLoader.getUniqueEntityId());
        //ModLoader.RegisterEntityID(EntityDartPoison.class, "PoisonDart", ModLoader.getUniqueEntityId());
        //ModLoader.RegisterEntityID(EntityDartGolden.class, "GoldenDart", ModLoader.getUniqueEntityId());
        //ModLoader.RegisterEntityID(EntityDartEnchanted.class, "EnchantedDart", ModLoader.getUniqueEntityId());
    }

    public static boolean canPoison(Entity entity) {
        return true;
        // todo: entity return !(entity instanceof EntitySlider) && !(entity instanceof EntitySentry) && !(entity instanceof EntityMiniCloud) && !(entity instanceof EntityFireMonster) && !(entity instanceof EntityAechorPlant) && !(entity instanceof EntityFiroBall) && !(entity instanceof EntityCockatrice) && !(entity instanceof EntityHomeShot);
    }

    public static void distractEntity(Entity ent) {
        double gauss = rand.nextGaussian();
        double newMotD = motDFac * gauss;
        motD = motTaper * newMotD + (1.0 - motTaper) * motD;
        ent.xVelocity += motD;
        ent.zVelocity += motD;
        double newRotD = rotDFac * gauss;
        rotD = rotTaper * newRotD + (1.0 - rotTaper) * rotD;
        ent.yaw = (float) ((double) ent.yaw + rotD);
        ent.pitch = (float) ((double) ent.pitch + rotD);
    }

    public static void tickRender(Minecraft game) {
        if (world == game.world && (game.player == null || !game.player.removed && game.player.health > 0)) {
            if (world != null) {
                if (poisonTime < 0) {
                    ++poisonTime;
                    displayCureEffect(game);
                } else if (poisonTime != 0) {
                    long time = game.world.getWorldTime();
                    int mod = poisonTime % 50;
                    if (clock != time) {
                        distractEntity(game.player);
                        if (mod == 0) {
                            game.player.damage((Entity) null, 1);
                        }

                        --poisonTime;
                        clock = time;
                    }

                    displayPoisonEffect(game, mod);
                }
            }
        } else {
            world = game.world;
            poisonTime = 0;
        }
    }

    public static boolean afflictPoison() {
        if (poisonTime < 0) {
            return false;
        } else {
            poisonTime = 500;
            //world = mc.world;
            return true;
        }
    }

    public static boolean curePoison() {
        if (poisonTime == -500) {
            return false;
        } else {
            poisonTime = -500;
            //world = mc.world;
            return true;
        }
    }

    public static float getPoisonAlpha(float f) {
        return f * f / 5.0F + 0.4F;
    }

    public static float getCureAlpha(float f) {
        return f * f / 10.0F + 0.4F;
    }

    public static void displayCureEffect(Minecraft game) {
        if (game.currentScreen == null) {
            flashColor(game, "%blur%/aether/poison/curevignette.png", getCureAlpha(-((float) poisonTime) / 500.0F));
        }
    }

    public static void displayPoisonEffect(Minecraft game, int mod) {
        if (game.currentScreen == null) {
            flashColor(game, "%blur%/aether/poison/poisonvignette.png", getPoisonAlpha((float) mod / 50.0F));
        }
    }

    public static void flashColor(Minecraft mc, String file, float a) {
        ScreenScaler scaledresolution = new ScreenScaler(mc.options, mc.actualWidth, mc.actualHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, a);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, mc.textureManager.getTextureId(file));
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(0.0, (double) height, -90.0, 0.0, 1.0);
        tessellator.vertex((double) width, (double) height, -90.0, 1.0, 1.0);
        tessellator.vertex((double) width, 0.0, -90.0, 1.0, 0.0);
        tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator.tessellate();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, a);
    }

    public static void AddRenderer(Map map) {
        //map.put(EntityPoisonNeedle.class, new RenderPoisonNeedle());
        //map.put(EntityDartPoison.class, new RenderDartPoison());
        //map.put(EntityDartGolden.class, new RenderDartGolden());
        //map.put(EntityDartEnchanted.class, new RenderDartEnchanted());
    }
}
