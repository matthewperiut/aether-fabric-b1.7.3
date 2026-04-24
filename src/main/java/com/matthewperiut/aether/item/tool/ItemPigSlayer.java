package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ItemPigSlayer extends TemplateSwordItem {
    Random rand = new Random();

    public ItemPigSlayer(Identifier i) {
        super(i, ToolMaterial.IRON);
        this.setMaxDamage(0);
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (entityliving != null && entityliving1 != null) {
            if (!entityliving.world.isRemote) {
                String s = EntityRegistry.getId(entityliving);
                if (!s.equals("") && s.toLowerCase().contains("pig") || s.toLowerCase().contains("phyg")) {
                    if (entityliving.health > 0) {
                        entityliving.health = 1;
                        entityliving.hurtTime = 0;
                        entityliving.damage(entityliving1, 9999);
                    }

                    ((LivingEntityAccessor) entityliving).invokeGetDrops();
                    entityliving.markDead();
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean func_25008_a(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        return true;
    }
}
