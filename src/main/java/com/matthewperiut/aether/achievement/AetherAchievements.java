package com.matthewperiut.aether.achievement;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.achievement.Achievement;
import net.modificationstation.stationapi.api.client.gui.screen.achievement.AchievementPage;
import net.modificationstation.stationapi.api.event.achievement.AchievementRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class AetherAchievements {
    @Entrypoint.Namespace
    public static final Namespace MOD_ID = Null.get();
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
        player.incrementStat(achievement);
    }

    @EventListener
    private void registerAchievements(AchievementRegisterEvent event) {
        // works sp and mp
        enterAether = (new Achievement(acOff, "aether:enterAether", 0, 0, Block.GLOWSTONE, null)).register();
        // todo: entities
        defeatBronze = (new Achievement(acOff + 1, "aether:defeatBronze", -2, 3, new ItemStack(AetherItems.Key, 1, 0), enterAether)).register();
        // todo: entities
        defeatSilver = (new Achievement(acOff + 2, "aether:defeatSilver", 0, 4, new ItemStack(AetherItems.Key, 1, 1), enterAether)).register();
        // todo: entities
        defeatGold = (new Achievement(acOff + 3, "aether:defeatGold", 2, 3, new ItemStack(AetherItems.Key, 1, 2), enterAether)).register();
        // works sp and mp
        enchanter = (new Achievement(acOff + 4, "aether:enchanter", 2, 1, AetherBlocks.Enchanter, enterAether)).register();
        // works sp and mp
        incubator = (new Achievement(acOff + 5, "aether:incubator", 2, -1, AetherBlocks.Incubator, enterAether)).register();
        // works sp and mp
        blueCloud = (new Achievement(acOff + 6, "aether:blueCloud", -2, -1, new ItemStack(AetherBlocks.Aercloud, 1, 1), enterAether)).register();
        // todo: entities
        flyingPig = (new Achievement(acOff + 7, "aether:flyingPig", -2, 1, Item.SADDLE, enterAether)).register();
        // works sp and mp
        gravTools = (new Achievement(acOff + 8, "aether:gravTools", -1, -3, AetherItems.PickGravitite, enterAether)).register();
        // works sp
        lore = (new Achievement(acOff + 9, "aether:lore", 1, -3, Item.BOOK, enterAether)).register();
        // works sp
        loreception = (new Achievement(acOff + 10, "aether:loreception", 1, -5, Item.BOOK, lore)).register();
        event.achievements.add(AetherAchievements.enterAether);/*, "Hostile Paradise", "Ascend to the Aether");*/
        event.achievements.add(AetherAchievements.defeatBronze);/*, "Like a Bossaru!", "Defeat the bronze boss");*/
        event.achievements.add(AetherAchievements.defeatSilver);/*, "Dethroned", "Defeat the silver boss");*/
        event.achievements.add(AetherAchievements.defeatGold);/*, "Extinguished", "Defeat the gold boss");*/
        event.achievements.add(AetherAchievements.enchanter);/*, "Do you believe in magic?", "Craft an enchanter");*/
        event.achievements.add(AetherAchievements.incubator);/*, "Now you're family", "Incubate a moa");*/
        event.achievements.add(AetherAchievements.blueCloud);/*, "To infinity and beyond!", "Bounce on a blue cloud");*/
        event.achievements.add(AetherAchievements.flyingPig);/*, "When phygs fly!", "Fly on a phyg");*/
        event.achievements.add(AetherAchievements.gravTools);/*, "Pink is the new blue", "Craft a gravitite tool");*/
        event.achievements.add(AetherAchievements.lore);/*, "The more you know!", "Read a book of lore");*/
        event.achievements.add(AetherAchievements.loreception);/*, "Lore-ception", "It's a book in a book in a book in...");*/
        final AchievementPage page = new AetherACPage();
        page.addAchievements(enterAether, defeatBronze, defeatSilver, defeatGold, enchanter, incubator, gravTools, blueCloud, flyingPig, lore, loreception);
    }
}
