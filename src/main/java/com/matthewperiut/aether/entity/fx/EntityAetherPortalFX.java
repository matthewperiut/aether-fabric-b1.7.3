package com.matthewperiut.aether.entity.fx;

import net.minecraft.client.entity.particle.PortalParticleEntity;
import net.minecraft.world.World;

public class EntityAetherPortalFX extends PortalParticleEntity {
    public EntityAetherPortalFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.red = this.green = this.blue = 1.0F * f;
        this.red *= 0.2F;
        this.green *= 0.2F;
    }
}
