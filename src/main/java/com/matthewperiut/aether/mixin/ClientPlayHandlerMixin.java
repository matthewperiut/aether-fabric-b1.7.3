package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.achievement.AetherAchievements;
import net.minecraft.achievement.Achievement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.toast.AchievementToast;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayHandlerMixin {
    @Shadow
    protected Minecraft minecraft;

    @Redirect(method = "increaseStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/toast/AchievementToast;set(Lnet/minecraft/achievement/Achievement;)V"))
    public void onIncreaseStat(AchievementToast instance, Achievement achievement) {
        int achievementId = achievement.id - 5242880;
        if (achievementId >= AetherAchievements.acOff && achievementId <= AetherAchievements.acOff + 10) {
            if (achievementId == AetherAchievements.acOff + 1) {
                // bronze
                minecraft.soundManager.playSound("aether:other.achievement.achievementbronze", 1.0F, 1.0F);
            } else if (achievementId == AetherAchievements.acOff + 2) {
                // silver
                minecraft.soundManager.playSound("aether:other.achievement.achievementsilver", 1.0F, 1.0F);
            } else if (achievementId == AetherAchievements.acOff + 3) {
                // gold
                minecraft.soundManager.playSound("aether:other.achievement.achievementgold", 1.0F, 1.0F);
            } else {
                minecraft.soundManager.playSound("aether:other.achievement.achievementgen", 1.0F, 1.0F);
            }
        }
        instance.set(achievement);
    }
}
