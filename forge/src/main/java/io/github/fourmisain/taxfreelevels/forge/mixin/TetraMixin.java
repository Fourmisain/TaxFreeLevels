package io.github.fourmisain.taxfreelevels.forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fourmisain.taxfreelevels.TaxFreeLevels;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;

@Mixin(WorkbenchTile.class)
public abstract class TetraMixin {
    @ModifyArg(
        method = "craft(Lnet/minecraft/entity/player/PlayerEntity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;addExperienceLevels(I)V"
        ),
        index = 0
    )
    public int taxfreelevels$flattenCraftCost(int negativeLevelCost, @Local(argsOnly = true) PlayerEntity player) {
        TaxFreeLevels.applyFlattenedXpCost(player, -negativeLevelCost);
        return 0; // we already paid in XP
    }
}
