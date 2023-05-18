package io.github.fourmisain.taxfreelevels.mixin;

// for 1.18 and 1.19
//@Mixin(ExtractEnchantments.class)
public abstract class CharmMixin {
//	@Unique
//	private static PlayerEntity taxfreelevels$player;
//
//	@Inject(
//		method = "lambda$handleOnTake$1(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
//		at = @At("HEAD")
//	)
//	private static void taxfreelevels$capturePlayer(ItemStack out, PlayerEntity player, ItemStack stack, World level, BlockPos pos, CallbackInfo ci) {
//		taxfreelevels$player = player;
//	}
//
//	@ModifyArg(
//		method = "lambda$handleOnTake$1(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
//		),
//		index = 0
//	)
//	private static int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
//		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
//		return 0; // we already paid in XP
//	}
}
