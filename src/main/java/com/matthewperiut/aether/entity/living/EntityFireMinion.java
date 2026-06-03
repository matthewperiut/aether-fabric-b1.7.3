package com.matthewperiut.aether.entity.living;

import com.periut.retroapi.entity.spawn.RetroMobSpawnData;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import com.matthewperiut.aether.Aether;

import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.world.World;


public class EntityFireMinion extends MonsterEntity implements RetroMobSpawnData {
    public EntityFireMinion(World world) {
        super(world);
        this.texture = "aether:stationapi/textures/mobs/firemonster.png";
        this.movementSpeed = 1.5F;
        this.attackDamage = 5;
        this.health = 40;
        this.fireImmune = true;
    }

    public void tick() {
        super.tick();
        if (this.health > 0) {
            for (int j = 0; j < 4; ++j) {
                double a = (double) (this.random.nextFloat() - 0.5F);
                double b = (double) this.random.nextFloat();
                double c = (double) (this.random.nextFloat() - 0.5F);
                double d = this.x + a * b;
                double e = this.boundingBox.minY + b - 0.5;
                double f = this.z + c * b;
                this.world.addParticle("flame", d, e, f, 0.0, -0.07500000298023224, 0.0);
            }
        }

    }

    @Override
    public NamespacedIdentifier getHandlerId() {
        return Aether.id("FireMinion");
    }
}
