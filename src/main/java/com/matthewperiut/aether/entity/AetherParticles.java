package com.matthewperiut.aether.entity;

import com.matthewperiut.aether.entity.fx.EntityAetherPortalFX;
import com.matthewperiut.aether.entity.fx.EntityCloudSmokeFX;
import net.minecraft.client.Minecraft;

public class AetherParticles {
    public static void addAetherParticle(Minecraft client, String particleId, double x, double y, double z, double xTo, double yTo, double zTo) {
        if (client != null && client.camera != null && client.particleManager != null) {
            double var14 = client.camera.x - x;
            double var16 = client.camera.y - y;
            double var18 = client.camera.z - z;
            double var20 = 16;
            if (!(var14 * var14 + var16 * var16 + var18 * var18 > var20 * var20)) {
                if (particleId.equals("aether_portal")) {
                    client.particleManager.addParticle(new EntityAetherPortalFX(client.world, x, y, z, xTo, yTo, zTo));
                } else if (particleId.equals("white_cloud_smoke")) {
                    client.particleManager.addParticle(new EntityCloudSmokeFX(client.world, x, y, z, xTo, yTo, zTo, 2.5F, 1.0F, 1.0F, 1.0F));
                }
            }
        }
    }
}
