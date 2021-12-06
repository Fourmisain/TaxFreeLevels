package io.github.fourmisain.taxfreelevels.mixin;

import draylar.reroll.Reroll;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Reroll.class)
public abstract class RerollMixin {
	/**
	 * Flatten reroll cost
	 * Should replace PlayerEntity.addExperienceLevels(-levelsPerReroll)
	 */
	@Redirect(
		method = {"lambda$null$2", "reroll"},
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/class_1657;method_7316(I)V"
		),
		remap = false
	)
	private static void taxfreelevels$flattenRerollCost(PlayerEntity player, int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedEnchantmentCost(player, -negativeLevelCost);
	}
}
