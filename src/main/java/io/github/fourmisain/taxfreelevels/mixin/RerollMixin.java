package io.github.fourmisain.taxfreelevels.mixin;

import draylar.reroll.Reroll;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Reroll.class, priority = 1500)
public abstract class RerollMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(method = "reroll", at = @At("HEAD"))
	private static void taxfreelevels$capturePlayer(PlayerEntity player, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "reroll",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V",
			remap = true
		),
		index = 0,
		remap = false
	)
	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
