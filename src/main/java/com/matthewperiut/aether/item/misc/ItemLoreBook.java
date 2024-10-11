package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.client.gui.GuiLore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.SideUtil;

public class ItemLoreBook extends TemplateItem {
    public ItemLoreBook(Identifier identifier) {
        super(identifier);
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
        SideUtil.run(() -> useLoreClient(player, item), () -> useLoreServer());
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
