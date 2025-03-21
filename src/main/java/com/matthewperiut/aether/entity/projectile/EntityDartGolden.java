package com.matthewperiut.aether.entity.projectile;

import com.matthewperiut.aether.item.AetherItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

@HasTrackingParameters(trackingDistance = 50, sendVelocity = TriState.TRUE, updatePeriod = 1)
public class EntityDartGolden extends EntityProjectileBase implements EntitySpawnDataProvider {
    public static int texfxindex = 94;
    public LivingEntity victim;

    public EntityDartGolden(World world) {
        super(world);
    }

    public EntityDartGolden(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public EntityDartGolden(World world, LivingEntity ent) {
        super(world, ent);
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.item = new ItemStack(AetherItems.Dart, 1, 0);
        this.curvature = 0.0F;
        this.dmg = 4;
        this.speed = 1.5F;
    }

    public boolean checkWaterCollisions() {
        return this.victim == null && super.checkWaterCollisions();
    }

    public void markDead() {
        this.victim = null;
        super.markDead();
    }

    public boolean onHitBlock() {
        this.curvature = 0.03F;
        this.world.playSound(this, "random.drr", 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        return this.victim == null;
    }

    public boolean canBeShot(Entity ent) {
        return super.canBeShot(ent) && this.victim == null;
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("GoldenDart");
    }
}
