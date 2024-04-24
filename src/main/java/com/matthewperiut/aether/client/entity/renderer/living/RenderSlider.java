package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntitySlider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderSlider extends LivingEntityRenderer {
    public RenderSlider(EntityModel ms, float f) {
        super(ms, f);
        this.model = ms;
    }

    protected void method_823(LivingEntity entityliving, float f) {
        EntitySlider e1 = (EntitySlider) entityliving;
        if (e1.harvey > 0.01F) {
            GL11.glRotatef(e1.harvey * -30.0F, (float) e1.rennis, 0.0F, (float) e1.dennis);
        }

    }

    protected boolean setSliderEyeBrightness(EntitySlider slider, int i, float f) {
        if (i != 0) {
            return false;
        } else {
            if (slider.awake) {
                if (slider.criticalCondition()) {
                    this.bindTexture("aether:stationapi/textures/mobs/sliderAwakeGlow_red.png");
                } else {
                    this.bindTexture("aether:stationapi/textures/mobs/sliderAwakeGlow.png");
                }
            } else {
                this.bindTexture("aether:stationapi/textures/mobs/sliderSleepGlow.png");
            }

            float f1 = (1.0F - slider.getBrightnessAtEyes(1.0F)) * 0.5F;
            GL11.glEnable(3042);
            GL11.glDisable(3008);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return true;
        }
    }

    protected boolean render(LivingEntity entityliving, int i, float f) {
        return this.setSliderEyeBrightness((EntitySlider) entityliving, i, f);
    }
}
