package com.matthewperiut.aether.achievement;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.block.Holystone;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.gui.screen.achievement.AchievementPage;

import java.util.Random;

import static com.matthewperiut.aether.achievement.AetherAchievements.MOD_ID;

public class AetherACPage extends AchievementPage {
    public AetherACPage() {
        super(MOD_ID, "Aether");
    }

    @Override
    public int getBackgroundTexture(final Random random, final int i, final int j, int randomizedRow, int currentTexture) {
        int k = Block.SAND.texture;
        final int l = randomizedRow;
        if (l > 37 || j == 35) {
            k = AetherBlocks.Aercloud.texture;
        } else if (l == 22) {
            k = ((random.nextInt(2) != 0) ? Holystone.sprMossy : AetherBlocks.GravititeOre.texture);
        } else if (l == 10) {
            k = AetherBlocks.ZaniteOre.texture;
        } else if (l == 8) {
            k = AetherBlocks.AmbrosiumOre.texture;
        } else if (l > 4) {
            k = Holystone.sprNormal;
        } else if (l > 0) {
            k = AetherBlocks.Dirt.texture;
        }
        return k;
    }
}
