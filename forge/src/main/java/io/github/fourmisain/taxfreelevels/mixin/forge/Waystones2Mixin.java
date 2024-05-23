package io.github.fourmisain.taxfreelevels.mixin.forge;

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
@Mixin(targets = "net.blay09.mods.waystones.xp.ExperienceLevelCost")
public abstract class Waystones2Mixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "consume(Lnet/minecraft/world/entity/player/Player;)V",
		at = @At("HEAD"),
		remap = false
	)
	private void taxfreelevels$capturePlayer(PlayerEntity player, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "consume(Lnet/minecraft/world/entity/player/Player;)V",
		at = @At(
			value = "INVOKE",
			target = "m_6749_"
		),
		index = 0,
		remap = false
	)
	private int taxfreelevels$flattenWaystoneCost(int negativeLevelCost) {
		LogManager.getLogger("debug").info("negativeLevelCost {}", negativeLevelCost);
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
