package com.matthewperiut.aether.blockentity.container;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerListener;
import net.minecraft.container.slot.FurnaceOutputSlot;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ContainerEnchanter extends Container {
    private final BlockEntityEnchanter enchanter;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public ContainerEnchanter(PlayerInventory inventoryplayer, BlockEntityEnchanter tileentityenchanter) {
        this.enchanter = tileentityenchanter;
        this.addSlot(new Slot(tileentityenchanter, 0, 56, 17));
        this.addSlot(new Slot(tileentityenchanter, 1, 56, 53));
        this.addSlot(new FurnaceOutputSlot(inventoryplayer.player, tileentityenchanter, 2, 116, 35));

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

    public void tick() {
        super.tick();

        for (int i = 0; i < this.listeners.size(); ++i) {
            ContainerListener icrafting = (ContainerListener) this.listeners.get(i);
            if (this.cookTime != this.enchanter.enchantTimeForItem) {
                icrafting.updateProperty(this, 0, this.enchanter.enchantTimeForItem);
            }

            if (this.burnTime != this.enchanter.enchantProgress) {
                icrafting.updateProperty(this, 1, this.enchanter.enchantProgress);
            }

            if (this.itemBurnTime != this.enchanter.enchantPowerRemaining) {
                icrafting.updateProperty(this, 2, this.enchanter.enchantPowerRemaining);
            }
        }

        this.cookTime = this.enchanter.enchantTimeForItem;
        this.burnTime = this.enchanter.enchantProgress;
        this.itemBurnTime = this.enchanter.enchantPowerRemaining;
    }

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

    public boolean canUse(PlayerEntity entityplayer) {
        return this.enchanter.canPlayerUse(entityplayer);
    }

    public ItemStack transferSlot(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
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

            slot.onCrafted(itemstack1);
        }

        return itemstack;
    }
}
