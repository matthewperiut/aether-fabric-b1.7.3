package com.matthewperiut.aether.optional;

import net.minecraft.world.World;
import net.minecraft.world.gen.Generator;
import net.modificationstation.stationapi.impl.world.CaveGenBaseImpl;

public class StapiNewCaveImpl
{
    public static void giveStapiWhatItWants(Generator carver, World world) {
        ((CaveGenBaseImpl) carver).stationapi_setWorld(world);
    }
}
