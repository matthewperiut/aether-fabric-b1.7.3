package com.matthewperiut.aether.item.misc;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.client.gui.GuiLore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLoreBook extends Item {
    public ItemLoreBook() {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Environment(EnvType.SERVER)
    private static void useLoreServer() {

    }

    @Environment(EnvType.CLIENT)
    private static void useLoreClient(final PlayerEntity player, final ItemStack item) {
        //noinspection deprecation
        if (FabricLoader.getInstance().getGameInstance() instanceof Minecraft mc)
            mc.setScreen(new GuiLore(player.inventory, item.getDamage2()));
    }

    public int getColorMultiplier(int i) {
        if (i == 0) {
            return 8388479;
        } else {
            return i == 1 ? 16744319 : 8355839;
        }
    }

    public ItemStack use(ItemStack item, World world, PlayerEntity player) {
        if (net.fabricmc.loader.api.FabricLoader.getInstance().getEnvironmentType() == net.fabricmc.api.EnvType.CLIENT) {
            useLoreClient(player, item);
        } else {
            useLoreServer();
        }
        return item;
    }

    public String getTranslationKey(ItemStack itemstack) {
        int i = itemstack.getDamage();
        if (i > 2) {
            i = 2;
        }

        return super.getTranslationKey() + i;
    }
}
