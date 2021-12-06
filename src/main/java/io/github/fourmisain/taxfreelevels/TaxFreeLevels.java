package io.github.fourmisain.taxfreelevels;

import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaxFreeLevels {
    public static String MOD_ID = "taxfreelevels";
    public static String CUSTOM_OPTIONS_FIELD = "taxfreelevels:options";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

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

    /** A way to add experience without affecting the player's score */
    public static void addNoScoreExperience(PlayerEntity player, int xp) {
        /*
         * note: there's the SCORE data tracker (used on the death screen) but also totalExperience,
         * which used for the XP scoreboard criterion, neither of which we wanna touch
         */
        player.experienceProgress += (float) xp / (float) player.getNextLevelExperience();
        player.addExperience(0);
    }

    public static void applyFlattenedAnvilCost(PlayerEntity player, int levelCost) {
        int xpCost = TaxFreeLevels.getXpDifference(player, 0, levelCost);
        addNoScoreExperience(player, -xpCost);
    }

    public static void applyFlattenedEnchantmentCost(PlayerEntity player, int levelCost) {
        if (player.experienceLevel >= 30) {
            int xpCost = TaxFreeLevels.getXpDifference(player, 30 - levelCost, 30);
            addNoScoreExperience(player, -xpCost);
        } else {
            player.addExperienceLevels(-levelCost);
        }
    }
}
