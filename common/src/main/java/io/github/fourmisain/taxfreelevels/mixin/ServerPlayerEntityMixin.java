package io.github.fourmisain.taxfreelevels.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@Definition(id = "experience", local = @Local(type = int.class, argsOnly = true))
	@Expression("experience != 0")
	@ModifyExpressionValue(method = "addExperience", at = @At("MIXINEXTRAS:EXPRESSION"))
	private boolean skipCheck(boolean original) {
		if (TaxFreeLevels.forceRecalculateAndSync.get())
			return true;

		return original;
	}
}
