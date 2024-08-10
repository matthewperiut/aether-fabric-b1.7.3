package com.matthewperiut.aether.blockentity.container;

import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import net.minecraft.class_633;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

public class ContainerFreezer extends ScreenHandler {
    private final BlockEntityFreezer freezer;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public ContainerFreezer(PlayerInventory inventoryplayer, BlockEntityFreezer tileentityfreezer) {
        this.freezer = tileentityfreezer;
        this.addSlot(new Slot(tileentityfreezer, 0, 56, 17));
        this.addSlot(new Slot(tileentityfreezer, 1, 56, 53));
        this.addSlot(new FurnaceOutputSlot(inventoryplayer.player, tileentityfreezer, 2, 116, 35));

        int j;
        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventoryplayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    @Override
    protected void insertItem(ItemStack itemstack, int i, int j, boolean flag) {
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        for (int i = 0; i < this.listeners.size(); ++i) {
            // class_633 -> ScreenHandlerListener
            class_633 icrafting = (class_633) this.listeners.get(i);
            if (this.cookTime != this.freezer.frozenTimeForItem) {
                // method_2099 -> onPropertyUpdate
                icrafting.method_2099(this, 0, this.freezer.frozenTimeForItem);
            }

            if (this.burnTime != this.freezer.frozenProgress) {
                icrafting.method_2099(this, 1, this.freezer.frozenProgress);
            }

            if (this.itemBurnTime != this.freezer.frozenPowerRemaining) {
                icrafting.method_2099(this, 2, this.freezer.frozenPowerRemaining);
            }
        }

        this.cookTime = this.freezer.frozenTimeForItem;
        this.burnTime = this.freezer.frozenProgress;
        this.itemBurnTime = this.freezer.frozenPowerRemaining;
    }

    @Override
    public void setProperty(int i, int j) {
        if (i == 0) {
            this.freezer.frozenTimeForItem = j;
        }

        if (i == 1) {
            this.freezer.frozenProgress = j;
        }

        if (i == 2) {
            this.freezer.frozenPowerRemaining = j;
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.freezer.canPlayerUse(player);
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
