package io.github.fourmisain.taxfreelevels.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// for 1.17
@Mixin(targets = "svenhjol.charm.module.extract_enchantments.ExtractEnchantments$2")
public abstract class Charm1_17Mixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "lambda$onTake$0(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At("HEAD")
	)
	private void taxfreelevels$capturePlayer(Inventory inventory, PlayerEntity player, ItemStack stack, ItemStack stack2, World world, BlockPos pos, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "lambda$onTake$0(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}