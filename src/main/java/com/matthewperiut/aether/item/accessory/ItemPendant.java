package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.NecklaceRenderer;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemPendant extends ItemMoreArmor implements HasCustomRenderer {
    public ItemPendant(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
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
        renderer = new NecklaceRenderer(texture).withColor(new Color(colour));
    }
}
