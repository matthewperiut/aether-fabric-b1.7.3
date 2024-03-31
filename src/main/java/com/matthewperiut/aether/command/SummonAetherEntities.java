package com.matthewperiut.aether.command;

import com.matthewperiut.aether.entity.living.EntityAechorPlant;
import com.matthewperiut.aether.entity.living.EntityMoa;
import com.matthewperiut.aether.util.MoaColour;
import com.matthewperiut.spc.api.SummonRegistry;

public class SummonAetherEntities {
    public static void register() {
        SummonRegistry.add(EntityAechorPlant.class, (world, pos, param) -> new EntityAechorPlant(world), "");
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
        /*
            SummonRegistry.add(PigEntity.class, (level, pos, param) -> {
            int meta = Integer.parseInt(param[5]);
            if (meta != 0)
                meta = 1;

            PigEntity pig = new PigEntity(level);
            ((EntityAccessor) pig).getDataTracker().setInt(16, (byte) meta);
            return pig;
        }, "{saddle (0 or 1)}");
         */
    }
}
