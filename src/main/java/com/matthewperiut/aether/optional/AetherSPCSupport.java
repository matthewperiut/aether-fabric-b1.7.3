package com.matthewperiut.aether.optional;

import com.matthewperiut.aether.entity.living.EntityFireMonster;
import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.util.RetroChatUtil;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AetherSPCSupport {
    public static void init() {
        AetherSummonEntities.register();

        RetroChatUtil.commands.add(new Command() {
            @Override
            public void command(SharedCommandSource sharedCommandSource, String[] strings) {
                PlayerEntity p = sharedCommandSource.getPlayer();
                if (p != null) {
                    for (Object e : p.world.entities) {
                        if (e instanceof EntityFireMonster fire) {
                            sharedCommandSource.sendFeedback(fire.x + " " + fire.y);
                        }
                    }
                }
            }

            @Override
            public String name() {
                return "findpyroboss";
            }

            @Override
            public void manual(SharedCommandSource sharedCommandSource) {

            }
        });
    }
}
