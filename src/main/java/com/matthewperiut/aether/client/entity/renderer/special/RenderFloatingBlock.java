package com.matthewperiut.aether.client.entity.renderer.special;

import com.matthewperiut.aether.entity.special.EntityFloatingBlock;
import net.minecraft.block.Block;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RenderFloatingBlock extends EntityRenderer {
    private static BlockRenderManager renderBlocks;

    public RenderFloatingBlock() {
        renderBlocks = new BlockRenderManager();
        this.shadowRadius = 0.5F;
    }

    public void func_156_a(EntityFloatingBlock entityFloatingBlock, double d, double d1, double d2, float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        this.bindTexture("/terrain.png");
        Block block = Block.BLOCKS[entityFloatingBlock.blockID];
        World world = entityFloatingBlock.getWorld();
        GL11.glDisable(2896);
        renderBlockFallingSand(block, world, MathHelper.floor(entityFloatingBlock.x), MathHelper.floor(entityFloatingBlock.y), MathHelper.floor(entityFloatingBlock.z), entityFloatingBlock.metadata);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }

    public static void renderBlockFallingSand(Block block, World world, int i, int j, int k, int meta) {
        int l = block.getColor(meta);
        float red = (float) (l >> 16 & 255) / 255.0F;
        float green = (float) (l >> 8 & 255) / 255.0F;
        float blue = (float) (l & 255) / 255.0F;
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.startQuads();
        float f4 = block.getLuminance(world, i, j, k);
        float f5 = block.getLuminance(world, i, j - 1, k);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f * f5 * red, f * f5 * green, f * f5 * blue);
        renderBlocks.renderBottomFace(block, -0.5, -0.5, -0.5, block.getTexture(0, meta));
        f5 = block.getLuminance(world, i, j + 1, k);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f1 * f5 * red, f1 * f5 * green, f1 * f5 * blue);
        renderBlocks.renderTopFace(block, -0.5, -0.5, -0.5, block.getTexture(1, meta));
        f5 = block.getLuminance(world, i, j, k - 1);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f2 * f5 * red, f2 * f5 * green, f2 * f5 * blue);
        renderBlocks.renderEastFace(block, -0.5, -0.5, -0.5, block.getTexture(2, meta));
        f5 = block.getLuminance(world, i, j, k + 1);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f2 * f5 * red, f2 * f5 * green, f2 * f5 * blue);
        renderBlocks.renderWestFace(block, -0.5, -0.5, -0.5, block.getTexture(3, meta));
        f5 = block.getLuminance(world, i - 1, j, k);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f3 * f5 * red, f3 * f5 * green, f3 * f5 * blue);
        renderBlocks.renderNorthFace(block, -0.5, -0.5, -0.5, block.getTexture(4, meta));
        f5 = block.getLuminance(world, i + 1, j, k);
        if (f5 < f4) {
            f5 = f4;
        }

        tessellator.color(f3 * f5 * red, f3 * f5 * green, f3 * f5 * blue);
        renderBlocks.renderSouthFace(block, -0.5, -0.5, -0.5, block.getTexture(5, meta));
        tessellator.draw();
    }

    public void render(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.func_156_a((EntityFloatingBlock) entity, d, d1, d2, f, f1);
    }
}
