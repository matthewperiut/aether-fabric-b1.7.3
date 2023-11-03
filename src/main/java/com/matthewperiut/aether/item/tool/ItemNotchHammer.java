package com.matthewperiut.aether.item.tool;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemNotchHammer extends TemplateItemBase {
    private int weaponDamage;

    public ItemNotchHammer(Identifier i) {
        super(i);
        this.maxStackSize = 1;
        this.setDurability(ToolMaterial.IRON.getDurability());
        this.weaponDamage = 4 + ToolMaterial.IRON.getAttackDamage() * 2;
    }

    public float getStrengthOnBlock(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        itemstack.applyDamage(1, entityliving1);
        return true;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        itemstack.applyDamage(2, entityliving);
        return true;
    }

    public int getAttackDamage(Entity entity) {
        return this.weaponDamage;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        itemstack.applyDamage(1, entityplayer);
        world.playSound(entityplayer, "mob.ghast.fireball", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            // todo: entity EntityNotchWave notchwave = new EntityNotchWave(world, entityplayer);
            // world.spawnEntity(notchwave);
        }

        return itemstack;
    }

    public boolean isRendered3d() {
        return true;
    }
}
