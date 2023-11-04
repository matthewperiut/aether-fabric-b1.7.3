package com.matthewperiut.aether.item.accessory;

import com.matthewperiut.accessoryapi.api.render.AccessoryRenderer;
import com.matthewperiut.accessoryapi.api.render.HasCustomRenderer;
import com.matthewperiut.accessoryapi.api.render.builtin.GloveRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.item.CustomArmourValue;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.awt.*;
import java.util.Optional;

public class ItemGloves extends ItemMoreArmor implements HasCustomRenderer, CustomArmourValue {
    AccessoryRenderer renderer;

    public ItemGloves(Identifier i, int j, String path, int l, int m) {
        super(i, j, path, l, m);
    }

    public ItemGloves(Identifier i, int j, String path, int l, int m, boolean flag) {
        super(i, j, path, l, m, flag);
    }

    @Override
    public Optional<AccessoryRenderer> getRenderer() {
        return Optional.ofNullable(renderer);
    }

    @Override
    public void constructRenderer() {
        renderer = new GloveRenderer(texture).withColor(new Color(colour));
    }

    @Override
    public double modifyDamageDealt(PlayerEntity playerEntity, int armourSlot, int initialDamage, double currentAdjustedDamage) {
        playerEntity.inventory.armor[armourSlot].applyDamage(1, playerEntity);
        if (playerEntity.inventory.armor[armourSlot].getDamage() < 1) {
            playerEntity.inventory.armor[armourSlot] = null;
        }

        // todo: glove damage adjustments
        return currentAdjustedDamage - (.1 * currentAdjustedDamage);
    }
}
