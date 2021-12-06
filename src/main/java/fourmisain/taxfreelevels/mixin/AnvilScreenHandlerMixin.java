package fourmisain.taxfreelevels.mixin;

import fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = AnvilScreenHandler.class, priority = 1500)
public abstract class AnvilScreenHandlerMixin {
	@Unique private PlayerEntity taxfreelevels$player;

	@Inject(method = "onTakeOutput", at = @At("HEAD"))
	public void taxfreelevels$capturePlayer(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
		taxfreelevels$player = player;
	}

	@ModifyArg(
		method = "onTakeOutput",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
		),
		index = 0
	)
	public int taxfreelevels$flattenAnvilCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedAnvilCost(taxfreelevels$player, -negativeLevelCost);
		return 0;
	}

	@Shadow @Final private Property levelCost;

	/**
	 * Make item renaming always cost 1 level
	 * This should inject after levelCost.set(j + i)
	 */
	@Inject(method = "updateResult",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/screen/Property;get()I",
			ordinal = 1
		),
		locals = LocalCapture.CAPTURE_FAILSOFT)
	public void taxfreelevels$makeRenamingCheap(CallbackInfo ci, ItemStack itemStack, int i, int j, int k) {
		// k is set to 1 when renaming, i is the total cost without the repair cost j,
		// so this condition means we are only renaming:
		if (k > 0 && k == i) {
			levelCost.set(1);
		}
	}
}
