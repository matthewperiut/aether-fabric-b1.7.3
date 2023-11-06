package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityIncubator;
import com.matthewperiut.aether.blockentity.container.ContainerIncubator;
import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.inventory.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiIncubator extends ContainerScreen
{
    private final BlockEntityIncubator incubatorInventory;

    public GuiIncubator(PlayerInventory inventoryplayer, BlockEntityIncubator tileentityIncubator)
    {
        super(new ContainerIncubator(inventoryplayer, tileentityIncubator));
        this.incubatorInventory = tileentityIncubator;
    }

    protected void renderForeground()
    {
        this.textRenderer.drawText("Incubator", 60, 6, 4210752);
        this.textRenderer.drawText("Inventory", 8, this.containerHeight - 96 + 2, 4210752);
    }

    protected void renderContainerBackground(float f)
    {
        int i = this.client.textureManager.getTextureId("/assets/aether/stationapi/textures/gui/incubator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.textureManager.bindTexture(i);
        int j = (this.width - this.containerWidth) / 2;
        int k = (this.height - this.containerHeight) / 2;
        this.blit(j, k, 0, 0, this.containerWidth, this.containerHeight);
        int i1;
        if (this.incubatorInventory.isBurning())
        {
            i1 = this.incubatorInventory.getBurnTimeRemainingScaled(12);
            this.blit(j + 74, k + 47 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.incubatorInventory.getCookProgressScaled(54);
        this.blit(j + 103, k + 70 - i1, 179, 70 - i1, 10, i1);
    }
}
