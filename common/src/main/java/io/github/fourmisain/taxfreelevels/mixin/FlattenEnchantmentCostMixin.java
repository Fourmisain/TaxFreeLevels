package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class, priority = 1500)
public abstract class FlattenEnchantmentCostMixin {
	@Shadow
	public int experienceLevel;

	@Inject(method = "applyEnchantmentCosts", at = @At(value = "HEAD"))
	public void taxfreelevels$rememberExperienceLevel(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci, @Share("previousLevel") LocalIntRef previousLevel) {
		previousLevel.set(experienceLevel);
	}

	@Inject(
		method = "applyEnchantmentCosts",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/entity/player/PlayerEntity;experienceLevel:I",
			ordinal = 2 // right before this.experienceLevel < 0
		)
	)
	public void taxfreelevels$flattenEnchantmentCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci, @Share("previousLevel") LocalIntRef previousLevel) {
		// calculate cost instead of using experienceLevels parameter for compatibility
		int levelCost = previousLevel.get() - experienceLevel;

		// reset level and apply level cost as XP cost
		experienceLevel = previousLevel.get();
		TaxFreeLevels.applyFlattenedXpCost((PlayerEntity) (Object) this, levelCost);
	}
}
