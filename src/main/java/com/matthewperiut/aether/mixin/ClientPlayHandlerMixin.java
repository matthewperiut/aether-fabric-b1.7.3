package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.achievement.AetherAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AchievementWidget;
import net.minecraft.stat.achievement.Achievement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractClientPlayerEntity.class)
public class ClientPlayHandlerMixin
{
    @Shadow
    protected Minecraft client;

    @Redirect(method = "increaseStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/AchievementWidget;setAchievementGet(Lnet/minecraft/stat/achievement/Achievement;)V"))
    public void onIncreaseStat(AchievementWidget instance, Achievement achievement)
    {
        System.out.println(achievement.id);
        int achievementId = achievement.id - 5242880;
        if (achievementId >= AetherAchievements.acOff && achievementId <= AetherAchievements.acOff + 10)
        {
            if (achievementId == AetherAchievements.acOff + 1)
            {
                // bronze
                client.soundHelper.playSound("aether:other.achievement.achievementbronze", 1.0F, 1.0F);
            }
            else if (achievementId == AetherAchievements.acOff + 2)
            {
                // silver
                client.soundHelper.playSound("aether:other.achievement.achievementsilver", 1.0F, 1.0F);
            }
            else if (achievementId == AetherAchievements.acOff + 3)
            {
                // gold
                client.soundHelper.playSound("aether:other.achievement.achievementgold", 1.0F, 1.0F);
            }
            else
            {
                client.soundHelper.playSound("aether:other.achievement.achievementgen", 1.0F, 1.0F);
            }
        }
        instance.setAchievementGet(achievement);
    }
}
