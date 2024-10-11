package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.entity.living.EntitySheepuff;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import org.lwjgl.opengl.GL11;

public class RenderSheepuff extends LivingEntityRenderer {
    private EntityModel wool;
    private EntityModel puffed;

    public RenderSheepuff(EntityModel modelbase, EntityModel modelbase1, EntityModel modelbase2, float f) {
        super(modelbase1, f);
        this.setDecorationModel(modelbase);
        this.wool = modelbase;
        this.puffed = modelbase2;
    }

    protected boolean setWoolColorAndRender(EntitySheepuff entitysheep, int i, float f) {
        if (i == 0 && !entitysheep.getSheared()) {
            if (entitysheep.getPuffed()) {
                this.setDecorationModel(this.puffed);
                this.bindTexture("aether:stationapi/textures/mobs/sheepuff_fur.png");
            } else {
                this.setDecorationModel(this.wool);
                this.bindTexture("aether:stationapi/textures/mobs/sheepuff_fur.png");
            }

            float f1 = entitysheep.getBrightnessAtEyes(f);
            int j = entitysheep.getFleeceColor();
            GL11.glColor3f(f1 * SheepEntity.COLORS[j][0], f1 * SheepEntity.COLORS[j][1], f1 * SheepEntity.COLORS[j][2]);
            return true;
        } else {
            return false;
        }
    }

    protected boolean bindTexture(LivingEntity entityliving, int i, float f) {
        return this.setWoolColorAndRender((EntitySheepuff) entityliving, i, f);
    }
}
