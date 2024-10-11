package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.blockentity.container.ContainerLore;
import com.matthewperiut.aether.item.AetherItems;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GuiLore extends HandledScreen {

    public static ArrayList<Lore> lores;

    static {
        (GuiLore.lores = new ArrayList<Lore>()).add(new Lore(Block.STONE, "Stone", "Found everywhere.", "Makes steps", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.GRASS_BLOCK, "Grass", "Found in light.", "Spreads to dirt.", "Flowers and trees", "will grow on it.", "Click with a hoe", "to make farmland", 0));
        GuiLore.lores.add(new Lore(Block.DIRT, "Dirt", "Found everywhere.", "Grass, trees and", "flowers will grow", "on it.", "Click with a hoe", "to make farmland", 0));
        GuiLore.lores.add(new Lore(Block.COBBLESTONE, "Cobblestone", "Found when mining", "stone and when", "water meets lava.", "Makes stone tools,", "cobble steps and", "furnaces", 0));
        GuiLore.lores.add(new Lore(Block.PLANKS, "Wooden Planks", "Crafted from wood.", "Useful building", "material.", "Makes sticks, tools,", "boats, doors, chests", "and crafting tables", 0));
        GuiLore.lores.add(new Lore(Block.SAPLING, "Sapling", "Dropped by leaves.", "Grows a tree.", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.BEDROCK, "Bedrock / Adminium", "Not obtainable...", "The hardest known", "material; completely", "indestructible.", "", "", 0));
        GuiLore.lores.add(new Lore(Block.SAND, "Sand", "Found by water.", "Falls when ", "unsupported.", "Smeltable into glass", "", "", 0));
        GuiLore.lores.add(new Lore(Block.GRAVEL, "Gravel", "Found in rock.", "Falls when ", "unsupported.", "Chance to drop flint", "", "", 0));
        GuiLore.lores.add(new Lore(Block.GOLD_ORE, "Gold Ore", "Found in rock.", "Smeltable into", "gold ingots.", "Medium rarity", "", "", 0));
        GuiLore.lores.add(new Lore(Block.IRON_ORE, "Iron Ore", "Found in rock.", "Smeltable into", "iron ingots.", "Common", "", "", 0));
        GuiLore.lores.add(new Lore(Block.LOG, "Wood", "Found in trees.", "Craftable into", "planks.", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.SPONGE, "Sponge", "Not obtainable...", "Has no purpose", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.GLASS, "Glass", "Smelted from sand.", "Decorative block", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.LAPIS_BLOCK, "Lapis Lazuli", "Made from 9 lapis", "lazuli.", "Decorative block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.DISPENSER, "Dispenser", "Ejects items when ", "powered. Also", "shoots arrows", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.SANDSTONE, "Sandstone", "Made from 4 sand.", "Also found below", "sand naturally.", "Decorative block", "", "", 0));
        GuiLore.lores.add(new Lore(Block.NOTE_BLOCK, "Note Block", "Plays a note when", "powered. The", "block underneath", "defines the sound.", "Right click to", "change the note", 0));
        GuiLore.lores.add(new Lore(Block.POWERED_RAIL, "Powered Rail", "Quickens minecarts", "when powered.", "Brakes minecarts", "when unpowered", "", "", 0));
        GuiLore.lores.add(new Lore(Block.DETECTOR_RAIL, "Detector Rail", "Gives out power", "when a minecart is", "on it.", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.COBWEB, "Web", "Slows down", "anything that", "enters it", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.WOOL, "Wool", "Found on sheep.", "Can be dyed.", "Decorative Block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.DANDELION, "Dandelion", "Found on grass.", "Can make", "dandelion yellow", "dye", "", "", 0));
        GuiLore.lores.add(new Lore(Block.ROSE, "Rose", "Found on grass.", "Can make", "rose red dye", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.BROWN_MUSHROOM, "Mushroom", "Found on grass", "and in caves.", "Used to make", "mushroom soup", "", "", 0));
        GuiLore.lores.add(new Lore(Block.RED_MUSHROOM, "Mushroom", "Found on grass", "and in caves.", "Used to make", "mushroom soup", "", "", 0));
        GuiLore.lores.add(new Lore(Block.GOLD_BLOCK, "Gold", "Crafted from 9", "gold ingots.", "Decorative block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.IRON_BLOCK, "Iron", "Crafted from 9", "iron ingots.", "Decorative block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.SLAB, "Half-Step", "Used for making", "stairs and such.", "Decorative block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.BRICKS, "Bricks", "Decorative block", "", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.TNT, "TNT", "Made from sand", "and gunpowder.", "Will detonate when", "hit or powered.", "Handle with care", "", 0));
        GuiLore.lores.add(new Lore(Block.BOOKSHELF, "Bookshelf", "A pleasant array", "of books.", "Decorative Block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.MOSSY_COBBLESTONE, "Mossy Cobblestone", "Found in dungeons.", "Deorative Block", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.OBSIDIAN, "Obsidian", "Formed from water", "and lava.", "Very hard block", "that is useful in", "making fortifications", "and nether portals", 0));
        GuiLore.lores.add(new Lore(Block.TORCH, "Torch", "Made from coal and", "sticks.", "Most common light", "source used for", "mining and lighting", "homes.", 0));
        GuiLore.lores.add(new Lore(Block.WOODEN_STAIRS, "Wooden Stairs", "Made from wood.", "Useful for making", "staircases, but ", "more compact than", "half steps.", "", 0));
        GuiLore.lores.add(new Lore(Block.CHEST, "Chest", "Made from planks.", "Holds 27 slots.", "Can be joined to", "another chest to", "make a double", "chest.", 0));
        GuiLore.lores.add(new Lore(Block.DIAMOND_BLOCK, "Diamond", "Crafted from 9", "diamonds.", "Decorative block", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.CRAFTING_TABLE, "Workbench", "Used to create all", "complex items.", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.FURNACE, "Furnace", "Takes coal, wood", "or lava as fuel and", "smelts sand, cobble", "iron, gold, clay", "and lots more", "", 0));
        GuiLore.lores.add(new Lore(Block.LADDER, "Ladder", "Used to climb", "vertically", "upwards or", "downwards", "", "", 0));
        GuiLore.lores.add(new Lore(Block.RAIL, "Rail", "Allows minecarts", "to be placed and", "to move.", "You will need a lot", "of rails to make", "a minecart track", 0));
        GuiLore.lores.add(new Lore(Block.COBBLESTONE_STAIRS, "Cobblestone Stairs", "Made from cobble.", "Useful for making", "staircases, but ", "more compact than", "half steps.", "", 0));
        GuiLore.lores.add(new Lore(Block.LEVER, "Lever", "Gives a redstone", "current when on.", "Used as an input", "device for", "redstone circuits", "", 0));
        GuiLore.lores.add(new Lore(Block.STONE_PRESSURE_PLATE, "Pressure Plate", "Gives a redstone", "current when", "stepped on by", "a living thing.", "", "", 0));
        GuiLore.lores.add(new Lore(Block.WOODEN_PRESSURE_PLATE, "Pressure Plate", "Gives a redstone", "current when", "anything is on it", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.LIT_REDSTONE_TORCH, "Redstone Torch", "Gives a redstone", "current when", "unpowered, but", "does not when", "powered (NOT gate)", "", 0));
        GuiLore.lores.add(new Lore(Block.BUTTON, "Button", "Gives a redstone", "pulse for about", "a second when", "pushed", "", "", 0));
        GuiLore.lores.add(new Lore(Block.SNOW_BLOCK, "Snow", "Melts when near", "light.", "Could be used as", "camouflage in a", "snowy biome", "", 0));
        GuiLore.lores.add(new Lore(Block.CACTUS, "Cactus", "Found in deserts.", "Hurts living things", "that touch it.", "Can be used as", "defenses and can", "be farmed", 0));
        GuiLore.lores.add(new Lore(Block.CLAY, "Clay", "Found in sand.", "Can be split into", "clay lumps and then", "smelted to make", "bricks", "", 0));
        GuiLore.lores.add(new Lore(Block.JUKEBOX, "Jukebox", "Plays records", "found in dungeons.", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.FENCE, "Fence", "Stops anything", "from jumping over.", "Also used for poles.", "Start from the top", "and work down", "", 0));
        GuiLore.lores.add(new Lore(Block.PUMPKIN, "Pumpkin", "Found in small", "patches.", "Can be made into", "Jack-o-Lanterns", "", "", 0));
        GuiLore.lores.add(new Lore(Block.JACK_O_LANTERN, "Jack-o-Lantern", "Made from pumpkins", "and torches.", "Useful lightsource", "and rather scary", "", "", 0));
        GuiLore.lores.add(new Lore(Block.TRAPDOOR, "Trapdoor", "Opens upwards to", "allow access to", "space below", "", "", "", 0));
        GuiLore.lores.add(new Lore(Block.PISTON, "Piston", "Extends when", "powered. Useful", "for traps, doors", "and machines.", "", "", 0));
        GuiLore.lores.add(new Lore(Block.STICKY_PISTON, "Sticky Piston", "Extends when", "powered and pulls", "blocks when", "retracted. Useful", "in making doors", "and hidden blocks", 0));
        GuiLore.lores.add(new Lore(Block.LEAVES, "Leaves", "Grows on trees.", "Obtainable by", "using shears.", "Decorative block", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_SHOVEL, "Iron Shovel", "Digs grass, dirt,", "sand and gravel.", "Normal Speed", "Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_PICKAXE, "Iron Pickaxe", "Digs stone, cobble,", "and other rocks.", "Normal Speed", "Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_AXE, "Iron Axe", "Chops wood and ", "planks.", "Normal Speed", "Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_SWORD, "Iron Sword", "For attacking", "mobs and animals.", "Normal Damage", "Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_HOE, "Iron Hoe", "Turns grass or", "dirt into farmland.", "Normal Speed", "Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_SHOVEL, "Wooden Shovel", "Digs grass, dirt,", "sand and gravel.", "Very Slow Speed", "Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_PICKAXE, "Wooden Pickaxe", "Digs stone, cobble,", "and other rocks.", "Very Slow Speed", "Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_AXE, "Wooden Axe", "Chops wood and ", "planks.", "Very Slow Speed", "Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_SWORD, "Wooden Sword", "For attacking", "mobs and animals.", "Very Low Damage", "Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_HOE, "Wooden Hoe", "Turns grass or", "dirt into farmland.", "Very Slow Speed", "Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STONE_SHOVEL, "Stone Shovel", "Digs grass, dirt,", "sand and gravel.", "Slow Speed", "Average Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STONE_PICKAXE, "Stone Pickaxe", "Digs stone, cobble,", "and other rocks.", "Slow Speed", "Average Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STONE_AXE, "Stone Axe", "Chops wood and ", "planks.", "Slow Speed", "Average Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STONE_SWORD, "Stone Sword", "For attacking", "mobs and animals.", "Low Damage", "Average Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STONE_HOE, "Stone Hoe", "Turns grass or", "dirt into farmland.", "Slow Speed", "Average Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_SHOVEL, "Gold Shovel", "Digs grass, dirt,", "sand and gravel.", "Very Fast Speed", "Very Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_PICKAXE, "Gold Pickaxe", "Digs stone, cobble,", "and other rocks.", "Very Fast Speed", "Very Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_AXE, "Gold Axe", "Chops wood and ", "planks.", "Very Fast Speed", "Very Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_SWORD, "Gold Sword", "For attacking", "mobs and animals.", "Very High Damage", "Very Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_HOE, "Gold Hoe", "Turns grass or", "dirt into farmland.", "Very Fast Speed", "Very Few Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_SHOVEL, "Diamond Shovel", "Digs grass, dirt,", "sand and gravel.", "Fast Speed", "Very Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_PICKAXE, "Diamond Pickaxe", "Digs stone, cobble,", "and other rocks.", "Fast Speed", "Very Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_AXE, "Diamond Axe", "Chops wood and ", "planks.", "Fast Speed", "Very Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_SWORD, "Diamond Sword", "For attacking", "mobs and animals.", "High Damage", "Very Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_HOE, "Diamond Hoe", "Turns grass or", "dirt into farmland.", "Fast Speed", "Very Many Uses", "", "", 0));
        GuiLore.lores.add(new Lore(Item.FLINT_AND_STEEL, "Iron and Flint", "Makes fires", "and activates", "Nether portals.", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.APPLE, "Red Apple", "Heals the user", "a small amount.", "Found in dungeons", "and from Notch", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BOW, "Bow", "Shoots arrows", "and can be used", "to make dispensers.", "Skeletons use", "bows too", "", 0));
        GuiLore.lores.add(new Lore(Item.ARROW, "Arrow", "Ammo for the bow.", "Made from feathers,", "sticks and flints", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.COAL, "Coal", "Found in rock.", "Very common.", "Makes torches", "and is used as", "fuel in the furnace", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND, "Diamond", "Found deep under-", "ground. Very rare.", "Used for diamond", "tools, armour and", "jukeboxes", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_INGOT, "Iron Ingot", "Refined Iron.", "Makes iron tools,", "armour, rails,", "minecarts, doors,", "buckets and", "compasses", 0));
        GuiLore.lores.add(new Lore(Item.GOLD_INGOT, "Gold Ingot", "Refined Gold.", "Makes gold tools,", "armour and ", "watches", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STICK, "Stick", "Made from 2 planks.", "Very important", "crafting material", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BOWL, "Bowl", "For holding soup", "", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.MUSHROOM_STEW, "Mushroom Soup", "Tasty soup that", "heals a few hearts", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.STRING, "String", "Dropped by", "spiders.", "Used for bows and", "fishing rods", "", "", 0));
        GuiLore.lores.add(new Lore(Item.FEATHER, "Feather", "Dropped by", "chickens", "and zombies.", "Makes arrows", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GUNPOWDER, "Gunpowder", "Dropped by ghasts", "and creepers.", "Makes TNT", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SEEDS, "Seeds", "Gained by cutting", "wild grass.", "Placeable in", "farmland to grow", "crops", "", 0));
        GuiLore.lores.add(new Lore(Item.WHEAT, "Wheat", "Produced when", "harvesting crops.", "Used to make bread", "and cake", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BREAD, "Bread", "Delicious,", "nutritious", "bread.", "Heals a few hearts", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LEATHER_HELMET, "Leather Helmet", "Wear it on your", "head.", "Awful Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LEATHER_CHESTPLATE, "Leather Chestplate", "Wear it on your", "chest.", "Awful Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LEATHER_LEGGINGS, "Leather Greaves", "Wear it on your", "legs.", "Awful Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LEATHER_BOOTS, "Leather Boots", "Wear it on your", "feet.", "Awful Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_HELMET, "Gold Helmet", "Wear it on your", "head.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_CHESTPLATE, "Gold Chestplate", "Wear it on your", "chest.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_LEGGINGS, "Gold Greaves", "Wear it on your", "legs.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_BOOTS, "Gold Boots", "Wear it on your", "feet.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CHAIN_HELMET, "Chain Helmet", "Wear it on your", "head.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CHAIN_CHESTPLATE, "Chain Chestplate", "Wear it on your", "chest.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CHAIN_LEGGINGS, "Chain Greaves", "Wear it on your", "legs.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CHAIN_BOOTS, "Chain Boots", "Wear it on your", "feet.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_HELMET, "Iron Helmet", "Wear it on your", "head.", "Good Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_CHESTPLATE, "Iron Chestplate", "Wear it on your", "chest.", "Good Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_LEGGINGS, "Iron Greaves", "Wear it on your", "legs.", "Good Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_BOOTS, "Iron Boots", "Wear it on your", "feet.", "Good Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_HELMET, "Diamond Helmet", "Wear it on your", "head.", "Great Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_CHESTPLATE, "Diamond Chestplate", "Wear it on your", "chest.", "Great Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_LEGGINGS, "Diamond Greaves", "Wear it on your", "legs.", "Great Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DIAMOND_BOOTS, "Diamond Boots", "Wear it on your", "feet.", "Great Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.FLINT, "Flint", "Found in gravel.", "Used in arrows", "and firelighters", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.RAW_PORKCHOP, "Raw Pork", "Dropped by pigs.", "Can be cooked", "or eaten raw", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.COOKED_PORKCHOP, "Cooked Pork", "Dropped by pig", "zombies. Also", "obtainable from", "cooking pork.", "Heals a few hearts", "", 0));
        GuiLore.lores.add(new Lore(Item.PAINTING, "Painting", "Made from sticks", "and cloth.", "Puts a random", "painting where", "you click", "", 0));
        GuiLore.lores.add(new Lore(Item.GOLDEN_APPLE, "Golden Apple", "A ridiculously", "expensive apple", "which, despite being", "coated in gold,", "heals all hearts", "", 0));
        GuiLore.lores.add(new Lore(Item.SIGN, "Sign", "Made from sticks", "and planks.", "Can be placed on", "walls or the floor", "with your message", "", 0));
        GuiLore.lores.add(new Lore(Item.WOODEN_DOOR, "Wooden Door", "Made from planks.", "Allows you to shut", "out the creepers", "before they boom", "in your house", "", 0));
        GuiLore.lores.add(new Lore(Item.BUCKET, "Bucket", "Made from iron.", "Can pick up water", "and lava.", "If used on a cow,", "milk may be", "obtained", 0));
        GuiLore.lores.add(new Lore(Item.WATER_BUCKET, "Water Bucket", "Can be used to", "place a water", "source", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LAVA_BUCKET, "Lava Bucket", "Can be used to", "place a lava", "source", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.MINECART, "Minecart", "Can be ridden in,", "but make sure the", "animals can't get", "to your cart", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SADDLE, "Saddle", "Found in dungeons.", "Can be used to", "saddle a pig", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.IRON_DOOR, "Iron Door", "Made from iron.", "Behaves like a door", "but can only be", "opened by redstone", "", "", 0));
        GuiLore.lores.add(new Lore(Item.REDSTONE, "Redstone", "Used to carry", "redstone currents", "in redstone circuits", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SNOWBALL, "Snowball", "Found by digging", "snow with a spade.", "Can be thrown", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BOAT, "Boat", "Can be ridden in", "to cross lakes", "and oceans", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.LEATHER, "Leather", "Dropped by cows.", "Used in making", "leather armour", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.MILK_BUCKET, "Milk Bucket", "Gained by using", "a bucket on a cow.", "Used in making cake.", "Heals a few hearts", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BRICK, "Brick", "Smelted from clay.", "Used to make brick", "blocks", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CLAY, "Clay", "Found in clay", "blocks.", "Can be smelted", "into bricks", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SUGAR_CANE, "Sugarcanes", "Found on dirt or", "grass by water.", "Makes paper for", "books and sugar ", "for cakes.", "Can be farmed", 0));
        GuiLore.lores.add(new Lore(Item.PAPER, "Paper", "Made from", "sugarcane.", "Used in books and", "maps", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BOOK, "Book", "Made from paper.", "Used to make", "bookcases", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SLIMEBALL, "Slime Ball", "Dropped by slimes.", "Used to make", "sticky pistons", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CHEST_MINECART, "Storage Minecart", "A minecart that", "carries a chest", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.FURNACE_MINECART, "Powered Minecart", "A minecart that", "pushes other carts", "when given coal", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.EGG, "Egg", "Laid by chickens.", "Throw it to hatch", "a new chicken.", "Also used in", "making cake.", "", 0));
        GuiLore.lores.add(new Lore(Item.COMPASS, "Compass", "Made from iron", "and redstone.", "Points to your", "spawnpoint", "", "", 0));
        GuiLore.lores.add(new Lore(Item.FISHING_ROD, "Fishing Rod", "Made from sticks", "and string.", "Can be used for", "fishing or ", "pulling mobs around", "", 0));
        GuiLore.lores.add(new Lore(Item.CLOCK, "Watch", "Made from gold", "and redstone.", "Tells the time", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.GLOWSTONE_DUST, "Lightstone Dust", "Dropped by", "lightstone.", "Can be crafted", "into lightstone", "", "", 0));
        GuiLore.lores.add(new Lore(Item.RAW_FISH, "Raw Fish", "Gained by fishing.", "Can be cooked or", "eaten raw", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.COOKED_FISH, "Cooked Fish", "Gained by cooking", "raw fish.", "Heals a few hearts", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.DYE, "Dye", "Obtained from many", "places.", "Dyes can be mixed,", "added to wool", "and used on sheep", "", 0));
        GuiLore.lores.add(new Lore(Item.BONE, "Bone", "Dropped by", "skeletons.", "Used to make", "bonemeal and to", "tame wolves", "", 0));
        GuiLore.lores.add(new Lore(Item.SUGAR, "Sugar", "Made from", "sugarcane.", "Used to make cake", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.CAKE, "Cake", "The cake is a lie.", "", "", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.BED, "Bed", "Made from planks", "and wool.", "Allows you to", "sleep until", "morning and set", "your spawnpoint", 0));
        GuiLore.lores.add(new Lore(Item.REPEATER, "Repeater", "Made from stone", "and redstone.", "Repeats a signal", "with a delay, set", "by the toggle", "", 0));
        GuiLore.lores.add(new Lore(Item.COOKIE, "Cookie", "Made from cocoa", "beans and wheat.", "Heals a few hearts", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.MAP, "Map", "Made from paper", "and a compass.", "Makes a map of the", "area you are in", "", "", 0));
        GuiLore.lores.add(new Lore(Item.RECORD_THIRTEEN, "13", "Found in dungeons.", "Playable in jukebox.", "A rather odd tune", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.RECORD_CAT, "Cat", "Found in dungeons.", "Playable in jukebox.", "A very jolly tune", "", "", "", 0));
        GuiLore.lores.add(new Lore(Item.SHEARS, "Shears", "Made from iron.", "Used to shear", "sheep and to get", "leaf blocks from", "trees.", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.IronRing, "Iron Ring", "Made from iron.", "Wear it in your", "ring slot.", "Purely decorative", "item", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.GoldRing, "Gold Ring", "Made from gold.", "Wear it in your", "ring slot.", "Purely decorative", "item", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.IronPendant, "Iron Pendant", "Made from iron.", "Wear it in your", "pendant slot.", "Purely decorative", "item", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.GoldPendant, "Gold Pendant", "Made from gold.", "Wear it in your", "pendant slot.", "Purely decorative", "item", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.LeatherGlove, "Leather Glove", "Wear them on your", "hands.", "Awful Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.IronGlove, "Iron Glove", "Wear them on your", "hands.", "Good Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.GoldGlove, "Gold Glove", "Wear them on your", "hands.", "Bad Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(AetherItems.DiamondGlove, "Diamond Glove", "Wear them on your", "hands.", "Great Protection", "", "", "", 0));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.LoreBook, 1, 0), "Lore Book : Vol 1", "Contains information", "about blocks and", "items from the", "surface world", "", "", 0));
        GuiLore.lores.add(new Lore(Block.NETHERRACK, "Netherrack", "Main nether", " material.", "Burns forever", "", "", "", 1));
        GuiLore.lores.add(new Lore(Block.SOUL_SAND, "Slow Sand", "Found in patches", "Slows anything on it", "", "", "", "", 1));
        GuiLore.lores.add(new Lore(Block.GLOWSTONE, "Glowstone", "Found on the roof", "of the Nether.", "Drops 4 Glowstone", "dust.", "Used in Aether ", "portals", 1));
        GuiLore.lores.add(new Lore(Item.GLOWSTONE_DUST, "Glowstone Dust", "Obtained when mining", "a block of Glowstone.", "", "", "", "", 1));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.LoreBook, 1, 1), "Lore Book : Vol 2", "Contains information", "about blocks and", "items from the", "Nether", "", "", 1));
        GuiLore.lores.add(new Lore(AetherBlocks.Dirt, "Aether Dirt", "A paler dirt.", "Aether grass", "and skyroot trees", "will grow on it", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Grass, "Aether Grass", "A paler grass.", "Skyroot trees will", "grow on it.", "Allows Aether mobs", "to spawn", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherBlocks.Holystone, 1, 0), "Holystone", "Main material of", "the Aether.", "Makes holystone", "tools and", "enchanters", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Plank, "Skyroot Plank", "Made from skyroot.", "Used to make", "skyroot sticks and", "tools", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.SkyrootSapling, "Skyroot Sapling", "Dropped by ", "skyroot leaves.", "Plants a skyroot", "tree", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.GoldenOakSapling, "Golden Oak Sapling", "Dropped by golden", "oak leaves.", "Plants a golden", "oak", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Quicksoil, "Quicksoil", "Found at the edge", "of islands.", "Speeds up anything", "on it.", "Use it with blue", "clouds for epicness", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Log, "Skyroot Log", "Wood from skyroot", "trees.", "Makes skyroot", "planks", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Icestone, "Icestone", "Found in Holystone.", "Freezes water", "around it on", "placement", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.GravititeOre, "Gravitite Ore", "Found under big", "islands.", "Floats upwards,", "and can be made", "into tools.", "Can be enchanted", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.EnchantedGravitite, "Enchanted Gravitite", "Floats upwards", "when powered by", "redstone", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherBlocks.Holystone, 1, 3), "Mossy Holystone", "Found in dungeons.", "Decorative block", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherBlocks.Aercloud, 1, 1), "Blue Aercloud", "Found in clouds.", "When landed on,", "it will bounce you", "sky-high", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherBlocks.Aercloud, 1, 0), "Cold Aercloud", "Found in clouds.", "Stops fall damage", "when landed on", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherBlocks.Aercloud, 1, 2), "Gold Aercloud", "Found in clouds.", "Stops fall damage", "when landed on.", "Quite rare", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.AmbrosiumTorch, "Ambrosium Torch", "Made from skyroot", "sticks and", "ambrosium.", "Can be placed in", "the Aether", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.DungeonStone, "Dungeon Stone", "Found in dungeons.", "Decorative block", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.LightDungeonStone, "Lit Dungeon Stone", "Found in dungeons.", "Emits a faint light.", "Decorative block", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Pillar, "Pillar", "Found in silver", "dungeons.", "Decorative block", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Enchanter, "Enchanter", "Made from Zanite", "and Holystone.", "Enchants items", "and repairs tools", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Incubator, "Incubator", "Made from skyroot", "planks and", "Holystone.", "Incubates Moa", "eggs until they ", "hatch", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.ZaniteBlock, "Zanite Block", "Crafted with four", "Zanite Gemstones.", "Decorative block", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.PurpleFlower, "Purple Flower", "Common plant in", "the Aether.", "Can be crafted", "into dye.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.WhiteFlower, "White Flower", "Common plant in", "the Aether.", "Can be crafted", "into dye.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Freezer, "Freezer", "Allows you to", "freeze certain items.", "Uses Icestone as", "a fuel source.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.QuicksoilGlass, "Quicksoil Glass", "Gained by enchanting", "Quicksoil blocks.", "Translucent, gives", "off small amount", "of light.", "", 2));
        GuiLore.lores.add(new Lore(AetherBlocks.Aerogel, "Aerogel", "Found in dungeons.", "Incredibly high", "TNT resistance", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixBow, "Phoenix Bow", "Found in dungeons.", "Shoots flaming", "arrows that", "burn mobs", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.GummieSwet, "Gummy Swet", "Found in dungeons.", "Tasty swet", "flavoured", "gummy swets", "(May contain swet)", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordFire, "Fire Sword", "Found in dungeons.", "A sword imbued", "with the power of", "fire", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordLightning, "Lightning Sword", "Found in dungeons.", "A sword imbued", "with the power of", "lightning", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordHoly, "Holy Sword", "Found in dungeons.", "A holy sword that", "will deal extra", "damage to undead", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.HammerNotch, "Hammer of Notch", "Found in dungeons.", "A hammer that", "has a special", "attack which hits", "lots of mobs", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.LightningKnife, "Lightning Knife", "Found in dungeons.", "Throwable.", "Creates lightning", "on hit.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.PigSlayer, "Pig Slayer", "Found in Dungeons.", "Very good dagger.", "Kills pigs and", "pig zombies in", "1 hit", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.VictoryMedal, "Medallion", "Dropped by ", "Valkyries.", "A sign of victory", "from the Valkyries", "that you need to ", "fight the boss", 2));
        GuiLore.lores.add(new Lore(AetherItems.PickHolystone, "Holystone Pickaxe", "Digs holystone", "and Aether ores.", "Randomly gives", "extra ambrosium.", "Slow Speed", "Average Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.AxeHolystone, "Holystone Axe", "Chops Skyroot", "and Gilded Oak.", "Randomly gives", "extra ambrosium.", "Slow Speed", "Average Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.ShovelHolystone, "Holystone Shovel", "Digs Aether dirt,", "and quicksoil.", "Randomly gives", "extra ambrosium.", "Slow Speed", "Average Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordHolystone, "Holystone Sword", "For attacking", "mobs and animals.", "Randomly gives", "extra ambrosium.", "Slow Speed", "Average Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.PickSkyroot, "Skyroot Pickaxe", "Digs Holystone", "and Aether ores.", "Randomly gives", "double drops.", "Very Slow Speed", "Few Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.AxeSkyroot, "Skyroot Axe", "Chops Skyroot", "and Gilded Oak.", "Randomly gives", "double drops.", "Very Slow Speed", "Few Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.ShovelSkyroot, "Skyroot Shovel", "Digs Aether dirt,", "and quicksoil.", "Randomly gives", "double drops.", "Very Slow Speed", "Few Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordSkyroot, "Skyroot Sword", "For attacking", "mobs and animals.", "Randomly gives", "double drops.", "Very Slow Speed", "Few Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.PickZanite, "Zanite Pickaxe", "Digs Holystone", "and Aether ores.", "Power increases", "with damage.", "Normal Speed", "Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.AxeZanite, "Zanite Axe", "Chops Skyroot", "and Gilded Oak.", "Power increases", "with damage.", "Normal Speed", "Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.ShovelZanite, "Zanite Shovel", "Digs Aether dirt,", "and quicksoil.", "Power increases", "with damage.", "Normal Speed", "Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordZanite, "Zanite Sword", "For attacking", "mobs and animals.", "Power increases", "with damage.", "Normal Speed", "Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.PickGravitite, "Gravitite Pickaxe", "Digs Holystone", "and Aether ores.", "Right click will", "lift mobs.", "Fast Speed", "Very Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.AxeGravitite, "Gravitite Axe", "Chops Skyroot", "and Gilded Oak.", "Right click will", "lift mobs.", "Fast Speed", "Very Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.ShovelGravitite, "Gravitite Shovel", "Digs Aether dirt,", "and quicksoil.", "Right click will", "lift mobs.", "Fast Speed", "Very Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.SwordGravitite, "Gravitite Sword", "For attacking", "mobs and animals.", "Right click will", "lift mobs.", "Fast Speed", "Very Many Uses", 2));
        GuiLore.lores.add(new Lore(AetherItems.AmbrosiumShard, "Ambrosium Shard", "Found in Holystone.", "Makes Ambrosium", "Torches and is", "the fuel of the", "enchanter", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.Zanite, "Zanite Gem", "Found in Holystone.", "Makes Zanite tools", "and enchanters", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.Stick, "Skyroot Stick", "Made from skyroot", "planks.", "Vital crafting", "item for Aether", "tools", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Bucket, 1, 0), "Skyroot Bucket", "Made from skyroot.", "Can pick up water,", "milk and poison", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Bucket, 1, Block.FLOWING_WATER.id), "Skyroot Water Bucket", "A skyroot bucket", "full of water", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Bucket, 1, 1), "Skyroot Milk Bucket", "A skyroot bucket", "full of milk", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.GoldenAmber, "Golden Amber", "Dropped by golden", "oaks.", "Used to make", "golden darts", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.MoaEgg, "Moa Egg", "Laid by Moas.", "Place in an", "incubator to", "hatch  it", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Key, 1, 0), "Bronze Key", "Dropped by the", "Slider.", "Use it to gain", "access to the", "bronze treasure", "chest", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Key, 1, 1), "Silver Key", "Dropped by the", "Grand Valkyrie.", "Use it to gain", "access to the silver", "treasure chest", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Key, 1, 2), "Gold Key", "Dropped by the", "Sun Spririt", "Use it to gain", "access to the gold", "treasure chest", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.AechorPetal, "Aechor Petal", "Dropped by Aechor", "Plants.", "Used to tame and", "feed Moas", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.DartShooter, 1, 0), "Dart Shooter", "Found in Dungeons.", "Shoots gold darts", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.DartShooter, 1, 2), "Enchanted Shooter", "Shoots enchanted", "darts", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.DartShooter, 1, 1), "Poison Shooter", "Shoots poison", "darts", "", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Dart, 1, 0), "Golden Dart", "Found in Dungeons", "and crafted from", "golden orbs and", "skyroot sticks.", "Simplest ammo", "for the dart shooter", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Dart, 1, 1), "Enchanted Dart", "Found in Dungeons.", "Enchantable from", "Golden Darts.", "Has more attack", "than a golden dart", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Dart, 1, 2), "Poison Dart", "Found in Dungeons.", "Craftable from", "Golden Darts and", "poison buckets.", "Ammo for the dart", "shooter that poisons", 2));
        GuiLore.lores.add(new Lore(AetherItems.BlueMusicDisk, "Blue Music Disk", "Found in Dungeons.", "Can be played", "in jukeboxes.", "Plays the Aether", "tune", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Bucket, 1, 3), "Bucket of Remedy", "Enchantable from", "bucket of poison.", "Cures poison", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.Bucket, 1, 2), "Bucket of Posion", "Found in Dungeons.", "Obtainable from", "Aechor Plants.", "Used to make poison", "darts", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.CloudParachute, "Cloud Parachute", "Made from clouds.", "Will float the player", "gently down.", "Activates on click", "or when falling", "from the Aether", 2));
        GuiLore.lores.add(new Lore(AetherItems.CloudParachuteGold, "Gold Cloud Parachute", "Made from gold", "clouds.", "Similar to Cloud", "Parachute, but", "has 4 uses.", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.IronBubble, "Iron Bubble", "Found in dungeons.", "Allows you to", "breathe", "underwater forever", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.VampireBlade, "Vampire Blade", "Found in dungeons.", "Powerful sword", "that drains the", "health of anything", "hit", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.NatureStaff, "Nature Staff", "Made from sticks", "and Zanite.", "Allows you to", "control your moas", "by making them", "follow and halt", 2));
        GuiLore.lores.add(new Lore(AetherItems.CloudStaff, "Cloud Staff", "Found in Dungeons.", "Use this staff to", "summon two mini", "zephyrs which", "will shoot ice", "balls", 2));
        GuiLore.lores.add(new Lore(AetherItems.LifeShard, "Life Shard", "Found in Dungeons.", "Increases your", "maximum health by", "one heart", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.GoldenFeather, "Golden Feather", "Found in Dungeons.", "While holding this", "you will float", "gently to the ground", "and take no fall", "damage", 2));
        //GuiLore.lores.add(new Lore(AetherItems.RepShield, "Shield of Repulsion", "Found in Dungeons.", "Place it in shield", "slot.", "While standing still", "all projectiles", "will bounce off you", 2));
        GuiLore.lores.add(new Lore(AetherItems.Lance, "Lance", "Found in Dungeons.", "Powerful weapon", "with extended", "reach", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.AetherCape, "Swet Cape", "Found in Dungeons.", "Wear it as a cape.", "Purely asthetic", "item", "", "s", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZanitePendant, "Zanite Pendant", "Made from zanite.", "Wear it in your", "pendant slot.", "As it wears away", "it increases your", "mining speed", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteRing, "Zanite Ring", "Made from zanite.", "Wear it in your", "ring slot.", "As it wears away", "it increases your", "mining speed", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixHelm, "Phoenix Helmet", "Found in dungeons.", "Protects the ", "wearer from any", "fire or lava damage.", "Weak to water, but", "it holds a secret", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixBody, "Phoenix Chestplate", "Found in dungeons.", "Protects the ", "wearer from any", "fire or lava damage.", "Weak to water, but", "it holds a secret", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixLegs, "Phoenix Greaves", "Found in dungeons.", "Protects the ", "wearer from any", "fire or lava damage.", "Weak to water, but", "it holds a secret", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixBoots, "Phoenix Boots", "Found in dungeons.", "Protects the ", "wearer from any", "fire or lava damage.", "Weak to water, but", "it holds a secret", 2));
        GuiLore.lores.add(new Lore(AetherItems.HealingStone, "Healing Stone", "Gained by enchanting", "Holystone blocks.", "Heals four hearts", "of health.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.RedCape, "Red Cape", "Worn in cape", "slot.", "Purely decorative.", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.BlueCape, "Blue Cape", "Worn in cape", "slot.", "Purely decorative.", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.WhiteCape, "White Cape", "Worn in cape", "slot.", "Purely decorative.", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.YellowCape, "Yellow Cape", "Worn in cape", "slot.", "Purely decorative.", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.IcePendant, "Ice Pendant", "Worn in pendant", "slot.", "Freezes all water", "and lava sources", "around the player.", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.IceRing, "Ice Ring", "Worn in ring", "slots.", "Freezes all water", "and lava sources", "around the player.", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.AgilityCape, "Agility Cape", "Worn in cape", "slot.", "Gives the player", "the ability to", "walk up blocks", "without jumping.", 2));
        GuiLore.lores.add(new Lore(AetherItems.PickValkyrie, "Valkyrie Pickaxe", "A powerful pickaxe", "which once belonged", "to a Valkyrie.", "Has extended reach.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.AxeValkyrie, "Valkyrie Axe", "A powerful axe", "which once belonged", "to a Valkyrie.", "Has extended reach.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ShovelValkyrie, "Valkyrie Shovel", "A powerful shovel", "which once belonged", "to a Valkyrie.", "Has extended reach.", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ObsidianHelm, "Obsidian Helmet", "A cooled version of", "the Phoenix", "Helmet.", "Incredibly strong", "head armour", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ObsidianBody, "Obsidian Chestplate", "A cooled version of", "the Phoenix", "Chestplate.", "Incredibly strong", "chest armour", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ObsidianLegs, "Obsidian Greaves", "A cooled version of", "the Phoenix", "Leggings.", "Incredibly strong", "leg armour", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ObsidianBoots, "Obsidian Boots", "A cooled version of", "the Phoenix", "Boots.", "Incredibly strong", "foot armour", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteHelmet, "Zanite Helmet", "Wear it on your", "head.", "Good Protection.", "Provides better", "protection when", "more damaged", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteChestplate, "Zanite Chestplate", "Wear it on your", "chest.", "Good Protection.", "Provides better", "protection when", "more damaged", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteLeggings, "Zanite Greaves", "Wear it on your", "legs.", "Good Protection.", "Provides better", "protection when", "more damaged", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteBoots, "Zanite Boots", "Wear it on your", "feet.", "Good Protection.", "Provides better", "protection when", "more damaged", 2));
        GuiLore.lores.add(new Lore(AetherItems.GravititeHelmet, "Gravitite Helmet", "Wear it on your", "head.", "Great Protection.", "Full set stops", "fall damage and", "jumps higher", 2));
        GuiLore.lores.add(new Lore(AetherItems.GravititeBodyplate, "Gravitite Chestplate", "Wear it on your", "chest.", "Great Protection.", "Full set stops", "fall damage and", "jumps higher", 2));
        GuiLore.lores.add(new Lore(AetherItems.GravititePlatelegs, "Gravitite Greaves", "Wear it on your", "legs.", "Great Protection.", "Full set stops", "fall damage and", "jumps higher", 2));
        GuiLore.lores.add(new Lore(AetherItems.GravititeBoots, "Gravitite Boots", "Wear it on your", "feet.", "Great Protection.", "Full set stops", "fall damage and", "jumps higher", 2));
        GuiLore.lores.add(new Lore(AetherItems.NeptuneHelmet, "Neptune Helmet", "Found in dungeons.", "Wear it on your", "head.", "Great Protection.", "Full set speeds", "up water movement", 2));
        GuiLore.lores.add(new Lore(AetherItems.NeptuneChestplate, "Neptune Chestplate", "Found in dungeons.", "Wear it on your", "chest.", "Great Protection.", "Full set speeds", "up water movement", 2));
        GuiLore.lores.add(new Lore(AetherItems.NeptuneLeggings, "Neptune Greaves", "Found in dungeons.", "Wear it on your", "legs.", "Great Protection.", "Full set speeds", "up water movement", 2));
        GuiLore.lores.add(new Lore(AetherItems.NeptuneBoots, "Neptune Boots", "Found in dungeons.", "Wear it on your", "feet.", "Great Protection.", "Full set speeds", "up water movement", 2));
        GuiLore.lores.add(new Lore(AetherItems.ZaniteGlove, "Zanite Glove", "Wear them on your", "hands.", "Good Protection", "", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.GravititeGlove, "Gravitite Glove", "Wear them on your", "hands.", "Great Protection.", "Full set stops", "fall damage and", "jumps higher", 2));
        GuiLore.lores.add(new Lore(AetherItems.ObsidianGlove, "Obsidian Glove", "A cooled version of", "the Phoenix", "Glove.", "Incredibly strong", "hand armour", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.PhoenixGlove, "Phoenix Glove", "Found in dungeons.", "Protects the ", "wearer from any", "fire or lava damage.", "Weak to water, but", "it holds a secret", 2));
        GuiLore.lores.add(new Lore(AetherItems.NeptuneGlove, "Neptune Glove", "Found in dungeons.", "Wear them on your", "hands.", "Great Protection", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.RegenerationStone, "Regeneration Stone", "Use it in your", "accessory slots.", "Regenerates health", "over time", "", "", 2));
        GuiLore.lores.add(new Lore(AetherItems.InvisibilityCloak, "Invisibility Cloak", "Use it in your", "cloak slot.", "Makes you invisible", "", "", "", 2));
        GuiLore.lores.add(new Lore(new ItemStack(AetherItems.LoreBook, 1, 2), "Lore Book : Vol 3", "Contains information", "about blocks and", "items from the", "Aether", "", "", 2));
    }

    private final int type;

    public GuiLore(PlayerInventory inv, int i) {
        super(new ContainerLore(inv));
        this.type = i;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw("Book Of Lore", 37, 18, 4210752);
        this.textRenderer.draw("Volume " + (this.type + 1), 47, 28, 4210752);
        switch (this.type) {
            case 0: {
                this.textRenderer.draw("The Surface", 37, 38, 4210752);
                break;
            }
            case 1: {
                this.textRenderer.draw("The Nether", 37, 38, 4210752);
                break;
            }
            case 2: {
                this.textRenderer.draw("The Aether", 37, 38, 4210752);
                break;
            }
        }
        this.textRenderer.draw("Item : ", 46, 72, 4210752);
        final ItemStack item = ((ContainerLore) this.container).loreSlot.getStack(0);
        if (item != null) {
            for (final Lore lore : GuiLore.lores) {
                if (lore.equals(item) && lore.type == this.type) {
                    this.textRenderer.draw(lore.name, 134, 14, 4210752);
                    this.textRenderer.draw(lore.line1, 134, 28, 4210752);
                    this.textRenderer.draw(lore.line2, 134, 38, 4210752);
                    this.textRenderer.draw(lore.line3, 134, 48, 4210752);
                    this.textRenderer.draw(lore.line4, 134, 58, 4210752);
                    this.textRenderer.draw(lore.line5, 134, 68, 4210752);
                    this.textRenderer.draw(lore.line6, 134, 78, 4210752);
                    AetherAchievements.giveAchievement(AetherAchievements.lore, minecraft.player);
                    if (item.itemId == AetherItems.LoreBook.id) {
                        AetherAchievements.giveAchievement(AetherAchievements.loreception, minecraft.player);
                        break;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        this.container.onClosed(minecraft.player);
    }

    @Override
    protected void drawBackground(final float tickDelta) {
        this.backgroundWidth = 256;
        this.backgroundHeight = 195;
        final int i = minecraft.textureManager.getTextureId("/assets/aether/stationapi/textures/gui/lore.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.textureManager.bindTexture(i);
        final int j = (this.width - this.backgroundWidth) / 2;
        final int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(j, k, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    private Slot getSlotAt(int i, int j) {
        for (int var3 = 0; var3 < this.container.slots.size(); ++var3) {
            Slot var4 = (Slot) this.container.slots.get(var3);
            if (this.isPointOverSlot(var4, i, j)) {
                return var4;
            }
        }

        return null;
    }

    private boolean isPointOverSlot(Slot arg, int i, int j) {
        int var4 = (this.width - this.backgroundWidth) / 2;
        int var5 = (this.height - this.backgroundHeight) / 2;
        i -= var4;
        j -= var5;
        return i >= arg.x - 1 && i < arg.x + 16 + 1 && j >= arg.y - 1 && j < arg.y + 16 + 1;
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        if (k == 0 || k == 1) {
            Slot var4 = this.getSlotAt(i, j);
            int var5 = (this.width - this.backgroundWidth) / 2;
            int var6 = (this.height - this.backgroundHeight) / 2;
            boolean var7 = i < var5 || j < var6 || i >= var5 + this.backgroundWidth || j >= var6 + this.backgroundHeight;
            int var8 = -1;
            if (var4 != null) {
                var8 = var4.id;
            }

            if (var7) {
                var8 = -999;
            }

            if (var8 != -1) {
                boolean var9 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                ItemStack item = minecraft.interactionManager.clickSlot(this.container.syncId, var8, k, var9, minecraft.player);
                if (((Minecraft) FabricLoader.getInstance().getGameInstance()).isWorldRemote()) {
                    ((Slot) this.container.slots.get(0)).setStack(item);
                }
            }
        }
    }

    static class Lore {
        public ItemStack stack;
        public String name;
        public String line1;
        public String line2;
        public String line3;
        public String line4;
        public String line5;
        public String line6;
        public int type;

        public Lore(final ItemStack item, final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6, final int i) {
            this.stack = item;
            this.name = s;
            this.line1 = s1;
            this.line2 = s2;
            this.line3 = s3;
            this.line4 = s4;
            this.line5 = s5;
            this.line6 = s6;
            this.type = i;
        }

        public Lore(final int id, final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6, final int i) {
            this(new ItemStack(id, 1, -1), s, s1, s2, s3, s4, s5, s6, i);
        }

        public Lore(final Block block, final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6, final int i) {
            this(new ItemStack(block == null ? 0 : block.id, 1, -1), s, s1, s2, s3, s4, s5, s6, i);
        }

        public Lore(final Item item, final String s, final String s1, final String s2, final String s3, final String s4, final String s5, final String s6, final int i) {
            this(new ItemStack(item == null ? 0 : item.id, 1, -1), s, s1, s2, s3, s4, s5, s6, i);
        }

        public boolean equals(final Object other) {
            if (other == null) {
                return this.stack == null;
            }
            if (other instanceof Lore) {
                return ((Lore) other).stack.itemId == this.stack.itemId && (((Lore) other).stack.getDamage2() == this.stack.getDamage2() || this.stack.getDamage2() == -1);
            }
            return other instanceof ItemStack && ((ItemStack) other).itemId == this.stack.itemId && (((ItemStack) other).getDamage2() == this.stack.getDamage2() || this.stack.getDamage2() == -1);
        }
    }
}