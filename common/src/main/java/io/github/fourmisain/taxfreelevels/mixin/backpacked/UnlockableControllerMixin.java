package io.github.fourmisain.taxfreelevels.mixin.backpacked;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "com.mrcrayfish.backpacked.inventory.container.UnlockableController")
public abstract class UnlockableControllerMixin {
	@ModifyArg(
		method = "*",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/network/ServerPlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private static int taxfreelevels$flattenUpgradeCost(int negativeLevelCost, @Local(argsOnly = true) ServerPlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
