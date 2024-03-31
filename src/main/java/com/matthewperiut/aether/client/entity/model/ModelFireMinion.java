package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelFireMinion extends BipedEntityModel {
    public Cuboid bipedBody2;
    public Cuboid bipedBody3;
    public Cuboid bipedBody4;
    public Cuboid bipedRightArm2;
    public Cuboid bipedLeftArm2;
    public Cuboid bipedRightArm3;
    public Cuboid bipedLeftArm3;

    public ModelFireMinion() {
        this(0.0F);
    }

    public ModelFireMinion(float f) {
        this(f, 0.0F);
    }

    public ModelFireMinion(float f, float f1) {
        this.swingingLeft = false;
        this.swingingRight = false;
        this.sneaking = false;
        this.head = new Cuboid(0, 0);
        this.head.method_1818(-4.0F, -8.0F, -3.0F, 8, 5, 7, f);
        this.head.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.hat = new Cuboid(32, 0);
        this.hat.method_1818(-4.0F, -3.0F, -4.0F, 8, 3, 8, f);
        this.hat.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.torso = new Cuboid(0, 12);
        this.torso.method_1818(-5.0F, 0.0F, -2.5F, 10, 6, 5, f);
        this.torso.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody2 = new Cuboid(0, 23);
        this.bipedBody2.method_1818(-4.5F, 6.0F, -2.0F, 9, 5, 4, f);
        this.bipedBody2.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody3 = new Cuboid(30, 27);
        this.bipedBody3.method_1818(-4.5F, 11.0F, -2.0F, 5, 1, 4, f + 0.5F);
        this.bipedBody3.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody4 = new Cuboid(30, 27);
        this.bipedBody4.method_1818(-0.5F, 11.0F, -2.0F, 5, 1, 4, f + 0.5F);
        this.bipedBody4.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.rightArm = new Cuboid(30, 11);
        this.rightArm.method_1818(-2.5F, -2.5F, -2.5F, 5, 5, 5, f + 0.5F);
        this.rightArm.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.bipedRightArm2 = new Cuboid(30, 11);
        this.bipedRightArm2.method_1818(-2.5F, 2.5F, -2.5F, 5, 10, 5, f);
        this.bipedRightArm2.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.bipedRightArm3 = new Cuboid(30, 26);
        this.bipedRightArm3.method_1818(-2.5F, 7.5F, -2.5F, 5, 1, 5, f + 0.25F);
        this.bipedRightArm3.setRotationPoint(-8.0F, 2.0F + f1, 0.0F);
        this.leftArm = new Cuboid(30, 11);
        this.leftArm.mirror = true;
        this.leftArm.method_1818(-2.5F, -2.5F, -2.5F, 5, 5, 5, f + 0.5F);
        this.leftArm.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
        this.bipedLeftArm2 = new Cuboid(30, 11);
        this.bipedLeftArm2.mirror = true;
        this.bipedLeftArm2.method_1818(-2.5F, 2.5F, -2.5F, 5, 10, 5, f);
        this.bipedLeftArm2.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
        this.bipedLeftArm3 = new Cuboid(30, 26);
        this.bipedLeftArm3.mirror = true;
        this.bipedLeftArm3.method_1818(-2.5F, 7.5F, -2.5F, 5, 1, 5, f + 0.25F);
        this.bipedLeftArm3.setRotationPoint(8.0F, 2.0F + f1, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glTranslatef(0.0F, -0.25F, 0.0F);
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.head.render(f5);
        this.hat.render(f5);
        this.torso.render(f5);
        this.bipedBody2.render(f5);
        this.bipedBody3.render(f5);
        this.bipedBody4.render(f5);
        this.rightArm.render(f5);
        this.bipedRightArm2.render(f5);
        this.bipedRightArm3.render(f5);
        this.leftArm.render(f5);
        this.bipedLeftArm2.render(f5);
        this.bipedLeftArm3.render(f5);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.yaw = f3 / 57.29578F;
        this.head.pitch = f4 / 57.29578F;
        this.hat.yaw = this.head.yaw;
        this.hat.pitch = this.head.pitch;
        this.rightArm.pitch = 0.0F;
        this.leftArm.pitch = 0.0F;
        this.rightArm.roll = 0.0F;
        this.leftArm.roll = 0.0F;
        if (this.swingingLeft) {
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.3141593F;
        }

        if (this.swingingRight) {
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.3141593F;
        }

        this.rightArm.yaw = 0.0F;
        this.leftArm.yaw = 0.0F;
        Cuboid var10000;
        if (this.handSwingProgress > -9990.0F) {
            float f6 = this.handSwingProgress;
            this.torso.yaw = MathHelper.sin(MathHelper.sqrt(f6) * 3.141593F * 2.0F) * 0.2F;
            var10000 = this.rightArm;
            var10000.yaw += this.torso.yaw;
            var10000 = this.leftArm;
            var10000.yaw += this.torso.yaw;
            var10000 = this.leftArm;
            var10000.pitch += this.torso.yaw;
            f6 = 1.0F - this.handSwingProgress;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(this.handSwingProgress * 3.141593F) * -(this.head.pitch - 0.7F) * 0.75F;
            var10000 = this.rightArm;
            var10000.pitch = (float) ((double) var10000.pitch - ((double) f7 * 1.2 + (double) f8));
            var10000 = this.rightArm;
            var10000.yaw += this.torso.yaw * 2.0F;
            this.rightArm.roll = MathHelper.sin(this.handSwingProgress * 3.141593F) * -0.4F;
        }

        var10000 = this.rightArm;
        var10000.roll += MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        var10000 = this.leftArm;
        var10000.roll -= MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
        var10000 = this.rightArm;
        var10000.pitch += MathHelper.sin(f2 * 0.067F) * 0.05F;
        var10000 = this.leftArm;
        var10000.pitch -= MathHelper.sin(f2 * 0.067F) * 0.05F;
        this.bipedBody4.pitch = this.bipedBody3.pitch = this.bipedBody2.pitch = this.torso.pitch;
        this.bipedBody4.yaw = this.bipedBody3.yaw = this.bipedBody2.yaw = this.torso.yaw;
        this.bipedLeftArm3.pitch = this.bipedLeftArm2.pitch = this.leftArm.pitch;
        this.bipedLeftArm3.yaw = this.bipedLeftArm2.yaw = this.leftArm.yaw;
        this.bipedLeftArm3.roll = this.bipedLeftArm2.roll = this.leftArm.roll;
        this.bipedRightArm3.pitch = this.bipedRightArm2.pitch = this.rightArm.pitch;
        this.bipedRightArm3.yaw = this.bipedRightArm2.yaw = this.rightArm.yaw;
        this.bipedRightArm3.roll = this.bipedRightArm2.roll = this.rightArm.roll;
    }
}
