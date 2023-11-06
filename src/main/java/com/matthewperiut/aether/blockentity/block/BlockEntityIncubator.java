package com.matthewperiut.aether.blockentity.block;

import com.matthewperiut.aether.achievement.AetherAchievements;
import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

public class BlockEntityIncubator extends BlockEntity implements Inventory {
    public int torchPower;
    public int progress = 0;
    private ItemStack[] IncubatorItemStacks = new ItemStack[2];

    public BlockEntityIncubator() {
    }

    public int getInventorySize() {
        return this.IncubatorItemStacks.length;
    }

    public ItemStack getInventoryItem(int i) {
        return this.IncubatorItemStacks[i];
    }

    public ItemStack takeInventoryItem(int i, int j) {
        if (this.IncubatorItemStacks[i] != null) {
            ItemStack itemstack1;
            if (this.IncubatorItemStacks[i].count <= j) {
                itemstack1 = this.IncubatorItemStacks[i];
                this.IncubatorItemStacks[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.IncubatorItemStacks[i].split(j);
                if (this.IncubatorItemStacks[i].count == 0) {
                    this.IncubatorItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventoryItem(int i, ItemStack itemstack) {
        this.IncubatorItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxItemCount()) {
            itemstack.count = this.getMaxItemCount();
        }

    }

    public String getContainerName() {
        return "Incubator";
    }

    public void readNBT(CompoundTag nbttagcompound) {
        super.readNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Items");
        this.IncubatorItemStacks = new ItemStack[this.getInventorySize()];

        for (int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.get(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.IncubatorItemStacks.length) {
                this.IncubatorItemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        this.progress = nbttagcompound.getShort("BurnTime");
    }

    public void writeNBT(CompoundTag nbttagcompound) {
        super.writeNBT(nbttagcompound);
        nbttagcompound.put("BurnTime", (short) this.progress);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < this.IncubatorItemStacks.length; ++i) {
            if (this.IncubatorItemStacks[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte) i);
                this.IncubatorItemStacks[i].writeNBT(nbttagcompound1);
                nbttaglist.add(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getMaxItemCount() {
        return 64;
    }

    public int getCookProgressScaled(int i) {
        return this.progress * i / 6000;
    }

    public int getBurnTimeRemainingScaled(int i) {
        return this.torchPower * i / 500;
    }

    public boolean isBurning() {
        return this.torchPower > 0;
    }

    public void tick() {
        if (this.torchPower > 0) {
            --this.torchPower;
            if (this.getInventoryItem(1) != null) {
                ++this.progress;
            }
        }

        if (this.IncubatorItemStacks[1] == null || this.IncubatorItemStacks[1].itemId != AetherItems.MoaEgg.id) {
            this.progress = 0;
        }

        if (this.progress >= 6000) {
            if (this.IncubatorItemStacks[1] != null) {
                // todo: entity EntityMoa moa = new EntityMoa(this.world, true, false, false, MoaColour.getColour(this.IncubatorItemStacks[1].getMeta()));
                // moa.setPosition((double)this.x + 0.5, (double)this.y + 1.5, (double)this.z + 0.5);
                // this.world.spawnEntity(moa);
            }

            PlayerEntity player = world.getClosestPlayer(x, y, z, 100);
            if (player != null)
                AetherAchievements.giveAchievement(AetherAchievements.incubator, player);
            this.takeInventoryItem(1, 1);
            this.progress = 0;
        }

        if (this.torchPower <= 0 && this.IncubatorItemStacks[1] != null && this.IncubatorItemStacks[1].itemId == AetherItems.MoaEgg.id && this.getInventoryItem(0) != null && this.getInventoryItem(0).itemId == AetherBlocks.AmbrosiumTorch.id) {
            this.torchPower += 1000;
            this.takeInventoryItem(0, 1);
        }

    }

    public boolean canPlayerUse(PlayerEntity entityplayer) {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return entityplayer.squaredDistanceTo((double) this.x + 0.5, (double) this.y + 0.5, (double) this.z + 0.5) <= 64.0;
        }
    }
}
