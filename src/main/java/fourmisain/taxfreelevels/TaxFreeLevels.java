package fourmisain.taxfreelevels;

import net.minecraft.entity.player.PlayerEntity;

public class TaxFreeLevels {
    /** The XP needed to get from level 'from' to level 'to' */
    public static int getXpDifference(PlayerEntity player, int from, int to) {
        int currentLevel = player.experienceLevel;

        int xpSum = 0;
        for (int l = from; l < to; l++) {
            player.experienceLevel = l;
            xpSum += player.getNextLevelExperience();
        }

        player.experienceLevel = currentLevel;

        return xpSum;
    }

    public static void applyFlattenedAnvilCost(PlayerEntity player, int negativeLevelCost) {
        int xpCost = TaxFreeLevels.getXpDifference(player, 0, -negativeLevelCost);
        player.addExperience(-xpCost);
    }

    public static void applyFlattenedEnchantmentCost(PlayerEntity player, int levelCost) {
        if (player.experienceLevel >= 30) {
            int xpCost = TaxFreeLevels.getXpDifference(player, 30 - levelCost, 30);
            player.addExperience(-xpCost);
        } else {
            player.addExperienceLevels(-levelCost);
        }
    }
}
