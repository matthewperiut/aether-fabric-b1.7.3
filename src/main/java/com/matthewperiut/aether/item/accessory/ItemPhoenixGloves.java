package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.aether.item.tool.ItemPhoenixArmor;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemPhoenixGloves extends ItemGloves {
    public ItemPhoenixGloves(Identifier i, int j, String path, int l, int m, boolean b) {
        super(i, j, path, l, m, b);
    }

    private boolean phoenixArmor(PlayerEntity player, int slot) {
        if (player.inventory.armor[slot] != null) {
            if (player.inventory.armor[slot].getItem() instanceof ItemPhoenixArmor) {
                return true;
            }
        }
        coloriseRender = false;
        return false;
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {
        if (player.isSubmergedInWater()) {
            itemInstance.damage(1, player);
        }

        if (phoenixArmor(player, 0) &&
                phoenixArmor(player, 1) &&
                phoenixArmor(player, 2) &&
                phoenixArmor(player, 3)) {
            ((EntityAccessor) player).setFireImmune(true);

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                boolean local = player.equals(((Minecraft) FabricLoader.getInstance().getGameInstance()).player);
                player.world.addParticle("flame", player.x + random.nextGaussian() / 5.0, player.y - (local ? 0.5 : 0) + random.nextGaussian() / 5.0, player.z + random.nextGaussian() / 3.0, 0.0, 0.0, 0.0);
            }
        } else {
            ((EntityAccessor) player).setFireImmune(false);
        }

        if (itemInstance.getDamage() > itemInstance.getMaxDamage() - 2) {
            itemInstance = null;
        }

        return itemInstance;
    }

    @Override
    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        ((EntityAccessor) player).setFireImmune(false);

        super.onAccessoryRemoved(player, accessory);
    }
}
