package com.matthewperiut.aether;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.blockentity.AetherBlockEntities;
import com.matthewperiut.aether.client.texture.AetherTextures;
import com.matthewperiut.aether.entity.AetherEntities;
import com.matthewperiut.aether.gen.biome.AetherBiomes;
import com.matthewperiut.aether.gen.dim.AetherDimensions;
import com.matthewperiut.aether.gen.portal.AetherPortalListener;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.network.AerbunnyJumpPacket;
import com.matthewperiut.aether.network.MountInputPacket;
import com.matthewperiut.aether.optional.AetherSPCSupport;
import com.matthewperiut.aether.recipe.AetherRecipes;
import net.fabricmc.loader.api.FabricLoader;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import net.ornithemc.osl.core.api.util.NamespacedIdentifiers;
import net.ornithemc.osl.entrypoints.api.ModInitializer;

public class Aether implements ModInitializer {

    public static final String MOD_ID = "aether";

    public static NamespacedIdentifier id(String path) {
        return NamespacedIdentifiers.from(MOD_ID, path);
    }

    @Override
    public void init() {
        // Registration order matters: blocks -> items -> textures (sprite slots) -> recipes ->
        // entities -> block entities -> achievements -> dimensions -> biomes.
        AetherBlocks.registerBlocks();
        AetherItems.registerItems();
        AetherTextures.registerTextures();
        AetherRecipes.registerRecipes();
        AetherEntities.registerEntities();
        AetherBlockEntities.registerBlockEntities();
        AetherAchievements.registerAchievements();
        AetherDimensions.registerDimensions();
        AetherBiomes.registerBiomes();
        AetherPortalListener.register();

        MountInputPacket.register();
        AerbunnyJumpPacket.register();
        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {
            AetherSPCSupport.init();
        }
    }
}
