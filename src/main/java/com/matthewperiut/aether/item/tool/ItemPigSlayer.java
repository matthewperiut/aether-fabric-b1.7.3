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
            String s = EntityRegistry.getId(entityliving);
            if (!s.equals("") && s.toLowerCase().contains("pig")) {
                if (entityliving.health > 0) {
                    entityliving.health = 1;
                    entityliving.damagedTimer = 0;
                    entityliving.damage(entityliving1, 9999);
                }

                for (int j = 0; j < 20; ++j) {
                    double d = random.nextGaussian() * 0.02;
                    double d1 = random.nextGaussian() * 0.02;
                    double d2 = random.nextGaussian() * 0.02;
                    double d3 = 5.0;
                    entityliving.world.addParticle("flame", entityliving.x + random.nextFloat() * entityliving.width * 2.0F - (double) entityliving.width - d * d3, entityliving.y + (random.nextFloat() * entityliving.height) - d1 * d3, entityliving.z + (double) (random.nextFloat() * entityliving.width * 2.0F) - (double) entityliving.width - d2 * d3, d, d1, d2);
                }

                ((LivingEntityAccessor) entityliving).invokeGetDrops();
                entityliving.markDead();
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
