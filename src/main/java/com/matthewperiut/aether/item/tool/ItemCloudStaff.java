package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.entity.special.EntityMiniCloud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class ItemCloudStaff extends TemplateItem {
    public ItemCloudStaff(Identifier i) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(60);
    }

    private ItemStack useCloudStaff(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (!this.cloudsExist(world, entityplayer)) {

            EntityMiniCloud c1 = new EntityMiniCloud(world, entityplayer, false);
            EntityMiniCloud c2 = new EntityMiniCloud(world, entityplayer, true);
            world.spawnEntity(c1);
            world.spawnEntity(c2);
            itemstack.applyDamage(1, null);
        }

        return itemstack;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        return world.isClient ? itemstack : useCloudStaff(itemstack, world, entityplayer);
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
