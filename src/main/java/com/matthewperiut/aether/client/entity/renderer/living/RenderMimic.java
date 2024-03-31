package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelMimic;
import com.matthewperiut.aether.entity.living.EntityMimic;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderMimic extends EntityRenderer {
    private ModelMimic model = new ModelMimic();

    public RenderMimic() {
    }

    public void render(EntityMimic entityMimic, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.bindTexture("aether:stationapi/textures/mobs/Mimic1.png");
        this.model.render1(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, entityMimic);
        this.bindTexture("aether:stationapi/textures/mobs/Mimic2.png");
        this.model.render2(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, entityMimic);
        GL11.glPopMatrix();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.render((EntityMimic) entity, d, d1, d2, f, f1);
    }
}
