package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelFlyingPig2;
import com.matthewperiut.aether.entity.living.EntityPhyg;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class RenderPhyg extends LivingEntityRenderer {
    private EntityModel wingmodel;

    public RenderPhyg(EntityModel modelbase, EntityModel modelbase1, float f) {
        super(modelbase, f);
        this.setDecorationModel(modelbase1);
        this.wingmodel = modelbase1;
    }

    protected boolean setWoolColorAndRender(EntityPhyg pig, int i, float f) {
        if (i == 0) {
            this.bindTexture("aether:stationapi/textures/mobs/Mob_FlyingPigWings.png");
            ModelFlyingPig2.pig = pig;
            return true;
        } else {
            return false;
        }
    }

    protected boolean bindTexture(LivingEntity entityliving, int i, float f) {
        return this.setWoolColorAndRender((EntityPhyg) entityliving, i, f);
    }
}
