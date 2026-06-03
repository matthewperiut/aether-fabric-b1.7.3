package com.matthewperiut.aether.item.tool;

import net.minecraft.item.Item;
import com.periut.retroapi.register.item.RetroItemAccess;

import com.matthewperiut.aether.entity.projectile.EntityNotchWave;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;

public class ItemNotchHammer extends Item {
    private final int weaponDamage;

    public ItemNotchHammer() {
        super(RetroItemAccess.allocateId());
        this.maxCount = 1;
        this.setMaxDamage(ToolMaterial.IRON.getDurability());
        this.weaponDamage = 4 + ToolMaterial.IRON.getAttackDamage() * 2;
    }

    public float getMiningSpeedMultiplier(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        itemstack.damage(1, entityliving1);
        return true;
    }

    public boolean postMine(ItemStack itemstack, int i, int j, int k, int l, LivingEntity entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }

    public int getAttackDamage(Entity entity) {
        return this.weaponDamage;
    }

    public ItemStack use(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        itemstack.damage(1, entityplayer);
        world.playSound(entityplayer, "mob.ghast.fireball", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityNotchWave notchwave = new EntityNotchWave(world, entityplayer);
            world.spawnEntity(notchwave);
        }

        return itemstack;
    }

    public boolean isHandheld() {
        return true;
    }
}
