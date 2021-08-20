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
}
