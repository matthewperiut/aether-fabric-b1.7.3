package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelValkyrie extends BipedEntityModel {
    public ModelPart bipedBody2;
    public ModelPart bipedRightArm2;
    public ModelPart bipedLeftArm2;
    public ModelPart wingLeft;
    public ModelPart wingRight;
    public ModelPart[] skirt;
    public ModelPart[] sword;
    public ModelPart[] strand;
    public ModelPart[] halo;
    public static final int swordParts = 5;
    public static final int skirtParts = 6;
    public static final int strandParts = 22;
    public static final int haloParts = 4;
    public float sinage;
    public boolean gonRound;
    public boolean halow;

    public ModelValkyrie() {
        this(0.0F);
    }

    public ModelValkyrie(float f) {
        this(f, 0.0F);
    }

    public ModelValkyrie(float f, float f1) {
        this.leftArmPose = false;
        this.rightArmPose = false;
        this.sneaking = false;
        this.head = new ModelPart(0, 0);
        this.head.addCuboid(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
        this.head.setPivot(0.0F, 0.0F + f1, 0.0F);
        this.body = new ModelPart(12, 16);
        this.body.addCuboid(-3.0F, 0.0F, -1.5F, 6, 12, 3, f);
        this.body.setPivot(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody2 = new ModelPart(12, 16);
        this.bipedBody2.addCuboid(-3.0F, 0.5F, -1.25F, 6, 5, 3, f + 0.75F);
        this.bipedBody2.setPivot(0.0F, 0.0F + f1, 0.0F);
        this.rightArm = new ModelPart(30, 16);
        this.rightArm.addCuboid(-3.0F, -1.5F, -1.5F, 3, 12, 3, f);
        this.rightArm.setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.leftArm = new ModelPart(30, 16);
        this.leftArm.mirror = true;
        this.leftArm.addCuboid(-1.0F, -1.5F, -1.5F, 3, 12, 3, f);
        this.leftArm.setPivot(5.0F, 1.5F + f1, 0.0F);
        this.bipedRightArm2 = new ModelPart(30, 16);
        this.bipedRightArm2.addCuboid(-3.0F, -1.5F, -1.5F, 3, 3, 3, f + 0.75F);
        this.bipedRightArm2.setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.bipedLeftArm2 = new ModelPart(30, 16);
        this.bipedLeftArm2.mirror = true;
        this.bipedLeftArm2.addCuboid(-1.0F, -1.5F, -1.5F, 3, 3, 3, f + 0.75F);
        this.bipedLeftArm2.setPivot(5.0F, 1.5F + f1, 0.0F);
        this.rightLeg = new ModelPart(0, 16);
        this.rightLeg.addCuboid(-2.0F, 0.0F, -1.5F, 3, 12, 3, f);
        this.rightLeg.setPivot(-1.0F, 12.0F + f1, 0.0F);
        this.leftLeg = new ModelPart(0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.addCuboid(-2.0F, 0.0F, -1.5F, 3, 12, 3, f);
        this.leftLeg.setPivot(2.0F, 12.0F + f1, 0.0F);
        this.sword = new ModelPart[5];
        this.sword[0] = new ModelPart(9, 16);
        this.sword[0].addCuboid(-2.5F, 8.0F, 1.5F, 2, 2, 1, f);
        this.sword[0].setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.sword[1] = new ModelPart(32, 10);
        this.sword[1].addCuboid(-3.0F, 6.5F, -2.75F, 3, 5, 1, f + 0.5F);
        this.sword[1].setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.sword[2] = new ModelPart(42, 18);
        this.sword[2].addCuboid(-2.0F, 7.5F, -12.5F, 1, 3, 10, f);
        this.sword[2].setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.sword[3] = new ModelPart(42, 18);
        this.sword[3].addCuboid(-2.0F, 7.5F, -22.5F, 1, 3, 10, f);
        this.sword[3].setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.sword[4] = new ModelPart(28, 17);
        this.sword[4].addCuboid(-2.0F, 8.5F, -23.5F, 1, 1, 1, f);
        this.sword[4].setPivot(-4.0F, 1.5F + f1, 0.0F);
        this.wingLeft = new ModelPart(24, 31);
        this.wingLeft.addCuboid(0.0F, -4.5F, 0.0F, 19, 8, 1, f);
        this.wingLeft.setPivot(0.5F, 4.5F + f1, 2.625F);
        this.wingRight = new ModelPart(24, 31);
        this.wingRight.mirror = true;
        this.wingRight.addCuboid(-19.0F, -4.5F, 0.0F, 19, 8, 1, f);
        this.wingRight.setPivot(-0.5F, 4.5F + f1, 2.625F);
        this.skirt = new ModelPart[6];
        this.skirt[0] = new ModelPart(0, 0);
        this.skirt[0].addCuboid(0.0F, 0.0F, -1.0F, 3, 6, 1, f);
        this.skirt[0].setPivot(-3.0F, 9.0F + f1, -1.5F);
        this.skirt[1] = new ModelPart(0, 0);
        this.skirt[1].addCuboid(0.0F, 0.0F, -1.0F, 3, 6, 1, f);
        this.skirt[1].setPivot(0.0F, 9.0F + f1, -1.5F);
        this.skirt[2] = new ModelPart(0, 0);
        this.skirt[2].addCuboid(0.0F, 0.0F, 0.0F, 3, 6, 1, f);
        this.skirt[2].setPivot(-3.0F, 9.0F + f1, 1.5F);
        this.skirt[3] = new ModelPart(0, 0);
        this.skirt[3].addCuboid(0.0F, 0.0F, 0.0F, 3, 6, 1, f);
        this.skirt[3].setPivot(0.0F, 9.0F + f1, 1.5F);
        this.skirt[4] = new ModelPart(55, 19);
        this.skirt[4].addCuboid(-1.0F, 0.0F, 0.0F, 1, 6, 3, f);
        this.skirt[4].setPivot(-3.0F, 9.0F + f1, -1.5F);
        this.skirt[5] = new ModelPart(55, 19);
        this.skirt[5].addCuboid(0.0F, 0.0F, 0.0F, 1, 6, 3, f);
        this.skirt[5].setPivot(3.0F, 9.0F + f1, -1.5F);
        this.strand = new ModelPart[22];

        for (int i = 0; i < 22; ++i) {
            this.strand[i] = new ModelPart(42 + i % 7, 17);
        }

        this.strand[0].addCuboid(-5.0F, -7.0F, -4.0F, 1, 3, 1, f);
        this.strand[0].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[1].addCuboid(4.0F, -7.0F, -4.0F, 1, 3, 1, f);
        this.strand[1].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[2].addCuboid(-5.0F, -7.0F, -3.0F, 1, 4, 1, f);
        this.strand[2].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[3].addCuboid(4.0F, -7.0F, -3.0F, 1, 4, 1, f);
        this.strand[3].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[4].addCuboid(-5.0F, -7.0F, -2.0F, 1, 4, 1, f);
        this.strand[4].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[5].addCuboid(4.0F, -7.0F, -2.0F, 1, 4, 1, f);
        this.strand[5].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[6].addCuboid(-5.0F, -7.0F, -1.0F, 1, 5, 1, f);
        this.strand[6].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[7].addCuboid(4.0F, -7.0F, -1.0F, 1, 5, 1, f);
        this.strand[7].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[8].addCuboid(-5.0F, -7.0F, 0.0F, 1, 5, 1, f);
        this.strand[8].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[9].addCuboid(4.0F, -7.0F, 0.0F, 1, 5, 1, f);
        this.strand[9].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[10].addCuboid(-5.0F, -7.0F, 1.0F, 1, 6, 1, f);
        this.strand[10].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[11].addCuboid(4.0F, -7.0F, 1.0F, 1, 6, 1, f);
        this.strand[11].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[12].addCuboid(-5.0F, -7.0F, 2.0F, 1, 7, 1, f);
        this.strand[12].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[13].addCuboid(4.0F, -7.0F, 2.0F, 1, 7, 1, f);
        this.strand[13].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[14].addCuboid(-5.0F, -7.0F, 3.0F, 1, 8, 1, f);
        this.strand[14].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[15].addCuboid(4.0F, -7.0F, 3.0F, 1, 8, 1, f);
        this.strand[15].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[16].addCuboid(-4.0F, -7.0F, 4.0F, 1, 9, 1, f);
        this.strand[16].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[17].addCuboid(3.0F, -7.0F, 4.0F, 1, 9, 1, f);
        this.strand[17].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[18] = new ModelPart(42, 17);
        this.strand[18].addCuboid(-3.0F, -7.0F, 4.0F, 3, 10, 1, f);
        this.strand[18].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[19] = new ModelPart(43, 17);
        this.strand[19].addCuboid(0.0F, -7.0F, 4.0F, 3, 10, 1, f);
        this.strand[19].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[20].addCuboid(-1.0F, -7.0F, -5.0F, 1, 2, 1, f);
        this.strand[20].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.strand[21].addCuboid(0.0F, -7.0F, -5.0F, 1, 3, 1, f);
        this.strand[21].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.halo = new ModelPart[4];
        this.halo[0] = new ModelPart(43, 9);
        this.halo[0].addCuboid(-2.5F, -11.0F, -3.5F, 5, 1, 1, f);
        this.halo[0].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.halo[1] = new ModelPart(43, 9);
        this.halo[1].addCuboid(-2.5F, -11.0F, 2.5F, 5, 1, 1, f);
        this.halo[1].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.halo[2] = new ModelPart(42, 11);
        this.halo[2].addCuboid(-3.5F, -11.0F, -2.5F, 1, 1, 5, f);
        this.halo[2].setPivot(0.0F, 0.0F + f1, 0.0F);
        this.halo[3] = new ModelPart(42, 11);
        this.halo[3].addCuboid(2.5F, -11.0F, -2.5F, 1, 1, 5, f);
        this.halo[3].setPivot(0.0F, 0.0F + f1, 0.0F);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.head.render(f5);
        this.body.render(f5);
        this.rightArm.render(f5);
        this.leftArm.render(f5);
        this.rightLeg.render(f5);
        this.leftLeg.render(f5);
        this.bipedBody2.render(f5);
        this.bipedRightArm2.render(f5);
        this.bipedLeftArm2.render(f5);
        this.wingLeft.render(f5);
        this.wingRight.render(f5);

        int i;
        for (i = 0; i < 5; ++i) {
            this.sword[i].render(f5);
        }

        for (i = 0; i < 6; ++i) {
            this.skirt[i].render(f5);
        }

        for (i = 0; i < 22; ++i) {
            this.strand[i].render(f5);
        }

        if (this.halow) {
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);

            for (i = 0; i < 4; ++i) {
                this.halo[i].render(f5);
            }

            GL11.glEnable(3008);
        }

    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.head.yaw = f3 / 57.29578F;
        this.head.pitch = f4 / 57.29578F;
        this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.rightArm.roll = 0.05F;
        this.leftArm.roll = -0.05F;
        this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;

        int i;
        for (i = 0; i < 22; ++i) {
            this.strand[i].yaw = this.head.yaw;
            this.strand[i].pitch = this.head.pitch;
        }

        for (i = 0; i < 4; ++i) {
            this.halo[i].yaw = this.head.yaw;
            this.halo[i].pitch = this.head.pitch;
        }

        ModelPart var10000;
        if (this.riding) {
            var10000 = this.rightArm;
            var10000.pitch += -0.6283185F;
            var10000 = this.leftArm;
            var10000.pitch += -0.6283185F;
            this.rightLeg.pitch = -1.256637F;
            this.leftLeg.pitch = -1.256637F;
            this.rightLeg.yaw = 0.3141593F;
            this.leftLeg.yaw = -0.3141593F;
        }

        if (this.leftArmPose) {
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.3141593F;
        }

        if (this.rightArmPose) {
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.3141593F;
        }

        this.rightArm.yaw = 0.0F;
        this.leftArm.yaw = 0.0F;
        if (this.handSwingProgress > -9990.0F) {
            float f6 = this.handSwingProgress;
            this.bipedBody2.yaw = this.body.yaw = MathHelper.sin(MathHelper.sqrt(f6) * 3.141593F * 2.0F) * 0.2F;
            var10000 = this.rightArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.pitch += this.body.yaw;
            f6 = 1.0F - this.handSwingProgress;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            float f7 = MathHelper.sin(f6 * 3.141593F);
            float f8 = MathHelper.sin(this.handSwingProgress * 3.141593F) * -(this.head.pitch - 0.7F) * 0.75F;
            var10000 = this.rightArm;
            var10000.pitch = (float) ((double) var10000.pitch - ((double) f7 * 1.2 + (double) f8));
            var10000 = this.rightArm;
            var10000.yaw += this.body.yaw * 2.0F;
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

        for (i = 0; i < 5; ++i) {
            this.sword[i].roll = this.rightArm.roll;
            this.sword[i].yaw = this.rightArm.yaw;
            this.sword[i].pitch = this.rightArm.pitch;
        }

        this.bipedRightArm2.roll = this.rightArm.roll;
        this.bipedRightArm2.yaw = this.rightArm.yaw;
        this.bipedRightArm2.pitch = this.rightArm.pitch;
        this.bipedLeftArm2.roll = this.leftArm.roll;
        this.bipedLeftArm2.pitch = this.leftArm.pitch;
        this.wingLeft.yaw = -0.2F;
        this.wingRight.yaw = 0.2F;
        this.wingLeft.roll = -0.125F;
        this.wingRight.roll = 0.125F;
        var10000 = this.wingLeft;
        var10000.yaw = (float) ((double) var10000.yaw + Math.sin((double) this.sinage) / 6.0);
        var10000 = this.wingRight;
        var10000.yaw = (float) ((double) var10000.yaw - Math.sin((double) this.sinage) / 6.0);
        var10000 = this.wingLeft;
        var10000.roll = (float) ((double) var10000.roll + Math.cos((double) this.sinage) / (double) (this.gonRound ? 8.0F : 3.0F));
        var10000 = this.wingRight;
        var10000.roll = (float) ((double) var10000.roll - Math.cos((double) this.sinage) / (double) (this.gonRound ? 8.0F : 3.0F));
        this.skirt[0].pitch = -0.2F;
        this.skirt[1].pitch = -0.2F;
        this.skirt[2].pitch = 0.2F;
        this.skirt[3].pitch = 0.2F;
        this.skirt[4].roll = 0.2F;
        this.skirt[5].roll = -0.2F;
        if (this.leftLeg.pitch < -0.3F) {
            var10000 = this.skirt[1];
            var10000.pitch += this.leftLeg.pitch + 0.3F;
            var10000 = this.skirt[2];
            var10000.pitch -= this.leftLeg.pitch + 0.3F;
        }

        if (this.leftLeg.pitch > 0.3F) {
            var10000 = this.skirt[3];
            var10000.pitch += this.leftLeg.pitch - 0.3F;
            var10000 = this.skirt[0];
            var10000.pitch -= this.leftLeg.pitch - 0.3F;
        }

    }
}
