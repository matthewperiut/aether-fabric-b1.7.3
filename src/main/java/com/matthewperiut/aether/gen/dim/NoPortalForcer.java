package com.matthewperiut.aether.gen.dim;

import net.minecraft.class_467;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

// class_467 -> PortalForcer
public class NoPortalForcer extends class_467 {
    // method_1531 -> teleportToValidPortal
    @Override
    public boolean method_1531(World level, Entity entity) {
        return true;
    }

    // method_1532 -> createPortal
    @Override
    public boolean method_1532(World level, Entity entity) {
        return true;
    }
}