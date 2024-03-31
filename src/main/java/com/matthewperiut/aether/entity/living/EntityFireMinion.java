package com.matthewperiut.aether.entity.living;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import static com.matthewperiut.aether.entity.AetherEntities.MOD_ID;

public class EntityFireMinion extends MonsterEntity implements MobSpawnDataProvider {
    public EntityFireMinion(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/firemonster.png";
        this.movementSpeed = 1.5F;
        this.attackDamage = 5;
        this.health = 40;
        this.immuneToFire = true;
    }

    public void tick() {
        super.tick();
        if (this.health > 0) {
            for (int j = 0; j < 4; ++j) {
                double a = (double) (this.rand.nextFloat() - 0.5F);
                double b = (double) this.rand.nextFloat();
                double c = (double) (this.rand.nextFloat() - 0.5F);
                double d = this.x + a * b;
                double e = this.boundingBox.minY + b - 0.5;
                double f = this.z + c * b;
                this.world.addParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0);
            }
        }

    }

    @Override
    public Identifier getHandlerIdentifier() {
        return MOD_ID.id("FireMinion");
    }
}
