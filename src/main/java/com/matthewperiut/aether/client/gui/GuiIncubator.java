package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.container.ContainerIncubator;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiIncubator extends HandledScreen {
    private final BlockEntityIncubator incubatorInventory;

    public GuiIncubator(PlayerInventory inventoryplayer, BlockEntityIncubator tileentityIncubator) {
        super(new ContainerIncubator(inventoryplayer, tileentityIncubator));
        this.incubatorInventory = tileentityIncubator;
    }

    protected void drawForeground() {
        this.textRenderer.draw("Incubator", 60, 6, 4210752);
        this.textRenderer.draw("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    protected void drawBackground(float f) {
        int i = this.minecraft.textureManager.getTextureId("/assets/aether/stationapi/textures/gui/incubator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(i);
        int j = (this.width - this.backgroundWidth) / 2;
        int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(j, k, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int i1;
        if (this.incubatorInventory.isBurning()) {
            i1 = this.incubatorInventory.getBurnTimeRemainingScaled(12);
            this.drawTexture(j + 74, k + 47 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.incubatorInventory.getCookProgressScaled(54);
        this.drawTexture(j + 103, k + 70 - i1, 179, 70 - i1, 10, i1);
    }
}
