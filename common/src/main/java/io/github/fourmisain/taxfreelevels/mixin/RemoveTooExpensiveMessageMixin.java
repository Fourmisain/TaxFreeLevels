package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(AnvilScreen.class)
public abstract class RemoveTooExpensiveMessageMixin {
	// mods may ModifyConstant the level 40 limit, so for compatibility we change the creativeMode field instead
	@ModifyExpressionValue(method = "drawForeground", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 0))
	public boolean taxfreelevels$removeAnvilLimit(boolean original) {
		return TaxFreeLevelsConfig.get().removeAnvilLimit ? true : original;
	}
}
