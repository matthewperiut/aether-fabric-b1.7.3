package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor
{
    @Accessor("fallDistance")
    float getFallDistance();

    @Accessor("fallDistance")
    void setFallDistance(float fallDistance);

    @Invoker("isFlagSet")
    boolean invokeIsFlagSet(int i);

    @Accessor("immuneToFire")
    void setImmuneToFire(boolean immune);
}
