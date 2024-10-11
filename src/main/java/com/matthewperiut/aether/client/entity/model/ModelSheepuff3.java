package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class ModelSheepuff3 extends QuadrupedEntityModel {
    public ModelSheepuff3() {
        super(12, 0.0F);
        this.head = new ModelPart(0, 0);
        this.head.addCuboid(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.head.setPivot(0.0F, 6.0F, -8.0F);
        this.body = new ModelPart(28, 8);
        this.body.addCuboid(-4.0F, -8.0F, -7.0F, 8, 16, 6, 3.75F);
        this.body.setPivot(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.rightHindLeg = new ModelPart(0, 16);
        this.rightHindLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.rightHindLeg.setPivot(-3.0F, 12.0F, 7.0F);
        this.leftHindLeg = new ModelPart(0, 16);
        this.leftHindLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leftHindLeg.setPivot(3.0F, 12.0F, 7.0F);
        this.rightFrontLeg = new ModelPart(0, 16);
        this.rightFrontLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.rightFrontLeg.setPivot(-3.0F, 12.0F, -5.0F);
        this.leftFrontLeg = new ModelPart(0, 16);
        this.leftFrontLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leftFrontLeg.setPivot(3.0F, 12.0F, -5.0F);
    }
}
