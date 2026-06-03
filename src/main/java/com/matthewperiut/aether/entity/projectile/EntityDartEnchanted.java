package com.matthewperiut.aether.entity.projectile;

import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class EntityDartEnchanted extends EntityDartGolden {
    public static int texfxindex = 94;
    public LivingEntity victim;

    public EntityDartEnchanted(World world) {
        super(world);
    }

    public EntityDartEnchanted(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartEnchanted(World world, LivingEntity ent) {
        super(world, ent);
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.item = new ItemStack(AetherItems.Dart, 1, 2);
        this.dmg = 6;
    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("EnchantedDart");
    }
}
