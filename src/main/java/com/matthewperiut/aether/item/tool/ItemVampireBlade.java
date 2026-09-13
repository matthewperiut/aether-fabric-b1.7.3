package com.matthewperiut.aether.item.tool;

import com.periut.accessoryapi.api.PlayerExtraHP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ItemVampireBlade extends TemplateSwordItem {
    private final int weaponDamage;

    public ItemVampireBlade(Identifier i, ToolMaterial enumtoolmaterial) {
        super(i, enumtoolmaterial);
        this.setMaxDamage(enumtoolmaterial.getDurability());
        this.weaponDamage = 4 + enumtoolmaterial.getAttackDamage() * 2;
    }

    public boolean postHit(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        PlayerEntity player = (PlayerEntity) entityliving1;
        if (player.health < 20 + ((PlayerExtraHP) player).getExtraHP()) {
            ++player.health;
        }

        itemstack.damage(1, entityliving1);
        return true;
    }

    @Override
    public int getAttackDamage(Entity entity) {
        return this.weaponDamage;
    }
}
