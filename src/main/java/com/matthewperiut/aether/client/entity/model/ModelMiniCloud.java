package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelMiniCloud extends EntityModel {
    public Cuboid[] head;

    public ModelMiniCloud() {
        this(0.0F);
    }

    public ModelMiniCloud(float f) {
        this(f, 0.0F);
    }

    public ModelMiniCloud(float f, float f1) {
        this.head = new Cuboid[5];
        this.head[0] = new Cuboid(0, 0);
        this.head[1] = new Cuboid(36, 0);
        this.head[2] = new Cuboid(36, 0);
        this.head[3] = new Cuboid(36, 8);
        this.head[4] = new Cuboid(36, 8);
        this.head[0].method_1818(-4.5F, -4.5F, -4.5F, 9, 9, 9, f);
        this.head[0].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[1].method_1818(-3.5F, -3.5F, -5.5F, 7, 7, 1, f);
        this.head[1].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[2].method_1818(-3.5F, -3.5F, 4.5F, 7, 7, 1, f);
        this.head[2].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[3].method_1818(-5.5F, -3.5F, -3.5F, 1, 7, 7, f);
        this.head[3].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.head[4].method_1818(4.5F, -3.5F, -3.5F, 1, 7, 7, f);
        this.head[4].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);

        for (int i = 0; i < 5; ++i) {
            this.head[i].render(f5);
        }

    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        for (int i = 0; i < 5; ++i) {
            this.head[i].yaw = f3 / 57.29578F;
            this.head[i].pitch = f4 / 57.29578F;
        }

    }
}
