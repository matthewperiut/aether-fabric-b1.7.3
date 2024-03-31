//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class ModelSheepuff1 extends QuadrupedEntityModel {
    public ModelSheepuff1() {
        super(12, 0.0F);
        this.head = new Cuboid(0, 0);
        this.head.method_1818(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.torso = new Cuboid(28, 8);
        this.torso.method_1818(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
        this.torso.setRotationPoint(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.frontRightLeg = new Cuboid(0, 16);
        this.frontRightLeg.method_1818(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.frontRightLeg.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.frontLeftLeg = new Cuboid(0, 16);
        this.frontLeftLeg.method_1818(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.frontLeftLeg.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.backRightLeg = new Cuboid(0, 16);
        this.backRightLeg.method_1818(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.backRightLeg.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.backLeftLeg = new Cuboid(0, 16);
        this.backLeftLeg.method_1818(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.backLeftLeg.setRotationPoint(3.0F, 12.0F, -5.0F);
    }
}
