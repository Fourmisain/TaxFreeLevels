package io.github.fourmisain.taxfreelevels.mixin.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WarpMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(PlayerWaystoneManager.class)
public abstract class WaystonesMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "tryTeleportToWaystone(Lnet/minecraft/entity/Entity;Lnet/blay09/mods/waystones/api/IWaystone;Lnet/blay09/mods/waystones/core/WarpMode;Lnet/blay09/mods/waystones/api/IWaystone;)Z",
		at = @At("HEAD"),
		remap = false
	)
	private static void taxfreelevels$capturePlayer(Entity entity, IWaystone waystone, WarpMode warpMode, @Nullable IWaystone fromWaystone, CallbackInfoReturnable<Boolean> ci) {
		if (entity instanceof PlayerEntity) {
			taxfreelevels$player = (PlayerEntity) entity;
		}
	}

	@ModifyArg(
		method = "tryTeleportToWaystone(Lnet/minecraft/entity/Entity;Lnet/blay09/mods/waystones/api/IWaystone;Lnet/blay09/mods/waystones/core/WarpMode;Lnet/blay09/mods/waystones/api/IWaystone;)Z",
		at = @At(
			value = "INVOKE",
			target = "func_82242_a" // PlayerEntity.addExperienceLevels
		),
		index = 0,
		remap = false
	)
	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
