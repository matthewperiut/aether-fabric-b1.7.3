package com.matthewperiut.aether.item.util;

import net.minecraft.item.ArmorItem;

/**
 * Replacement for StationAPI's ArmorTextureProvider, consumed by Aether's own armor-render mixin.
 * Returns the worn-armor texture path (classpath resource) for this armor piece.
 */
public interface CustomArmorTexture {
    /** @param layer 1 for leggings layer, 0 otherwise (vanilla "_1"/"_2" suffix convention) */
    String getArmorTexture(ArmorItem item, int layer);
}
