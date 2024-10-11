package com.matthewperiut.aether.entity.fx;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.world.World;

public class EntityCloudSmokeFX extends Particle {
    float field_671_a;

    public EntityCloudSmokeFX(World world, double x, double y, double z, double initialMotionX, double initialMotionY, double intialMotionZ, float size, float red, float blue, float green) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.velocityX *= 0.10000000149011612;
        this.velocityY *= 0.10000000149011612;
        this.velocityZ *= 0.10000000149011612;
        this.velocityX += initialMotionX;
        this.velocityY += initialMotionY;
        this.velocityZ += intialMotionZ;
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.scale *= 0.75F;
        this.scale *= size;
        this.field_671_a = this.scale;
        this.maxParticleAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.maxParticleAge = (int) ((float) this.maxParticleAge * size);
        this.noClip = false;
    }

    public void render(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = ((float) this.particleAge + f) / (float) this.maxParticleAge * 32.0F;
        if (f6 < 0.0F) {
            f6 = 0.0F;
        }

        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.scale = this.field_671_a * f6;
        super.render(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.particleAge++ >= this.maxParticleAge) {
            this.markDead();
        }

        this.textureId = 7 - this.particleAge * 8 / this.maxParticleAge;
        this.velocityY += 0.004;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.y == this.prevY) {
            this.velocityX *= 1.1;
            this.velocityZ *= 1.1;
        }

        this.velocityX *= 0.9599999785423279;
        this.velocityY *= 0.9599999785423279;
        this.velocityZ *= 0.9599999785423279;
        if (this.onGround) {
            this.velocityX *= 0.699999988079071;
            this.velocityZ *= 0.699999988079071;
        }

    }
}
