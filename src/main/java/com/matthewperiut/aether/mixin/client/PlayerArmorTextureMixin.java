package com.matthewperiut.aether.mixin.client;

import com.matthewperiut.aether.item.util.CustomArmorTexture;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Substitutes Aether's custom worn-armor textures (CustomArmorTexture items) for the vanilla
 * /armor/<name>_<layer>.png path, leaving vanilla's armor-model setup untouched.
 * Replaces StationAPI's ArmorTextureProvider.
 */
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerArmorTextureMixin extends LivingEntityRenderer<PlayerEntity> {

    public PlayerArmorTextureMixin(EntityModel model, float shadowSize) {
        super(model, shadowSize);
    }

    @Redirect(method = "bindTexture(Lnet/minecraft/entity/player/PlayerEntity;IF)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;bindTexture(Ljava/lang/String;)V"))
    private void aether$customArmorTexture(PlayerEntityRenderer self, String vanillaPath, PlayerEntity player, int pass, float delta) {
        ItemStack stack = player.inventory.getArmorStack(3 - pass);
        if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof ArmorItem armor && item instanceof CustomArmorTexture custom) {
                this.bindTexture(custom.getArmorTexture(armor, pass == 2 ? 2 : 1));
                return;
            }
        }
        this.bindTexture(vanillaPath);
    }
}
