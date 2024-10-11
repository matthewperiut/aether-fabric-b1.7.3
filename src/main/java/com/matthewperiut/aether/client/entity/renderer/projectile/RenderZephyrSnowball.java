package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.entity.projectile.EntityZephyrSnowball;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderZephyrSnowball extends EntityRenderer {
    public RenderZephyrSnowball() {
    }

    public void func_4012_a(EntityZephyrSnowball entityZephyrSnowball, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glEnable(32826);
        float f2 = 2.0F;
        GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        int snowballIndex = 14;
        this.bindTexture("gui/items.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        float f3 = (float) (snowballIndex % 16 * 16) / 256.0F;
        float f4 = (float) (snowballIndex % 16 * 16 + 16) / 256.0F;
        float f5 = (float) (snowballIndex / 16 * 16 + 0) / 256.0F;
        float f6 = (float) (snowballIndex / 16 * 16 + 16) / 256.0F;
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.dispatcher.pitch, 1.0F, 0.0F, 0.0F);
        tessellator.startQuads();
        tessellator.normal(0.0F, 1.0F, 0.0F);
        tessellator.vertex((double) (0.0F - f8), (double) (0.0F - f9), 0.0, (double) f3, (double) f6);
        tessellator.vertex((double) (f7 - f8), (double) (0.0F - f9), 0.0, (double) f4, (double) f6);
        tessellator.vertex((double) (f7 - f8), (double) (1.0F - f9), 0.0, (double) f4, (double) f5);
        tessellator.vertex((double) (0.0F - f8), (double) (1.0F - f9), 0.0, (double) f3, (double) f5);
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_4012_a((EntityZephyrSnowball) entity, d, d1, d2, f, f1);
    }
}
