package com.matthewperiut.aether.item.tool;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemCloudStaff extends Item {
    public ItemCloudStaff() {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
        this.setMaxDamage(60);
    }

    private ItemStack useCloudStaff(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (!this.cloudsExist(world, entityplayer)) {

            EntityMiniCloud c1 = new EntityMiniCloud(world, entityplayer, false);
            EntityMiniCloud c2 = new EntityMiniCloud(world, entityplayer, true);
            world.spawnEntity(c1);
            world.spawnEntity(c2);
            itemstack.damage(1, null);
        }

        return itemstack;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        return world.isRemote ? itemstack : useCloudStaff(itemstack, world, entityplayer);
    }

    private boolean cloudsExist(World world, PlayerEntity entityplayer) {
        List list = world.getEntities(entityplayer, entityplayer.boundingBox.expand(128.0, 128.0, 128.0));

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);
            if (entity1 instanceof EntityMiniCloud) {
                return true;
            }
        }

        return false;
    }
}
