package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.container.slot.CraftingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public class CraftingResultSlotMixin {
    @Shadow
    private PlayerEntity player;

    @Inject(method = "onCrafted", at = @At("HEAD"))
    public void achievementCraftingCatcher(ItemStack stack, CallbackInfo ci) {
        if (stack.itemId == AetherBlocks.Enchanter.id)
            AetherAchievements.giveAchievement(AetherAchievements.enchanter, player);
        if (stack.itemId == AetherItems.SwordGravitite.id ||
                stack.itemId == AetherItems.PickGravitite.id ||
                stack.itemId == AetherItems.AxeGravitite.id ||
                stack.itemId == AetherItems.ShovelGravitite.id) {
            AetherAchievements.giveAchievement(AetherAchievements.gravTools, player);
        }
    }
}
