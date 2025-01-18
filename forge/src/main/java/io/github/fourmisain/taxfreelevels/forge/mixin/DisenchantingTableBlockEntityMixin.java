package io.github.fourmisain.taxfreelevels.forge.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import forge.com.cursee.disenchanting_table.core.DisenchantingTableBlockEntity;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = DisenchantingTableBlockEntity.class, priority = 1500)
public abstract class DisenchantingTableBlockEntityMixin {
    @WrapOperation(
        method = "takeExperienceFromNearestPlayer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;setExperienceLevel(I)V"
        )
    )
    private static void taxfreelevels$flattenDisenchantCost(ServerPlayerEntity player, int level, Operation<Void> original) {
        // player.setExperienceLevel(player.experienceLevel - 5)
        // level = player.experienceLevel - 5 => level - player.experienceLevel = - 5 => player.experienceLevel - level = 5
        int levelCost = player.experienceLevel - level;
        TaxFreeLevels.setLevelRequirement(levelCost);
        TaxFreeLevels.applyFlattenedXpCost(player, levelCost);
    }
}
