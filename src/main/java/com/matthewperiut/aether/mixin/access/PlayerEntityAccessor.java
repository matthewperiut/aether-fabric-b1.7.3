package com.matthewperiut.aether.mixin.access;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerEntity.class)
public interface PlayerEntityAccessor
{
    @Accessor("lyingOnBed")
    void setLyingOnBed(boolean lying);
}
