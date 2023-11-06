package com.matthewperiut.aether.blockentity.container;

import com.matthewperiut.aether.blockentity.block.BlockEntityFreezer;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerListener;
import net.minecraft.container.slot.FurnaceOutputSlot;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ContainerFreezer extends Container
{
    private BlockEntityFreezer freezer;
    private int cookTime = 0;
    private int burnTime = 0;
    private int itemBurnTime = 0;

    public ContainerFreezer(PlayerInventory inventoryplayer, BlockEntityFreezer tileentityfreezer)
    {
        this.freezer = tileentityfreezer;
        this.addSlot(new Slot(tileentityfreezer, 0, 56, 17));
        this.addSlot(new Slot(tileentityfreezer, 1, 56, 53));
        this.addSlot(new FurnaceOutputSlot(inventoryplayer.player, tileentityfreezer, 2, 116, 35));

        int j;
        for (j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(inventoryplayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }

    }

    protected void insertItem(ItemStack itemstack, int i, int j, boolean flag)
    {
    }

    public void tick()
    {
        super.tick();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            ContainerListener icrafting = (ContainerListener) this.listeners.get(i);
            if (this.cookTime != this.freezer.frozenTimeForItem)
            {
                icrafting.updateProperty(this, 0, this.freezer.frozenTimeForItem);
            }

            if (this.burnTime != this.freezer.frozenProgress)
            {
                icrafting.updateProperty(this, 1, this.freezer.frozenProgress);
            }

            if (this.itemBurnTime != this.freezer.frozenPowerRemaining)
            {
                icrafting.updateProperty(this, 2, this.freezer.frozenPowerRemaining);
            }
        }

        this.cookTime = this.freezer.frozenTimeForItem;
        this.burnTime = this.freezer.frozenProgress;
        this.itemBurnTime = this.freezer.frozenPowerRemaining;
    }

    public void setProperty(int i, int j)
    {
        if (i == 0)
        {
            this.freezer.frozenTimeForItem = j;
        }

        if (i == 1)
        {
            this.freezer.frozenProgress = j;
        }

        if (i == 2)
        {
            this.freezer.frozenPowerRemaining = j;
        }

    }

    public boolean canUse(PlayerEntity entityplayer)
    {
        return this.freezer.canPlayerUse(entityplayer);
    }

    public ItemStack transferSlot(int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.slots.get(i);
        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i == 2)
            {
                this.insertItem(itemstack1, 3, 39, true);
            }
            else if (i >= 3 && i < 30)
            {
                this.insertItem(itemstack1, 30, 39, false);
            }
            else if (i >= 30 && i < 39)
            {
                this.insertItem(itemstack1, 3, 30, false);
            }
            else
            {
                this.insertItem(itemstack1, 3, 39, false);
            }

            if (itemstack1.count == 0)
            {
                slot.setStack((ItemStack) null);
            }
            else
            {
                slot.markDirty();
            }

            if (itemstack1.count == itemstack.count)
            {
                return null;
            }

            slot.onCrafted(itemstack1);
        }

        return itemstack;
    }
}
