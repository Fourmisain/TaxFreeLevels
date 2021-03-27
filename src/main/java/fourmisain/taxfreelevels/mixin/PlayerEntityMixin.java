package fourmisain.taxfreelevels.mixin;

import fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
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
	public void updateExperienceLevel(PlayerEntity player, int newVanillaLevel) {
		int currentLevel = player.experienceLevel;
		int vanillaLevelCost = currentLevel - newVanillaLevel;

		if (currentLevel >= 30) {
			int xpCost = TaxFreeLevels.getXpDifference(player, 30 - vanillaLevelCost, 30);
			player.addExperience(-xpCost);
		} else {
			player.experienceLevel = newVanillaLevel;
		}
	}
}
