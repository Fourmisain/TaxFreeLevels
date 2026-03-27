package io.github.fourmisain.taxfreelevels.mixin;

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

@Mixin(value = Player.class, priority = 100)
public abstract class FlattenEnchantmentCostPreMixin {
	@Shadow public int experienceLevel;

	@Inject(method = "onEnchantmentPerformed", at = @At(value = "HEAD"))
	public void taxfreelevels$rememberExperienceLevel(ItemStack enchantedItem, int experienceLevels, CallbackInfo ci, @Share(value = "previousLevel", namespace = TaxFreeLevels.MOD_ID) LocalIntRef previousLevel) {
		previousLevel.set(experienceLevel);
	}
}
