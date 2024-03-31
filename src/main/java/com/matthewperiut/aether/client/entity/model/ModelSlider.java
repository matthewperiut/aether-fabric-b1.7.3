//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public class ModelSlider extends EntityModel {
    public Cuboid head;

    public ModelSlider() {
        this(0.0F);
    }

    public ModelSlider(float f) {
        this(f, 0.0F);
    }

    public ModelSlider(float f, float f1) {
        this.head = new Cuboid(0, 0);
        this.head.method_1818(-8.0F, -16.0F, -8.0F, 16, 16, 16, f);
        this.head.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.head.render(f5);
        GL11.glPopMatrix();
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.yaw = 0.0F;
        this.head.pitch = 0.0F;
    }
}
