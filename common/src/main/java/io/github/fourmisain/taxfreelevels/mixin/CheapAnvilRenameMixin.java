package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AnvilScreenHandler.class)
public abstract class CheapAnvilRenameMixin {
	@Shadow @Final private Property levelCost;

	/**
	 * Make item renaming always cost 1 level
	 * This should inject after levelCost.set(j + i) and levelCost.set(39)
	 */
	@Inject(method = "updateResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/Property;get()I",
			ordinal = 1
		)
	)
	public void taxfreelevels$makeRenamingCheap(CallbackInfo ci, @Local(ordinal = 0) int i, @Local(ordinal = 1) int j) {
		// j is set to 1 when renaming, i is the total cost without the repair cost l,
		// so this condition means we are only renaming:
		if (j > 0 && j == i) {
			levelCost.set(1);
		}
	}
}
