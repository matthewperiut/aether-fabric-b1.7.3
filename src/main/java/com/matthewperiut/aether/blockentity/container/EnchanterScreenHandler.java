package com.matthewperiut.aether.blockentity.container;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import net.minecraft.class_633;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

public class EnchanterScreenHandler extends ScreenHandler {
    private final BlockEntityEnchanter enchanter;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public EnchanterScreenHandler(PlayerInventory inventory, BlockEntityEnchanter enchanter) {
        this.enchanter = enchanter;
        this.addSlot(new Slot(enchanter, 0, 56, 17));
        this.addSlot(new Slot(enchanter, 1, 56, 53));
        this.addSlot(new FurnaceOutputSlot(inventory.player, enchanter, 2, 116, 35));
        int j;
        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }

    }

    @Override
    protected void insertItem(ItemStack stack, int i, int j, boolean flag) {
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        // class_633 -> ScreenHandlerListener
        for (int i = 0; i < this.listeners.size(); ++i) {
            class_633 icrafting = (class_633) this.listeners.get(i);
            if (this.cookTime != this.enchanter.enchantTimeForItem) {
                icrafting.method_2099(this, 0, this.enchanter.enchantTimeForItem);
            }

            if (this.burnTime != this.enchanter.enchantProgress) {
                icrafting.method_2099(this, 1, this.enchanter.enchantProgress);
            }

            if (this.itemBurnTime != this.enchanter.enchantPowerRemaining) {
                icrafting.method_2099(this, 2, this.enchanter.enchantPowerRemaining);
            }
        }

        this.cookTime = this.enchanter.enchantTimeForItem;
        this.burnTime = this.enchanter.enchantProgress;
        this.itemBurnTime = this.enchanter.enchantPowerRemaining;
    }

    @Override
    public void setProperty(int i, int j) {
        if (i == 0) {
            this.enchanter.enchantTimeForItem = j;
        }

        if (i == 1) {
            this.enchanter.enchantProgress = j;
        }

        if (i == 2) {
            this.enchanter.enchantPowerRemaining = j;
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.enchanter.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.slots.get(i);
        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i == 2) {
                this.insertItem(itemstack1, 3, 39, true);
            } else if (i >= 3 && i < 30) {
                this.insertItem(itemstack1, 30, 39, false);
            } else if (i >= 30 && i < 39) {
                this.insertItem(itemstack1, 3, 30, false);
            } else {
                this.insertItem(itemstack1, 3, 39, false);
            }

            if (itemstack1.count == 0) {
                slot.setStack(null);
            } else {
                slot.markDirty();
            }

            if (itemstack1.count == itemstack.count) {
                return null;
            }

            slot.onTakeItem(itemstack1);
        }

        return itemstack;
    }
}
