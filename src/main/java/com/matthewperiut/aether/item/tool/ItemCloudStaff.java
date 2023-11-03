package com.matthewperiut.aether.item.tool;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.List;

public class ItemCloudStaff extends TemplateItemBase {
    public ItemCloudStaff(Identifier i) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(60);
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        if (!this.cloudsExist(world, entityplayer)) {
            // todo: entity EntityMiniCloud c1 = new EntityMiniCloud(world, entityplayer, false);
            // todo: entity EntityMiniCloud c2 = new EntityMiniCloud(world, entityplayer, true);
            // todo: entity world.spawnEntity(c1);
            // todo: entity world.spawnEntity(c2);
            itemstack.applyDamage(1, (Entity) null);
        }

        return itemstack;
    }

    private boolean cloudsExist(World world, PlayerEntity entityplayer) {
        List list = world.getEntities(entityplayer, entityplayer.boundingBox.expand(128.0, 128.0, 128.0));

        for (int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity) list.get(j);
            // todo: entity if (entity1 instanceof EntityMiniCloud) {
            //    return true;
            //}
        }

        return false;
    }
}
