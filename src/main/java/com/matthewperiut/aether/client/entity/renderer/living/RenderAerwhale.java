package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelAerwhale;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderAerwhale extends EntityRenderer {
    private EntityModel model = new ModelAerwhale();

    public RenderAerwhale() {
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        this.bindTexture("aether:stationapi/textures/mobs/Mob_Aerwhale.png");
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(90.0F - entity.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - entity.pitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(5.0F, 5.0F, 5.0F);
        this.model.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
