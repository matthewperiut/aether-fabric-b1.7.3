package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelFlyingCow2;
import com.matthewperiut.aether.entity.living.EntityFlyingCow;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class RenderFlyingCow extends LivingEntityRenderer {
    private EntityModel wingmodel;

    public RenderFlyingCow(EntityModel modelbase, EntityModel modelbase1, float f) {
        super(modelbase, f);
        this.setModel(modelbase1);
        this.wingmodel = modelbase1;
    }

    protected boolean setWoolColorAndRender(EntityFlyingCow flyingcow, int i, float f) {
        if (i == 0) {
            this.bindTexture("aether:stationapi/textures/mobs/Mob_FlyingPigWings.png");
            ModelFlyingCow2.flyingcow = flyingcow;
            return true;
        } else {
            return false;
        }
    }

    protected boolean render(LivingEntity entityliving, int i, float f) {
        return this.setWoolColorAndRender((EntityFlyingCow) entityliving, i, f);
    }
}
