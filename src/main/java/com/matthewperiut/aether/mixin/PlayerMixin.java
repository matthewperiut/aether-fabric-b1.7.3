package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.util.AetherPlayerBooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract public class PlayerMixin extends Entity implements AetherPlayerBooks {
    @Shadow
    public abstract void dropItem(ItemStack arg);

    @Shadow
    public PlayerInventory inventory;

    public PlayerMixin(World arg) {
        super(arg);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    protected void initDataTrackerPoisonTime(CallbackInfo ci) {
        dataTracker.startTracking(29, (int) 0);
    }

    @Unique
    boolean hasGottenAetherBook = false;

    @Inject(method = "readAdditional", at = @At("TAIL"))
    public void readAdditional(CompoundTag tag, CallbackInfo ci) {
        if (tag.containsKey("AetherBook")) {
            hasGottenAetherBook = tag.getBoolean("AetherBook");
        }
    }

    @Inject(method = "writeAdditional", at = @At("TAIL"))
    public void writeAdditional(CompoundTag tag, CallbackInfo ci) {
        if (hasGottenAetherBook) {
            tag.put("AetherBook", hasGottenAetherBook);
        }
    }

    public void giveAetherBook() {
        ItemStack book = new ItemStack(AetherItems.LoreBook, 1, 2);
        if (!hasGottenAetherBook) {
            if (this.inventory.addStack(book))
                hasGottenAetherBook = true;
        }
    }
}
