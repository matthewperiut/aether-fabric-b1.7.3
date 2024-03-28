package com.matthewperiut.aether.poison;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.ScreenScaler;
import org.lwjgl.opengl.GL11;

public class ClientPoison {
    public static float getPoisonAlpha(float f) {
        return f * f / 5.0F + 0.4F;
    }

    public static float getCureAlpha(float f) {
        return f * f / 10.0F + 0.4F;
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

    public static void displayCureEffect(Minecraft mc) {
        if (mc.currentScreen == null) {
            flashColor(mc, "%blur%/assets/aether/poison/curevignette.png", getCureAlpha(-(((float) mc.player.getDataTracker().getInt(29)) / 500.0F)));
        }
    }

    public static void displayPoisonEffect(Minecraft mc, int mod) {
        if (mc.currentScreen == null) {
            flashColor(mc, "%blur%/assets/aether/poison/poisonvignette.png", getPoisonAlpha((float) mod / 50.0F));
        }
    }

    public static void tickRender(Minecraft mc) {
        int poisonTime = mc.player.getDataTracker().getInt(29);
        if (mc.world != null) {
            if (poisonTime < 0) {
                ++poisonTime;
                displayCureEffect(mc);
            } else if (poisonTime != 0) {
                int mod = poisonTime % 50;
                displayPoisonEffect(mc, mod);
            }
        }
    }

}
