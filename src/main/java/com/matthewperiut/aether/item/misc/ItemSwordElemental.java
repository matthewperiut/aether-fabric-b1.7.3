package com.matthewperiut.aether.item.misc;

import com.matthewperiut.aether.entity.projectile.EntityAetherLightning;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PigZombieEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemSwordElemental extends TemplateSwordItem {
    public static ArrayList<Class<? extends LivingEntity>> undead = new ArrayList();
    public static int textureId;

    static {
        undead.add(ZombieEntity.class);
        undead.add(SkeletonEntity.class);
        undead.add(PigZombieEntity.class);
    }

    private final int weaponDamage;
    private final int holyDamage;
    private final EnumElement element;
    private final int colour;

    public ItemSwordElemental(Identifier i, EnumElement element, int colour) {
        super(i, ToolMaterial.DIAMOND);
        setTextureId(textureId);
        this.maxCount = 1;
        this.setMaxDamage(element == EnumElement.Holy ? 128 : 32);
        this.weaponDamage = 4;
        this.holyDamage = 20;
        this.element = element;
        this.colour = colour;
    }

    public int getTextureId(int damage) {
        return textureId;
    }

    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (this.element == EnumElement.Fire) {
            entityliving.fireTicks = 600;
        } else if (this.element == EnumElement.Lightning) {
            if (!entityliving.world.isRemote) {
                entityliving.world.spawnEntity(new EntityAetherLightning(entityliving.world, entityliving.x, entityliving.y, entityliving.z));
            }
        }

        itemstack.damage(1, entityliving1);
        return true;
    }

    public int getAttackDamage(Entity entity) {
        if (this.element == EnumElement.Holy && entity instanceof LivingEntity living) {
            Iterator i$ = undead.iterator();

            while (i$.hasNext()) {
                Class<? extends LivingEntity> cls = (Class) i$.next();
                if (living.getClass().isAssignableFrom(cls)) {
                    return this.holyDamage;
                }
            }
        }

        return this.weaponDamage;
    }

    public int getColorMultiplier(int i) {
        return this.colour;
    }

    public boolean isHandheld() {
        return true;
    }
}
