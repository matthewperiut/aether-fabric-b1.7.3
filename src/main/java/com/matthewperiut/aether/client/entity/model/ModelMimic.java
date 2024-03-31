package com.matthewperiut.aether.client.entity.model;

import com.matthewperiut.aether.entity.living.EntityMimic;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelMimic extends EntityModel {
    Cuboid box = new Cuboid(0, 0);
    Cuboid boxLid;
    Cuboid leftLeg;
    Cuboid rightLeg;

    public ModelMimic() {
        this.box.method_1817(-8.0F, 0.0F, -8.0F, 16, 10, 16);
        this.box.setRotationPoint(0.0F, -24.0F, 0.0F);
        this.boxLid = new Cuboid(16, 10);
        this.boxLid.method_1817(0.0F, 0.0F, 0.0F, 16, 6, 16);
        this.boxLid.setRotationPoint(-8.0F, -24.0F, 8.0F);
        this.leftLeg = new Cuboid(0, 0);
        this.leftLeg.method_1817(-3.0F, 0.0F, -3.0F, 6, 15, 6);
        this.leftLeg.setRotationPoint(-4.0F, -15.0F, 0.0F);
        this.rightLeg = new Cuboid(0, 0);
        this.rightLeg.method_1817(-3.0F, 0.0F, -3.0F, 6, 15, 6);
        this.rightLeg.setRotationPoint(4.0F, -15.0F, 0.0F);
    }

    public void render1(float f, float f1, float f2, float f3, float f4, float f5, EntityMimic mimic) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.boxLid.pitch = 3.1415927F - mimic.mouth;
        this.rightLeg.pitch = mimic.legs;
        this.leftLeg.pitch = -mimic.legs;
        this.box.render(f5);
    }

    public void render2(float f, float f1, float f2, float f3, float f4, float f5, EntityMimic mimic) {
        this.boxLid.render(f5);
        this.leftLeg.render(f5);
        this.rightLeg.render(f5);
    }
}
