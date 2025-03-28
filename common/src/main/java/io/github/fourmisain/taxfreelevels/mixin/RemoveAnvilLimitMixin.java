package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(AnvilScreenHandler.class)
public abstract class RemoveAnvilLimitMixin {
	// mods may ModifyConstant the level 40 limit, so for compatibility we change isInCreativeMode() instead
	@ModifyExpressionValue(
		method = "updateResult",
		slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;get()I", ordinal = 0)), // levelCost.get()
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;isInCreativeMode()Z",
			ordinal = 0
		)
	)
	public boolean taxfreelevels$removeAnvilLimit(boolean original) {
		return TaxFreeLevelsConfig.get().removeAnvilLimit ? true : original;
	}
}
