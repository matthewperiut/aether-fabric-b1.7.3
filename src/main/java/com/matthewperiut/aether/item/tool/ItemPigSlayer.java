package com.matthewperiut.aether.item.tool;

import com.matthewperiut.aether.mixin.access.LivingEntityAccessor;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateSword;

import java.util.Random;

public class ItemPigSlayer extends TemplateSword {
    Random rand = new Random();

    public ItemPigSlayer(Identifier i) {
        super(i, ToolMaterial.IRON);
        this.setDurability(0);
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (entityliving != null && entityliving1 != null) {
            String s = EntityRegistry.getStringId(entityliving);
            if (!s.equals("") && s.toLowerCase().contains("pig")) {
                if (entityliving.health > 0) {
                    entityliving.health = 1;
                    entityliving.hurtTime = 0;
                    entityliving.damage(entityliving1, 9999);
                }

                for (int j = 0; j < 20; ++j) {
                    double d = rand.nextGaussian() * 0.02;
                    double d1 = rand.nextGaussian() * 0.02;
                    double d2 = rand.nextGaussian() * 0.02;
                    double d3 = 5.0;
                    entityliving.world.addParticle("flame", entityliving.x + rand.nextFloat() * entityliving.width * 2.0F - (double) entityliving.width - d * d3, entityliving.y + (rand.nextFloat() * entityliving.height) - d1 * d3, entityliving.z + (double) (rand.nextFloat() * entityliving.width * 2.0F) - (double) entityliving.width - d2 * d3, d, d1, d2);
                }

                ((LivingEntityAccessor) entityliving).invokeGetDrops();
                entityliving.removed = true;
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
