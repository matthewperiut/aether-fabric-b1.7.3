package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.aether.item.tool.ItemPhoenixArmour;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.registry.Identifier;

public class ItemPhoenixGloves extends ItemGloves {
    public ItemPhoenixGloves(Identifier i, int j, String path, int l, int m, boolean b) {
        super(i, j, path, l, m, b);
    }

    private boolean phoenixArmour(PlayerEntity player, int slot) {
        if (player.inventory.armor[slot] != null) {
            if (player.inventory.armor[slot].getItem() instanceof ItemPhoenixArmour) {
                return true;
            }
        }
        colouriseRender = false;
        return false;
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.isTouchingWater()) {
            itemInstance.applyDamage(1, player);
        }

        if (phoenixArmour(player, 0) &&
                phoenixArmour(player, 1) &&
                phoenixArmour(player, 2) &&
                phoenixArmour(player, 3)) {
            ((EntityAccessor) player).setImmuneToFire(true);

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                boolean local = player.equals(((Minecraft) FabricLoader.getInstance().getGameInstance()).player);
                player.world.addParticle("flame", player.x + rand.nextGaussian() / 5.0, player.y - (local ? 0.5 : 0) + rand.nextGaussian() / 5.0, player.z + rand.nextGaussian() / 3.0, 0.0, 0.0, 0.0);
            }
        } else {
            ((EntityAccessor) player).setImmuneToFire(false);
        }

        if (itemInstance.getDamage() > itemInstance.getDurability() - 2) {
            itemInstance = null;
        }

        return itemInstance;
    }

    @Override
    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        ((EntityAccessor) player).setImmuneToFire(false);

        super.onAccessoryRemoved(player, accessory);
    }
}
