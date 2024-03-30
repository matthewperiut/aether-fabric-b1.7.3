package com.matthewperiut.aether.entity.fx;

import net.minecraft.client.entity.particle.ParticleEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.world.World;

public class EntityCloudSmokeFX extends ParticleEntity {
    float field_671_a;

    public EntityCloudSmokeFX(World world, double x, double y, double z, double initialMotionX, double initialMotionY, double intialMotionZ, float size, float red, float blue, float green) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.xVelocity *= 0.10000000149011612;
        this.yVelocity *= 0.10000000149011612;
        this.zVelocity *= 0.10000000149011612;
        this.xVelocity += initialMotionX;
        this.yVelocity += initialMotionY;
        this.zVelocity += intialMotionZ;
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.field_2640 *= 0.75F;
        this.field_2640 *= size;
        this.field_671_a = this.field_2640;
        this.field_2639 = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.field_2639 = (int) ((float) this.field_2639 * size);
        this.field_1642 = false;
    }

    public void method_2002(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = ((float) this.field_2638 + f) / (float) this.field_2639 * 32.0F;
        if (f6 < 0.0F) {
            f6 = 0.0F;
        }

        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.field_2640 = this.field_671_a * f6;
        super.method_2002(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.field_2638++ >= this.field_2639) {
            this.remove();
        }

        this.texture = 7 - this.field_2638 * 8 / this.field_2639;
        this.yVelocity += 0.004;
        this.move(this.xVelocity, this.yVelocity, this.zVelocity);
        if (this.y == this.prevY) {
            this.xVelocity *= 1.1;
            this.zVelocity *= 1.1;
        }

        this.xVelocity *= 0.9599999785423279;
        this.yVelocity *= 0.9599999785423279;
        this.zVelocity *= 0.9599999785423279;
        if (this.onGround) {
            this.xVelocity *= 0.699999988079071;
            this.zVelocity *= 0.699999988079071;
        }

    }
}
