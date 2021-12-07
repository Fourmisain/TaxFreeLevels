package io.github.fourmisain.taxfreelevels.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class FlattenEnchantmentCostMixin {
	/**
	 * Flatten enchantment cost
	 * Should replace experienceLevel -= experienceLevels
	 */
	@Redirect(
		method = "applyEnchantmentCosts",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/entity/player/PlayerEntity;experienceLevel:I",
			ordinal = 1
		)
	)
	public void taxfreelevels$flattenEnchantmentCost(PlayerEntity player, int newLevel) {
		int levelCost = player.experienceLevel - newLevel;

		TaxFreeLevels.applyFlattenedXpCost(player, levelCost);
	}
}
