package io.github.fourmisain.taxfreelevels.mixin.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AnvilScreenHandler.class, priority = 1500)
public abstract class FlattenAnvilCostMixin {
	@Unique private PlayerEntity taxfreelevels$player;

	@Inject(method = "onTakeOutput", at = @At("HEAD"))
	public void taxfreelevels$capturePlayer(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "onTakeOutput",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	public int taxfreelevels$flattenAnvilCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
