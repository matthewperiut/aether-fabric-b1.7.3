package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.poison.AetherPoison;
import com.matthewperiut.aether.poison.PoisonControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

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
            removed = false;
            despawnTime = PoisonControl.maxPoisonTime;
            remove();
        }
        return super.onHitTarget(target);
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            if (this.victim != null) {
                if (this.victim.removed || this.despawnTime == 0) {
                    this.remove();
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
