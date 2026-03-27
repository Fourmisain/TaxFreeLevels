package io.github.fourmisain.taxfreelevels.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {
	@Shadow @Final
	public int[] costs;

	@Inject(method = "clickMenuButton", at = @At("HEAD"))
	public void taxfreelevels$setLevelRequirement(Player player, int id, CallbackInfoReturnable<Boolean> cir) {
		TaxFreeLevels.setLevelRequirement(costs[id]);
	}
}
