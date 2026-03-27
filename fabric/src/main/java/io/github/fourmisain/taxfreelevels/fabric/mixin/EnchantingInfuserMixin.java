package io.github.fourmisain.taxfreelevels.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "fuzs.enchantinginfuser.world.inventory.InfuserMenu")
public abstract class EnchantingInfuserMixin {
	@ModifyArg(
		method = "*",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"
		),
		index = 0
	)
	private int taxfreelevels$flattenRepairCost(int negativeLevelCost, @Local(argsOnly = true) Player player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
