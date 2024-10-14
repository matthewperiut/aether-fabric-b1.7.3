package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import net.modificationstation.stationapi.api.util.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemGloves extends ItemMoreArmor implements HasCustomRenderer {
    AccessoryRenderer renderer;

    public ItemGloves(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
    }

    public ItemGloves(Identifier i, int j, String path, int l, int m, boolean flag) {
        super(i, j, path, l, m, flag);
    }

    @Override
    public AccessoryRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void constructRenderer() {
        if (colouriseRender)
            renderer = new GloveRenderer(texture).withColor(new Color(colour));
        else
            renderer = new GloveRenderer(texture);
    }
}
