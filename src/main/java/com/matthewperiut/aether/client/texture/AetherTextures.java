package com.matthewperiut.aether.client.texture;

import com.matthewperiut.aether.block.*;
import com.matthewperiut.aether.item.AetherItems;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

import static net.modificationstation.stationapi.api.registry.Identifier.of;

public class AetherTextures {
    @Entrypoint.ModID
    private static final ModID MOD_ID = Null.get();

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        setBlockTextures();
        setItemTextures();
    }

    private void setBlockTextures() {
        ExpandableAtlas terrain = Atlases.getTerrain();

        AetherBlocks.Aercloud.texture = terrain.addTexture(of(MOD_ID, "block/Aercloud")).index;
        AetherBlocks.Aerogel.texture = terrain.addTexture(of(MOD_ID, "block/Aerogel")).index;
        // todo: bed texture - AetherBlocks.AetherBed.texture = aetherTerrain.addTexture(of(MOD_ID, "block/Aerogel")).index;
        AetherBlocks.Dirt.texture = terrain.addTexture(of(MOD_ID, "block/Dirt")).index;
        AetherBlocks.WhiteFlower.texture = terrain.addTexture(of(MOD_ID, "block/WhiteFlower")).index;
        AetherBlocks.PurpleFlower.texture = terrain.addTexture(of(MOD_ID, "block/PurpleFlower")).index;
        AetherGrass.sprTop = terrain.addTexture(of(MOD_ID, "block/GrassTop")).index;
        AetherGrass.sprSide = terrain.addTexture(of(MOD_ID, "block/GrassSide")).index;
        AetherLeaves.sprSkyroot = terrain.addTexture(of(MOD_ID, "block/SkyrootLeaves")).index;
        AetherLeaves.sprGoldenOak = terrain.addTexture(of(MOD_ID, "block/GoldenOakLeaves")).index;
        AetherLog.sprTop = terrain.addTexture(of(MOD_ID, "block/SkyrootLogTop")).index;
        AetherLog.sprSide = terrain.addTexture(of(MOD_ID, "block/SkyrootLogSide")).index;
        AetherLog.sprGoldenSide = terrain.addTexture(of(MOD_ID, "block/GoldenOak")).index;
        AetherPortal.spr = terrain.addTexture(of(MOD_ID, "block/Portal")).index;
        AetherBlocks.Plank.texture = terrain.addTexture(of(MOD_ID, "block/Plank")).index;
        AetherSapling.sprSkyroot = terrain.addTexture(of(MOD_ID, "block/SkyrootSapling")).index;
        AetherSapling.sprGoldenOak = terrain.addTexture(of(MOD_ID, "block/GoldenOakSapling")).index;
        AetherBlocks.AmbrosiumOre.texture = terrain.addTexture(of(MOD_ID, "block/AmbrosiumOre")).index;
        AetherBlocks.AmbrosiumTorch.texture = terrain.addTexture(of(MOD_ID, "block/AmbrosiumTorch")).index;
        AetherBlocks.GravititeOre.texture = terrain.addTexture(of(MOD_ID, "block/GravititeOre")).index;
        AetherBlocks.ZaniteOre.texture = terrain.addTexture(of(MOD_ID, "block/ZaniteOre")).index;
        DungeonBlock.sprBronzeLit = terrain.addTexture(of(MOD_ID, "block/LightCarvedStone")).index;
        DungeonBlock.sprSilverLit = terrain.addTexture(of(MOD_ID, "block/LightAngelicStone")).index;
        DungeonBlock.sprGoldLit = terrain.addTexture(of(MOD_ID, "block/LightHellfireStone")).index;
        DungeonBlock.sprBronze = terrain.addTexture(of(MOD_ID, "block/CarvedStone")).index;
        DungeonBlock.sprSilver = terrain.addTexture(of(MOD_ID, "block/AngelicStone")).index;
        DungeonBlock.sprGold = terrain.addTexture(of(MOD_ID, "block/HellfireStone")).index;
        AetherBlocks.Enchanter.texture = terrain.addTexture(of(MOD_ID, "block/EnchanterTop")).index;
        Enchanter.sideTexture = terrain.addTexture(of(MOD_ID, "block/EnchanterSide")).index;
        AetherBlocks.Freezer.texture = terrain.addTexture(of(MOD_ID, "block/FreezerTop")).index;
        Freezer.sideTexture = terrain.addTexture(of(MOD_ID, "block/FreezerSide")).index;
        AetherBlocks.Incubator.texture = terrain.addTexture(of(MOD_ID, "block/IncubatorTop")).index;
        TreasureChest.sideTexture = terrain.addTexture(of(MOD_ID, "block/LockedChestSide")).index;
        AetherBlocks.TreasureChest.texture = terrain.addTexture(of(MOD_ID, "block/LockedChestFront")).index;
        Incubator.sideTexture = terrain.addTexture(of(MOD_ID, "block/IncubatorSide")).index;
        Holystone.sprNormal = terrain.addTexture(of(MOD_ID, "block/Holystone")).index;
        Holystone.sprMossy = terrain.addTexture(of(MOD_ID, "block/MossyHolystone")).index;
        AetherBlocks.Icestone.texture = terrain.addTexture(of(MOD_ID, "block/Icestone")).index;
        Pillar.sprTop = terrain.addTexture(of(MOD_ID, "block/PillarTop")).index;
        Pillar.sprSide = terrain.addTexture(of(MOD_ID, "block/PillarSide")).index;
        Pillar.sprTopSide = terrain.addTexture(of(MOD_ID, "block/PillarCarved")).index;
        AetherBlocks.Quicksoil.texture = terrain.addTexture(of(MOD_ID, "block/Quicksoil")).index;
        AetherBlocks.QuicksoilGlass.texture = terrain.addTexture(of(MOD_ID, "block/QuicksoilGlass")).index;
    }

    private void setItemTextures() {
        AetherItems.AmbrosiumShard.setTexture(of(MOD_ID, "item/AmbrosiumShard"));
    }
}
