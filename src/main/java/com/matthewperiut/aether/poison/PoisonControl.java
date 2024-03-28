package com.matthewperiut.aether.poison;

import com.matthewperiut.aether.mixin.access.EntityAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.packet.play.EntityVelocityS2CPacket;
import net.minecraft.server.entity.player.ServerPlayerEntity;

public class PoisonControl {
    Entity parent;
    boolean canPoison;
    public long clock;
    public int poisonTime = 0;
    public static final int poisonInterval = 50;
    public static final int poisonDmg = 1;
    public static final int maxPoisonTime = 500;
    public double rotDFac = 0.7853981633974483;
    public double rotD;
    public double rotTaper = 0.125;
    public double motTaper = 0.2;
    public double motD;
    public double motDFac = 0.1;

    public PoisonControl(Entity parent) {
        this.parent = parent;

        canPoison = true;
        // todo: entity
        //canPoison = !(entity instanceof EntitySlider) && !(entity instanceof EntitySentry) && !(entity instanceof EntityMiniCloud) && !(entity instanceof EntityFireMonster) && !(entity instanceof EntityAechorPlant) && !(entity instanceof EntityFiroBall) && !(entity instanceof EntityCockatrice) && !(entity instanceof EntityHomeShot);
    }

    PoisonControl(Entity parent, boolean canPoison) {
        this.parent = parent;
        this.canPoison = canPoison;
    }

    public void setCanPoison(Boolean canPoison) {
        this.canPoison = canPoison;
    }

    public boolean canPoison(Entity entity) {
        return canPoison;
    }

    public void distractEntity() {
        double gauss = ((EntityAccessor) parent).getRand().nextGaussian();
        double newMotD = motDFac * gauss;
        motD = motTaper * newMotD + (1.0 - motTaper) * motD;
        parent.xVelocity += motD;
        parent.zVelocity += motD;
        double newRotD = rotDFac * gauss;
        rotD = rotTaper * newRotD + (1.0 - rotTaper) * rotD;
        parent.yaw = (float) ((double) parent.yaw + rotD);
        parent.pitch = (float) ((double) parent.pitch + rotD);
    }

    public boolean afflictPoison() {
        if (poisonTime < 0) {
            return false;
        } else {
            poisonTime = maxPoisonTime;
            return true;
        }
    }

    public boolean curePoison() {
        if (poisonTime == -maxPoisonTime) {
            return false;
        } else {
            poisonTime = -maxPoisonTime;
            return true;
        }
    }

    public void onTick() {
        if (parent.world.isClient)
            return;

        if (poisonTime < 0) {
            ++poisonTime;
        } else if (poisonTime != 0) {
            long time = parent.world.getWorldTime();
            int mod = poisonTime % poisonInterval;
            if (clock != time) {
                distractEntity();
                if (mod == 0) {
                    parent.damage(null, poisonDmg);
                }

                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
                    if (parent instanceof PlayerEntity player) {
                        informClientMovement(player);
                    }
                }

                --poisonTime;
                clock = time;
            }
        }
        if (parent instanceof PlayerEntity player) {
            player.getDataTracker().setInt(29, poisonTime);
        }
    }

    @Environment(EnvType.SERVER)
    private static void informClientMovement(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.packetHandler.send(new EntityVelocityS2CPacket(serverPlayer.entityId, serverPlayer.xVelocity, serverPlayer.yVelocity, serverPlayer.zVelocity));
        }
    }
}
