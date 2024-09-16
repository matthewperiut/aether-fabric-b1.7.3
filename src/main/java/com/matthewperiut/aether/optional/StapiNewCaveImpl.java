package com.matthewperiut.aether.optional;

import net.minecraft.world.World;
import net.minecraft.world.gen.Carver;
import net.modificationstation.stationapi.impl.world.CaveGenBaseImpl;

public class StapiNewCaveImpl
{
    public static void giveStapiWhatItWants(Carver carver, World world) {
        ((CaveGenBaseImpl) carver).stationapi_setWorld(world);
    }
}
