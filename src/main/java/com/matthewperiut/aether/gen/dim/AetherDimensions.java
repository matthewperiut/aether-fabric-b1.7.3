package com.matthewperiut.aether.gen.dim;

import com.matthewperiut.aether.Aether;
import com.periut.retroapi.dimension.RetroDimensions;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;

public class AetherDimensions {
    public static final String MOD_ID = Aether.MOD_ID;
    public static NamespacedIdentifier THE_AETHER;

    public static void registerDimensions() {
        THE_AETHER = Aether.id("the_aether");
        RetroDimensions.register(THE_AETHER, AetherDimension::new);
    }
}
