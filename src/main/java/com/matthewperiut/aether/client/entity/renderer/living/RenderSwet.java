//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntitySwet;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderSwet extends LivingEntityRenderer {
    private EntityModel field_22001_a;

    public RenderSwet(EntityModel modelbase, EntityModel modelbase1, float f) {
        super(modelbase, f);
        this.field_22001_a = modelbase1;
    }

    protected boolean a(EntitySwet entityswets, int i, float f) {
        if (i == 0) {
            this.setModel(this.field_22001_a);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return true;
        } else {
            if (i == 1) {
                GL11.glDisable(3042);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            return false;
        }
    }

    protected void a(EntitySwet entityswets, float f) {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;
        if (!entityswets.onGround) {
            if (entityswets.yVelocity > 0.8500000238418579) {
                f1 = 1.425F;
                f2 = 0.575F;
            } else if (entityswets.yVelocity < -0.8500000238418579) {
                f1 = 0.575F;
                f2 = 1.425F;
            } else {
                float f4 = (float) entityswets.yVelocity * 0.5F;
                f1 += f4;
                f2 -= f4;
            }
        }

        if (entityswets.passenger != null) {
            f3 = 1.5F + (entityswets.passenger.width + entityswets.passenger.height) * 0.75F;
        }

        GL11.glScalef(f2 * f3, f1 * f3, f2 * f3);
    }

    protected void method_823(LivingEntity entityliving, float f) {
        this.a((EntitySwet) entityliving, f);
    }

    protected boolean render(LivingEntity entityliving, int i, float f) {
        return this.a((EntitySwet) entityliving, i, f);
    }
}
