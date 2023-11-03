package com.matthewperiut.aether.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("fallDistance")
    float getFallDistance();

    @Accessor("fallDistance")
    void setFallDistance(float fallDistance);
}
