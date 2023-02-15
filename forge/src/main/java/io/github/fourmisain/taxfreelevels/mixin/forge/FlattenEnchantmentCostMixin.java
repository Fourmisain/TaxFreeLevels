package io.github.fourmisain.taxfreelevels.mixin.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class, priority = 1500)
public abstract class FlattenEnchantmentCostMixin {
	@Shadow
	public int experienceLevel;

	@Unique
	private int taxfreelevels$previousLevel;

	@Inject(method = "applyEnchantmentCosts", at = @At(value = "HEAD"))
	public void taxfreelevels$rememberExperienceLevel(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci) {
		taxfreelevels$previousLevel = experienceLevel;
	}

	// Forge patched experienceLevel -= experienceLevels to use addExperienceLevels(), so the ordinal is different
	@Inject(
		method = "applyEnchantmentCosts",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/entity/player/PlayerEntity;experienceLevel:I",
			ordinal = 0 // right before this.experienceLevel < 0
		)
	)
	public void taxfreelevels$flattenEnchantmentCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci) {
		// calculate cost instead of using experienceLevels parameter for compatibility
		int levelCost = taxfreelevels$previousLevel - experienceLevel;

		// reset level and apply level cost as XP cost
		experienceLevel = taxfreelevels$previousLevel;
		TaxFreeLevels.applyFlattenedXpCost((PlayerEntity) (Object) this, levelCost);
	}
}
