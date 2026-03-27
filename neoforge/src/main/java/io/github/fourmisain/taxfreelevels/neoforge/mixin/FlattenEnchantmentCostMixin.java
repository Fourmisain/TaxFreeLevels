package io.github.fourmisain.taxfreelevels.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, priority = 1500)
public abstract class FlattenEnchantmentCostMixin {
	@Shadow
	public int experienceLevel;

	// NeoForge patched experienceLevel -= experienceLevels to use addExperienceLevels(), so the ordinal is different
	@Inject(
		method = "onEnchantmentPerformed",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/player/Player;experienceLevel:I",
			ordinal = 0 // right before this.experienceLevel < 0
		)
	)
	public void taxfreelevels$flattenEnchantmentCost(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci, @Share(value = "previousLevel", namespace = TaxFreeLevels.MOD_ID) LocalIntRef previousLevel) {
		// calculate cost instead of using experienceLevels parameter for compatibility
		int levelCost = previousLevel.get() - experienceLevel;

		// reset level and apply level cost as XP cost
		experienceLevel = previousLevel.get();
		TaxFreeLevels.applyFlattenedXpCost((Player) (Object) this, levelCost);
	}
}
