package io.github.fourmisain.taxfreelevels.mixin.forge;

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

@Mixin(value = InfuserMenu.class, priority = 1500)
public abstract class EnchantingInfuserMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "lambda$clickMenuButton$5(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At("HEAD"),
		remap = false
	)
	private void taxfreelevels$capturePlayer(ItemStack itemstack, int cost, PlayerEntity player, World level, BlockPos pos, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "lambda$clickMenuButton$5(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
		at = @At(
			value = "INVOKE",
			target = "func_192024_a" // Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentCosts(Lnet/minecraft/item/ItemStack;I)V
		),
		index = 1,
		remap = false
	)
	private int taxfreelevels$flattenEnchantingCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
