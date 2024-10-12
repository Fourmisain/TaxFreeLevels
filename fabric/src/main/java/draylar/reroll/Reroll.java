package draylar.reroll;

import net.minecraft.entity.player.PlayerEntity;

public class Reroll {
	public static void reroll(PlayerEntity player) {
		player.addExperienceLevels(-3);
	}
}
