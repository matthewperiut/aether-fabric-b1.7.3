package com.matthewperiut.aether.item.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateRecord;

public class ItemAetherRecord extends TemplateRecord {
    public ItemAetherRecord(Identifier i, String s) {
        super(i, s);
    }

    public boolean useOnBlock(ItemStack arg, PlayerEntity arg2, World arg3, int i, int j, int k, int l) {
        if (arg3.getBlockId(i, j, k) == Block.JUKEBOX.id && arg3.getBlockMeta(i, j, k) == 0) {
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ((Minecraft) FabricLoader.getInstance().getGameInstance()).overlay.showJukeboxMessage("Noisestorm - " + this.title);
                ((Minecraft) FabricLoader.getInstance().getGameInstance()).soundHelper.playStreaming("aether:aethertune", i, j, k, 1.0F, 1.0F);
            }
            if (arg3.isClient) {
                return true;
            } else {
                ((JukeboxBlock) Block.JUKEBOX).setRecord(arg3, i, j, k, this.id);
                --arg.count;
                return true;
            }
        } else {
            return false;
        }
    }
}
