package com.matthewperiut.aether.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.block.MetaNamedBlockItemProvider;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class DungeonBlock extends TemplateBlock implements MetaNamedBlockItemProvider {
    public static int sprBronze;
    public static int sprSilver;
    public static int sprGold;
    public static int sprBronzeLit;
    public static int sprSilverLit;
    public static int sprGoldLit;

    protected DungeonBlock(Identifier identifier) {
        super(identifier, Material.STONE);
    }

    public static int func_21034_c(int i) {
        return ~i & 15;
    }

    public static int func_21035_d(int i) {
        return ~i & 15;
    }

    public int getTexture(int i, int j) {
        if (j == 2) {
            return this.isLit() ? sprGoldLit : sprGold;
        } else if (j == 1) {
            return this.isLit() ? sprSilverLit : sprSilver;
        } else {
            return this.isLit() ? sprBronzeLit : sprBronze;
        }
    }

    private boolean isLit() {
        return this.id == AetherBlocks.LightDungeonStone.id || this.id == AetherBlocks.LockedLightDungeonStone.id;
    }

    protected int getDroppedItemMeta(int i) {
        return i;
    }

    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
