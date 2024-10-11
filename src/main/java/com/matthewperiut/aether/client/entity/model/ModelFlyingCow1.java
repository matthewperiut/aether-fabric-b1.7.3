package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class ModelFlyingCow1 extends QuadrupedEntityModel {
    ModelPart udders;
    ModelPart horn1;
    ModelPart horn2;

    public ModelFlyingCow1() {
        super(12, 0.0F);
        this.head = new ModelPart(0, 0);
        this.head.addCuboid(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setPivot(0.0F, 4.0F, -8.0F);
        this.horn1 = new ModelPart(22, 0);
        this.horn1.addCuboid(-4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn1.setPivot(0.0F, 3.0F, -7.0F);
        this.horn2 = new ModelPart(22, 0);
        this.horn2.addCuboid(3.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn2.setPivot(0.0F, 3.0F, -7.0F);
        this.udders = new ModelPart(52, 0);
        this.udders.addCuboid(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
        this.udders.setPivot(0.0F, 14.0F, 6.0F);
        this.udders.pitch = 1.570796F;
        this.body = new ModelPart(18, 4);
        this.body.addCuboid(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.body.setPivot(0.0F, 5.0F, 2.0F);
        --this.rightHindLeg.pivotX;
        ++this.leftHindLeg.pivotX;
        ModelPart var10000 = this.rightHindLeg;
        var10000.pivotZ += 0.0F;
        var10000 = this.leftHindLeg;
        var10000.pivotZ += 0.0F;
        --this.rightFrontLeg.pivotX;
        ++this.leftFrontLeg.pivotX;
        --this.rightFrontLeg.pivotZ;
        --this.leftFrontLeg.pivotZ;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(f, f1, f2, f3, f4, f5);
        this.horn1.render(f5);
        this.horn2.render(f5);
        this.udders.render(f5);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        super.setAngles(f, f1, f2, f3, f4, f5);
        this.horn1.yaw = this.head.yaw;
        this.horn1.pitch = this.head.pitch;
        this.horn2.yaw = this.head.yaw;
        this.horn2.pitch = this.head.pitch;
    }
}
