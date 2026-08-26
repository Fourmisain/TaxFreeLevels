package io.github.fourmisain.taxfreelevels.forge.mixin.quark;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.violetmoon.quark.addons.oddities.block.be.MatrixEnchantingTableBlockEntity;
import org.violetmoon.quark.addons.oddities.inventory.EnchantmentMatrix;

@Mixin(MatrixEnchantingTableBlockEntity.class)
public abstract class MatrixEnchantingTableBlockEntityMixin {
	@Shadow
	public int bookshelfPower;

	@Inject(method = "generateAndPay", at = @At("HEAD"))
	public void taxfreelvels$setLevelRequirement(EnchantmentMatrix matrix, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		TaxFreeLevels.setLevelRequirement(matrix.getMinXpLevel(bookshelfPower));
	}

	@Inject(method = "generateAndPay", at = @At("RETURN"))
	public void taxfreelvels$resetLevelRequirement(EnchantmentMatrix matrix, PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		TaxFreeLevels.resetLevelRequirement();
	}

	@ModifyArg(
		method = "generateAndPay",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private int taxfreelevels$flattenRepairCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
