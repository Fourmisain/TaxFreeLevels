package io.github.fourmisain.taxfreelevels.mixin.sophisticatedbackpacks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("SimplifiableConditionalExpression")
@Pseudo
@Debug(print = true, export = true)
@Mixin(targets = "net.p3pp3rf1y.sophisticatedbackpacks.upgrades.anvil.AnvilUpgradeTab")
public abstract class AnvilUpgradeTabMixin {
	@ModifyExpressionValue(method = "renderCost", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;creativeMode:Z", ordinal = 0), require = 0)
	public boolean taxfreelevels$removeTooExpensiveMessage(boolean original) {
		return TaxFreeLevelsConfig.get().removeAnvilLimit ? true : original;
	}
}
