package com.matthewperiut.aether.client.gui;

import com.matthewperiut.aether.blockentity.block.BlockEntityTreasureChest;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import org.lwjgl.opengl.GL11;

public class GuiTreasureChest extends HandledScreen {
    private final Inventory upperChestInventory;
    private final Inventory lowerChestInventory;
    private final String name;
    private int inventoryRows;

    public GuiTreasureChest(final Inventory iinventory, final BlockEntityTreasureChest iinventory1) {
        super(new GenericContainerScreenHandler(iinventory, iinventory1));
        this.inventoryRows = 0;
        this.upperChestInventory = iinventory;
        this.lowerChestInventory = iinventory1;
        this.passEvents = false;
        final char c = '\u00de';
        final int i = c - 'l';
        this.inventoryRows = iinventory1.size() / 9;
        this.backgroundHeight = i + this.inventoryRows * 18;

        switch (iinventory1.rarity) {
            case 1: {
                this.name = "Bronze Treasure Chest";
                break;
            }
            case 2: {
                this.name = "Silver Treasure Chest";
                break;
            }
            case 3: {
                this.name = "Gold Treasure Chest";
                break;
            }
            default: {
                this.name = "Treasure Chest";
            }
        }
    }

    @Override
    protected void drawForeground() {
        this.textRenderer.draw(this.name, 8, 6, 4210752);
        this.textRenderer.draw(this.upperChestInventory.getName(), 8, this.backgroundHeight - 96 + 2, 4210752);
    }

    @Override
    protected void drawBackground(final float tickDelta) {
        final int i = this.minecraft.textureManager.getTextureId("/gui/container.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.textureManager.bindTexture(i);
        final int j = (this.width - this.backgroundWidth) / 2;
        final int k = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(j, k, 0, 0, this.backgroundWidth, this.inventoryRows * 18 + 17);
        this.drawTexture(j, k + this.inventoryRows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }
}