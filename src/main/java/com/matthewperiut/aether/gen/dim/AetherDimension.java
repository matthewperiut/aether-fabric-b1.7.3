package com.matthewperiut.aether.gen.dim;

import com.matthewperiut.aether.gen.biome.AetherBiomeSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.block.Block;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.source.WorldSource;
import net.modificationstation.stationapi.api.client.level.dimension.TravelMessageProvider;
import net.modificationstation.stationapi.api.registry.Identifier;

import static com.matthewperiut.aether.gen.dim.AetherDimensions.MOD_ID;

@EnvironmentInterface(value = EnvType.CLIENT, itf = TravelMessageProvider.class)
public class AetherDimension extends Dimension implements TravelMessageProvider {
    public static final String
            ENTERING_MESSAGE = "gui." + Identifier.of(MOD_ID, "ascending"),
            LEAVING_MESSAGE = "gui." + Identifier.of(MOD_ID, "descending");

    private final float[] colours = new float[4];

    public AetherDimension(int serialId) {
        id = serialId;
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new AetherBiomeSource(1);
    }

    @Override
    public WorldSource createWorldSource() {
        return new AetherWorldSource(world, world.getSeed());
    }

    @Override
    public float getSunPosition(long time, float delta) {
//        boolean hasKilledGold = ModLoader.getMinecraftInstance().statFileWriter.hasAchievementUnlocked(AetherAchievements.defeatGold);
//        if(hasKilledGold)
//        {
//            int timeTicks = (int)(l % 0x13880L);
//            float timeFraction = ((float)timeTicks + f) / 120000F - 0.25F;
//            if(timeTicks > 60000)
//            {
//                timeTicks -= 40000;
//                timeFraction = ((float)timeTicks + f) / 20000F - 0.25F;
//            }
//            if(timeFraction < 0.0F)
//            {
//                timeFraction++;
//            }
//            if(timeFraction > 1.0F)
//            {
//                timeFraction--;
//            }
//            float f2 = timeFraction;
//            timeFraction = 1.0F - (float)((Math.cos((double)timeFraction * 3.1415926535897931D) + 1.0D) / 2D);
//            timeFraction = f2 + (timeFraction - f2) / 3F;
//            return timeFraction;
//        } else
//        {
        return 0.0F;
//        }
    }

    @Override
    public float[] getSunHorizonColor(float time, float delta) {
        float f2 = 0.4F;
        float f3 = MathHelper.cos(time * 3.141593F * 2.0F) - 0.0F;
        float f4 = -0F;
        if (f3 >= f4 - f2 && f3 <= f4 + f2) {
            float f5 = ((f3 - f4) / f2) * 0.5F + 0.5F;
            float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * 3.141593F)) * 0.99F;
            f6 *= f6;
            colours[0] = f5 * 0.3F + 0.1F;
            colours[1] = f5 * f5 * 0.7F + 0.2F;
            colours[2] = f5 * f5 * 0.7F + 0.2F;
            colours[3] = f6;
            return colours;
        } else
            return null;
    }

    @Override
    public Vec3d getSkyColor(float time, float delta) {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(time * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F)
            f2 = 0.0F;
        if (f2 > 1.0F)
            f2 = 1.0F;
        float f3 = (float) (i >> 16 & 0xff) / 255F;
        float f4 = (float) (i >> 8 & 0xff) / 255F;
        float f5 = (float) (i & 0xff) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return Vec3d.from(f3, f4, f5);
    }

    @Override
    public boolean hasPaleSky() {
        return false;
    }

    @Override
    public float getCloudHeight() {
        return 8;
    }

    @Override
    public boolean canSpawnOn(int x, int y) {
        int var3 = this.world.getSurfaceBlockId(x, y);
        return var3 != 0 && Block.BY_ID[var3].material.blocksMovement();
    }

    @Override
    public boolean canPlayerSleep() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getEnteringTranslationKey() {
        return ENTERING_MESSAGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getLeavingTranslationKey() {
        return LEAVING_MESSAGE;
    }
}
