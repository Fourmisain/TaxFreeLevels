package io.github.fourmisain.taxfreelevels.mixin.neoforge;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerWaystoneManager.class)
public abstract class WaystonesMixin {
	@ModifyArg(
		method = "applyXpCost(Lnet/minecraft/world/entity/player/Player;I)V",
		at = @At(
			value = "INVOKE",
			target = "m_6749_" // PlayerEntity.addExperienceLevels
		),
		index = 0,
		remap = false
	)
	private static int taxfreelevels$flattenWaystoneCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
