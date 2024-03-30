package com.matthewperiut.aether.client.entity.renderer.projectile;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.entity.special.EntityCloudParachute;
import net.minecraft.client.render.block.BlockRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderCloudParachute extends EntityRenderer {
    private BlockRenderer renderBlocks = new BlockRenderer();

    public RenderCloudParachute() {
        this.field_2678 = 0.5F;
    }

    public void renderCloud(EntityCloudParachute entitycloud, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        this.bindTexture("/terrain.png");
        GL11.glDisable(2896);
        RenderFloatingBlock.renderBlockFallingSand(AetherBlocks.Aercloud, entitycloud.getWorld(), MathHelper.floor(entitycloud.x), MathHelper.floor(entitycloud.y), MathHelper.floor(entitycloud.z), entitycloud.gold ? 2 : 0);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderCloud((EntityCloudParachute) entity, d, d1, d2, f, f1);
    }
}
