package com.matthewperiut.aether.mixin.client;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixinJitterFix {
	@Inject(at = @At("HEAD"), method = "move", cancellable = true, require = 0)
	private void init(CallbackInfo ci) {
		if (((Minecraft) FabricLoaderImpl.INSTANCE.getGameInstance()).world.isRemote &&
				(Object) this instanceof LivingEntity &&
				!((Object) this instanceof ClientPlayerEntity))
			ci.cancel();
	}
}