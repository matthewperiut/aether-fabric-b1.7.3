package com.matthewperiut.aether.block;

import com.matthewperiut.aether.Aether;
import com.periut.retroapi.register.block.RetroBlockAccess;
import com.periut.retroapi.register.block.RetroMetaBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class AetherBlocks {
    public static final String MOD_ID = Aether.MOD_ID;
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

    public static boolean isGood(int id, int meta) {
        return id == 0 || id == Aercloud.id;
    }

    public static boolean isEarth(int id, int meta) {
        return id == Dirt.id || id == Grass.id || id == Holystone.id && meta <= 1;
    }

    private static Block reg(Block block, String name) {
        return RetroBlockAccess.of(block).register(Aether.id(name));
    }

    private static Block regMeta(Block block, String name) {
        return RetroBlockAccess.of(block).register(Aether.id(name), RetroMetaBlockItem::new);
    }

    public static void registerBlocks() {
        Portal = reg(new AetherPortal().setHardness(-1.0F).setResistance(6000000.0F), "aether_portal");
        Dirt = reg(new AetherDirt().setHardness(0.2F).setSoundGroup(Block.GRAVEL_SOUND_GROUP), "aether_dirt");
        Grass = reg(new AetherGrass().setHardness(0.2F).setSoundGroup(Block.DIRT_SOUND_GROUP), "aether_grass");
        Quicksoil = reg(new Quicksoil().setHardness(0.5F).setSoundGroup(Block.SAND_SOUND_GROUP), "quicksoil");
        QuicksoilGlass = reg(new QuicksoilGlass().setLuminance(0.7375F).setHardness(0.2F).setOpacity(0).setSoundGroup(Block.GLASS_SOUND_GROUP), "quicksoil_glass");
        Holystone = regMeta(new Holystone().setHardness(0.5F).setSoundGroup(Block.STONE_SOUND_GROUP), "holystone");
        Icestone = reg(new Icestone().setHardness(3.0F).setSoundGroup(Block.GLASS_SOUND_GROUP), "icestone");
        WhiteFlower = reg(new AetherFlower().setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP), "white_flower");
        PurpleFlower = reg(new AetherFlower().setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP), "purple_flower");
        Aercloud = regMeta(new Aercloud().setHardness(0.2F).setOpacity(3).setSoundGroup(Block.WOOL_SOUND_GROUP), "aercloud");
        Aerogel = reg(new Aerogel().setHardness(1.0F).setResistance(2000.0F).setOpacity(3).setSoundGroup(Block.STONE_SOUND_GROUP), "aerogel");
        Log = regMeta(new AetherLog().setHardness(2.0F).setSoundGroup(Block.WOOD_SOUND_GROUP), "skyroot_log");
        Plank = reg(new Block(RetroBlockAccess.allocateId(), Material.WOOD).setHardness(2.0F).setResistance(5.0F).setSoundGroup(Block.WOOD_SOUND_GROUP), "skyroot_planks");
        SkyrootLeaves = reg(new AetherLeaves(false).setHardness(0.2F).setOpacity(1).setSoundGroup(Block.DIRT_SOUND_GROUP), "skyroot_leaves");
        GoldenOakLeaves = reg(new AetherLeaves(true).setHardness(0.2F).setOpacity(1).setSoundGroup(Block.DIRT_SOUND_GROUP), "golden_leaves");
        SkyrootSapling = reg(new AetherSapling(false).setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP), "skyroot_sapling");
        GoldenOakSapling = reg(new AetherSapling(true).setHardness(0.0F).setSoundGroup(Block.DIRT_SOUND_GROUP), "golden_oak_sapling");
        AmbrosiumOre = reg(new AmbrosiumOre().setHardness(3.0F).setResistance(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "ambrosium_ore");
        AmbrosiumTorch = reg(new AmbrosiumTorch().setLuminance(0.9375F).setSoundGroup(Block.WOOD_SOUND_GROUP), "ambrosium_torch");
        BurntTorch = reg(new BurntTorch().setSoundGroup(Block.WOOD_SOUND_GROUP), "burnt_torch");
        ZaniteOre = reg(new ZaniteOre().setHardness(3.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "zanite_ore");
        ZaniteBlock = reg(new ZaniteBlock(Block.IRON_BLOCK.textureId).setHardness(3.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "zanite_block");
        GravititeOre = reg(new BlockFloating(false).setHardness(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "gravitite_ore");
        EnchantedGravitite = reg(new EnchantedGravititeBlock(Block.IRON_BLOCK.textureId, true).setHardness(5.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "enchanted_gravitite");
        DungeonStone = regMeta(new DungeonBlock().setHardness(0.5F).setSoundGroup(Block.STONE_SOUND_GROUP), "dungeon_stone");
        LightDungeonStone = regMeta(new DungeonBlock().setHardness(0.5F).setSoundGroup(Block.STONE_SOUND_GROUP).setLuminance(0.75F), "light_dungeon_stone");
        LockedDungeonStone = regMeta(new DungeonBlock().setHardness(-1.0F).setResistance(1000000.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "locked_dungeon_stone");
        LockedLightDungeonStone = regMeta(new DungeonBlock().setHardness(-1.0F).setResistance(1000000.0F).setSoundGroup(Block.STONE_SOUND_GROUP).setLuminance(0.5F), "light_locked_dungeon_stone");
        Trap = reg(new Trap().setHardness(-1.0F).setResistance(1000000.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "trap");
        Pillar = regMeta(new Pillar().setHardness(0.5F).setSoundGroup(Block.STONE_SOUND_GROUP), "pillar");
        Freezer = reg(new Freezer().setHardness(2.5F).setSoundGroup(Block.STONE_SOUND_GROUP), "freezer");
        Incubator = reg(new Incubator().setHardness(2.0F), "incubator");
        Enchanter = reg(new Enchanter().setHardness(2.0F), "enchanter");
        TreasureChest = reg(new TreasureChest().setHardness(-1.0F).setSoundGroup(Block.STONE_SOUND_GROUP), "treasure_chest");
        Chest = reg(new AetherChest().setHardness(2.5f).setSoundGroup(Block.WOOD_SOUND_GROUP).ignoreMetaUpdates(), "aether_chest");
        ChestMimic = reg(new MimicBlock().setHardness(2.0F).setSoundGroup(Block.WOOD_SOUND_GROUP), "mimic");
        Bed = reg(new AetherBed().setHardness(0.2F).disableTrackingStatistics().ignoreMetaUpdates(), "aether_bed");
    }
}
