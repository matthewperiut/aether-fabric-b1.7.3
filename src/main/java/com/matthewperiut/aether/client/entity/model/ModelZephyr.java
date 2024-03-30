package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelZephyr extends EntityModel {
    Cuboid body;

    public ModelZephyr() {
        byte byte0 = -16;
        this.body = new Cuboid(0, 0);
        this.body.method_1817(-8.0F, -4.0F, -8.0F, 10, 7, 12);
        Cuboid var10000 = this.body;
        var10000.rotationPointY += (float) (24 + byte0);
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        this.setAngles(f, f1, f2, f3, f4, f5);
        this.body.render(f5);
    }
}
