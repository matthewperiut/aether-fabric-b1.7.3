package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntityMoa;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderMoa extends LivingEntityRenderer {
    public RenderMoa(EntityModel modelbase, float f) {
        super(modelbase, f);
    }

    public void renderChicken(EntityMoa entitymoa, double d, double d1, double d2, float f, float f1) {
        super.render(entitymoa, d, d1, d2, f, f1);
    }

    protected float getWingRotation(EntityMoa entitymoa, float f) {
        float f1 = entitymoa.field_756_e + (entitymoa.field_752_b - entitymoa.field_756_e) * f;
        float f2 = entitymoa.field_757_d + (entitymoa.destPos - entitymoa.field_757_d) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    protected float method_828(LivingEntity entityliving, float f) {
        return this.getWingRotation((EntityMoa) entityliving, f);
    }

    public void render(LivingEntity entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderChicken((EntityMoa) entityliving, d, d1, d2, f, f1);
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderChicken((EntityMoa) entity, d, d1, d2, f, f1);
    }

    protected void scalemoa() {
        GL11.glScalef(1.8F, 1.8F, 1.8F);
    }

    protected void method_823(LivingEntity entityliving, float f) {
        if (!(entityliving instanceof EntityMoa) || !((EntityMoa) entityliving).baby) {
            this.scalemoa();
        }
    }
}
