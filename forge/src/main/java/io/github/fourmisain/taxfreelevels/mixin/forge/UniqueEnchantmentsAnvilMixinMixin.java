package io.github.fourmisain.taxfreelevels.mixin.forge;

import com.bawnorton.mixinsquared.TargetHandler;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// targets https://github.com/TinyModularThings/UniqueEnchantments/blob/f92e5edb8888522f6dc6e89ff6ed9f30fd284ab7/src/main/java/uniquebase/utils/mixin/common/tile/AnvilMixin.java#L24-L30
@Mixin(value = AnvilScreenHandler.class, priority = 1500)
public class UniqueEnchantmentsAnvilMixinMixin {
	@TargetHandler(
		mixin = "uniquebase.utils.mixin.common.tile.AnvilMixin",
		name = "drainXP"
	)
	@Redirect(
		method = "@MixinSquared:Handler",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		)
	)
	private void taxfreelevels$flattenAnvilCost(PlayerEntity player, int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
	}
}
