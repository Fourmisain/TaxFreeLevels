package io.github.fourmisain.taxfreelevels.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import svenhjol.charm.module.extract_enchantments.ExtractEnchantments;

// for 1.18 and 1.19
@Mixin(ExtractEnchantments.class)
public abstract class CharmMixin {
	@ModifyArg(
		method = "lambda$handleOnTake$1(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
