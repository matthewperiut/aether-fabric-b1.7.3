package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.entity.projectile.EntityFlamingArrow;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderFlamingArrow extends EntityRenderer {
    public RenderFlamingArrow() {
    }

    public void func_154_a(EntityFlamingArrow entityarrow, double d, double d1, double d2, float f, float f1) {
        if (entityarrow.prevYaw != 0.0F || entityarrow.prevPitch != 0.0F) {
            this.bindTexture("aether:stationapi/textures/other/FlamingArrows.png");
            GL11.glPushMatrix();
            GL11.glTranslatef((float) d, (float) d1, (float) d2);
            GL11.glRotatef(entityarrow.prevYaw + (entityarrow.yaw - entityarrow.prevYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entityarrow.prevPitch + (entityarrow.pitch - entityarrow.prevPitch) * f1, 0.0F, 0.0F, 1.0F);
            Tessellator tessellator = Tessellator.INSTANCE;
            int i = 0;
            float f2 = 0.0F;
            float f3 = 0.5F;
            float f4 = (float) (0 + i * 10) / 32.0F;
            float f5 = (float) (5 + i * 10) / 32.0F;
            float f6 = 0.0F;
            float f7 = 0.15625F;
            float f8 = (float) (5 + i * 10) / 32.0F;
            float f9 = (float) (10 + i * 10) / 32.0F;
            float f10 = 0.05625F;
            GL11.glEnable(32826);
            float f11 = (float) entityarrow.arrowShake - f1;
            if (f11 > 0.0F) {
                float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(f10, f10, f10);
            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
            GL11.glNormal3f(f10, 0.0F, 0.0F);
            tessellator.startQuads();
            tessellator.vertex(-7.0, -2.0, -2.0, (double) f6, (double) f8);
            tessellator.vertex(-7.0, -2.0, 2.0, (double) f7, (double) f8);
            tessellator.vertex(-7.0, 2.0, 2.0, (double) f7, (double) f9);
            tessellator.vertex(-7.0, 2.0, -2.0, (double) f6, (double) f9);
            tessellator.draw();
            GL11.glNormal3f(-f10, 0.0F, 0.0F);
            tessellator.startQuads();
            tessellator.vertex(-7.0, 2.0, -2.0, (double) f6, (double) f8);
            tessellator.vertex(-7.0, 2.0, 2.0, (double) f7, (double) f8);
            tessellator.vertex(-7.0, -2.0, 2.0, (double) f7, (double) f9);
            tessellator.vertex(-7.0, -2.0, -2.0, (double) f6, (double) f9);
            tessellator.draw();

            for (int j = 0; j < 4; ++j) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, f10);
                tessellator.startQuads();
                tessellator.vertex(-8.0, -2.0, 0.0, (double) f2, (double) f4);
                tessellator.vertex(8.0, -2.0, 0.0, (double) f3, (double) f4);
                tessellator.vertex(8.0, 2.0, 0.0, (double) f3, (double) f5);
                tessellator.vertex(-8.0, 2.0, 0.0, (double) f2, (double) f5);
                tessellator.draw();
            }

            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_154_a((EntityFlamingArrow) entity, d, d1, d2, f, f1);
    }
}
