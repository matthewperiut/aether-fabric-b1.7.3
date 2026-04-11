package com.matthewperiut.aether.entity;

/**
 * Interface for Aether rideable entities that need player input synced from client to server.
 * In b1.7.3, the server doesn't receive player movement fields (forwardSpeed/sidewaysSpeed/jumping),
 * so we sync them via custom packets.
 */
public interface MountInput {
    float getMountForward();
    float getMountStrafe();
    boolean getMountJump();
    float getMountYaw();
    float getMountPitch();

    void setMountInput(float forward, float strafe, boolean jump, float yaw, float pitch);
}
