package io.github.fourmisain.taxfreelevels.mixin;

import fuzs.enchantinginfuser.world.inventory.InfuserMenu;
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

@Mixin(InfuserMenu.class)
public abstract class EnchantingInfuserMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = {
			"lambda$clickEnchantButton$5(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickEnchantButton$6(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
		},
		require = 1,
		at = @At("HEAD")
	)
	private void taxfreelevels$capturePlayer(ItemStack itemstack, int cost, PlayerEntity player, World level, BlockPos pos, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = {
			"lambda$clickEnchantButton$5(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickEnchantButton$6(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
		},
		require = 1,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private int taxfreelevels$flattenEnchantingCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}

	@Inject(
		method = {
			"lambda$clickRepairButton$6(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickRepairButton$7(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
		},
		require = 1,
		at = @At("HEAD")
	)
	private void taxfreelevels$capturePlayer2(PlayerEntity player, int repairCost, ItemStack itemstack, World level, BlockPos pos, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = {
			"lambda$clickRepairButton$6(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickRepairButton$7(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
		},
		require = 1,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	private int taxfreelevels$flattenRepairCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
