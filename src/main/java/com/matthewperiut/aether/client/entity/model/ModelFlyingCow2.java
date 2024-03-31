package com.matthewperiut.aether.client.entity.model;

import com.matthewperiut.aether.entity.living.EntityFlyingCow;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.EntityModel;

public class ModelFlyingCow2 extends EntityModel {
    private Cuboid leftWingInner = new Cuboid(0, 0);
    private Cuboid leftWingOuter = new Cuboid(20, 0);
    private Cuboid rightWingInner = new Cuboid(0, 0);
    private Cuboid rightWingOuter = new Cuboid(40, 0);
    public static EntityFlyingCow flyingcow;

    public ModelFlyingCow2() {
        this.leftWingInner.method_1818(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.leftWingOuter.method_1818(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingInner.method_1818(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.method_1818(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
        this.rightWingOuter.yaw = 3.1415927F;
    }

    public void render(float f, float f1, float f2, float f3, float f4, float f5) {
        float wingBend = -((float) Math.acos((double) flyingcow.wingFold));
        float x = 32.0F * flyingcow.wingFold / 4.0F;
        float y = -32.0F * (float) Math.sqrt((double) (1.0F - flyingcow.wingFold * flyingcow.wingFold)) / 4.0F;
        float z = 0.0F;
        float x2 = x * (float) Math.cos((double) flyingcow.wingAngle) - y * (float) Math.sin((double) flyingcow.wingAngle);
        float y2 = x * (float) Math.sin((double) flyingcow.wingAngle) + y * (float) Math.cos((double) flyingcow.wingAngle);
        this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        x *= 3.0F;
        x2 = x * (float) Math.cos((double) flyingcow.wingAngle) - y * (float) Math.sin((double) flyingcow.wingAngle);
        y2 = x * (float) Math.sin((double) flyingcow.wingAngle) + y * (float) Math.cos((double) flyingcow.wingAngle);
        this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 6.0F, z);
        this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 6.0F, z);
        this.leftWingInner.roll = flyingcow.wingAngle + wingBend + 1.5707964F;
        this.leftWingOuter.roll = flyingcow.wingAngle - wingBend + 1.5707964F;
        this.rightWingInner.roll = -(flyingcow.wingAngle + wingBend - 1.5707964F);
        this.rightWingOuter.roll = -(flyingcow.wingAngle - wingBend + 1.5707964F);
        this.leftWingOuter.render(f5);
        this.leftWingInner.render(f5);
        this.rightWingOuter.render(f5);
        this.rightWingInner.render(f5);
    }

    public void setAngles(float f, float f1, float f2, float f3, float f4, float f5) {
    }
}
