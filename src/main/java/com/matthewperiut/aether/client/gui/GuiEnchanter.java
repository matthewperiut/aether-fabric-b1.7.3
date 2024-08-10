package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityEnchanter;
import com.matthewperiut.aether.blockentity.container.EnchanterScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiEnchanter extends HandledScreen {
    private final BlockEntityEnchanter enchanterInventory;

    public GuiEnchanter(PlayerInventory inventoryplayer, BlockEntityEnchanter tileentityEnchanter) {
        super(new EnchanterScreenHandler(inventoryplayer, tileentityEnchanter));
        this.enchanterInventory = tileentityEnchanter;
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.drawWithShadow("Enchanter", 60, 6, 4210752);
        this.textRenderer.drawWithShadow("Inventory", 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int i = this.minecraft.textureManager.getTextureId("/assets/aether/stationapi/textures/gui/enchanter.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(i);
        int j = (this.width - this.backgroundWidth) / 2;
        int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(j, k, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int i1;
        if (this.enchanterInventory.isBurning()) {
            i1 = this.enchanterInventory.getBurnTimeRemainingScaled(12);
            this.drawTexture(j + 57, k + 47 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.enchanterInventory.getCookProgressScaled(24);
        this.drawTexture(j + 79, k + 35, 176, 14, i1 + 1, 16);
    }
}
