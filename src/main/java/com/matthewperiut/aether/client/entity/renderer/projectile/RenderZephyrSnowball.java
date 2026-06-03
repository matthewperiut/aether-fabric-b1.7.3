package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.entity.projectile.EntityNotchWave;
import com.matthewperiut.aether.entity.projectile.EntityZephyrSnowball;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import com.periut.retroapi.client.texture.AtlasExpander;
import org.lwjgl.opengl.GL11;

public class RenderZephyrSnowball extends EntityRenderer {
    public RenderZephyrSnowball() {
    }

    public void func_4012_a(EntityZephyrSnowball entityZephyrSnowball, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glEnable(32826);
        float size = 2.0F;
        GL11.glScalef(size, size, size);
        this.bindTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        // Vanilla 16x16-sprite grid UVs, scaled to RetroAPI's (possibly expanded) item atlas.
        int tex = Item.SNOWBALL.getTextureId(0);
        double atlas = AtlasExpander.itemAtlasSize;
        double minU = (tex % 16) * 16 / atlas;
        double maxU = minU + 16 / atlas;
        double minV = (tex / 16) * 16 / atlas;
        double maxV = minV + 16 / atlas;
        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.dispatcher.pitch, 1.0F, 0.0F, 0.0F);
        tessellator.startQuads();
        tessellator.normal(0.0F, 1.0F, 0.0F);
        tessellator.vertex(-0.5F, -0.25F, 0.0, minU, maxV);
        tessellator.vertex(0.5F, -0.25F, 0.0, maxU, maxV);
        tessellator.vertex(0.5F,  0.75F, 0.0, maxU, minV);
        tessellator.vertex(-0.5F, 0.75F, 0.0, minU, minV);
        tessellator.draw();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_4012_a((EntityZephyrSnowball) entity, d, d1, d2, f, f1);
    }
}