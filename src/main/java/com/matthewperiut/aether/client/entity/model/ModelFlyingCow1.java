package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class ModelFlyingCow1 extends QuadrupedEntityModel {
    Cuboid udders;
    Cuboid horn1;
    Cuboid horn2;

    public ModelFlyingCow1() {
        super(12, 0.0F);
        this.head = new Cuboid(0, 0);
        this.head.method_1818(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
        this.horn1 = new Cuboid(22, 0);
        this.horn1.method_1818(-4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn1.setRotationPoint(0.0F, 3.0F, -7.0F);
        this.horn2 = new Cuboid(22, 0);
        this.horn2.method_1818(3.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
        this.horn2.setRotationPoint(0.0F, 3.0F, -7.0F);
        this.udders = new Cuboid(52, 0);
        this.udders.method_1818(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
        this.udders.setRotationPoint(0.0F, 14.0F, 6.0F);
        this.udders.pitch = 1.570796F;
        this.torso = new Cuboid(18, 4);
        this.torso.method_1818(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
        this.torso.setRotationPoint(0.0F, 5.0F, 2.0F);
        --this.frontRightLeg.rotationPointX;
        ++this.frontLeftLeg.rotationPointX;
        Cuboid var10000 = this.frontRightLeg;
        var10000.rotationPointZ += 0.0F;
        var10000 = this.frontLeftLeg;
        var10000.rotationPointZ += 0.0F;
        --this.backRightLeg.rotationPointX;
        ++this.backLeftLeg.rotationPointX;
        --this.backRightLeg.rotationPointZ;
        --this.backLeftLeg.rotationPointZ;
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
