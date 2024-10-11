package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelAerbunny;
import com.matthewperiut.aether.entity.living.EntityAerbunny;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

public class RenderAerbunny extends LivingEntityRenderer {
    public ModelAerbunny mb;

    public RenderAerbunny(EntityModel modelbase, float f) {
        super(modelbase, f);
        this.mb = (ModelAerbunny) modelbase;
    }

    protected void rotAerbunny(EntityAerbunny entitybunny) {
        if (!entitybunny.onGround && entitybunny.vehicle == null) {
            if (entitybunny.velocityY > 0.5) {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            } else if (entitybunny.velocityY < -0.5) {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitybunny.velocityY * 30.0), -1.0F, 0.0F, 0.0F);
            }
        }

        this.mb.puffiness = entitybunny.puffiness;
    }

    protected void applyScale(LivingEntity entityliving, float f) {
        this.rotAerbunny((EntityAerbunny) entityliving);
    }
}
