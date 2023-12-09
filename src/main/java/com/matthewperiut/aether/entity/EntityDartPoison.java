package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.util.AetherPoison;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityDartPoison extends EntityDartGolden {
    public static int texfxindex = 94;
    public LivingEntity victim;
    public int poisonTime;

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

    public boolean onHitTarget(Entity entity) {
        if (entity instanceof LivingEntity && AetherPoison.canPoison(entity)) {
            LivingEntity ent = (LivingEntity) entity;
            if (ent instanceof PlayerEntity) {
                //AetherPoison.afflictPoison();
                return super.onHitTarget(entity);
            } else {
                List list = this.world.getEntities(this, ent.boundingBox.expand(2.0, 2.0, 2.0));

                for (int i = 0; i < list.size(); ++i) {
                    Entity lr2 = (Entity) list.get(i);
                    if (lr2 instanceof EntityDartPoison) {
                        EntityDartPoison arr = (EntityDartPoison) lr2;
                        if (arr.victim == ent) {
                            arr.poisonTime = 500;
                            arr.removed = false;
                            ent.damage(this.shooter, this.dmg);
                            this.remove();
                            return false;
                        }
                    }
                }

                this.victim = ent;
                ent.damage(this.shooter, this.dmg);
                this.poisonTime = 500;
                return false;
            }
        } else {
            return super.onHitTarget(entity);
        }
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            if (this.victim != null) {
                if (this.victim.removed || this.poisonTime == 0) {
                    this.remove();
                    return;
                }

                // ParticleEntity fx = new PoofParticleEntity(this.world, this.x, this.y, this.z, Item.SLIMEBALL);
                // fx.renderDistanceMultiplier = 10.0;
                // fx.texture = texfxindex;
                // AetherPoison.mc.particleManager.addParticle(fx);
                this.removed = false;
                this.inGround = false;
                this.x = this.victim.x;
                this.y = this.victim.boundingBox.minY + (double) this.victim.height * 0.8;
                this.z = this.victim.z;
                // todo: poison AetherPoison.distractEntity(this.victim);
                --this.poisonTime;
                if (this.poisonTime % 50 == 0) {
                    this.victim.damage(this.shooter, 1);
                }
            }

        }
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("PoisonDart");
    }
}
