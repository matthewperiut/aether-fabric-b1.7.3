package com.matthewperiut.aether.client.entity.model;

import com.matthewperiut.aether.entity.living.EntityMimic;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelMimic extends EntityModel {
    ModelPart box = new ModelPart(0, 0);
    ModelPart boxLid;
    ModelPart leftLeg;
    ModelPart rightLeg;

    public ModelMimic() {
        this.box.addCuboid(-8.0F, 0.0F, -8.0F, 16, 10, 16);
        this.box.setPivot(0.0F, -24.0F, 0.0F);
        this.boxLid = new ModelPart(16, 10);
        this.boxLid.addCuboid(0.0F, 0.0F, 0.0F, 16, 6, 16);
        this.boxLid.setPivot(-8.0F, -24.0F, 8.0F);
        this.leftLeg = new ModelPart(0, 0);
        this.leftLeg.addCuboid(-3.0F, 0.0F, -3.0F, 6, 15, 6);
        this.leftLeg.setPivot(-4.0F, -15.0F, 0.0F);
        this.rightLeg = new ModelPart(0, 0);
        this.rightLeg.addCuboid(-3.0F, 0.0F, -3.0F, 6, 15, 6);
        this.rightLeg.setPivot(4.0F, -15.0F, 0.0F);
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
