package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.periut.accessoryapi.api.render.AccessoryRenderer;
import com.periut.accessoryapi.api.render.HasCustomRenderer;
import com.periut.accessoryapi.api.render.builtin.NecklaceRenderer;

import java.awt.*;
import java.util.Optional;

public class ItemIcePendant extends ItemIceAccessory implements HasCustomRenderer {
    AccessoryRenderer renderer;

    public ItemIcePendant(int j, String path, int l, int m) {
        super(j, path, l, m);
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
