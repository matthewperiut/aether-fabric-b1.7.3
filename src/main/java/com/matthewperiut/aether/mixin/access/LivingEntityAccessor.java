package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("setBoundingBoxSpacing")
    void invokeSetSize(float f, float g);

    @Invoker("dropItems")
    void invokeGetDrops();

    @Accessor("forwardSpeed")
    float getForwardVelocity();

    @Accessor("sidewaysSpeed")
    float getHorizontalVelocity();

    @Accessor("forwardSpeed")
    void setForwardVelocity(float v);

    @Accessor("sidewaysSpeed")
    void setHorizontalVelocity(float v);

    @Accessor("jumping")
    boolean getJumping();

    @Accessor("prevHealth")
    void setPrevHealth(int i);
/*
    @Accessor("field_1029")
    void set1029(float f);

    @Accessor("field_1060")
    void set1060(float f);*/
}
