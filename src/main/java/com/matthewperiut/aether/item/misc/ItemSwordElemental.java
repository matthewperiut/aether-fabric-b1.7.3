package com.matthewperiut.aether.item.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplateSword;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemSwordElemental extends TemplateSword {
    public static ArrayList<Class<? extends LivingEntity>> undead = new ArrayList();
    public static int textureId;
    private int weaponDamage;
    private int holyDamage;
    private EnumElement element;
    private int colour;

    public ItemSwordElemental(Identifier i, EnumElement element, int colour) {
        super(i, ToolMaterial.DIAMOND);
        setTexturePosition(textureId);
        this.maxStackSize = 1;
        this.setDurability(element == EnumElement.Holy ? 128 : 32);
        this.weaponDamage = 4;
        this.holyDamage = 20;
        this.element = element;
        this.colour = colour;
    }

    public int getTexturePosition(int damage) {
        return textureId;
    }

        public float getStrengthOnBlock(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        itemstack.applyDamage(2, entityliving);
        return true;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        if (this.element == EnumElement.Fire) {
            entityliving.fireTicks = 600;
        } else if (this.element == EnumElement.Lightning) {
            double var10004 = (double)((int)entityliving.x);
            double var10005 = (double)((int)entityliving.y);
            // todo: entity ModLoader.getMinecraftInstance().world.spawnEntity(new EntityAetherLightning(ModLoader.getMinecraftInstance().world, var10004, var10005, (double)((int)entityliving.z)));
        }

        itemstack.applyDamage(1, entityliving1);
        return true;
    }

    public int getAttackDamage(Entity entity) {
        if (this.element == EnumElement.Holy && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)entity;
            Iterator i$ = undead.iterator();

            while(i$.hasNext()) {
                Class<? extends LivingEntity> cls = (Class)i$.next();
                if (living.getClass().isAssignableFrom(cls)) {
                    return this.holyDamage;
                }
            }
        }

        return this.weaponDamage;
    }

    public int getNameColor(int i) {
        return this.colour;
    }

    public boolean isRendered3d() {
        return true;
    }

    static {
        undead.add(ZombieEntity.class);
        undead.add(SkeletonEntity.class);
        undead.add(ZombiePigmanEntity.class);
    }
}
