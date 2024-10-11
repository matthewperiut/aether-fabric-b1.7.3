package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelZephyr extends EntityModel {
    ModelPart body;

    public ModelZephyr() {
        byte byte0 = -16;
        this.body = new ModelPart(0, 0);
        this.body.addCuboid(-8.0F, -4.0F, -8.0F, 10, 7, 12);
        ModelPart var10000 = this.body;
        var10000.pivotY += (float) (24 + byte0);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.body.render(f5);
    }
}
