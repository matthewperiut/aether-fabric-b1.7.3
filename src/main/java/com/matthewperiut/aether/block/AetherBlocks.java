package com.matthewperiut.aether.block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class AetherBlocks {
    @Entrypoint.Namespace
    private static final Namespace MOD_ID = Null.get();
    public static Block Portal;
    public static Block Dirt;
    public static Block Grass;
    public static Block Quicksoil;
    public static Block Holystone;
    public static Block Icestone;
    public static Block Aercloud;
    public static Block Aerogel;
    public static Block Log;
    public static Block Plank;
    public static Block SkyrootLeaves;
    public static Block GoldenOakLeaves;
    public static Block SkyrootSapling;
    public static Block GoldenOakSapling;
    public static Block AmbrosiumOre;
    public static Block AmbrosiumTorch;
    public static Block BurntTorch;
    public static Block ZaniteOre;
    public static Block GravititeOre;
    public static Block EnchantedGravitite;
    public static Block Enchanter;
    public static Block Incubator;
    public static Block Trap;
    public static Block ChestMimic;
    public static Block TreasureChest;
    public static Block DungeonStone;
    public static Block LightDungeonStone;
    public static Block LockedDungeonStone;
    public static Block LockedLightDungeonStone;
    public static Block Pillar;
    public static Block ZaniteBlock;
    public static Block QuicksoilGlass;
    public static Block Freezer;
    public static Block WhiteFlower;
    public static Block PurpleFlower;
    public static Block Bed;
    public static Block Chest;

    //public static void AddRenderer(Map map) {
    //map.put(EntityFloatingBlock.class, new RenderFloatingBlock());
    //map.put(EntityMimic.class, new RenderMimic());
    //}

    public static boolean isGood(int id, int meta) {
        return id == 0 || id == Aercloud.id;
    }

    public static boolean isEarth(int id, int meta) {
        return id == Dirt.id || id == Grass.id || id == Holystone.id && meta <= 1;
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        Portal = (new AetherPortal(Identifier.of(MOD_ID, "aether_portal"))).setHardness(-1.0F).setBlastResistance(6000000.0F).setTranslationKey(MOD_ID, "aether_portal");
        Dirt = (new AetherDirt(Identifier.of(MOD_ID, "aether_dirt"))).setHardness(0.2F).setSounds(Block.GRAVEL_SOUNDS).setTranslationKey(MOD_ID, "aether_dirt");
        Grass = (new AetherGrass(Identifier.of(MOD_ID, "aether_grass"))).setHardness(0.2F).setSounds(Block.GRASS_SOUNDS).setTranslationKey(MOD_ID, "aether_grass");
        Quicksoil = (new Quicksoil(Identifier.of(MOD_ID, "quicksoil"))).setHardness(0.5F).setSounds(Block.SAND_SOUNDS).setTranslationKey(MOD_ID, "quicksoil");
        QuicksoilGlass = (new QuicksoilGlass(Identifier.of(MOD_ID, "quicksoil_glass"))).setLightEmittance(0.7375F).setHardness(0.2F).setLightOpacity(0).setSounds(Block.GLASS_SOUNDS).setTranslationKey(MOD_ID, "quicksoil_glass");
        Holystone = (new Holystone(Identifier.of(MOD_ID, "holystone"))).setHardness(0.5F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "holystone");
        Icestone = (new Icestone(Identifier.of(MOD_ID, "icestone"))).setHardness(3.0F).setSounds(Block.GLASS_SOUNDS).setTranslationKey(MOD_ID, "icestone");
        WhiteFlower = (new AetherFlower(Identifier.of(MOD_ID, "white_flower"))).setHardness(0.0F).setSounds(Block.GRASS_SOUNDS).setTranslationKey(MOD_ID, "white_flower");
        PurpleFlower = (new AetherFlower(Identifier.of(MOD_ID, "purple_flower"))).setHardness(0.0F).setSounds(Block.GRASS_SOUNDS).setTranslationKey(MOD_ID, "purple_flower");
        Aercloud = (new Aercloud(Identifier.of(MOD_ID, "aercloud"))).setHardness(0.2F).setLightOpacity(3).setSounds(Block.WOOL_SOUNDS).setTranslationKey(MOD_ID, "aercloud");
        Aerogel = (new Aerogel(Identifier.of(MOD_ID, "aerogel"))).setHardness(1.0F).setBlastResistance(2000.0F).setLightOpacity(3).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "aerogel");
        Log = (new AetherLog(Identifier.of(MOD_ID, "skyroot_log"))).setHardness(2.0F).setSounds(Block.WOOD_SOUNDS).setTranslationKey(MOD_ID, "skyroot_log");
        Plank = (new TemplateBlock(Identifier.of(MOD_ID, "skyroot_planks"), Material.WOOD)).setHardness(2.0F).setBlastResistance(5.0F).setSounds(Block.WOOD_SOUNDS).setTranslationKey(MOD_ID, "skyroot_planks");
        SkyrootLeaves = (new AetherLeaves(Identifier.of(MOD_ID, "skyroot_leaves"), false)).setHardness(0.2F).setLightOpacity(1).setSounds(Block.GRASS_SOUNDS).setTranslationKey(MOD_ID, "skyroot_leaves");
        GoldenOakLeaves = (new AetherLeaves(Identifier.of(MOD_ID, "golden_leaves"), true)).setHardness(0.2F).setLightOpacity(1).setSounds(Block.GRASS_SOUNDS).setTranslationKey(MOD_ID, "golden_leaves");
        SkyrootSapling = (new AetherSapling(Identifier.of(MOD_ID, "skyroot_sapling"), false)).setTranslationKey(MOD_ID, "skyroot_sapling").setHardness(0.0F).setSounds(Block.GRASS_SOUNDS);
        GoldenOakSapling = (new AetherSapling(Identifier.of(MOD_ID, "golden_oak_sapling"), true)).setTranslationKey(MOD_ID, "golden_oak_sapling").setHardness(0.0F).setSounds(Block.GRASS_SOUNDS);
        AmbrosiumOre = (new AmbrosiumOre(Identifier.of(MOD_ID, "ambrosium_ore"))).setHardness(3.0F).setBlastResistance(5.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "ambrosium_ore");
        AmbrosiumTorch = (new AmbrosiumTorch(Identifier.of(MOD_ID, "ambrosium_torch"))).setLightEmittance(0.9375F).setSounds(Block.WOOD_SOUNDS).setTranslationKey(MOD_ID, "ambrosium_torch");
        BurntTorch = (new BurntTorch(Identifier.of(MOD_ID, "burnt_torch"))).setSounds(Block.WOOD_SOUNDS).setTranslationKey(MOD_ID, "burnt_torch");
        ZaniteOre = (new ZaniteOre(Identifier.of(MOD_ID, "zanite_ore"))).setHardness(3.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "zanite_ore");
        ZaniteBlock = (new ZaniteBlock(Identifier.of(MOD_ID, "zanite_block"), Block.IRON_BLOCK.texture)).setHardness(3.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "zanite_block");
        GravititeOre = (new BlockFloating(Identifier.of(MOD_ID, "gravitite_ore"), false)).setHardness(5.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "gravitite_ore");
        EnchantedGravitite = (new EnchantedGravititeBlock(Identifier.of(MOD_ID, "enchanted_gravitite"), Block.IRON_BLOCK.texture, true)).setHardness(5.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "enchanted_gravitite");
        DungeonStone = (new DungeonBlock(Identifier.of(MOD_ID, "dungeon_stone"))).setHardness(0.5F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "dungeon_stone");
        LightDungeonStone = (new DungeonBlock(Identifier.of(MOD_ID, "light_dungeon_stone"))).setHardness(0.5F).setSounds(Block.PISTON_SOUNDS).setLightEmittance(0.75F).setTranslationKey(MOD_ID, "light_dungeon_stone");
        LockedDungeonStone = (new DungeonBlock(Identifier.of(MOD_ID, "locked_dungeon_stone"))).setHardness(-1.0F).setBlastResistance(1000000.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "locked_dungeon_stone");
        LockedLightDungeonStone = (new DungeonBlock(Identifier.of(MOD_ID, "light_locked_dungeon_stone"))).setHardness(-1.0F).setBlastResistance(1000000.0F).setSounds(Block.PISTON_SOUNDS).setLightEmittance(0.5F).setTranslationKey(MOD_ID, "light_locked_dungeon_stone");
        Trap = (new Trap(Identifier.of(MOD_ID, "trap"))).setHardness(-1.0F).setBlastResistance(1000000.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "trap");
        Pillar = (new Pillar(Identifier.of(MOD_ID, "pillar"))).setHardness(0.5F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "pillar");
        Freezer = (new Freezer(Identifier.of(MOD_ID, "freezer"))).setHardness(2.5F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "freezer");
        Incubator = (new Incubator(Identifier.of(MOD_ID, "incubator"))).setTranslationKey(MOD_ID, "incubator").setHardness(2.0F);
        Enchanter = (new Enchanter(Identifier.of(MOD_ID, "enchanter"))).setTranslationKey(MOD_ID, "enchanter").setHardness(2.0F);
        TreasureChest = (new TreasureChest(Identifier.of(MOD_ID, "treasure_chest"))).setHardness(-1.0F).setSounds(Block.PISTON_SOUNDS).setTranslationKey(MOD_ID, "treasure_chest");
        Chest = (new AetherChest(Identifier.of("aether_chest")).setHardness(2.5f).setTranslationKey(MOD_ID, "aether_chest").setSounds(Block.WOOD_SOUNDS).disableNotifyOnMetaDataChange());
        ChestMimic = (new MimicBlock(Identifier.of(MOD_ID, "mimic"))).setHardness(2.0F).setSounds(Block.WOOD_SOUNDS).setTranslationKey(MOD_ID, "mimic");
        Bed = (new AetherBed(Identifier.of(MOD_ID, "aether_bed"))).setHardness(0.2F).setTranslationKey(MOD_ID, "aether_bed").disableStat().disableNotifyOnMetaDataChange();
        //AetherItems.Aercloud = (new ItemBlockAercloud(Aercloud.id));

        /*
        ModLoader.RegisterBlock(Holystone, ItemBlockHolystone.class);
        ModLoader.RegisterBlock(Aercloud, ItemBlockAercloud.class);
        ModLoader.RegisterBlock(Log, ItemBlockAetherLog.class);
        ModLoader.RegisterBlock(DungeonStone, ItemDungeonBlock.class);
        ModLoader.RegisterBlock(LightDungeonStone, ItemDungeonBlock.class);
        ModLoader.RegisterBlock(Pillar, ItemDungeonBlock.class);
        ModLoader.RegisterBlock(Quicksoil, ItemBlockQuicksoil.class);
        ModLoader.RegisterTileEntity(TileEntityIncubator.class, "Incubator");
        ModLoader.RegisterTileEntity(TileEntityEnchanter.class, "Enchanter");
        ModLoader.RegisterTileEntity(TileEntityFreezer.class, "Freezer");
        ModLoader.RegisterEntityID(EntityMimic.class, "Mimic", ModLoader.getUniqueEntityId());*/
        /*
        Pickaxe = new ToolBase();
        Shovel = new ToolBase();
        Axe = new ToolBase();
        Pickaxe.mineBlocks.addAll(Arrays.asList(new BlockHarvestPower(Holystone.id, 20.0F), new BlockHarvestPower(Icestone.id, 20.0F), new BlockHarvestPower(AmbrosiumOre.id, 20.0F), new BlockHarvestPower(DungeonStone.id, 20.0F), new BlockHarvestPower(LightDungeonStone.id, 20.0F), new BlockHarvestPower(Pillar.id, 20.0F), new BlockHarvestPower(TreasureChest.id, 20.0F), new BlockHarvestPower(ZaniteOre.id, 40.0F), new BlockHarvestPower(GravititeOre.id, 60.0F), new BlockHarvestPower(EnchantedGravitite.id, 60.0F), new BlockHarvestPower(Aerogel.id, 60.0F)));
        Shovel.mineBlocks.addAll(Arrays.asList(new BlockHarvestPower(Dirt.id, 0.0F), new BlockHarvestPower(Grass.id, 0.0F), new BlockHarvestPower(Quicksoil.id, 0.0F), new BlockHarvestPower(Aercloud.id, 0.0F)));
        Axe.mineBlocks.addAll(Arrays.asList(new BlockHarvestPower(Log.id, 0.0F), new BlockHarvestPower(Plank.id, 0.0F), new BlockHarvestPower(SkyrootLeaves.id, 0.0F), new BlockHarvestPower(GoldenOakLeaves.id, 60.0F)));
        */
        /*
         */
    }

    public void RegisterBlocks(Block... blocks) {
        Block[] arr$ = blocks;
        int len$ = blocks.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            Block block = arr$[i$];
            //ModLoader.RegisterBlock(block);
        }

    }

    public int override(String path) {
        return 0;
    }
}

