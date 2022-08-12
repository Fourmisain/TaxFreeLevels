package io.github.fourmisain.taxfreelevels.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import svenhjol.charm.module.extract_enchantments.ExtractEnchantments;

@Mixin(ExtractEnchantments.class)
public abstract class CharmMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "lambda$handleOnTake$1(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At("HEAD")
	)
	private static void taxfreelevels$capturePlayer(ItemStack out, PlayerEntity player, ItemStack stack, World level, BlockPos pos, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "lambda$handleOnTake$1(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
