package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemGloves extends ItemMoreArmor implements HasCustomRenderer {
    public ItemGloves(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
    }

    public ItemGloves(Identifier i, int j, String path, int l, int m, boolean flag) {
        super(i, j, path, l, m, flag);
    }

    AccessoryRenderer renderer;
    @Override
    public Optional<AccessoryRenderer> getRenderer()
    {
        return Optional.ofNullable(renderer);
    }

    @Override
    public void constructRenderer()
    {
        renderer = new GloveRenderer(texture).withColor(new Color(colour));
    }
}
