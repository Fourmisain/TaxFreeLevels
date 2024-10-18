package io.github.fourmisain.taxfreelevels.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {
	@Shadow @Final
	public int[] enchantmentPower;

	@Inject(method = "onButtonClick", at = @At("HEAD"))
	public void taxfreelevels$setLevelRequirement(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
		TaxFreeLevels.setLevelRequirement(enchantmentPower[id]);
	}
}
