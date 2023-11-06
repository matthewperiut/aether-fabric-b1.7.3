package com.matthewperiut.aether.blockentity.block;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.util.ArrayList;
import java.util.List;

public class BlockEntityEnchanter extends BlockEntity implements Inventory
{
    private static List<Enchantment> enchantments = new ArrayList();

    static
    {
        addEnchantment(new ItemStack(AetherBlocks.GravititeOre, 1), new ItemStack(AetherBlocks.EnchantedGravitite, 1), 1000);
        addEnchantment(new ItemStack(AetherItems.PickSkyroot, 1), new ItemStack(AetherItems.PickSkyroot, 1), 250);
        addEnchantment(new ItemStack(AetherItems.SwordSkyroot, 1), new ItemStack(AetherItems.SwordSkyroot, 1), 250);
        addEnchantment(new ItemStack(AetherItems.ShovelSkyroot, 1), new ItemStack(AetherItems.ShovelSkyroot, 1), 200);
        addEnchantment(new ItemStack(AetherItems.AxeSkyroot, 1), new ItemStack(AetherItems.AxeSkyroot, 1), 200);
        addEnchantment(new ItemStack(AetherItems.PickHolystone, 1), new ItemStack(AetherItems.PickHolystone, 1), 600);
        addEnchantment(new ItemStack(AetherItems.SwordHolystone, 1), new ItemStack(AetherItems.SwordHolystone, 1), 600);
        addEnchantment(new ItemStack(AetherItems.ShovelHolystone, 1), new ItemStack(AetherItems.ShovelHolystone, 1), 500);
        addEnchantment(new ItemStack(AetherItems.AxeHolystone, 1), new ItemStack(AetherItems.AxeHolystone, 1), 500);
        addEnchantment(new ItemStack(AetherItems.PickZanite, 1), new ItemStack(AetherItems.PickZanite, 1), 2500);
        addEnchantment(new ItemStack(AetherItems.SwordZanite, 1), new ItemStack(AetherItems.SwordZanite, 1), 2500);
        addEnchantment(new ItemStack(AetherItems.ShovelZanite, 1), new ItemStack(AetherItems.ShovelZanite, 1), 2000);
        addEnchantment(new ItemStack(AetherItems.AxeZanite, 1), new ItemStack(AetherItems.AxeZanite, 1), 2000);
        addEnchantment(new ItemStack(AetherItems.PickGravitite, 1), new ItemStack(AetherItems.PickGravitite, 1), 6000);
        addEnchantment(new ItemStack(AetherItems.SwordGravitite, 1), new ItemStack(AetherItems.SwordGravitite, 1), 6000);
        addEnchantment(new ItemStack(AetherItems.ShovelGravitite, 1), new ItemStack(AetherItems.ShovelGravitite, 1), 5000);
        addEnchantment(new ItemStack(AetherItems.AxeGravitite, 1), new ItemStack(AetherItems.AxeGravitite, 1), 5000);
        addEnchantment(new ItemStack(AetherItems.Dart, 1, 0), new ItemStack(AetherItems.Dart, 1, 2), 250);
        addEnchantment(new ItemStack(AetherItems.Bucket, 1, 2), new ItemStack(AetherItems.Bucket, 1, 3), 1000);
        addEnchantment(new ItemStack(Item.RECORD_13, 1), new ItemStack(AetherItems.BlueMusicDisk, 1), 2500);
        addEnchantment(new ItemStack(Item.RECORD_CAT, 1), new ItemStack(AetherItems.BlueMusicDisk, 1), 2500);
        addEnchantment(new ItemStack(Item.LEATHER_HELMET, 1), new ItemStack(Item.LEATHER_HELMET, 1), 400);
        addEnchantment(new ItemStack(Item.LEATHER_CHESTPLATE, 1), new ItemStack(Item.LEATHER_CHESTPLATE, 1), 500);
        addEnchantment(new ItemStack(Item.LEATHER_LEGGINGS, 1), new ItemStack(Item.LEATHER_LEGGINGS, 1), 500);
        addEnchantment(new ItemStack(Item.LEATHER_BOOTS, 1), new ItemStack(Item.LEATHER_BOOTS, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_PICKAXE, 1), new ItemStack(Item.WOOD_PICKAXE, 1), 500);
        addEnchantment(new ItemStack(Item.WOOD_SHOVEL, 1), new ItemStack(Item.WOOD_SHOVEL, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_SWORD, 1), new ItemStack(Item.WOOD_SWORD, 1), 500);
        addEnchantment(new ItemStack(Item.WOOD_AXE, 1), new ItemStack(Item.WOOD_AXE, 1), 400);
        addEnchantment(new ItemStack(Item.WOOD_HOE, 1), new ItemStack(Item.WOOD_HOE, 1), 300);
        addEnchantment(new ItemStack(Item.STONE_PICKAXE, 1), new ItemStack(Item.STONE_PICKAXE, 1), 1000);
        addEnchantment(new ItemStack(Item.STONE_SHOVEL, 1), new ItemStack(Item.STONE_SHOVEL, 1), 750);
        addEnchantment(new ItemStack(Item.STONE_SWORD, 1), new ItemStack(Item.STONE_SWORD, 1), 1000);
        addEnchantment(new ItemStack(Item.STONE_AXE, 1), new ItemStack(Item.STONE_AXE, 1), 750);
        addEnchantment(new ItemStack(Item.STONE_HOE, 1), new ItemStack(Item.STONE_HOE, 1), 750);
        addEnchantment(new ItemStack(Item.IRON_HELMENT, 1), new ItemStack(Item.IRON_HELMENT, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_CHESTPLATE, 1), new ItemStack(Item.IRON_CHESTPLATE, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_LEGGINGS, 1), new ItemStack(Item.IRON_LEGGINGS, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_BOOTS, 1), new ItemStack(Item.IRON_BOOTS, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_PICKAXE, 1), new ItemStack(Item.IRON_PICKAXE, 1), 2500);
        addEnchantment(new ItemStack(Item.IRON_SHOVEL, 1), new ItemStack(Item.IRON_SHOVEL, 1), 2000);
        addEnchantment(new ItemStack(Item.IRON_SWORD, 1), new ItemStack(Item.IRON_SWORD, 1), 2500);
        addEnchantment(new ItemStack(Item.IRON_AXE, 1), new ItemStack(Item.IRON_AXE, 1), 1500);
        addEnchantment(new ItemStack(Item.IRON_HOE, 1), new ItemStack(Item.IRON_HOE, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_HELMET, 1), new ItemStack(Item.GOLD_HELMET, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_CHESTPLATE, 1), new ItemStack(Item.GOLD_CHESTPLATE, 1), 1200);
        addEnchantment(new ItemStack(Item.GOLD_LEGGINGS, 1), new ItemStack(Item.GOLD_LEGGINGS, 1), 1200);
        addEnchantment(new ItemStack(Item.GOLD_BOOTS, 1), new ItemStack(Item.GOLD_BOOTS, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_PICKAXE, 1), new ItemStack(Item.GOLD_PICKAXE, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_SHOVEL, 1), new ItemStack(Item.GOLD_SHOVEL, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_SWORD, 1), new ItemStack(Item.GOLD_SWORD, 1), 1500);
        addEnchantment(new ItemStack(Item.GOLD_AXE, 1), new ItemStack(Item.GOLD_AXE, 1), 1000);
        addEnchantment(new ItemStack(Item.GOLD_HOE, 1), new ItemStack(Item.GOLD_HOE, 1), 1000);
        addEnchantment(new ItemStack(Item.DIAMOND_HELMET, 1), new ItemStack(Item.DIAMOND_HELMET, 1), 5000);
        addEnchantment(new ItemStack(Item.DIAMOND_CHESTPLATE, 1), new ItemStack(Item.DIAMOND_CHESTPLATE, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_LEGGINGS, 1), new ItemStack(Item.DIAMOND_LEGGINGS, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_BOOTS, 1), new ItemStack(Item.DIAMOND_BOOTS, 1), 5000);
        addEnchantment(new ItemStack(Item.DIAMOND_PICKAXE, 1), new ItemStack(Item.DIAMOND_PICKAXE, 1), 7000);
        addEnchantment(new ItemStack(Item.DIAMOND_SHOVEL, 1), new ItemStack(Item.DIAMOND_SHOVEL, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_SWORD, 1), new ItemStack(Item.DIAMOND_SWORD, 1), 6500);
        addEnchantment(new ItemStack(Item.DIAMOND_AXE, 1), new ItemStack(Item.DIAMOND_AXE, 1), 6000);
        addEnchantment(new ItemStack(Item.DIAMOND_HOE, 1), new ItemStack(Item.DIAMOND_HOE, 1), 6000);
        addEnchantment(new ItemStack(Item.FISHING_ROD, 1), new ItemStack(Item.FISHING_ROD, 1), 500);
        addEnchantment(new ItemStack(AetherBlocks.Quicksoil, 1), new ItemStack(AetherBlocks.QuicksoilGlass, 1), 250);
        addEnchantment(new ItemStack(AetherBlocks.Holystone, 1), new ItemStack(AetherItems.HealingStone, 1), 750);
        addEnchantment(new ItemStack(AetherItems.GravititeHelmet, 1), new ItemStack(AetherItems.GravititeHelmet, 1), 12000);
        addEnchantment(new ItemStack(AetherItems.GravititeBodyplate, 1), new ItemStack(AetherItems.GravititeBodyplate, 1), 20000);
        addEnchantment(new ItemStack(AetherItems.GravititePlatelegs, 1), new ItemStack(AetherItems.GravititePlatelegs, 1), 15000);
        addEnchantment(new ItemStack(AetherItems.GravititeBoots, 1), new ItemStack(AetherItems.GravititeBoots, 1), 12000);
        addEnchantment(new ItemStack(AetherItems.GravititeGlove, 1), new ItemStack(AetherItems.GravititeGlove, 1), 10000);
        addEnchantment(new ItemStack(AetherItems.ZaniteHelmet, 1), new ItemStack(AetherItems.ZaniteHelmet, 1), 6000);
        addEnchantment(new ItemStack(AetherItems.ZaniteChestplate, 1), new ItemStack(AetherItems.ZaniteChestplate, 1), 10000);
        addEnchantment(new ItemStack(AetherItems.ZaniteLeggings, 1), new ItemStack(AetherItems.ZaniteLeggings, 1), 8000);
        addEnchantment(new ItemStack(AetherItems.ZaniteBoots, 1), new ItemStack(AetherItems.ZaniteBoots, 1), 5000);
        addEnchantment(new ItemStack(AetherItems.ZaniteGlove, 1), new ItemStack(AetherItems.ZaniteGlove, 1), 4000);
        addEnchantment(new ItemStack(AetherItems.ZaniteRing, 1), new ItemStack(AetherItems.ZaniteRing, 1), 2000);
        addEnchantment(new ItemStack(AetherItems.ZanitePendant, 1), new ItemStack(AetherItems.ZanitePendant, 1), 2000);
        addEnchantment(new ItemStack(AetherItems.LeatherGlove, 1), new ItemStack(AetherItems.LeatherGlove, 1), 300);
        addEnchantment(new ItemStack(AetherItems.IronGlove, 1), new ItemStack(AetherItems.IronGlove, 1), 1200);
        addEnchantment(new ItemStack(AetherItems.GoldGlove, 1), new ItemStack(AetherItems.GoldGlove, 1), 800);
        addEnchantment(new ItemStack(AetherItems.DiamondGlove, 1), new ItemStack(AetherItems.DiamondGlove, 1), 4000);
        addEnchantment(new ItemStack(AetherItems.DartShooter, 1, 0), new ItemStack(AetherItems.DartShooter, 1, 2), 2000);
    }

