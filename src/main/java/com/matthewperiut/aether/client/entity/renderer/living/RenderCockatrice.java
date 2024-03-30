//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntityCockatrice;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderCockatrice extends LivingEntityRenderer {
    public RenderCockatrice(EntityModel modelbase, float f) {
        super(modelbase, f);
    }

    public void renderChicken(EntityCockatrice entitybadmoa, double d, double d1, double d2, float f, float f1) {
        super.render(entitybadmoa, d, d1, d2, f, f1);
    }

    protected float getWingRotation(EntityCockatrice entitybadmoa, float f) {
        float f1 = entitybadmoa.field_756_e + (entitybadmoa.field_752_b - entitybadmoa.field_756_e) * f;
        float f2 = entitybadmoa.field_757_d + (entitybadmoa.destPos - entitybadmoa.field_757_d) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    protected float method_828(LivingEntity entityliving, float f) {
        return this.getWingRotation((EntityCockatrice) entityliving, f);
    }

    public void render(LivingEntity entityliving, double d, double d1, double d2, float f, float f1) {
        this.renderChicken((EntityCockatrice) entityliving, d, d1, d2, f, f1);
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderChicken((EntityCockatrice) entity, d, d1, d2, f, f1);
    }

    protected void scalemoa() {
        GL11.glScalef(1.8F, 1.8F, 1.8F);
    }

    protected void method_823(LivingEntity entityliving, float f) {
        this.scalemoa();
    }
}
