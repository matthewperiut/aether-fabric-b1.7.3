package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.poison.AetherPoison;
import com.matthewperiut.aether.poison.PoisonControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityDartPoison extends EntityDartGolden {
    public static int texfxindex = 94;
    public LivingEntity victim;
    public int despawnTime;

    public EntityDartPoison(World world) {
        super(world);
    }

    public EntityDartPoison(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartPoison(World world, LivingEntity ent) {
        super(world, ent);
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.item = new ItemStack(AetherItems.Dart, 1, 1);
        this.dmg = 2;
    }

    public boolean onHitTarget(Entity target) {
        if (target instanceof AetherPoison poison) {
            poison.getPoison().afflictPoison();
            dead = false;
            despawnTime = PoisonControl.maxPoisonTime;
            markDead();
        }
        return super.onHitTarget(target);
    }

    public void tick() {
        super.tick();
        if (!this.dead) {
            if (this.victim != null) {
                if (!this.victim.isAlive() || this.despawnTime == 0) {
                    this.markDead();
                    return;
                }
                --this.despawnTime;
            }
        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("PoisonDart");
    }
}
