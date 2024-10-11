package com.matthewperiut.aether.blockentity.container;

import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;

public class ContainerIncubator extends ScreenHandler {
    private final BlockEntityIncubator incubator;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public ContainerIncubator(PlayerInventory inventoryplayer, BlockEntityIncubator tileentityIncubator) {
        this.incubator = tileentityIncubator;
        this.addSlot(new TileEntityIncubatorSlot(tileentityIncubator, 1, 73, 17));
        this.addSlot(new Slot(tileentityIncubator, 0, 73, 53));

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

    protected void insertItem(ItemStack itemstack, int i, int j, boolean flag) {
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        for (int i = 0; i < this.listeners.size(); ++i) {
            ScreenHandlerListener icrafting = (ScreenHandlerListener) this.listeners.get(i);

            if (this.burnTime != this.incubator.progress) {
                icrafting.onPropertyUpdate(this, 0, this.incubator.progress);
            }

            if (this.itemBurnTime != this.incubator.torchPower) {
                icrafting.onPropertyUpdate(this, 1, this.incubator.torchPower);
            }
        }

        this.burnTime = this.incubator.progress;
        this.itemBurnTime = this.incubator.torchPower;
    }

    @Override
    public void setProperty(int i, int j) {
        if (i == 0) {
            this.incubator.progress = j;
        }

        if (i == 1) {
            this.incubator.torchPower = j;
        }
    }

    public boolean canUse(PlayerEntity entityplayer) {
        return this.incubator.canPlayerUse(entityplayer);
    }

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

    static class TileEntityIncubatorSlot extends Slot {
        public TileEntityIncubatorSlot(Inventory inv, int slot, int x, int y) {
            super(inv, slot, x, y);
        }

        public int getMaxItemCount() {
            return 1;
        }
    }
}
