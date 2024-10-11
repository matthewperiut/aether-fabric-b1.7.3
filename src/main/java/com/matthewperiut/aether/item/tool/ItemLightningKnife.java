package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.entity.projectile.EntityLightningKnife;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemLightningKnife extends TemplateItem {
    public ItemLightningKnife(Identifier i) {
        super(i);
        this.maxCount = 16;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        --itemstack.count;
        if (!world.isRemote) {
            world.playSound(entityplayer, "aether:mob.dartshoot", 2.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
            world.spawnEntity(new EntityLightningKnife(world, entityplayer));
        }

        return itemstack;
    }
}
