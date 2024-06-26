package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
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
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("EnchantedDart");
    }
}
