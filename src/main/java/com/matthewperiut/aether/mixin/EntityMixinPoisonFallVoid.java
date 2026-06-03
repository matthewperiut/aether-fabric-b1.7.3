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
import net.minecraft.world.World;
import com.matthewperiut.aether.gen.dim.AetherDimensions;
import com.periut.retroapi.dimension.DimensionHelper;
import com.periut.retroapi.dimension.DimensionRegistration;
import com.periut.retroapi.dimension.RetroDimensionRegistry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Entity.class)
public abstract class EntityMixinPoisonFallVoid implements AetherPoison {

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
    protected abstract void markDead();

    @Shadow public double x;

    @Shadow public double y;

    @Shadow public double z;

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tickInVoid()V"))
    public void modify(Entity instance) {
        if (instance instanceof PlayerEntity player) {
            DimensionRegistration aether = RetroDimensionRegistry.getByIdentifier(AetherDimensions.THE_AETHER);
            if (aether != null) {
                if (player.dimensionId == aether.getSerialId()) {
                    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                        if (!world.isRemote) {
                            // Client executes on client world
                            DimensionHelper.switchDimension(player, AetherDimensions.THE_AETHER, 1, new BareAetherTravelAgent());
                            VoidUtil.teleport(player, player.x, 200, player.z);
                        }
                    } else {
                        // Server executes on server world
                        DimensionHelper.switchDimension(player, AetherDimensions.THE_AETHER, 1, new BareAetherTravelAgent());
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
        markDead();
    }
}
