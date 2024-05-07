package com.matthewperiut.aether.mixin;

import com.matthewperiut.aether.gen.dim.BareAetherTravelAgent;
import com.matthewperiut.aether.item.AetherItems;
import com.matthewperiut.aether.poison.AetherPoison;
import com.matthewperiut.aether.poison.PoisonControl;
import com.matthewperiut.aether.util.VoidUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.world.dimension.DimensionHelper;
import net.modificationstation.stationapi.api.world.dimension.VanillaDimensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

import static com.matthewperiut.aether.gen.dim.AetherDimensions.MOD_ID;

@Mixin(Entity.class)
public abstract class EntityMixin implements AetherPoison {

    // Aether Poison below
    @Unique
    PoisonControl poisonControl = new PoisonControl((Entity) (Object) this);

    public PoisonControl getPoison() {
        return poisonControl;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    void poisonTick(CallbackInfo ci) {
        poisonControl.onTick();
    }

    // Falling out of world below
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

                    for (int i = 0; i < player.inventory.main.length; i++) {
                        if (player.inventory.main[i] == null)
                            continue;
                        if (player.inventory.main[i].itemId == AetherItems.CloudParachute.id) {
                            player.inventory.main[i].use(world, player);
                            player.inventory.main[i] = null;
                            break;
                        }
                        if (player.inventory.main[i].itemId == AetherItems.CloudParachuteGold.id) {
                            player.inventory.main[i].use(world, player);
                            break;
                        }
                    }

                    return;
                }
            }
        }
        kill();
    }
}
