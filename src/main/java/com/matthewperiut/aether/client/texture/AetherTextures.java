package com.matthewperiut.aether.client.texture;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.block.*;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.item.misc.*;
import com.periut.retroapi.register.block.RetroTextures;
import com.periut.retroapi.register.item.RetroItemAccess;
import net.minecraft.item.Item;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;

/**
 * Registers all Aether block/item textures with RetroAPI's atlas expander.
 * Runs from common init (texture slot allocation is side-neutral; compositing is client-only).
 */
public class AetherTextures {
    public static int Ring;
    public static int Pendant;
    public static int CapeTexture;
    public static int Glove;
    public static int GloveChain;

    private static NamespacedIdentifier of(String path) {
        return Aether.id(path);
    }

    private static int block(String name) {
        return RetroTextures.addBlockTexture(of(name)).id;
    }

    private static int item(String name) {
        return RetroTextures.addItemTexture(of(name)).id;
    }

    private static void tex(Item item, String name) {
        RetroItemAccess.of(item).texture(of(name));
    }

    public static void registerTextures() {
        setBlockTextures();
        setItemTextures();
    }

    public static void setBlockTextures() {
        AetherBlocks.Aercloud.textureId = block("Aercloud");
        AetherBlocks.Aerogel.textureId = block("Aerogel");
        AetherBlocks.Dirt.textureId = block("Dirt");
        AetherBlocks.WhiteFlower.textureId = block("WhiteFlower");
        AetherBlocks.PurpleFlower.textureId = block("PurpleFlower");
        AetherGrass.sprTop = block("GrassTop");
        AetherGrass.sprSide = block("GrassSide");
        AetherLeaves.sprSkyroot = block("SkyrootLeaves");
        AetherLeaves.sprGoldenOak = block("GoldenOakLeaves");
        AetherLog.sprTop = block("SkyrootLogTop");
        AetherLog.sprSide = block("SkyrootLogSide");
        AetherLog.sprGoldenSide = block("GoldenOak");
        AetherPortal.spr = block("Portal");
        AetherBlocks.Plank.textureId = block("Plank");
        AetherSapling.sprSkyroot = block("SkyrootSapling");
        AetherSapling.sprGoldenOak = block("GoldenOakSapling");
        AetherBlocks.AmbrosiumOre.textureId = block("AmbrosiumOre");
        AetherBlocks.AmbrosiumTorch.textureId = block("AmbrosiumTorch");
        AetherBlocks.BurntTorch.textureId = block("BurntTorch");
        AetherBlocks.GravititeOre.textureId = block("GravititeOre");
        AetherBlocks.ZaniteOre.textureId = block("ZaniteOre");
        DungeonBlock.sprBronzeLit = block("LightCarvedStone");
        DungeonBlock.sprSilverLit = block("LightAngelicStone");
        DungeonBlock.sprGoldLit = block("LightHellfireStone");
        DungeonBlock.sprBronze = block("CarvedStone");
        DungeonBlock.sprSilver = block("AngelicStone");
        DungeonBlock.sprGold = block("HellfireStone");
        AetherBlocks.Enchanter.textureId = block("EnchanterTop");
        Enchanter.sideTexture = block("EnchanterSide");
        AetherBlocks.Freezer.textureId = block("FreezerTop");
        Freezer.sideTexture = block("FreezerSide");
        AetherBlocks.Incubator.textureId = block("IncubatorTop");
        TreasureChest.sideTexture = block("LockedChestSide");
        AetherBlocks.TreasureChest.textureId = block("LockedChestFront");
        Incubator.sideTexture = block("IncubatorSide");
        Holystone.sprNormal = block("Holystone");
        Holystone.sprMossy = block("MossyHolystone");
        AetherBlocks.Icestone.textureId = block("Icestone");
        Pillar.sprTop = block("PillarTop");
        Pillar.sprSide = block("PillarSide");
        Pillar.sprTopSide = block("PillarCarved");
        AetherBlocks.Quicksoil.textureId = block("Quicksoil");
        AetherBlocks.QuicksoilGlass.textureId = block("QuicksoilGlass");
        // Holystone block icon defaults to normal stone sprite until textures resolve
        AetherBlocks.Holystone.textureId = Holystone.sprNormal;
    }

