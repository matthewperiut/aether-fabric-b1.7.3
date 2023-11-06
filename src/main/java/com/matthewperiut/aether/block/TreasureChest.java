package com.matthewperiut.aether.block;

import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import com.matthewperiut.aether.blockentity.container.ContainerTreasureChest;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.block.Block;
import net.minecraft.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateChest;

import java.util.Random;

import static com.matthewperiut.aether.blockentity.AetherBlockEntities.MOD_ID;

public class TreasureChest extends TemplateChest {
    public static int sideTexture;

    public TreasureChest(Identifier identifier) {
        super(identifier);
    }

    public static void PlaceTreasureChest(World world, int x, int y, int z, int rarity) {
        world.setBlockWithMetadata(x, y, z, AetherBlocks.TreasureChest.id, 1 + rarity);
    }

    @Override
    public int getTextureForSide(final BlockView tileView, final int x, final int y, final int z, final int meta) {
        if (meta == 1) {
            return 62;
        }
        if (meta == 0) {
            return 62;
        }
        final int i1 = tileView.getBlockId(x, y, z - 1);
        final int j1 = tileView.getBlockId(x, y, z + 1);
        final int k1 = tileView.getBlockId(x - 1, y, z);
        final int l1 = tileView.getBlockId(x + 1, y, z);
        byte byte0 = 3;
        if (Block.FULL_OPAQUE[i1] && !Block.FULL_OPAQUE[j1]) {
            byte0 = 3;
        }
        if (Block.FULL_OPAQUE[j1] && !Block.FULL_OPAQUE[i1]) {
            byte0 = 2;
        }
        if (Block.FULL_OPAQUE[k1] && !Block.FULL_OPAQUE[l1]) {
            byte0 = 5;
        }
        if (Block.FULL_OPAQUE[l1] && !Block.FULL_OPAQUE[k1]) {
            byte0 = 4;
        }
        return (meta != byte0) ? sideTexture : texture;
    }

    @Override
    public int getTextureForSide(final int side) {
        if (side == 1) {
            return 62;
        }
        if (side == 0) {
            return 62;
        }
        if (side == 3) {
            return texture;
        }
        return sideTexture;
    }

    @Override
    public void onBlockRemoved(World arg, int i, int j, int k) {
        if (arg.getBlockMeta(i, j, k) == 0) super.onBlockRemoved(arg, i, j, k);
    }

    private ItemStack getGoldLoot(final Random random) {
        final int item = random.nextInt(8);
        switch (item) {
            case 0: {
                return new ItemStack(AetherItems.IronBubble);
            }
            case 1: {
                return new ItemStack(AetherItems.VampireBlade);
            }
            case 2: {
                return new ItemStack(AetherItems.PigSlayer);
            }
            case 3: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixHelm);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixLegs);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixBody);
                }
                break;
            }
            case 4: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PhoenixBoots);
                }
                return new ItemStack(AetherItems.PhoenixGlove);
            }
            case 5: {
                return new ItemStack(AetherItems.LifeShard);
            }
            case 6: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeHelmet);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititePlatelegs);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeBodyplate);
                }
                break;
            }
            case 7: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.GravititeBoots);
                }
                return new ItemStack(AetherItems.GravititeGlove);
            }
        }
        //return null;
        return new ItemStack(AetherItems.ObsidianBody);
    }

    private ItemStack getSilverLoot(final Random random) {
        final int item = random.nextInt(9);
        switch (item) {
            case 0: {
                return new ItemStack(AetherItems.GummieSwet, random.nextInt(16));
            }
            case 1: {
                return new ItemStack(AetherItems.SwordLightning);
            }
            case 2: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.AxeValkyrie);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.ShovelValkyrie);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.PickValkyrie);
                }
                break;
            }
            case 3: {
                return new ItemStack(AetherItems.SwordHoly);
            }
            case 4: {
                return new ItemStack(AetherItems.GoldenFeather);
            }
            case 5: {
                return new ItemStack(AetherItems.RegenerationStone);
            }
            case 6: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.NeptuneHelmet);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.NeptuneLeggings);
                }
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.NeptuneChestplate);
                }
                break;
            }
            case 7: {
                if (random.nextBoolean()) {
                    return new ItemStack(AetherItems.NeptuneBoots);
                }
                return new ItemStack(AetherItems.NeptuneGlove);
            }
            case 8: {
                return new ItemStack(AetherItems.InvisibilityCloak);
            }
        }
        return new ItemStack(AetherItems.ZanitePendant);
    }

    private ItemStack getBronzeLoot(final Random random) {
        final int item = random.nextInt(7);
        switch (item) {
            case 0: {
                return new ItemStack(AetherItems.GummieSwet, random.nextInt(8), random.nextInt(2));
            }
            case 1: {
                return new ItemStack(AetherItems.PhoenixBow);
            }
            case 2: {
                return new ItemStack(AetherItems.SwordFire);
            }
            case 3: {
                return new ItemStack(AetherItems.HammerNotch);
            }
            case 4: {
                return new ItemStack(AetherItems.LightningKnife, random.nextInt(16));
            }
            case 5: {
                return new ItemStack(AetherItems.Lance);
            }
            case 6: {
                return new ItemStack(AetherItems.AgilityCape);
            }
            default: {
                return new ItemStack(AetherItems.Stick);
            }
        }
    }

    @Override
    public boolean canUse(final World level, final int x, final int y, final int z, final PlayerEntity player) {
        final int meta = level.getBlockMeta(x, y, z);
        BlockEntity tileEntity = level.getBlockEntity(x, y, z);
        if (tileEntity instanceof BlockEntityTreasureChest treasureChest) {
            if (meta == 5) {
                GuiHelper.openGUI(player, MOD_ID.id("treasure_chest"), treasureChest, new ContainerTreasureChest(player.inventory, treasureChest));
                return true;
            }
            if (meta > 1) {
                ((BlockEntityTreasureChest) tileEntity).setRarity(meta - 1);
                level.setBlockMeta(x, y, z, 1);
            }
            if (meta == 0) {
                GuiHelper.openGUI(player, MOD_ID.id("treasure_chest"), treasureChest, new ContainerTreasureChest(player.inventory, treasureChest));
                return true;
            }
            if (level.isClient) {
                return true;
            }
            final ItemStack itemstack = player.inventory.getHeldItem();
            if (itemstack != null && meta == 1) {
                if (itemstack.itemId == AetherItems.Key.id && itemstack.getDamage() + 1 == treasureChest.rarity) {
                    player.inventory.takeInventoryItem(player.inventory.selectedHotBarSlot, 1);
                    level.setBlockMeta(x, y, z, 0);
                    Random rand = new Random();
                    switch (treasureChest.rarity) {
                        case 0:
                            break;
                        case 1:
                            for (int p = 0; p < 3 + rand.nextInt(3); ++p) {
                                final ItemStack item = this.getBronzeLoot(rand);
                                treasureChest.setInventoryItem(rand.nextInt(treasureChest.getInventorySize()), item);
                            }
                            break;
                        case 2:
                            for (int p = 0; p < 3 + rand.nextInt(3); ++p) {
                                final ItemStack item = this.getSilverLoot(rand);
                                treasureChest.setInventoryItem(rand.nextInt(treasureChest.getInventorySize()), item);
                            }
                            break;
                        case 3:
                            for (int p = 0; p < 3 + rand.nextInt(3); ++p) {
                                final ItemStack item = this.getGoldLoot(rand);
                                treasureChest.setInventoryItem(rand.nextInt(treasureChest.getInventorySize()), item);
                            }
                            break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new BlockEntityTreasureChest();
    }
}
