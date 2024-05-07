package com.matthewperiut.aether.player;


import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.entity.player.PlayerHandler;
import net.modificationstation.stationapi.api.util.math.Vec3d;

public class AetherPlayerHandler implements PlayerHandler
{
    private final PlayerEntity player;

    public AetherPlayerHandler(PlayerEntity playerBase)
    {
        player = playerBase;
    }

    public boolean attackTargetEntityBaseWithCurrentItem(Entity entity) {
        // Apply extra damage from gloves

        ItemStack[] stacks = AccessoryAccess.getAccessories(player, "gloves");
        for (ItemStack stack : stacks) {
            if (stack != null) {
                if (stack.itemId == AetherItems.LeatherGlove.id || stack.itemId == AetherItems.GoldGlove.id) {
                    entity.damage(player, 2);
                }
                if (stack.itemId == AetherItems.IronGlove.id || stack.itemId == AetherItems.ZaniteGlove.id || stack.itemId == AetherItems.GravititeGlove.id) {
                    entity.damage(player, 3);
                }
                if (stack.itemId == AetherItems.DiamondGlove.id || stack.itemId == AetherItems.NeptuneGlove.id ||
                    stack.itemId == AetherItems.ObsidianGlove.id || stack.itemId == AetherItems.PhoenixGlove.id) {
                    entity.damage(player, 4);
                }

                if (stack.itemId == AetherItems.GravititeGlove.id) {
                    // apply extra knockback
                    Vec3d dir = ((new Vec3d(player.x, player.y, player.z)).relativize(new Vec3d(entity.x, entity.y, entity.z))).normalize();
                    entity.setVelocity(entity.xVelocity + dir.x, entity.yVelocity + dir.y, entity.zVelocity + dir.z);
                }
                if (stack.itemId == AetherItems.PhoenixGlove.id) {
                    // set on fire
                    entity.fireTicks = 20;
                }
            }
        }

        return false;
    }
}
