package com.matthewperiut.aether.item;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class AetherItems {
    public static final String dir = "/aether/items/";
    @Entrypoint.ModID
    private static final ModID MOD_ID = Null.get();
    public static double motionOffset = 0.05;
    public static double ybuff = 0.3;
    public static Item VictoryMedal;
    public static Item Key;
    public static Item LoreBook;
    public static Item MoaEgg;
    public static Item AechorPetal;
    public static Item GoldenAmber;
    public static Item Stick;
    public static Item Dart;
    public static Item DartShooter;
    public static Item AmbrosiumShard;
    public static Item Zanite;
    public static Item BlueMusicDisk;
    public static Item Bucket;
    public static Item PickSkyroot;
    public static Item PickHolystone;
    public static Item PickZanite;
    public static Item PickGravitite;
    public static Item ShovelSkyroot;
    public static Item ShovelHolystone;
    public static Item ShovelZanite;
    public static Item ShovelGravitite;
    public static Item AxeSkyroot;
    public static Item AxeHolystone;
    public static Item AxeZanite;
    public static Item AxeGravitite;
    public static Item SwordSkyroot;
    public static Item SwordHolystone;
    public static Item SwordZanite;
    public static Item SwordGravitite;
    public static Item IronBubble;
    public static Item PigSlayer;
    public static Item VampireBlade;
    public static Item NatureStaff;
    public static Item SwordFire;
    public static Item SwordLightning;
    public static Item SwordHoly;
    public static Item LightningKnife;
    public static Item GummieSwet;
    public static Item HammerNotch;
    public static Item PhoenixBow;
    public static Item PhoenixHelm;
    public static Item PhoenixBody;
    public static Item PhoenixLegs;
    public static Item PhoenixBoots;
    public static Item ObsidianHelm;
    public static Item ObsidianBody;
    public static Item ObsidianLegs;
    public static Item ObsidianBoots;
    public static Item CloudStaff;
    public static Item CloudParachute;
    public static Item CloudParachuteGold;
    public static Item GravititeHelmet;
    public static Item GravititeBodyplate;
    public static Item GravititePlatelegs;
    public static Item GravititeBoots;
    public static Item ZaniteHelmet;
    public static Item ZaniteChestplate;
    public static Item ZaniteLeggings;
    public static Item ZaniteBoots;
    public static Item LifeShard;
    public static Item GoldenFeather;
    public static Item Lance;
    public static Item RepShield;
    public static Item AetherCape;
    public static Item IronRing;
    public static Item GoldRing;
    public static Item ZaniteRing;
    public static Item IronPendant;
    public static Item GoldPendant;
    public static Item ZanitePendant;
    public static Item LeatherGlove;
    public static Item IronGlove;
    public static Item GoldGlove;
    public static Item DiamondGlove;
    public static Item ZaniteGlove;
    public static Item GravititeGlove;
    public static Item PhoenixGlove;
    public static Item ObsidianGlove;
    public static Item NeptuneGlove;
    public static Item NeptuneHelmet;
    public static Item NeptuneChestplate;
    public static Item NeptuneLeggings;
    public static Item NeptuneBoots;
    public static Item RegenerationStone;
    public static Item InvisibilityCloak;
    public static Item AxeValkyrie;
    public static Item PickValkyrie;
    public static Item ShovelValkyrie;
    public static Item HealingStone;
    public static Item AgilityCape;
    public static Item WhiteCape;
    public static Item RedCape;
    public static Item YellowCape;
    public static Item BlueCape;
    public static Item IceRing;
    public static Item IcePendant;
    public static Item Aercloud;
    private static final int ticks = 0;
    private static boolean jumpBoosted;

    //public static int ElementalSwordIcon = ModLoader.addOverride("/gui/items.png", "/aether/items/ElementalSword.png");
    //public static int gravArmour = ModLoader.AddArmor("Gravitite");
    //public static int zaniteArmour = ModLoader.AddArmor("Zanite");
    //public static int neptuneArmour = ModLoader.AddArmor("Neptune");
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        AmbrosiumShard = (new Ambrosium(Identifier.of(MOD_ID, "ambrosium"), 1)).setTranslationKey(MOD_ID, "ambrosium");
    }
}
