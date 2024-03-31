//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ModelMoa extends EntityModel {
    public Cuboid head;
    public Cuboid body;
    public Cuboid legs;
    public Cuboid legs2;
    public Cuboid wings;
    public Cuboid wings2;
    public Cuboid jaw;
    public Cuboid neck;
    public Cuboid feather1;
    public Cuboid feather2;
    public Cuboid feather3;
    public Random random;

    public ModelMoa() {
        byte byte0 = 16;
        this.random = new Random();
        this.head = new Cuboid(0, 13);
        this.head.method_1818(-2.0F, -4.0F, -6.0F, 4, 4, 8, 0.0F);
        this.head.setRotationPoint(0.0F, (float) (-8 + byte0), -4.0F);
        this.jaw = new Cuboid(24, 13);
        this.jaw.method_1818(-2.0F, -1.0F, -6.0F, 4, 1, 8, -0.1F);
        this.jaw.setRotationPoint(0.0F, (float) (-8 + byte0), -4.0F);
        this.body = new Cuboid(0, 0);
        this.body.method_1818(-3.0F, -3.0F, 0.0F, 6, 8, 5, 0.0F);
        this.body.setRotationPoint(0.0F, (float) (0 + byte0), 0.0F);
        this.legs = new Cuboid(22, 0);
        this.legs.method_1817(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs.setRotationPoint(-2.0F, (float) (0 + byte0), 1.0F);
        this.legs2 = new Cuboid(22, 0);
        this.legs2.method_1817(-1.0F, -1.0F, -1.0F, 2, 9, 2);
        this.legs2.setRotationPoint(2.0F, (float) (0 + byte0), 1.0F);
        this.wings = new Cuboid(52, 0);
        this.wings.method_1817(-1.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings.setRotationPoint(-3.0F, (float) (-4 + byte0), 0.0F);
        this.wings2 = new Cuboid(52, 0);
        this.wings2.method_1817(0.0F, -0.0F, -1.0F, 1, 8, 4);
        this.wings2.setRotationPoint(3.0F, (float) (-4 + byte0), 0.0F);
        this.neck = new Cuboid(44, 0);
        this.neck.method_1817(-1.0F, -6.0F, -1.0F, 2, 6, 2);
        this.neck.setRotationPoint(0.0F, (float) (-2 + byte0), -4.0F);
        this.feather1 = new Cuboid(30, 0);
        this.feather1.method_1818(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather1.setRotationPoint(0.0F, (float) (1 + byte0), 1.0F);
        this.feather2 = new Cuboid(30, 0);
        this.feather2.method_1818(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather2.setRotationPoint(0.0F, (float) (1 + byte0), 1.0F);
        this.feather3 = new Cuboid(30, 0);
        this.feather3.method_1818(-1.0F, -5.0F, 5.0F, 2, 1, 5, -0.3F);
        this.feather3.setRotationPoint(0.0F, (float) (1 + byte0), 1.0F);
        Cuboid var10000 = this.feather1;
        var10000.rotationPointY += 0.5F;
        var10000 = this.feather2;
        var10000.rotationPointY += 0.5F;
        var10000 = this.feather3;
        var10000.rotationPointY += 0.5F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.head.render(f5);
        this.jaw.render(f5);
        this.body.render(f5);
        this.legs.render(f5);
        this.legs2.render(f5);
        this.wings.render(f5);
        this.wings2.render(f5);
        this.neck.render(f5);
        this.feather1.render(f5);
        this.feather2.render(f5);
        this.feather3.render(f5);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = 3.141593F;
        this.head.pitch = f4 / 57.29578F;
        this.head.yaw = f3 / 57.29578F;
        this.jaw.pitch = this.head.pitch;
        this.jaw.yaw = this.head.yaw;
        this.body.pitch = 1.570796F;
        this.legs.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.legs2.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        if (f2 > 0.001F) {
            this.wings.rotationPointZ = -1.0F;
            this.wings2.rotationPointZ = -1.0F;
            this.wings.rotationPointY = 12.0F;
            this.wings2.rotationPointY = 12.0F;
            this.wings.pitch = 0.0F;
            this.wings2.pitch = 0.0F;
            this.wings.roll = f2;
            this.wings2.roll = -f2;
            this.legs.pitch = 0.6F;
            this.legs2.pitch = 0.6F;
        } else {
            this.wings.rotationPointZ = -3.0F;
            this.wings2.rotationPointZ = -3.0F;
            this.wings.rotationPointY = 14.0F;
            this.wings2.rotationPointY = 14.0F;
            this.wings.pitch = f6 / 2.0F;
            this.wings2.pitch = f6 / 2.0F;
            this.wings.roll = 0.0F;
            this.wings2.roll = 0.0F;
        }

        this.feather1.yaw = -0.375F;
        this.feather2.yaw = 0.0F;
        this.feather3.yaw = 0.375F;
        this.feather1.pitch = 0.25F;
        this.feather2.pitch = 0.25F;
        this.feather3.pitch = 0.25F;
        this.neck.pitch = 0.0F;
        this.neck.yaw = this.head.yaw;
        Cuboid var10000 = this.jaw;
        var10000.pitch += 0.35F;
    }
}
