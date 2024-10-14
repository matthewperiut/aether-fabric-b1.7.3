package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.CapeRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.ConfigurableRenderer;
import net.modificationstation.stationapi.api.util.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemCape extends ItemMoreArmor implements HasCustomRenderer {

    private ConfigurableRenderer renderer;

    public ItemCape(Identifier i, int j, String path, int l) {
        super(i, j, path, l);
    }

    public ItemCape(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
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
