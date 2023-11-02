package com.matthewperiut.aether.item.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemLoreBook extends TemplateItemBase {
    public ItemLoreBook(Identifier identifier) {
        super(identifier);
        this.maxStackSize = 1;
        this.setHasSubItems(true);
        this.setDurability(0);
    }

    public int getNameColor(int i) {
        if (i == 0) {
            return 8388479;
        } else {
            return i == 1 ? 16744319 : 8355839;
        }
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        // todo: gui ModLoader.OpenGUI(entityplayer, new GuiLore(entityplayer.inventory, itemstack.getMeta()));
        return itemstack;
    }

    public String getTranslationKey(ItemStack itemstack) {
        int i = itemstack.getMeta();
        if (i > 2) {
            i = 2;
        }

        return super.getTranslationKey() + "." + i;
    }
}
