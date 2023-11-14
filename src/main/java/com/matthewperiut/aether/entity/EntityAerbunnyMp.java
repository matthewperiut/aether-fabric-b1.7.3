package com.matthewperiut.aether.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.packet.play.ChatMessagePacket;
import net.minecraft.packet.play.EntityVelocityS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxixAlignedBoundingBox;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.packet.PacketHelper;

import java.util.List;

public class EntityAerbunnyMp extends EntityAerbunny {
    public EntityAerbunnyMp(World world) {
        super(world);
    }

    public void tickHandSwing() {
        super.tickHandSwing();
        /* todo: packet
        ModLoaderPacket packetMove = new ModLoaderPacket();
        packetMove.packetType = 69;
        packetMove.dataFloat = new float[]{(float)this.xVelocity, (float)this.yVelocity, (float)this.zVelocity, (float)this.x, (float)this.y, (float)this.z, this.yaw, this.pitch, this.puffiness};
        ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packetMove);
         */
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            EntityVelocityS2CPacket packet = new EntityVelocityS2CPacket(this);

            List<ServerPlayerEntity> players = world.getEntities(ServerPlayerEntity.class, AxixAlignedBoundingBox.create(x-20, y-20, z-20, x+20,y+20,z+20));
            for (ServerPlayerEntity player : players) {
                player.packetHandler.send(packet);
            }
        }
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, String.valueOf(0));
        this.dataTracker.startTracking(17, (byte)0);
        this.dataTracker.startTracking(18, String.valueOf(""));
    }

    public void runLikeHell() {
    }

    protected Entity findPlayerToRunFrom() {
        return null;
    }

    public void cloudPoop() {
        double a = (double)(this.rand.nextFloat() - 0.5F);
        double d = this.x + a * 0.4000000059604645;
        double e = this.boundingBox.minY;
        double f = this.z + a * 0.4000000059604645;
        /* todo: packet
        ModLoaderPacket packet = new ModLoaderPacket();
        packet.packetType = 71;
        packet.dataFloat = new float[]{(float)d, (float)e, (float)f, 0.0F, -0.075F, 0.0F, (float)this.x, (float)this.y, (float)this.z};
        packet.dataString = new String[]{"explode"};
        ModLoaderMp.SendPacket(ModLoaderMp.GetModInstance(mod_AetherMp.class), packet); */
    }

    public void jump() {
    }

    public boolean interact(PlayerEntity entityplayer) {
        if (this.vehicle != null && !this.getPlayer().equals(entityplayer.name)) {
            return true;
        } else {
            if (!this.getPlayer().equals("")) {
                this.setPlayer("");
            }

            return super.interact(entityplayer);
        }
    }

    public float getPuffiness() {
        String s = this.dataTracker.getString(16);
        if (s == null || s.equals("")) {
            s = "0";
        }

        return Float.valueOf(s);
    }

    public void setPuffiness(float value) {
        this.dataTracker.setInt(16, String.valueOf(value));
    }

    public boolean getRenderOnGround() {
        return (this.dataTracker.getByte(17) & 1) != 0;
    }

    public void setRenderOnGround(boolean flag) {
        if (flag) {
            this.dataTracker.setInt(17, (byte)1);
        } else {
            this.dataTracker.setInt(17, (byte)0);
        }

    }

    public String getPlayer() {
        return this.dataTracker.getString(18);
    }

    public void setPlayer(String value) {
        this.dataTracker.setInt(16, String.valueOf(value));
    }
}
