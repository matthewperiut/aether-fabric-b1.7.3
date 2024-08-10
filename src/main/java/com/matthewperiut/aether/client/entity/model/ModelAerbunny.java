package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelAerbunny extends EntityModel {
    public ModelPart a;
    public ModelPart b;
    public ModelPart b2;
    public ModelPart b3;
    public ModelPart e1;
    public ModelPart e2;
    public ModelPart ff1;
    public ModelPart ff2;
    public ModelPart g;
    public ModelPart g2;
    public ModelPart h;
    public ModelPart h2;
    public float puffiness;

    public ModelAerbunny() {
        byte byte0 = 16;
        this.a = new ModelPart(0, 0);
        this.a.addCuboid(-2.0F, -1.0F, -4.0F, 4, 4, 6, 0.0F);
        this.a.setPivot(0.0F, (float) (-1 + byte0), -4.0F);
        this.g = new ModelPart(14, 0);
        this.g.addCuboid(-2.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g.setPivot(0.0F, (float) (-1 + byte0), -4.0F);
        this.g2 = new ModelPart(14, 0);
        this.g2.addCuboid(1.0F, -5.0F, -3.0F, 1, 4, 2, 0.0F);
        this.g2.setPivot(0.0F, (float) (-1 + byte0), -4.0F);
        this.h = new ModelPart(20, 0);
        this.h.addCuboid(-4.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h.setPivot(0.0F, (float) (-1 + byte0), -4.0F);
        this.h2 = new ModelPart(20, 0);
        this.h2.addCuboid(2.0F, 0.0F, -3.0F, 2, 3, 2, 0.0F);
        this.h2.setPivot(0.0F, (float) (-1 + byte0), -4.0F);
        this.b = new ModelPart(0, 10);
        this.b.addCuboid(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
        this.b.setPivot(0.0F, (float) (0 + byte0), 0.0F);
        this.b2 = new ModelPart(0, 24);
        this.b2.addCuboid(-2.0F, 4.0F, -2.0F, 4, 3, 4, 0.0F);
        this.b2.setPivot(0.0F, (float) (0 + byte0), 0.0F);
        this.b3 = new ModelPart(29, 0);
        this.b3.addCuboid(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
        this.b3.setPivot(0.0F, 0.0F, 0.0F);
        this.e1 = new ModelPart(24, 16);
        this.e1.addCuboid(-2.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e1.setPivot(3.0F, (float) (3 + byte0), -3.0F);
        this.e2 = new ModelPart(24, 16);
        this.e2.addCuboid(0.0F, 0.0F, -1.0F, 2, 2, 2);
        this.e2.setPivot(-3.0F, (float) (3 + byte0), -3.0F);
        this.ff1 = new ModelPart(16, 24);
        this.ff1.addCuboid(-2.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff1.setPivot(3.0F, (float) (3 + byte0), 4.0F);
        this.ff2 = new ModelPart(16, 24);
        this.ff2.addCuboid(0.0F, 0.0F, -4.0F, 2, 2, 4);
        this.ff2.setPivot(-3.0F, (float) (3 + byte0), 4.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.a.render(f5);
        this.g.render(f5);
        this.g2.render(f5);
        this.h.render(f5);
        this.h2.render(f5);
        this.b.render(f5);
        this.b2.render(f5);
        GL11.glPushMatrix();
        float a = 1.0F + this.puffiness * 0.5F;
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glScalef(a, a, a);
        this.b3.render(f5);
        GL11.glPopMatrix();
        this.e1.render(f5);
        this.e2.render(f5);
        this.ff1.render(f5);
        this.ff2.render(f5);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.a.pitch = -(f4 / 57.29578F);
        this.a.yaw = f3 / 57.29578F;
        this.g.pitch = this.a.pitch;
        this.g.yaw = this.a.yaw;
        this.g2.pitch = this.a.pitch;
        this.g2.yaw = this.a.yaw;
        this.h.pitch = this.a.pitch;
        this.h.yaw = this.a.yaw;
        this.h2.pitch = this.a.pitch;
        this.h2.yaw = this.a.yaw;
        this.b.pitch = 1.570796F;
        this.b2.pitch = 1.570796F;
        this.b3.pitch = 1.570796F;
        this.e1.pitch = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        this.ff1.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
        this.e2.pitch = MathHelper.cos(f * 0.6662F) * 1.0F * f1;
        this.ff2.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.2F * f1;
    }
}
