package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.entity.projectile.EntityNotchWave;
import com.matthewperiut.aether.entity.projectile.EntityZephyrSnowball;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.util.Identifier;
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
        Atlas.Sprite snowballSprite = Atlases.getGuiItems().getTexture(Identifier.of("minecraft:item/snowball"));
        this.bindTexture("/gui/items.png");
        Tessellator tessellator = Tessellator.INSTANCE;
        double minU = snowballSprite.getStartU();
        double maxU = snowballSprite.getEndU();
        double minV = snowballSprite.getStartV();
        double maxV = snowballSprite.getEndV();
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