package io.github.fourmisain.taxfreelevels.forge.mixin;

import dev.shadowsoffire.placebo.util.EnchantmentUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static dev.shadowsoffire.placebo.util.EnchantmentUtils.*;
import static io.github.fourmisain.taxfreelevels.TaxFreeLevels.addNoScoreExperience;
import static io.github.fourmisain.taxfreelevels.TaxFreeLevels.getFlattenedXpCost;

@Mixin(EnchantmentUtils.class)
public abstract class EnchantmentUtilsMixin {
	/**
	 * Replaces Placebo's (and therefore Apotheosis') "optimal cost" calculation by turning the XP cost back
	 * into a level cost and using Tax Free Level's calculation from there.
	 * @author Fourmisain
	 */
	@Overwrite
	public static boolean chargeExperience(PlayerEntity player, int xpCost) {
		if (xpCost < 0) return false; // consistent with original chargeExperience

		// getTotalExperienceForLevel(l) == getXpDifference(player, 0, l), assuming player.getNextLevelExperience() hasn't changed
		// getLevelForExperience(getTotalExperienceForLevel(l)) == l

		// turn xpCost into levelCost + remainder
		int levelCost = getLevelForExperience(xpCost);
		int xpCostRemainder = (xpCost - getTotalExperienceForLevel(levelCost)); // this should be 0 for regular Anvil/Enchanting Table use

		// flatten level cost, add remainder
		int flattenedXpCost = getFlattenedXpCost(player, levelCost) + xpCostRemainder;

		if (getExperience(player) < flattenedXpCost)
			return false;

		// finally pay the cost
		addNoScoreExperience(player, -flattenedXpCost);
		return true;
	}
}
