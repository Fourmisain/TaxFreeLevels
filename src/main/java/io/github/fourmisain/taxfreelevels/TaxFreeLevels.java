package io.github.fourmisain.taxfreelevels;

import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaxFreeLevels {
    public static String MOD_ID = "taxfreelevels";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    /** For example usage see this mod's fabric.mod.json */
    public static String CUSTOM_OPTIONS_FIELD = "taxfreelevels:options";

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

    /** The "flattened" XP cost */
    public static int getFlattenedXpCost(PlayerEntity player, int levelCost) {
        // pretend the player is at most level 30
        int pretendLevel = Math.min(player.experienceLevel, 30);

        // get the XP cost for the given levelCost, e.g. the XP needed from level 27 to 30
        // if the levelCost is bigger than pretendLevel, return the XP needed from 0 to levelCost
        int from = Math.max(pretendLevel - levelCost, 0);
        return TaxFreeLevels.getXpDifference(player, from, from + levelCost);
    }

    /** Pay the "flattened" XP cost for the given level cost */
    public static void applyFlattenedXpCost(PlayerEntity player, int levelCost) {
        if (player.experienceLevel >= 30) {
            addNoScoreExperience(player, -getFlattenedXpCost(player, levelCost));
        } else {
            player.addExperienceLevels(-levelCost);
        }
    }
}
