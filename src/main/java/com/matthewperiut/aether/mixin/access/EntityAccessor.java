package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("fallDistance")
    float getFallDistance();

    @Accessor("fallDistance")
    void setFallDistance(float fallDistance);

    @Invoker("getFlag")
    boolean invokeGetFlag(int i);

    @Accessor("fireImmune")
    void setFireImmune(boolean immune);

    @Accessor("fireImmune")
    boolean isFireImmune();

    @Accessor("random")
    Random getRandom();
}
