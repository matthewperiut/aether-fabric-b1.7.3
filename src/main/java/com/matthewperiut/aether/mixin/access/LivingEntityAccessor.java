package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("setSize")
    void invokeSetSize(float f, float g);

    @Invoker("getDrops")
    void invokeGetDrops();

    @Accessor("forwardVelocity")
    float getForwardVelocity();

    @Accessor("horizontalVelocity")
    float getHorizontalVelocity();

    @Accessor("jumping")
    boolean getJumping();

    @Accessor("field_1058")
    void set1058(int i);
/*
    @Accessor("field_1029")
    void set1029(float f);

    @Accessor("field_1060")
    void set1060(float f);*/
}
