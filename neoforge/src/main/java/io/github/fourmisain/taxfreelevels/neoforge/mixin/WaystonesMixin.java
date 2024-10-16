package io.github.fourmisain.taxfreelevels.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "net.blay09.mods.waystones.core.PlayerWaystoneManager")
public abstract class WaystonesMixin {
	@ModifyArg(
		method = "applyXpCost(Lnet/minecraft/world/entity/player/Player;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V",
			remap = true
		),
		index = 0,
		remap = true
	)
	private static int taxfreelevels$flattenWaystoneCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
