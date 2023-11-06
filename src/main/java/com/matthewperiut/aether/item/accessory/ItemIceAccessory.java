package com.matthewperiut.aether.item.accessory;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemIceAccessory extends ItemMoreArmor
{
    public ItemIceAccessory(Identifier i, int j, String path, int l, int m)
    {
        super(i, j, path, l, m);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance)
    {
        super.tickWhileWorn(player, itemInstance);
        boolean froze = false;
        final int i = MathHelper.floor(player.x);
        final int j = MathHelper.floor(player.boundingBox.minY);
        final int k = MathHelper.floor(player.z);
        final double yoff = player.y - j;
        final Material mat0 = player.world.getMaterial(i, j, k);
        final Material mat2 = player.world.getMaterial(i, j - 1, k);
        for (int l = i - 1; l <= i + 1; ++l)
        {
            for (int i2 = j - 1; i2 <= j + 1; ++i2)
            {
                for (int j2 = k - 1; j2 <= k + 1; ++j2)
                {
                    if (player.world.getBlockId(l, i2, j2) == 8)
                    {
                        player.world.setBlock(l, i2, j2, 79);
                        froze = true;
                    }
                    else if (player.world.getBlockId(l, i2, j2) == 9)
                    {
                        player.world.setBlock(l, i2, j2, 79);
                        froze = true;
                    }
                    else if (player.world.getBlockId(l, i2, j2) == 10)
                    {
                        player.world.setBlock(l, i2, j2, 49);
                        froze = true;
                    }
                    else if (player.world.getBlockId(l, i2, j2) == 11)
                    {
                        player.world.setBlock(l, i2, j2, 49);
                        froze = true;
                    }
                }
            }
        }

        if (froze)
        {
            itemInstance.applyDamage(1, player);
            if (itemInstance.getDamage() > itemInstance.getDurability() - 2)
            {
                itemInstance = null;
            }
        }

        return itemInstance;
    }
}
