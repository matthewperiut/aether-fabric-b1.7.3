package com.matthewperiut.aether.client.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelAerwhale extends EntityModel {
    Cuboid body;
    Cuboid body2 = new Cuboid(0, 0);
    Cuboid body3;
    Cuboid fin1;
    Cuboid fin2;
    Cuboid fin3;
    Cuboid fin4;

    public ModelAerwhale() {
        this.body2.method_1817(-2.5F, -2.5F, -2.5F, 5, 5, 5);
        this.body3 = new Cuboid(0, 10);
        this.body3.method_1817(-1.5F, -1.5F, 2.5F, 3, 3, 4);
        this.fin1 = new Cuboid(0, 17);
        this.fin1.method_1817(-7.5F, -0.5F, 2.5F, 8, 1, 4);
        this.fin2 = new Cuboid(0, 17);
        this.fin2.method_1817(-0.5F, -0.5F, 2.5F, 8, 1, 4);
        this.fin3 = new Cuboid(0, 22);
        this.fin3.method_1817(-7.5F, 1.5F, -6.5F, 4, 1, 2);
        this.fin4 = new Cuboid(0, 22);
        this.fin4.method_1817(3.5F, 1.5F, -6.5F, 4, 1, 2);
        this.body = new Cuboid(20, 0);
        this.body.method_1817(-3.5F, -3.5F, -12.5F, 7, 6, 10);
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
