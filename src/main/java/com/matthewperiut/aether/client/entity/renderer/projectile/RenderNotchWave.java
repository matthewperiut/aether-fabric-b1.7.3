package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.entity.projectile.EntityNotchWave;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderNotchWave extends EntityRenderer {
    public RenderNotchWave() {
    }

    public void func_4012_a(EntityNotchWave entityNotchWave, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glEnable(32826);
        this.bindTexture("aether:stationapi/textures/other/NotchWave.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.dispatcher.pitch, 1.0F, 0.0F, 0.0F);
        tessellator.startQuads();
        tessellator.normal(0.0F, 1.0F, 0.0F);
        tessellator.vertex((double) (0.0F - f8), (double) (0.0F - f9), 0.0, 0.0, 0.0);
        tessellator.vertex((double) (f7 - f8), (double) (0.0F - f9), 0.0, 0.0, 1.0);
        tessellator.vertex((double) (f7 - f8), (double) (1.0F - f9), 0.0, 1.0, 1.0);
        tessellator.vertex((double) (0.0F - f8), (double) (1.0F - f9), 0.0, 1.0, 0.0);
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_4012_a((EntityNotchWave) entity, d, d1, d2, f, f1);
    }
}
