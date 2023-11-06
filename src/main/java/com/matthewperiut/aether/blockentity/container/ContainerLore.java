package com.matthewperiut.aether.blockentity.container;

import net.minecraft.client.inventory.ClientInventory;
import net.minecraft.container.Container;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ContainerLore extends Container
{
    public Inventory loreSlot = new ClientInventory("Lore Item", 1);

    public ContainerLore(PlayerInventory inventoryplayer)
    {
        this.addSlot(new Slot(this.loreSlot, 0, 82, 66));

        int j1;
        for (j1 = 0; j1 < 3; ++j1)
        {
            for (int l1 = 0; l1 < 9; ++l1)
            {
                this.addSlot(new Slot(inventoryplayer, l1 + j1 * 9 + 9, 48 + l1 * 18, 113 + j1 * 18));
            }
        }

        for (j1 = 0; j1 < 9; ++j1)
        {
            this.addSlot(new Slot(inventoryplayer, j1, 48 + j1 * 18, 171));
        }

    }

    protected void insertItem(ItemStack itemstack, int i, int j, boolean flag)
    {
    }

    public void onClosed(PlayerEntity entityplayer)
    {
        super.onClosed(entityplayer);
        ItemStack itemstack = this.loreSlot.getInventoryItem(0);
        if (itemstack != null)
        {
            entityplayer.dropItem(itemstack);
        }

    }

    public boolean canUse(PlayerEntity entityplayer)
    {
        return true;
    }

    public ItemStack transferSlot(int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.slots.get(i);
        if (slot != null && slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i == 0)
            {
                this.insertItem(itemstack1, 10, 46, true);
            }
            else if (i >= 10 && i < 37)
            {
                this.insertItem(itemstack1, 37, 46, false);
            }
            else if (i >= 37 && i < 46)
            {
                this.insertItem(itemstack1, 10, 37, false);
            }
            else
            {
                this.insertItem(itemstack1, 10, 46, false);
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
