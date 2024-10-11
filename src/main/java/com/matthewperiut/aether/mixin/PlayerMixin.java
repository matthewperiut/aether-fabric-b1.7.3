package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.util.AetherPlayerBooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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

    @Inject(method = "readNbt", at = @At("TAIL"))
    public void readAdditional(NbtCompound tag, CallbackInfo ci) {
        if (tag.contains("AetherBook")) {
            hasGottenAetherBook = tag.getBoolean("AetherBook");
        }
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    public void writeAdditional(NbtCompound tag, CallbackInfo ci) {
        if (hasGottenAetherBook) {
            tag.putBoolean("AetherBook", hasGottenAetherBook);
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
