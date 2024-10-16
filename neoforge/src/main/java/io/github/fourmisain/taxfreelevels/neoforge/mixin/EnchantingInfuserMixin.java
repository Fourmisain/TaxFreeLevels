package io.github.fourmisain.taxfreelevels.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import fuzs.enchantinginfuser.world.inventory.InfuserMenu;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InfuserMenu.class)
public abstract class EnchantingInfuserMixin {
	@ModifyArg(
		method = {
			"lambda$clickRepairButton$8(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickRepairButton$9(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V",
			"lambda$clickRepairButton$10(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"
		},
		require = 1,
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
