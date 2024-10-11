package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.poison.AetherPoison;
import com.matthewperiut.aether.poison.PoisonControl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityPoisonNeedle extends EntityProjectileBase implements EntitySpawnDataProvider {
    public static int texfxindex = 94;
    public LivingEntity victim;
    public int despawnTime;

    public EntityPoisonNeedle(World world) {
        super(world);
    }

    public EntityPoisonNeedle(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityPoisonNeedle(World world, LivingEntity ent) {
        super(world, ent);
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dmg = 0;
        this.speed = 1.5F;
    }

    public boolean checkWaterCollisions() {
        return this.victim == null && super.checkWaterCollisions();
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

    public void markDead() {
        this.victim = null;
        super.markDead();
    }

    public boolean onHitBlock() {
        return this.victim == null;
    }

    public boolean canBeShot(Entity ent) {
        return super.canBeShot(ent) && this.victim == null;
    }

    public void tick(Minecraft client) {
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
        return MOD_ID.id("PoisonNeedle");
    }
}
