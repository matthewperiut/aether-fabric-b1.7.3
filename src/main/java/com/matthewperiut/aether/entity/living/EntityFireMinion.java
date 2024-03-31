package com.matthewperiut.aether.entity.living;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class EntityFireMinion extends MonsterEntity {
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
}
