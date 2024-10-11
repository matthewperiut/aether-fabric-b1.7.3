package com.matthewperiut.aether.gen.dim;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;

public class BareAetherTravelAgent extends PortalForcer {
    @Override
    public boolean teleportToValidPortal(World level, Entity entity) {
        return true;
    }

    @Override
    public boolean createPortal(World level, Entity entity) {
        return true;
    }
}