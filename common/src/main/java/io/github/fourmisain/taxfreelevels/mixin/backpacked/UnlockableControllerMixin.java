package io.github.fourmisain.taxfreelevels.mixin.backpacked;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Pseudo
@Mixin(targets = "com.mrcrayfish.backpacked.inventory.container.UnlockableController")
public abstract class UnlockableControllerMixin {
	@ModifyArg(
		method = "*",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;giveExperienceLevels(I)V"
		),
		index = 0
	)
	private static int taxfreelevels$flattenUpgradeCost(int negativeLevelCost, @Local(argsOnly = true) ServerPlayer player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
