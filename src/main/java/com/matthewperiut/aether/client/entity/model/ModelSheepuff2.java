package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

public class ModelSheepuff2 extends QuadrupedEntityModel {
    public ModelSheepuff2() {
        super(12, 0.0F);
        this.head = new Cuboid(0, 0);
        this.head.method_1818(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.torso = new Cuboid(28, 8);
        this.torso.method_1818(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
        this.torso.setRotationPoint(0.0F, 5.0F, 2.0F);
    }
}
