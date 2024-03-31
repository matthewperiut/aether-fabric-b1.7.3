package com.matthewperiut.aether.optional;

import com.matthewperiut.aether.entity.living.EntityMoa;
import com.matthewperiut.aether.entity.living.EntitySheepuff;
import com.matthewperiut.aether.entity.living.EntitySwet;
import com.matthewperiut.aether.entity.living.EntityValkyrie;
import com.matthewperiut.aether.util.MoaColour;
import com.matthewperiut.spc.api.SummonRegistry;

public class AetherSummonEntities {
    public static void register() {
        SummonRegistry.add(EntityMoa.class, (world, pos, param) -> {
            boolean saddled = false;
            if (param.length > 5)
                if (Integer.parseInt(param[5]) == 1) {
                    saddled = true;
                }
            boolean grown = true;
            if (param.length > 6)
                if (Integer.parseInt(param[6]) == 0) {
                    grown = false;
                }
            MoaColour color = MoaColour.getColour(0);
            if (param.length > 7) {
                if (param[7].equalsIgnoreCase("black")) {
                    color = MoaColour.getColour(1);
                }
                if (param[7].equalsIgnoreCase("white")) {
                    color = MoaColour.getColour(2);
                }
            }
            return new EntityMoa(world, !grown, grown, saddled, color);
        }, "{saddled 0/1} {grown 0/1} {Blue/Black/White}");
        SummonRegistry.add(EntitySheepuff.class, (level, pos, parameters) -> {
            int color = Integer.parseInt(parameters[5]);
            if (color >= 0 && color <= 15) {
                return new EntitySheepuff(level, color);
            } else {
                return null;
            }
        }, "{color 0-15}");

        SummonRegistry.add(EntitySwet.class, (level, pos, parameters) -> {
            int color = Integer.parseInt(parameters[5]);
            if (color >= 0 && color <= 1) {
                return new EntitySwet(level, color);
            } else {
                return null;
            }
        }, "{color 0/1}");

        SummonRegistry.add(EntityValkyrie.class, (level, pos, parameters) -> {
            boolean boss = Integer.parseInt(parameters[5]) != 0;
            return new EntityValkyrie(level, pos.x, pos.y, pos.z, boss);
        }, "{boss (0/1)}");
    }
}
