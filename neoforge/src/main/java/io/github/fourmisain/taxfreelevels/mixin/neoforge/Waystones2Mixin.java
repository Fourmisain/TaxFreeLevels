package io.github.fourmisain.taxfreelevels.mixin.neoforge;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "net.blay09.mods.waystones.xp.ExperienceLevelCost")
public abstract class Waystones2Mixin {
	@ModifyArg(
		method = "consume(Lnet/minecraft/world/entity/player/Player;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V",
			remap = true
		),
		index = 0,
		remap = false
	)
	private int taxfreelevels$flattenWaystoneCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		LogManager.getLogger("debug").info("negativeLevelCost {}", negativeLevelCost);
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
