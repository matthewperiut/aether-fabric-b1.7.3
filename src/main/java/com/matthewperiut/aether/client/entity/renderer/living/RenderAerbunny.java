package com.matthewperiut.aether.client.entity.renderer.living;

import com.matthewperiut.aether.client.entity.model.ModelAerbunny;
import com.matthewperiut.aether.entity.living.EntityAerbunny;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;

import java.util.WeakHashMap;

public class RenderAerbunny extends LivingEntityRenderer {
    public ModelAerbunny mb;
    // Smooth tilt per entity
    private static final WeakHashMap<EntityAerbunny, Float> smoothTilt = new WeakHashMap<>();

    public RenderAerbunny(EntityModel modelbase, float f) {
        super(modelbase, f);
        this.mb = (ModelAerbunny) modelbase;
    }

    protected void rotAerbunny(EntityAerbunny entitybunny) {
        if (!entitybunny.getSyncedOnGround() && !entitybunny.isRidingPlayer()) {
            // Read server-synced velocityY from DataTracker slot 18
            float serverVelY = Float.intBitsToFloat(entitybunny.getDataTracker().getInt(18));

            // Compute target tilt
            float targetTilt;
            if (serverVelY > 0.5F) {
                targetTilt = 15.0F;
            } else if (serverVelY < -0.5F) {
                targetTilt = -15.0F;
            } else {
                targetTilt = serverVelY * 30.0F;
            }

            // Smooth lerp
            float prev = smoothTilt.getOrDefault(entitybunny, 0.0F);
            float tilt = prev + (targetTilt - prev) * 0.3F;
            if (Math.abs(tilt) < 0.5F) tilt = 0.0F;
            smoothTilt.put(entitybunny, tilt);

            GL11.glRotatef(tilt, -1.0F, 0.0F, 0.0F);
        } else {
            smoothTilt.put(entitybunny, 0.0F);
        }

        this.mb.puffiness = entitybunny.puffiness;
    }

    protected void applyScale(LivingEntity entityliving, float f) {
        this.rotAerbunny((EntityAerbunny) entityliving);
    }
}
