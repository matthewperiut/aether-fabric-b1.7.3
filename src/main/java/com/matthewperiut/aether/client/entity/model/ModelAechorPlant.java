package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

public class ModelAechorPlant extends EntityModel {
    private static int petals = 10;
    private static int thorns = 4;
    private static int stamens = 3;
    public float sinage;
    public float sinage2;
    public float size;
    private Cuboid[] petal;
    private Cuboid[] leaf;
    private Cuboid[] stamen;
    private Cuboid[] stamen2;
    private Cuboid[] thorn;
    private Cuboid stem;
    private Cuboid head;
    private float pie;

    public ModelAechorPlant() {
        this(0.0F);
    }

    public ModelAechorPlant(float f) {
        this(f, 0.0F);
    }

    public ModelAechorPlant(float f, float f1) {
        this.pie = 6.283186F;
        this.size = 1.0F;
        this.petal = new Cuboid[petals];
        this.leaf = new Cuboid[petals];

        int i;
        for (i = 0; i < petals; ++i) {
            this.petal[i] = new Cuboid(0, 0);
            if (i % 2 == 0) {
                this.petal[i] = new Cuboid(29, 3);
                this.petal[i].method_1818(-4.0F, -1.0F, -12.0F, 8, 1, 9, f - 0.25F);
                this.petal[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
            } else {
                this.petal[i].method_1818(-4.0F, -1.0F, -13.0F, 8, 1, 10, f - 0.125F);
                this.petal[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
            }

            this.leaf[i] = new Cuboid(38, 13);
            this.leaf[i].method_1818(-2.0F, -1.0F, -9.5F, 4, 1, 8, f - 0.15F);
            this.leaf[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.stamen = new Cuboid[stamens];
        this.stamen2 = new Cuboid[stamens];

        for (i = 0; i < stamens; ++i) {
            this.stamen[i] = new Cuboid(36, 13);
            this.stamen[i].method_1818(0.0F, -9.0F, -1.5F, 1, 6, 1, f - 0.25F);
            this.stamen[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        for (i = 0; i < stamens; ++i) {
            this.stamen2[i] = new Cuboid(32, 15);
            this.stamen2[i].method_1818(0.0F, -10.0F, -1.5F, 1, 1, 1, f + 0.125F);
            this.stamen2[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.head = new Cuboid(0, 12);
        this.head.method_1818(-3.0F, -3.0F, -3.0F, 6, 2, 6, f + 0.75F);
        this.head.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        this.stem = new Cuboid(24, 13);
        this.stem.method_1818(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
        this.stem.setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        this.thorn = new Cuboid[thorns];

        for (i = 0; i < thorns; ++i) {
            this.thorn[i] = new Cuboid(32, 13);
            this.thorn[i].setRotationPoint(0.0F, 1.0F + f1, 0.0F);
        }

        this.thorn[0].method_1818(-1.75F, 1.25F, -1.0F, 1, 1, 1, f - 0.25F);
        this.thorn[1].method_1818(-1.0F, 2.25F, 0.75F, 1, 1, 1, f - 0.25F);
        this.thorn[2].method_1818(0.75F, 1.25F, 0.0F, 1, 1, 1, f - 0.25F);
        this.thorn[3].method_1818(0.0F, 2.25F, -1.75F, 1, 1, 1, f - 0.25F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.2F, 0.0F);
        GL11.glScalef(this.size, this.size, this.size);

        int i;
        for (i = 0; i < petals; ++i) {
            this.petal[i].render(f5);
            this.leaf[i].render(f5);
        }

        for (i = 0; i < stamens; ++i) {
            this.stamen[i].render(f5);
            this.stamen2[i].render(f5);
        }

        this.head.render(f5);
        this.stem.render(f5);

        for (i = 0; i < thorns; ++i) {
            this.thorn[i].render(f5);
        }

        GL11.glPopMatrix();
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.pitch = 0.0F;
        this.head.yaw = f4 / 57.29578F;
        float boff = this.sinage2;
        this.stem.yaw = this.head.yaw;
        this.stem.rotationPointY = boff * 0.5F;

        int i;
        for (i = 0; i < thorns; ++i) {
            this.thorn[i].yaw = this.head.yaw;
            this.thorn[i].rotationPointY = boff * 0.5F;
        }

        Cuboid var10000;
        for (i = 0; i < petals; ++i) {
            this.petal[i].pitch = i % 2 == 0 ? -0.25F : -0.4125F;
            var10000 = this.petal[i];
            var10000.pitch += this.sinage;
            this.petal[i].yaw = this.head.yaw;
            var10000 = this.petal[i];
            var10000.yaw += this.pie / (float) petals * (float) i;
            this.leaf[i].pitch = i % 2 == 0 ? 0.1F : 0.2F;
            var10000 = this.leaf[i];
            var10000.pitch += this.sinage * 0.75F;
            this.leaf[i].yaw = this.head.yaw + this.pie / (float) petals / 2.0F;
            var10000 = this.leaf[i];
            var10000.yaw += this.pie / (float) petals * (float) i;
            this.petal[i].rotationPointY = boff;
            this.leaf[i].rotationPointY = boff;
        }

        for (i = 0; i < stamens; ++i) {
            this.stamen[i].pitch = 0.2F + (float) i / 15.0F;
            this.stamen[i].yaw = this.head.yaw + 0.1F;
            var10000 = this.stamen[i];
            var10000.yaw += this.pie / (float) stamens * (float) i;
            var10000 = this.stamen[i];
            var10000.pitch += this.sinage * 0.4F;
            this.stamen2[i].pitch = 0.2F + (float) i / 15.0F;
            this.stamen2[i].yaw = this.head.yaw + 0.1F;
            var10000 = this.stamen2[i];
            var10000.yaw += this.pie / (float) stamens * (float) i;
            var10000 = this.stamen2[i];
            var10000.pitch += this.sinage * 0.4F;
            this.stamen[i].rotationPointY = boff + this.sinage * 2.0F;
            this.stamen2[i].rotationPointY = boff + this.sinage * 2.0F;
        }

        this.head.rotationPointY = boff + this.sinage * 2.0F;
    }
}
