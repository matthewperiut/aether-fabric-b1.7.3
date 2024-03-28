package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.block.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateChestBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class AetherChest extends TemplateChestBlock
{
    Random rand;

    public AetherChest(Identifier identifier)
    {
        super(identifier);
        rand = new Random();
    }

    private ItemStack getNormalLootSilver(final Random random)
    {
        final int item = random.nextInt(15);
        switch (item)
        {
            case 0:
            {
                return new ItemStack(AetherItems.PickZanite);
            }
            case 1:
            {
                return new ItemStack(AetherItems.Bucket, 1, 2);
            }
            case 2:
            {
                return new ItemStack(AetherItems.DartShooter);
            }
            case 3:
            {
                return new ItemStack(AetherItems.MoaEgg, 1, 0);
            }
            case 4:
            {
                return new ItemStack(AetherItems.AmbrosiumShard, random.nextInt(10) + 1);
            }
            case 5:
            {
                return new ItemStack(AetherItems.Dart, random.nextInt(5) + 1, 0);
            }
            case 6:
            {
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 1);
            }
            case 7:
            {
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 2);
            }
            case 8:
            {
                if (random.nextInt(20) == 0)
                {
                    return new ItemStack(AetherItems.BlueMusicDisk);
                }
                break;
            }
            case 9:
            {
                return new ItemStack(AetherItems.Bucket);
            }
            case 10:
            {
                if (random.nextInt(10) == 0)
                {
                    return new ItemStack(Item.byId[Item.RECORD_13.id + random.nextInt(2)]);
                }
                break;
            }
            case 11:
            {
                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteBoots);
                }
                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteHelmet);
                }
                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteLeggings);
                }
                if (random.nextInt(2) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteChestplate);
                }
                break;
            }
            case 12:
            {
                if (random.nextInt(4) == 0)
                {
                    return new ItemStack(AetherItems.IronPendant);
                }
            }
            case 13:
            {
                if (random.nextInt(10) == 0)
                {
                    return new ItemStack(AetherItems.GoldPendant);
                }
            }
            case 14:
            {
                if (random.nextInt(15) == 0)
                {
                    return new ItemStack(AetherItems.ZaniteRing);
                }
                break;
            }
        }
        return new ItemStack(AetherBlocks.AmbrosiumTorch, random.nextInt(5));
    }

    private ItemStack getNormalLootBronze(Random random) {
        int item = random.nextInt(14);
        switch (item) {
            case 0:
                return new ItemStack(AetherItems.PickZanite);
            case 1:
                return new ItemStack(AetherItems.AxeZanite);
            case 2:
                return new ItemStack(AetherItems.SwordZanite);
            case 3:
                return new ItemStack(AetherItems.ShovelZanite);
            case 4:
                return new ItemStack(AetherItems.AgilityCape);
            case 5:
                return new ItemStack(AetherItems.AmbrosiumShard, random.nextInt(10) + 1);
            case 6:
                return new ItemStack(AetherItems.Dart, random.nextInt(5) + 1, 0);
            case 7:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 1);
            case 8:
                return new ItemStack(AetherItems.Dart, random.nextInt(3) + 1, 2);
            case 9:
                if (random.nextInt(20) == 0) {
                    return new ItemStack(AetherItems.BlueMusicDisk);
                }
                break;
            case 10:
                return new ItemStack(AetherItems.Bucket);
            case 11:
                if (random.nextInt(10) == 0) {
                    return new ItemStack(Item.byId[Item.RECORD_13.id + random.nextInt(2)]);
                }
                break;
            case 12:
                if (random.nextInt(4) == 0) {
                    return new ItemStack(AetherItems.IronRing);
                }
                break;
            case 13:
                if (random.nextInt(10) == 0) {
                    return new ItemStack(AetherItems.GoldRing);
                }
        }

        return new ItemStack(AetherBlocks.AmbrosiumTorch);
    }

    private ItemStack getNormalLoot(Random random, int meta) {
        if (meta == 1)
            return getNormalLootSilver(random);
        else
            return getNormalLootBronze(random);
    }

    public void replaceChest(World world, int x, int y, int z) {
        int meta = world.getBlockMeta(x,y,z);
        world.setBlock(x,y,z, CHEST.id);

        final ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(x,y,z);
        for (int i = 0; i < 3 + rand.nextInt(3); ++i)
        {
            final ItemStack item = this.getNormalLoot(rand, meta);
            chest.setInventoryItem(rand.nextInt(chest.getInventorySize()), item);
        }
    }

    @Override
    public boolean canUse(World world, int x, int y, int z, PlayerEntity player)
    {
        replaceChest(world, x, y, z);
        return CHEST.canUse(world, x, y, z, player);
    }

    @Override
    public void onBlockPlaced(World arg, int i, int j, int k)
    {
        
    }

    @Override
    public void onBlockRemoved(World arg, int i, int j, int k) {

    }
}
