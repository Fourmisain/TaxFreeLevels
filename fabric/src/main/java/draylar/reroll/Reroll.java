package draylar.reroll;

import net.minecraft.world.entity.player.Player;

public class Reroll {
	public static void reroll(Player player) {
		player.giveExperienceLevels(-3);
	}
}
