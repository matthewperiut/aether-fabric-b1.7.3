package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.item.tool.ItemPhoenixArmour;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemPhoenixGloves extends ItemGloves {
    public ItemPhoenixGloves(int j, String path, int l, int m, boolean b) {
        super(j, path, l, m, b);
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
        if (player.isWet()) {
            itemInstance.damage(1, player);
            if (itemInstance.getDamage2() > itemInstance.getMaxDamage() - 2) {
                ((EntityAccessor) player).setImmuneToFire(false);
                return new ItemStack(AetherItems.ObsidianGlove, 1);
            }
        }

        if (phoenixArmour(player, 0) &&
                phoenixArmour(player, 1) &&
                phoenixArmour(player, 2) &&
                phoenixArmour(player, 3)) {
            ((EntityAccessor) player).setImmuneToFire(true);

            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                boolean local = com.matthewperiut.aether.client.ClientHelper.isLocalPlayer(player);
                player.world.addParticle("flame", player.x + random.nextGaussian() / 5.0, player.y - (local ? 0.5 : 0) + random.nextGaussian() / 5.0, player.z + random.nextGaussian() / 3.0, 0.0, 0.0, 0.0);
            }
        } else {
            ((EntityAccessor) player).setImmuneToFire(false);
        }

        return itemInstance;
    }

    @Override
    public void onAccessoryRemoved(PlayerEntity player, ItemStack accessory) {
        ((EntityAccessor) player).setImmuneToFire(false);

        super.onAccessoryRemoved(player, accessory);
    }
}
