package io.github.fourmisain.taxfreelevels.mixin.forge;

import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owmii.losttrinkets.network.packet.UnlockSlotPacket;

import java.util.function.Supplier;

@Mixin(UnlockSlotPacket.class)
public abstract class LostTrinketMixin {
	@Unique
	private static PlayerEntity taxfreelevels$player;

	@Inject(
		method = "lambda$handle$0(Ljava/util/function/Supplier;)V",
		at = @At("HEAD"),
		remap = false
	)
	private static void taxfreelevels$capturePlayer(Supplier<NetworkEvent.Context> ctx, CallbackInfo ci) {
		taxfreelevels$player = ctx.get().getSender();
	}

	@ModifyArg(
		method = "lambda$handle$0(Ljava/util/function/Supplier;)V",
		at = @At(
			value = "INVOKE",
			target = "func_82242_a" // PlayerEntity.addExperienceLevels
		),
		index = 0,
		remap = false
	)
	private static int taxfreelevels$flattenTricketUnlockCost(int negativeLevelCost) {
		TaxFreeLevels.applyFlattenedXpCost(taxfreelevels$player, -negativeLevelCost);
		return 0; // we already paid in XP
	}
}
