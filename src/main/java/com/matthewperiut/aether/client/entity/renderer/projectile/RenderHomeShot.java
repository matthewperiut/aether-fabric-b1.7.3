package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.client.entity.model.ModelHomeShot;
import com.matthewperiut.aether.entity.projectile.EntityHomeShot;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class RenderHomeShot extends LivingEntityRenderer {
    private ModelHomeShot shotty;

    public RenderHomeShot(EntityModel ms, float f) {
        super(ms, f);
        this.shotty = (ModelHomeShot) ms;
    }

    public void method_823(LivingEntity el, float f) {
        EntityHomeShot hs = (EntityHomeShot) el;

        for (int i = 0; i < 3; ++i) {
            this.shotty.sinage[i] = hs.sinage[i];
        }

    }
}
