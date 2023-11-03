package com.matthewperiut.aether.client.texture;

import com.matthewperiut.aether.block.*;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.item.misc.ItemDart;
import com.matthewperiut.aether.item.misc.ItemDartShooter;
import com.matthewperiut.aether.item.misc.ItemSkyrootBucket;
import com.matthewperiut.aether.item.misc.ItemSwordElemental;
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
        ExpandableAtlas items = Atlases.getGuiItems();
        AetherItems.AmbrosiumShard.setTexture(of(MOD_ID, "item/AmbrosiumShard"));
        AetherItems.VictoryMedal.setTexture(of(MOD_ID, "item/VictoryMedal"));
        AetherItems.Key.setTexture(of(MOD_ID, "item/Key"));
        AetherItems.MoaEgg.setTexture(of(MOD_ID, "item/MoaEgg"));
        AetherItems.AechorPetal.setTexture(of(MOD_ID, "item/AechorPetal"));
        AetherItems.GoldenAmber.setTexture(of(MOD_ID, "item/GoldenAmber"));
        AetherItems.Stick.setTexture(of(MOD_ID, "item/Stick"));
        ItemDart.sprGolden = items.addTexture(of(MOD_ID, "item/DartGolden")).index;
        ItemDart.sprEnchanted = items.addTexture(of(MOD_ID, "item/DartEnchanted")).index;
        ItemDart.sprPoison = items.addTexture(of(MOD_ID, "item/DartPoison")).index;
        ItemDartShooter.sprNormal = items.addTexture(of(MOD_ID, "item/DartShooter")).index;
        ItemDartShooter.sprEnchanted = items.addTexture(of(MOD_ID, "item/DartShooterPoison")).index;
        ItemDartShooter.sprPoison = items.addTexture(of(MOD_ID, "item/DartShooterEnchanted")).index;
        AetherItems.HealingStone.setTexture(of(MOD_ID, "item/HealingStone"));
        AetherItems.Zanite.setTexture(of(MOD_ID, "item/Zanite"));
        AetherItems.BlueMusicDisk.setTexture(of(MOD_ID, "item/BlueMusicDisk"));
        ItemSkyrootBucket.sprEmpty = items.addTexture(of(MOD_ID, "item/Bucket")).index;
        ItemSkyrootBucket.sprWater = items.addTexture(of(MOD_ID, "item/BucketWater")).index;
        ItemSkyrootBucket.sprMilk = items.addTexture(of(MOD_ID, "item/BucketMilk")).index;
        ItemSkyrootBucket.sprPoison = items.addTexture(of(MOD_ID, "item/BucketPoison")).index;
        ItemSkyrootBucket.sprRemedy = items.addTexture(of(MOD_ID, "item/BucketRemedy")).index;
        AetherItems.BlueMusicDisk.setTexture(of(MOD_ID, "item/BlueMusicDisk"));
        AetherItems.SwordSkyroot.setTexture(of(MOD_ID, "item/SwordSkyroot"));
        AetherItems.PickSkyroot.setTexture(of(MOD_ID, "item/PickSkyroot"));
        AetherItems.AxeSkyroot.setTexture(of(MOD_ID, "item/AxeSkyroot"));
        AetherItems.ShovelSkyroot.setTexture(of(MOD_ID, "item/ShovelSkyroot"));
        AetherItems.SwordHolystone.setTexture(of(MOD_ID, "item/SwordHolystone"));
        AetherItems.PickHolystone.setTexture(of(MOD_ID, "item/PickHolystone"));
        AetherItems.AxeHolystone.setTexture(of(MOD_ID, "item/AxeHolystone"));
        AetherItems.ShovelHolystone.setTexture(of(MOD_ID, "item/ShovelHolystone"));
        AetherItems.SwordZanite.setTexture(of(MOD_ID, "item/SwordZanite"));
        AetherItems.PickZanite.setTexture(of(MOD_ID, "item/PickZanite"));
        AetherItems.AxeZanite.setTexture(of(MOD_ID, "item/AxeZanite"));
        AetherItems.ShovelZanite.setTexture(of(MOD_ID, "item/ShovelZanite"));
        AetherItems.SwordGravitite.setTexture(of(MOD_ID, "item/SwordGravitite"));
        AetherItems.PickGravitite.setTexture(of(MOD_ID, "item/PickGravitite"));
        AetherItems.AxeGravitite.setTexture(of(MOD_ID, "item/AxeGravitite"));
        AetherItems.ShovelGravitite.setTexture(of(MOD_ID, "item/ShovelGravitite"));
        AetherItems.PickValkyrie.setTexture(of(MOD_ID, "item/ValkyriePickaxe"));
        AetherItems.AxeValkyrie.setTexture(of(MOD_ID, "item/ValkyrieAxe"));
        AetherItems.ShovelValkyrie.setTexture(of(MOD_ID, "item/ValkyrieShovel"));
        AetherItems.IronBubble.setTexture(of(MOD_ID, "item/IronBubble"));
        AetherItems.PigSlayer.setTexture(of(MOD_ID, "item/PigSlayer"));
        AetherItems.VampireBlade.setTexture(of(MOD_ID, "item/VampireBlade"));
        AetherItems.NatureStaff.setTexture(of(MOD_ID, "item/NatureStaff"));
        ItemSwordElemental.textureId = items.addTexture(of(MOD_ID, "item/ElementalSword")).index;
        AetherItems.LightningKnife.setTexture(of(MOD_ID, "item/LightningKnife"));
        AetherItems.GummieSwet.setTexture(of(MOD_ID, "item/GummieSwet"));
        AetherItems.HammerNotch.setTexture(of(MOD_ID, "item/HammerNotch"));
        AetherItems.CloudStaff.setTexture(of(MOD_ID, "item/CloudStaff"));
        AetherItems.PhoenixBow.setTexture(of(MOD_ID, "item/PhoenixBow"));
        AetherItems.LifeShard.setTexture(of(MOD_ID, "item/LifeShard"));
        AetherItems.GoldenFeather.setTexture(of(MOD_ID, "item/GoldenFeather"));
        AetherItems.Lance.setTexture(of(MOD_ID, "item/Lance"));
        AetherItems.RepShield.setTexture(of(MOD_ID, "item/RepulsionShield"));
        Ring = items.addTexture(of(MOD_ID, "item/Ring")).index;
        Pendant = items.addTexture(of(MOD_ID, "item/Pendant")).index;
        CapeTexture = items.addTexture(of(MOD_ID, "item/Cape")).index;
        Glove = items.addTexture(of(MOD_ID, "item/Glove")).index;
        GloveChain = items.addTexture(of(MOD_ID, "item/GloveChain")).index;
        AetherItems.IronRing.setTexturePosition(Ring);
        AetherItems.GoldRing.setTexturePosition(Ring);
        AetherItems.ZaniteRing.setTexturePosition(Ring);
        AetherItems.IceRing.setTexturePosition(Ring);
        AetherItems.IronPendant.setTexturePosition(Pendant);
        AetherItems.GoldPendant.setTexturePosition(Pendant);
        AetherItems.ZanitePendant.setTexturePosition(Pendant);
        AetherItems.IcePendant.setTexturePosition(Pendant);
        AetherItems.RegenerationStone.setTexture(of(MOD_ID, "item/RegenerationStone"));
        AetherItems.AetherCape.setTexture(of(MOD_ID, "item/AetherCape"));
        AetherItems.InvisibilityCloak.setTexture(of(MOD_ID, "item/InvisibilityCloak"));
        AetherItems.AgilityCape.setTexture(of(MOD_ID, "item/AgilityCape"));
        AetherItems.WhiteCape.setTexturePosition(CapeTexture);
        AetherItems.RedCape.setTexturePosition(CapeTexture);
        AetherItems.YellowCape.setTexturePosition(CapeTexture);
        AetherItems.BlueCape.setTexturePosition(CapeTexture);

    }

    public static int Ring;
    public static int Pendant;
    public static int CapeTexture;
    public static int Glove;
    public static int GloveChain;
}
