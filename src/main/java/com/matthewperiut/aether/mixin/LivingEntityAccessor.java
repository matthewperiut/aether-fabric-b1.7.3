package com.matthewperiut.aether.mixin;

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
    float get1029();

    @Accessor("horizontalVelocity")
    float get1060();

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
