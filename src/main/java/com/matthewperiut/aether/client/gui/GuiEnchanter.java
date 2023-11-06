package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.container.ContainerEnchanter;
import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.inventory.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiEnchanter extends ContainerScreen {
    private final BlockEntityEnchanter enchanterInventory;

    public GuiEnchanter(PlayerInventory inventoryplayer, BlockEntityEnchanter tileentityEnchanter) {
        super(new ContainerEnchanter(inventoryplayer, tileentityEnchanter));
        this.enchanterInventory = tileentityEnchanter;
    }

    protected void renderForeground() {
        this.textRenderer.drawText("Enchanter", 60, 6, 4210752);
        this.textRenderer.drawText("Inventory", 8, this.containerHeight - 96 + 2, 4210752);
    }

    protected void renderContainerBackground(float f) {
        int i = this.client.textureManager.getTextureId("/assets/aether/stationapi/textures/gui/enchanter.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.textureManager.bindTexture(i);
        int j = (this.width - this.containerWidth) / 2;
        int k = (this.height - this.containerHeight) / 2;
        this.blit(j, k, 0, 0, this.containerWidth, this.containerHeight);
        int i1;
        if (this.enchanterInventory.isBurning()) {
            i1 = this.enchanterInventory.getBurnTimeRemainingScaled(12);
            this.blit(j + 57, k + 47 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.enchanterInventory.getCookProgressScaled(24);
        this.blit(j + 79, k + 35, 176, 14, i1 + 1, 16);
    }
}
