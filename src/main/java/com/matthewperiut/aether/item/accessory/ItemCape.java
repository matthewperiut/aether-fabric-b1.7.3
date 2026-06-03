package com.matthewperiut.aether.item.accessory;

import com.periut.retroapi.register.item.RetroItemAccess;

import com.periut.accessoryapi.api.render.AccessoryRenderer;
import com.periut.accessoryapi.api.render.HasCustomRenderer;
import com.periut.accessoryapi.api.render.builtin.CapeRenderer;
import com.periut.accessoryapi.api.render.builtin.ConfigurableRenderer;

import java.awt.*;
import java.util.Optional;

public class ItemCape extends ItemMoreArmor implements HasCustomRenderer {

    private ConfigurableRenderer renderer;

    public ItemCape(int j, String path, int l) {
        super(j, path, l);
    }

    public ItemCape(int j, String path, int l, int m) {
        super(j, path, l, m);
    }

    @Override
    public AccessoryRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void constructRenderer() {
        renderer = new CapeRenderer(texture).withColor(new Color(colour));
    }
}
