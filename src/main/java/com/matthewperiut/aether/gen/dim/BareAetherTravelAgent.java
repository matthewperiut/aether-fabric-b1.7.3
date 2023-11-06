package com.matthewperiut.aether.gen.dim;

import net.minecraft.entity.Entity;
import net.minecraft.util.NetherTeleporter;
import net.minecraft.world.World;

public class BareAetherTravelAgent extends NetherTeleporter {
    @Override
    public boolean tryTeleport(World level, Entity entity) {
        return true;
    }

    @Override
    public boolean placeNetherPortal(World level, Entity entity) {
        return true;
    }
}