package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public class ModelHomeShot extends EntityModel {
    public ModelPart[] head;
    public float[] sinage;
    private static final float sponge = 57.295773F;

    public ModelHomeShot() {
        this(0.0F);
    }

    public ModelHomeShot(float f) {
        this(f, 0.0F);
    }

    public ModelHomeShot(float f, float f1) {
        this.sinage = new float[3];
        this.head = new ModelPart[3];
        this.head[0] = new ModelPart(0, 0);
        this.head[1] = new ModelPart(32, 0);
        this.head[2] = new ModelPart(0, 16);

        for (int i = 0; i < 3; ++i) {
            this.head[i].addCuboid(-4.0F, -4.0F, -4.0F, 8, 8, 8, f);
            this.head[i].setPivot(0.0F, 0.0F + f1, 0.0F);
        }

    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        GL11.glTranslatef(0.0F, 0.75F, 0.0F);
        GL11.glEnable(2977);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glRotatef(this.sinage[0] * 57.295773F, 1.0F, 0.0F, 0.0F);
        this.head[0].render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef(this.sinage[1] * 57.295773F, 0.0F, 1.0F, 0.0F);
        this.head[1].render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glRotatef(this.sinage[2] * 57.295773F, 0.0F, 0.0F, 1.0F);
        this.head[2].render(f5);
        GL11.glPopMatrix();
        GL11.glEnable(3008);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        for (int i = 0; i < 3; ++i) {
            this.head[i].yaw = f3 / 57.29578F;
            this.head[i].pitch = f4 / 57.29578F;
        }

    }
}