    public static void setItemTextures() {
        tex(AetherItems.AmbrosiumShard, "AmbrosiumShard");
        tex(AetherItems.VictoryMedal, "VictoryMedal");
        tex(AetherItems.Key, "Key");
        tex(AetherItems.MoaEgg, "MoaEgg");
        tex(AetherItems.AechorPetal, "AechorPetal");
        tex(AetherItems.GoldenAmber, "GoldenAmber");
        tex(AetherItems.Stick, "Stick");
        ItemDart.sprGolden = item("DartGolden");
        ItemDart.sprEnchanted = item("DartEnchanted");
        ItemDart.sprPoison = item("DartPoison");
        ItemDartShooter.sprNormal = item("DartShooter");
        ItemDartShooter.sprEnchanted = item("DartShooterEnchanted");
        ItemDartShooter.sprPoison = item("DartShooterPoison");
        tex(AetherItems.HealingStone, "HealingStone");
        tex(AetherItems.Zanite, "Zanite");
        tex(AetherItems.BlueMusicDisk, "BlueMusicDisk");
        ItemSkyrootBucket.sprEmpty = item("Bucket");
        ItemSkyrootBucket.sprWater = item("BucketWater");
        ItemSkyrootBucket.sprMilk = item("BucketMilk");
        ItemSkyrootBucket.sprPoison = item("BucketPoison");
        ItemSkyrootBucket.sprRemedy = item("BucketRemedy");
        tex(AetherItems.SwordSkyroot, "SwordSkyroot");
        tex(AetherItems.PickSkyroot, "PickSkyroot");
        tex(AetherItems.AxeSkyroot, "AxeSkyroot");
        tex(AetherItems.ShovelSkyroot, "ShovelSkyroot");
        tex(AetherItems.SwordHolystone, "SwordHolystone");
        tex(AetherItems.PickHolystone, "PickHolystone");
        tex(AetherItems.AxeHolystone, "AxeHolystone");
        tex(AetherItems.ShovelHolystone, "ShovelHolystone");
        tex(AetherItems.SwordZanite, "SwordZanite");
        tex(AetherItems.PickZanite, "PickZanite");
        tex(AetherItems.AxeZanite, "AxeZanite");
        tex(AetherItems.ShovelZanite, "ShovelZanite");
        tex(AetherItems.SwordGravitite, "SwordGravitite");
        tex(AetherItems.PickGravitite, "PickGravitite");
        tex(AetherItems.AxeGravitite, "AxeGravitite");
        tex(AetherItems.ShovelGravitite, "ShovelGravitite");
        tex(AetherItems.Lance, "Lance");
        tex(AetherItems.PickValkyrie, "ValkyriePickaxe");
        tex(AetherItems.AxeValkyrie, "ValkyrieAxe");
        tex(AetherItems.ShovelValkyrie, "ValkyrieShovel");
        tex(AetherItems.PigSlayer, "PigSlayer");
        tex(AetherItems.VampireBlade, "VampireBlade");
        tex(AetherItems.NatureStaff, "NatureStaff");
        ItemSwordElemental.textureId = item("ElementalSword");
        tex(AetherItems.LightningKnife, "LightningKnife");
        tex(AetherItems.GummieSwet, "GummieSwet");
        tex(AetherItems.HammerNotch, "HammerNotch");
        tex(AetherItems.CloudStaff, "CloudStaff");
        tex(AetherItems.PhoenixBow, "PhoenixBow");
        tex(AetherItems.LifeShard, "LifeShard");
        tex(AetherItems.GoldenFeather, "GoldenFeather");
        tex(AetherItems.RepShield, "RepulsionShield");
        Ring = item("Ring");
        Pendant = item("Pendant");
        CapeTexture = item("Cape");
        Glove = item("Glove");
        GloveChain = item("GloveChain");
        AetherItems.IronRing.setTextureId(Ring);
        AetherItems.GoldRing.setTextureId(Ring);
        AetherItems.ZaniteRing.setTextureId(Ring);
        AetherItems.IceRing.setTextureId(Ring);
        AetherItems.IronPendant.setTextureId(Pendant);
        AetherItems.GoldPendant.setTextureId(Pendant);
        AetherItems.ZanitePendant.setTextureId(Pendant);
        AetherItems.IcePendant.setTextureId(Pendant);
        tex(AetherItems.RegenerationStone, "RegenerationStone");
        tex(AetherItems.IronBubble, "IronBubble");
        tex(AetherItems.AetherCape, "AetherCape");
        tex(AetherItems.InvisibilityCloak, "InvisibilityCloak");
        tex(AetherItems.AgilityCape, "AgilityCape");
        AetherItems.WhiteCape.setTextureId(CapeTexture);
        AetherItems.RedCape.setTextureId(CapeTexture);
        AetherItems.YellowCape.setTextureId(CapeTexture);
        AetherItems.BlueCape.setTextureId(CapeTexture);
        AetherItems.LeatherGlove.setTextureId(Glove);
        AetherItems.IronGlove.setTextureId(Glove);
        AetherItems.GoldGlove.setTextureId(Glove);
        AetherItems.DiamondGlove.setTextureId(Glove);
        AetherItems.ZaniteGlove.setTextureId(Glove);
        AetherItems.GravititeGlove.setTextureId(Glove);
        AetherItems.PhoenixGlove.setTextureId(GloveChain);
        AetherItems.ObsidianGlove.setTextureId(Glove);
        AetherItems.NeptuneGlove.setTextureId(GloveChain);
        ItemCloudParachute.tex = item("CloudParachute");
    }
}
