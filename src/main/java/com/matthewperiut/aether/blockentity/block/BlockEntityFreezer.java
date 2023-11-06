package com.matthewperiut.aether.blockentity.block;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityFreezer extends BlockEntity implements Inventory
{
    private static List<Frozen> frozen = new ArrayList();

    static
    {
        addFrozen(new ItemStack(Item.WATER_BUCKET, 1), new ItemStack(Block.ICE, 5), 500);
        addFrozen(new ItemStack(AetherItems.Bucket, 1, 8), new ItemStack(Block.ICE, 5), 500);
        addFrozen(new ItemStack(Item.LAVA_BUCKET, 1), new ItemStack(Block.OBSIDIAN, 2), 500);
        addFrozen(new ItemStack(AetherBlocks.Aercloud, 1, 0), new ItemStack(AetherBlocks.Aercloud, 1, 1), 50);
        addFrozen(new ItemStack(AetherItems.GoldPendant, 1), new ItemStack(AetherItems.IcePendant, 1), 2500);
        addFrozen(new ItemStack(AetherItems.GoldRing, 1), new ItemStack(AetherItems.IceRing, 1), 1500);
        addFrozen(new ItemStack(AetherItems.IronRing, 1), new ItemStack(AetherItems.IceRing, 1), 1500);
        addFrozen(new ItemStack(AetherItems.IronPendant, 1), new ItemStack(AetherItems.IcePendant, 1), 2500);
    }

    public int frozenProgress = 0;
    public int frozenPowerRemaining = 0;
    public int frozenTimeForItem = 0;
    private ItemStack[] frozenItemStacks = new ItemStack[3];
    private Frozen currentFrozen;

    public BlockEntityFreezer()
    {
    }

    public static void addFrozen(ItemStack from, ItemStack to, int i)
    {
        frozen.add(new Frozen(from, to, i));
    }

    public int getInventorySize()
    {
        return this.frozenItemStacks.length;
    }

    public ItemStack getInventoryItem(int i)
    {
        return this.frozenItemStacks[i];
    }

    public ItemStack takeInventoryItem(int i, int j)
    {
        if (this.frozenItemStacks[i] != null)
        {
            ItemStack itemstack1;
            if (this.frozenItemStacks[i].count <= j)
            {
                itemstack1 = this.frozenItemStacks[i];
                this.frozenItemStacks[i] = null;
                return itemstack1;
            }
            else
            {
                itemstack1 = this.frozenItemStacks[i].split(j);
                if (this.frozenItemStacks[i].count == 0)
                {
                    this.frozenItemStacks[i] = null;
                }

                return itemstack1;
            }
        }
        else
        {
            return null;
        }
    }

    public void setInventoryItem(int i, ItemStack itemstack)
    {
        this.frozenItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxItemCount())
        {
            itemstack.count = this.getMaxItemCount();
        }

    }

    public String getContainerName()
    {
        return "Freezer";
    }

    public void readNBT(CompoundTag nbttagcompound)
    {
        super.readNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Items");
        this.frozenItemStacks = new ItemStack[this.getInventorySize()];

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.get(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.frozenItemStacks.length)
            {
                this.frozenItemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        this.frozenProgress = nbttagcompound.getShort("BurnTime");
        this.frozenTimeForItem = nbttagcompound.getShort("CookTime");
    }

    public void writeNBT(CompoundTag nbttagcompound)
    {
        super.writeNBT(nbttagcompound);
        nbttagcompound.put("BurnTime", (short) this.frozenProgress);
        nbttagcompound.put("CookTime", (short) this.frozenTimeForItem);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < this.frozenItemStacks.length; ++i)
        {
            if (this.frozenItemStacks[i] != null)
            {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte) i);
                this.frozenItemStacks[i].writeNBT(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getMaxItemCount()
    {
        return 64;
    }

    public int getCookProgressScaled(int i)
    {
        return this.frozenTimeForItem == 0 ? 0 : this.frozenProgress * i / this.frozenTimeForItem;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        return this.frozenPowerRemaining * i / 500;
    }

    public boolean isBurning()
    {
        return this.frozenPowerRemaining > 0;
    }

    public void tick()
    {
        if (this.frozenPowerRemaining > 0)
        {
            --this.frozenPowerRemaining;
            if (this.currentFrozen != null)
            {
                ++this.frozenProgress;
            }
        }

        if (this.currentFrozen != null && (this.frozenItemStacks[0] == null || this.frozenItemStacks[0].itemId != this.currentFrozen.frozenFrom.itemId))
        {
            this.currentFrozen = null;
            this.frozenProgress = 0;
        }

        if (this.currentFrozen != null && this.frozenProgress >= this.currentFrozen.frozenPowerNeeded)
        {
            if (this.frozenItemStacks[2] == null)
            {
                this.setInventoryItem(2, new ItemStack(this.currentFrozen.frozenTo.getItem(), 1, this.currentFrozen.frozenTo.getMeta()));
            }
            else
            {
                this.setInventoryItem(2, new ItemStack(this.currentFrozen.frozenTo.getItem(), this.getInventoryItem(2).count + 1, this.currentFrozen.frozenTo.getMeta()));
            }

            if (this.getInventoryItem(0).itemId != Item.WATER_BUCKET.id && this.getInventoryItem(0).itemId != Item.LAVA_BUCKET.id)
            {
                if (this.getInventoryItem(0).itemId == AetherItems.Bucket.id)
                {
                    this.setInventoryItem(0, new ItemStack(AetherItems.Bucket));
                }
                else
                {
                    this.takeInventoryItem(0, 1);
                }
            }
            else
            {
                this.setInventoryItem(0, new ItemStack(Item.BUCKET));
            }

            this.frozenProgress = 0;
            this.currentFrozen = null;
            this.frozenTimeForItem = 0;
        }

        if (this.frozenPowerRemaining <= 0 && this.currentFrozen != null && this.getInventoryItem(1) != null && this.getInventoryItem(1).itemId == AetherBlocks.Icestone.id)
        {
            this.frozenPowerRemaining += 500;
            this.takeInventoryItem(1, 1);
        }

        if (this.currentFrozen == null)
        {
            ItemStack itemstack = this.getInventoryItem(0);

            for (int i = 0; i < frozen.size(); ++i)
            {
                if (itemstack != null && frozen.get(i) != null && itemstack.itemId == ((Frozen) frozen.get(i)).frozenFrom.itemId && itemstack.getMeta() == ((Frozen) frozen.get(i)).frozenFrom.getMeta())
                {
                    if (this.frozenItemStacks[2] == null)
                    {
                        this.currentFrozen = (Frozen) frozen.get(i);
                        this.frozenTimeForItem = this.currentFrozen.frozenPowerNeeded;
                    }
                    else if (this.frozenItemStacks[2].itemId == ((Frozen) frozen.get(i)).frozenTo.itemId && ((Frozen) frozen.get(i)).frozenTo.getItem().getMaxStackSize() > this.frozenItemStacks[2].count)
                    {
                        this.currentFrozen = (Frozen) frozen.get(i);
                        this.frozenTimeForItem = this.currentFrozen.frozenPowerNeeded;
                    }
                }
            }
        }

    }

    public boolean canPlayerUse(PlayerEntity entityplayer)
    {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this)
        {
            return false;
        }
        else
        {
            return entityplayer.squaredDistanceTo((double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5) <= 64.0;
        }
    }

    static class Frozen
    {
        public ItemStack frozenFrom;
        public ItemStack frozenTo;
        public int frozenPowerNeeded;

        public Frozen(ItemStack from, ItemStack to, int i)
        {
            this.frozenFrom = from;
            this.frozenTo = to;
            this.frozenPowerNeeded = i;
        }
    }

}
