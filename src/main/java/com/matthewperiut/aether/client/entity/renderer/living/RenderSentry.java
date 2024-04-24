package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntitySentry;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderSentry extends LivingEntityRenderer {
    public RenderSentry(EntityModel modelbase, float f) {
        super(modelbase, f);
        this.setModel(modelbase);
    }

    protected void method_823(LivingEntity entityliving, float f) {
        float f1 = 1.75F;
        GL11.glScalef(f1, f1, f1);
    }

    protected boolean a(EntitySentry sentry, int i, float f) {
        if (i != 0) {
            return false;
        } else if (i != 0) {
            return false;
        } else if (sentry.active) {
            this.bindTexture("aether:stationapi/textures/mobs/SentryEye.png");
            float f1 = (1.0F - sentry.getBrightnessAtEyes(1.0F)) * 0.75F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        } else {
            return false;
        }
    }

    protected boolean render(LivingEntity entityliving, int i, float f) {
        return this.a((EntitySentry) entityliving, i, f);
    }
}