    public int enchantProgress = 0;
    public int enchantPowerRemaining = 0;
    public int enchantTimeForItem = 0;
    private ItemStack[] enchanterItemStacks = new ItemStack[3];
    private Enchantment currentEnchantment;

    public BlockEntityEnchanter()
    {
    }

    public static void addEnchantment(ItemStack from, ItemStack to, int i)
    {
        enchantments.add(new Enchantment(from, to, i));
    }

    public int getInventorySize()
    {
        return this.enchanterItemStacks.length;
    }

    public ItemStack getInventoryItem(int i)
    {
        return this.enchanterItemStacks[i];
    }

    public ItemStack takeInventoryItem(int i, int j)
    {
        if (this.enchanterItemStacks[i] != null)
        {
            ItemStack itemstack1;
            if (this.enchanterItemStacks[i].count <= j)
            {
                itemstack1 = this.enchanterItemStacks[i];
                this.enchanterItemStacks[i] = null;
                return itemstack1;
            }
            else
            {
                itemstack1 = this.enchanterItemStacks[i].split(j);
                if (this.enchanterItemStacks[i].count == 0)
                {
                    this.enchanterItemStacks[i] = null;
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
        this.enchanterItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.count > this.getMaxItemCount())
        {
            itemstack.count = this.getMaxItemCount();
        }

    }

    public String getContainerName()
    {
        return "Enchanter";
    }

    public void readNBT(CompoundTag nbttagcompound)
    {
        super.readNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getListTag("Items");
        this.enchanterItemStacks = new ItemStack[this.getInventorySize()];

        for (int i = 0; i < nbttaglist.size(); ++i)
        {
            CompoundTag nbttagcompound1 = (CompoundTag) nbttaglist.get(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.enchanterItemStacks.length)
            {
                this.enchanterItemStacks[byte0] = new ItemStack(nbttagcompound1);
            }
        }

        this.enchantProgress = nbttagcompound.getShort("BurnTime");
        this.enchantTimeForItem = nbttagcompound.getShort("CookTime");
    }

    public void writeNBT(CompoundTag nbttagcompound)
    {
        super.writeNBT(nbttagcompound);
        nbttagcompound.put("BurnTime", (short) this.enchantProgress);
        nbttagcompound.put("CookTime", (short) this.enchantTimeForItem);
        ListTag nbttaglist = new ListTag();

        for (int i = 0; i < this.enchanterItemStacks.length; ++i)
        {
            if (this.enchanterItemStacks[i] != null)
            {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.put("Slot", (byte) i);
                this.enchanterItemStacks[i].writeNBT(nbttagcompound1);
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
        return this.enchantTimeForItem == 0 ? 0 : this.enchantProgress * i / this.enchantTimeForItem;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        return this.enchantPowerRemaining * i / 500;
    }

    public boolean isBurning()
    {
        return this.enchantPowerRemaining > 0;
    }

    public void tick()
    {
        if (this.enchantPowerRemaining > 0)
        {
            --this.enchantPowerRemaining;
            if (this.currentEnchantment != null)
            {
                ++this.enchantProgress;
            }
        }

        if (this.currentEnchantment != null && (this.enchanterItemStacks[0] == null || this.enchanterItemStacks[0].itemId != this.currentEnchantment.enchantFrom.itemId))
        {
            this.currentEnchantment = null;
            this.enchantProgress = 0;
        }

        if (this.currentEnchantment != null && this.enchantProgress >= this.currentEnchantment.enchantPowerNeeded)
        {
            if (this.enchanterItemStacks[2] == null)
            {
                this.setInventoryItem(2, new ItemStack(this.currentEnchantment.enchantTo.getItem(), 1, this.currentEnchantment.enchantTo.getMeta()));
            }
            else
            {
                this.setInventoryItem(2, new ItemStack(this.currentEnchantment.enchantTo.getItem(), this.getInventoryItem(2).count + 1, this.currentEnchantment.enchantTo.getMeta()));
            }

            this.takeInventoryItem(0, 1);
            this.enchantProgress = 0;
            this.currentEnchantment = null;
            this.enchantTimeForItem = 0;
        }

        if (this.enchantPowerRemaining <= 0 && this.currentEnchantment != null && this.getInventoryItem(1) != null && this.getInventoryItem(1).itemId == AetherItems.AmbrosiumShard.id)
        {
            this.enchantPowerRemaining += 500;
            this.takeInventoryItem(1, 1);
        }

        if (this.currentEnchantment == null)
        {
            ItemStack itemstack = this.getInventoryItem(0);

            for (int i = 0; i < enchantments.size(); ++i)
            {
                if (itemstack != null && enchantments.get(i) != null && itemstack.itemId == ((Enchantment) enchantments.get(i)).enchantFrom.itemId)
                {
                    if (this.enchanterItemStacks[2] == null)
                    {
                        this.currentEnchantment = (Enchantment) enchantments.get(i);
                        this.enchantTimeForItem = this.currentEnchantment.enchantPowerNeeded;
                    }
                    else if (this.enchanterItemStacks[2].itemId == ((Enchantment) enchantments.get(i)).enchantTo.itemId && ((Enchantment) enchantments.get(i)).enchantTo.getItem().getMaxStackSize() > this.enchanterItemStacks[2].count)
                    {
                        this.currentEnchantment = (Enchantment) enchantments.get(i);
                        this.enchantTimeForItem = this.currentEnchantment.enchantPowerNeeded;
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

    static class Enchantment
    {
        public ItemStack enchantFrom;
        public ItemStack enchantTo;
        public int enchantPowerNeeded;

        public Enchantment(ItemStack from, ItemStack to, int i)
        {
            this.enchantFrom = from;
            this.enchantTo = to;
            this.enchantPowerNeeded = i;
        }
    }

}
