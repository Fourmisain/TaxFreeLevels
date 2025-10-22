package io.github.fourmisain.taxfreelevels.forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "com.chirptheboy.disenchanting.block.disenchant.ContainerDisenchant")
public abstract class DisenchantingForgeMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "disenchantItem",
		at = @At("HEAD"),
		remap = false
	)
	private void taxfreelevels$capturePlayer(CallbackInfo ci, @Local(argsOnly = true) PlayerEntity player) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "disenchantItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V",
			remap = true
		),
		index = 0,
		remap = false
	)
	private int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
