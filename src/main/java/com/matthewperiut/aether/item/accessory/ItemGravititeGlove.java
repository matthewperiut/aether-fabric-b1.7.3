package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.aether.entity.living.EntityAerbunny;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemGravititeGlove extends ItemGloves {
    public ItemGravititeGlove(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
    }

    @Override
    public ItemStack tickWhileWorn(PlayerEntity player, ItemStack itemInstance) {

        if (player.inventory.armor[0] == null ||
                player.inventory.armor[1] == null ||
                player.inventory.armor[2] == null ||
                player.inventory.armor[3] == null)
            return super.tickWhileWorn(player, itemInstance);

        // straight up (EntityAerbunny)
        if (!(player.passenger instanceof EntityAerbunny)) {
            if (!player.onGround && !player.checkWaterCollisions()) {
                if (player.inventory.armor[3].itemId == AetherItems.GravititeHelmet.id &&
                        player.inventory.armor[2].itemId == AetherItems.GravititeBodyplate.id &&
                        player.inventory.armor[1].itemId == AetherItems.GravititePlatelegs.id &&
                        player.inventory.armor[0].itemId == AetherItems.GravititeBoots.id) {
                    ((EntityAccessor) player).setFallDistance(0.0F);
                    player.velocityY += 0.05000000074505806;
                    //if (player.yVelocity < -0.22499999403953552 && ((LivingEntityAccessor) player).getJumping()) {
                    //    player.yVelocity = 0.125;
                    //}
                }
            }

        }

        return super.tickWhileWorn(player, itemInstance);
    }
}
