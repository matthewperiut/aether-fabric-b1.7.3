package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.NecklaceRenderer;
import net.modificationstation.stationapi.api.util.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemIcePendant extends ItemIceAccessory implements HasCustomRenderer {
    AccessoryRenderer renderer;

    public ItemIcePendant(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
    }

    @Override
    public AccessoryRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void constructRenderer() {
        renderer = new NecklaceRenderer(texture).withColor(new Color(colour));
    }
}
