package com.matthewperiut.aether.achievement;

import com.matthewperiut.aether.Aether;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import com.periut.retroapi.achievement.RetroAchievements;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.achievement.Achievement;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherAchievements {
    public static final int acOff = 800;
    public static Achievement enterAether;
    public static Achievement defeatBronze;
    public static Achievement defeatSilver;
    public static Achievement defeatGold;
    public static Achievement enchanter;
    public static Achievement incubator;
    public static Achievement gravTools;
    public static Achievement blueCloud;
    public static Achievement flyingPig;
    public static Achievement lore;
    public static Achievement loreception;

    public static void giveAchievement(Achievement achievement, PlayerEntity player) {
        RetroAchievements.grant(achievement, player);
    }

    public static void registerAchievements() {
        enterAether = RetroAchievements.register(acOff, Aether.id("enterAether"), "aether:enterAether", 0, 0, Block.GLOWSTONE, null);
        defeatBronze = RetroAchievements.register(acOff + 1, Aether.id("defeatBronze"), "aether:defeatBronze", -2, 3, new ItemStack(AetherItems.Key, 1, 0), enterAether);
        defeatSilver = RetroAchievements.register(acOff + 2, Aether.id("defeatSilver"), "aether:defeatSilver", 0, 4, new ItemStack(AetherItems.Key, 1, 1), enterAether);
        defeatGold = RetroAchievements.register(acOff + 3, Aether.id("defeatGold"), "aether:defeatGold", 2, 3, new ItemStack(AetherItems.Key, 1, 2), enterAether);
        enchanter = RetroAchievements.register(acOff + 4, Aether.id("enchanter"), "aether:enchanter", 2, 1, AetherBlocks.Enchanter, enterAether);
        incubator = RetroAchievements.register(acOff + 5, Aether.id("incubator"), "aether:incubator", 2, -1, AetherBlocks.Incubator, enterAether);
        blueCloud = RetroAchievements.register(acOff + 6, Aether.id("blueCloud"), "aether:blueCloud", -2, -1, new ItemStack(AetherBlocks.Aercloud, 1, 1), enterAether);
        flyingPig = RetroAchievements.register(acOff + 7, Aether.id("flyingPig"), "aether:flyingPig", -2, 1, Item.SADDLE, enterAether);
        gravTools = RetroAchievements.register(acOff + 8, Aether.id("gravTools"), "aether:gravTools", -1, -3, AetherItems.PickGravitite, enterAether);
        lore = RetroAchievements.register(acOff + 9, Aether.id("lore"), "aether:lore", 1, -3, Item.BOOK, enterAether);
        loreception = RetroAchievements.register(acOff + 10, Aether.id("loreception"), "aether:loreception", 1, -5, Item.BOOK, lore);

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            AetherACPage.register(enterAether, defeatBronze, defeatSilver, defeatGold, enchanter, incubator, gravTools, blueCloud, flyingPig, lore, loreception);
        }
    }
}
