package com.matthewperiut.aether.optional;

import com.matthewperiut.aether.entity.living.EntityFireMonster;
import com.periut.retrocommands.api.Command;
import com.periut.retrocommands.util.RetroChatUtil;
import com.periut.retrocommands.util.SharedCommandSource;
import net.minecraft.entity.player.PlayerEntity;

public class AetherRetroCommandsSupport {
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
