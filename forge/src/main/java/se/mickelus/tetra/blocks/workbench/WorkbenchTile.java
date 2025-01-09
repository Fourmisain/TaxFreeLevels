package se.mickelus.tetra.blocks.workbench;

import net.minecraft.entity.player.PlayerEntity;

public class WorkbenchTile {
    public void craft(PlayerEntity player) {
        player.addExperienceLevels(0);
    }
}
