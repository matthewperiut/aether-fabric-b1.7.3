package com.matthewperiut.aether.blockentity.block;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;

public class BlockEntityTreasureChest extends ChestBlockEntity {
    public int rarity = 0;

    public void setRarity(int r) {
        rarity = r;
    }

    @Override
    public void readNbt(NbtCompound arg) {
        super.readNbt(arg);
        rarity = arg.getInt("rarity");
    }

    @Override
    public void writeNbt(NbtCompound arg) {
        super.writeNbt(arg);
        arg.putInt("rarity", rarity);
    }
}