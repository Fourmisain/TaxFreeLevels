package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.inventories.BedrockAnvilScreenHandler;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = BedrockAnvilScreenHandler.class, priority = 1500)
public abstract class SpectrumMixin {
	@ModifyArg(
		method = "onTakeOutput",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	public int taxfreelevels$flattenAnvilCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
