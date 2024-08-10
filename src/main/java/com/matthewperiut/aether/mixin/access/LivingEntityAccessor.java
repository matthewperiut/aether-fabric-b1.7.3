package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("setBoundingBoxSpacing")
    void invokeSetBoundingBoxSpacing(float f, float g);

    @Invoker("drop")
    void invokeDrop();

    // ForwardVelocity is field_1029
    @Accessor("field_1029")
    float getForwardVelocity();

    // HorizontalVelocity is field_1060
    @Accessor("field_1060")
    float getHorizontalVelocity();

    @Accessor("field_1029")
    void setForwardVelocity(float v);

    @Accessor("field_1060")
    void setHorizontalVelocity(float v);

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
