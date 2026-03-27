package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
	public ServerPlayerMixin(Level level, GameProfile profile) {
		super(level, profile);
	}

	@Definition(id = "experience", local = @Local(type = int.class, argsOnly = true))
	@Expression("experience != 0")
	@ModifyExpressionValue(method = "giveExperiencePoints", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean skipCheck(boolean original) {
		if (TaxFreeLevels.forceRecalculateAndSync.get())
			return true;

		return original;
	}
}
