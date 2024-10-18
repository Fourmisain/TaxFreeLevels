package io.github.fourmisain.taxfreelevels.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import draylar.reroll.Reroll;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Reroll.class, priority = 1500, remap = false)
public abstract class RerollMixin {
	@Inject(method = "reroll", at = @At(value = "INVOKE", target = "Ldraylar/reroll/impl/PlayerEntityManipulator;rerollEnchantmentSeed()V"), remap = false)
	private static void taxfreelevels$setLevelRequirement(CallbackInfo ci, @Local(argsOnly = true) PlayerEntity player) {
		if (player.currentScreenHandler instanceof EnchantmentScreenHandler enchantmentScreenHandler) {
			// you would normally "reroll" by using the cheapest option
			TaxFreeLevels.setLevelRequirement(enchantmentScreenHandler.enchantmentPower[0]);
		}
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
	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}

	@Inject(method = "reroll", at = @At("TAIL"), remap = false)
	private static void taxfreelevels$resetLevelRequirement(CallbackInfo ci) {
		TaxFreeLevels.resetLevelRequirement();
	}
}
