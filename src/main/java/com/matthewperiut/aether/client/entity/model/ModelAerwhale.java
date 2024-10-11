package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelAerwhale extends EntityModel {
    ModelPart body;
    ModelPart body2 = new ModelPart(0, 0);
    ModelPart body3;
    ModelPart fin1;
    ModelPart fin2;
    ModelPart fin3;
    ModelPart fin4;

    public ModelAerwhale() {
        this.body2.addCuboid(-2.5F, -2.5F, -2.5F, 5, 5, 5);
        this.body3 = new ModelPart(0, 10);
        this.body3.addCuboid(-1.5F, -1.5F, 2.5F, 3, 3, 4);
        this.fin1 = new ModelPart(0, 17);
        this.fin1.addCuboid(-7.5F, -0.5F, 2.5F, 8, 1, 4);
        this.fin2 = new ModelPart(0, 17);
        this.fin2.addCuboid(-0.5F, -0.5F, 2.5F, 8, 1, 4);
        this.fin3 = new ModelPart(0, 22);
        this.fin3.addCuboid(-7.5F, 1.5F, -6.5F, 4, 1, 2);
        this.fin4 = new ModelPart(0, 22);
        this.fin4.addCuboid(3.5F, 1.5F, -6.5F, 4, 1, 2);
        this.body = new ModelPart(20, 0);
        this.body.addCuboid(-3.5F, -3.5F, -12.5F, 7, 6, 10);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.body.render(f5);
        this.body2.render(f5);
        this.body3.render(f5);
        this.fin1.render(f5);
        this.fin2.render(f5);
        this.fin3.render(f5);
        this.fin4.render(f5);
    }
}
