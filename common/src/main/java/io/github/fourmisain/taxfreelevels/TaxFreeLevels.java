package io.github.fourmisain.taxfreelevels;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaxFreeLevels {
    public static final String MOD_ID = "taxfreelevels";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    /** For example usage see this mod's fabric.mod.json */
    public static String CUSTOM_OPTIONS_FIELD = "taxfreelevels:options";

    private static final ThreadLocal<Integer> levelRequirement = ThreadLocal.withInitial(() -> -1);

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

        // assuming addNoScoreExperience() is only called once per pay, we use the opportunity to setup the next pay
        resetLevelRequirement();
    }

    public static void resetLevelRequirement() {
        levelRequirement.remove();
    }

    public static void setLevelRequirement(int value) {
        levelRequirement.set(value);
    }

    public static int getLevelRequirement() {
        return levelRequirement.get();
    }

    /** The "flattened" XP cost */
    public static int getFlattenedXpCost(PlayerEntity player, int levelCost) {
        int base;
        if (getLevelRequirement() >= 0) {
            base = Math.max(getLevelRequirement(), TaxFreeLevelsConfig.get().levelBase);
        } else {
            base = TaxFreeLevelsConfig.get().levelBase;
        }

        // pretend the player is at most at the given "base" level
        int pretendLevel = Math.min(player.experienceLevel, base);

        // get the XP cost for the given levelCost, e.g. the XP needed from level 27 to 30
        // if the levelCost is bigger than pretendLevel, return the XP needed from 0 to levelCost
        int from = Math.max(pretendLevel - levelCost, 0);
        return getXpDifference(player, from, from + levelCost);
    }

    /** Pay the "flattened" XP cost for the given level cost */
    public static void applyFlattenedXpCost(PlayerEntity player, int levelCost) {
        /*
         * in vanilla, paying levels doesn't actually touch the level progress.
         * since you need more XP for higher levels this actually means you lose some XP due to your progress now equating to less XP
         * e.g. if you're 50% into level 30, that's worth 53.5 XP and if you go down to level 20, that 50% progress is now worth only 28.5 XP
         * it's not much to worry about, but it's the reason why we also pay in XP if the player level is below 30
         */
        addNoScoreExperience(player, -getFlattenedXpCost(player, levelCost));
    }
}
