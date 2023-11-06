package com.matthewperiut.aether.command;

import com.matthewperiut.aether.entity.EntityAechorPlant;
import com.matthewperiut.spc.api.SummonRegistry;

public class SummonAetherEntities {
    public static void register() {
        SummonRegistry.add(EntityAechorPlant.class, (world, pos, param) -> new EntityAechorPlant(world), "");
    }
}
