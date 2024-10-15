package com.matthewperiut.aether.block;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

import java.util.Random;

public class AetherLog extends TemplateBlock implements MetaNamedBlockItemProvider {
    private static final Random rand = new Random();
    public static int sprTop;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogTop.png");
    public static int sprSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/SkyrootLogSide.png");
    public static int sprGoldenSide;// = ModLoader.addOverride("/terrain.png", "/aether/blocks/GoldenOak.png");

    public AetherLog(Identifier identifier) {
        super(identifier, sprSide, Material.WOOD);
    }

    public int getTexture(int i, int j) {
        if (i <= 1 && j <= 3) {
            return sprTop;
        } else {
            return j <= 1 ? sprSide : sprGoldenSide;
        }
    }

    public int getDroppedItemCount(Random random) {
        return 1;
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {
        if (!world.isRemote) {
            int count = 1;
            PlayerEntity player = world.getClosestPlayer(x,y,z,16.F);

            if (player != null) {
                if (UtilSkyroot.axe(player)) {
                    count = 2;
                }

                ItemStack heldItem = player.inventory.main[player.inventory.selectedSlot];

                if (heldItem != null && (heldItem.itemId == AetherItems.AxeZanite.id || heldItem.itemId == AetherItems.AxeGravitite.id)) {
                    if (meta > 1) {
                        ItemStack stack = new ItemStack(AetherItems.GoldenAmber.id, rand.nextInt(4), 0);
                        world.spawnEntity(new ItemEntity(world, x, y, z, stack));
                    }
                }
            }

            for(int i = 0; i < count; ++i) {
                if (!(world.random.nextFloat() > luck)) {
                    int var9 = this.getDroppedItemId(meta, world.random);
                    if (var9 > 0) {
                        this.dropStack(world, x, y, z, new ItemStack(var9, 1, this.getDroppedItemMeta(meta)));
                    }
                }
            }
        }
    }

    @Override
    public void onPlaced(World arg, int i, int j, int k) {
        int meta = arg.getBlockMeta(i,j,k);
        if (meta % 2 != 0)
            arg.setBlockMeta(i, j, k, meta+1);
        super.onPlaced(arg, i, j, k);
    }

    public int[] getValidMetas() {
        return new int[]{0, 1};
    }
}