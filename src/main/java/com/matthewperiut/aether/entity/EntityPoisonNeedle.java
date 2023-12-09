package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.util.AetherPoison;
import com.matthewperiut.aether.util.AetherPoisonOld;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.server.entity.EntitySpawnDataProvider;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityPoisonNeedle extends EntityProjectileBase implements EntitySpawnDataProvider {
    public static int texfxindex = 94;
    public LivingEntity victim;
    public int poisonTime;

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

    public boolean method_1393() {
        return this.victim == null && super.method_1393();
    }

    public boolean onHitTarget(Entity entity) {
        if (entity instanceof LivingEntity && AetherPoison.canPoison(entity)) {
            LivingEntity ent = (LivingEntity) entity;
            List list = this.world.getEntities(this, ent.boundingBox.expand(2.0, 2.0, 2.0));

            for (int i = 0; i < list.size(); ++i) {
                Entity lr2 = (Entity) list.get(i);
                if (lr2 instanceof EntityPoisonNeedle) {
                    EntityPoisonNeedle arr = (EntityPoisonNeedle) lr2;
                    if (arr.victim == ent) {
                        arr.poisonTime = 500;
                        arr.removed = false;
                        this.remove();
                        return false;
                    }
                }
            }

            this.victim = ent;
            ent.damage(this.shooter, this.dmg);
            this.poisonTime = 500;
            return false;
        } else {
            return super.onHitTarget(entity);
        }
    }

    public void remove() {
        this.victim = null;
        super.remove();
    }

    public boolean onHitBlock() {
        return this.victim == null;
    }

    public boolean canBeShot(Entity ent) {
        return super.canBeShot(ent) && this.victim == null;
    }

    public void tick(Minecraft client) {
        super.tick();
        if (!this.removed) {
            if (this.victim != null) {
                if (this.victim.removed || this.poisonTime == 0) {
                    this.remove();
                    return;
                }

                //ParticleEntity fx = new PoofParticleEntity(this.world, this.x, this.y, this.z, Item.SLIMEBALL);
                //fx.renderDistanceMultiplier = 10.0;
                //fx.texture = texfxindex;
                //client.particleManager.addParticle(fx);
                this.removed = false;
                this.inGround = false;
                this.x = this.victim.x;
                this.y = this.victim.boundingBox.minY + (double) this.victim.height * 0.8;
                this.z = this.victim.z;
                AetherPoisonOld.distractEntity(this.victim);
                --this.poisonTime;
            }

        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("PoisonNeedle");
    }
}
