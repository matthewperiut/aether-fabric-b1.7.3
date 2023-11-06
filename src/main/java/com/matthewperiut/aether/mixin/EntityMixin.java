package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.gen.dim.BareAetherTravelAgent;
import com.matthewperiut.aether.util.VoidUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper;
import net.modificationstation.stationapi.api.world.dimension.VanillaDimensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.OptionalInt;

import static com.matthewperiut.aether.gen.dim.AetherDimensions.MOD_ID;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public World world;

    @Shadow
    protected abstract void kill();

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;kill()V"))
    public void kill(Entity instance) {
        if (instance instanceof PlayerEntity player) {
            @NotNull OptionalInt dimensionId = DimensionRegistry.INSTANCE.getLegacyId(MOD_ID.id("the_aether"));
            if (dimensionId.isPresent()) {
                if (player.dimensionId == dimensionId.getAsInt()) {
                    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                        if (!world.isClient) {
                            // Client executes on client world
                            DimensionHelper.switchDimension(player, VanillaDimensions.OVERWORLD, 1, new BareAetherTravelAgent());
                            VoidUtil.teleport(player, player.x, 200, player.z);
                        }
                    } else {
                        // Server executes on server world
                        DimensionHelper.switchDimension(player, VanillaDimensions.OVERWORLD, 1, new BareAetherTravelAgent());
                        VoidUtil.teleport(player, player.x, 200, player.z);
                    }

                    return;
                }
            }
        }
        kill();
    }
}
