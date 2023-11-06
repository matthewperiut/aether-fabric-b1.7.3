package com.matthewperiut.aether.blockentity.block;

import net.minecraft.entity.block.ChestBlockEntity;
import net.minecraft.util.io.CompoundTag;

public class BlockEntityTreasureChest extends ChestBlockEntity
{
    public int rarity = 0;

    public void setRarity(int r)
    {
        rarity = r;
    }

    @Override
    public void readNBT(CompoundTag arg)
    {
        super.readNBT(arg);
        rarity = arg.getInt("rarity");
    }

    @Override
    public void writeNBT(CompoundTag arg)
    {
        super.writeNBT(arg);
        arg.put("rarity", rarity);
    }
}