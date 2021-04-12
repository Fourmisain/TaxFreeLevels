package fourmisain.taxfreelevels.mixin;

import draylar.reroll.Reroll;
import fourmisain.taxfreelevels.TaxFreeLevels;
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
	private static void updateExperienceLevel(PlayerEntity player, int negativeLevelCost) {
		int currentLevel = player.experienceLevel;

		if (currentLevel >= 30) {
			int xpCost = TaxFreeLevels.getXpDifference(player, 30 + negativeLevelCost, 30);
			player.addExperience(-xpCost);
		} else {
			player.addExperienceLevels(negativeLevelCost);
		}
	}
}
