package io.github.fourmisain.taxfreelevels.forge.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.blay09.mods.waystones.core.PlayerWaystoneManager")
public abstract class WaystonesMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "applyXpCost(Lnet/minecraft/world/entity/player/Player;I)V",
		at = @At("HEAD"),
		remap = false
	)
	private static void taxfreelevels$capturePlayer(PlayerEntity player, int xpLevelCost, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "applyXpCost(Lnet/minecraft/world/entity/player/Player;I)V",
		at = @At(
			value = "INVOKE",
			target = "m_6749_" // PlayerEntity.addExperienceLevels
		),
		index = 0,
		remap = false
	)
	private static int taxfreelevels$flattenWaystoneCost(int negativeLevelCost) {
		LogManager.getLogger("debug").info("negativeLevelCost {}", negativeLevelCost);
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
