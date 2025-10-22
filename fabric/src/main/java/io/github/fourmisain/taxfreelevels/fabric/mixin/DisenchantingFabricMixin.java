package io.github.fourmisain.taxfreelevels.fabric.mixin;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "com.chirptheboy.disenchanting.screen.DisenchanterScreenHandler")
public abstract class DisenchantingFabricMixin {
	@Shadow @Final
	private PlayerEntity player;

	@ModifyArg(
		method = "disenchantItem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V",
			remap = true
		),
		index = 0,
		remap = false
	)
	private int taxfreelevels$flattenRerollCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
