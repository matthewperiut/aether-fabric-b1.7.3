//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelValkyrie;
import com.matthewperiut.aether.entity.living.EntityValkyrie;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class RenderValkyrie extends BipedEntityRenderer {
    public ModelValkyrie mv1;

    public RenderValkyrie(BipedEntityModel model, float f) {
        super(model, f);
        this.mv1 = (ModelValkyrie) model;
    }

    protected void method_823(LivingEntity entityliving, float f) {
        EntityValkyrie v1 = (EntityValkyrie) entityliving;
        this.mv1.sinage = v1.sinage;
        this.mv1.gonRound = v1.onGround;
        this.mv1.halow = !v1.otherDimension();
    }
}
