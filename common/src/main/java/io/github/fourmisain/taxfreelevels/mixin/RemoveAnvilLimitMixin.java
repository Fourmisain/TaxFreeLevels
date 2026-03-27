package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.fourmisain.taxfreelevels.TaxFreeLevelsConfig;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@SuppressWarnings("SimplifiableConditionalExpression")
@Mixin(AnvilMenu.class)
public abstract class RemoveAnvilLimitMixin {
	// mods may ModifyConstant the level 40 limit, so for compatibility we change isInCreativeMode() instead
	@ModifyExpressionValue(
		method =  {
			"createResult",
			"createResultInternal" // NeoForge >= 21.5.73-beta
		},
		slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;get()I", ordinal = 0)), // cost.get()
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z",
			ordinal = 0
		),
		require = 1
	)
	public boolean taxfreelevels$removeAnvilLimit(boolean original) {
		return TaxFreeLevelsConfig.get().removeAnvilLimit ? true : original;
	}
}
