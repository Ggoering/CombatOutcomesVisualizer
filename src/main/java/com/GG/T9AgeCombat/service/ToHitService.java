package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToHitService {
    private static final int TO_HIT_DEFAULT_THRESHOLD = 4;
    private static final int TO_HIT_MINIMAL_SKILL_DIFFERENCE = 1;
    private static final int TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE = 2;
    private static final int TO_HIT_MINIMUM_THRESHOLD = 2;
    private static final int TO_HIT_MAXIMUM_THRESHOLD = 6;
    DiceRollingService diceRollingService;

    public ToHitService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    int rollToHit(Unit attacker, Unit defender, int quantity) {
        int toHitThreshold = determineToHitThreshold(attacker.getActualOffensiveWeaponSkill(),
                defender.getActualDefensiveWeaponSkill(), attacker.getActualToHitBonus());
        List<Integer> resultList = diceRollingService.roll(quantity);

        return diceRollingService.getFinalWithReRolls(resultList, toHitThreshold, attacker.getReRollToHitLessThan(),
                attacker.getReRollToHitGreaterThan());
    }

    int determineToHitThreshold(int attackerOWS, int defenderDWS, int attackerToHitBonus) {
        int weaponSkillDifference = attackerOWS - defenderDWS;
        int toHitThreshold;

        if (weaponSkillDifference >= 4) {
            toHitThreshold = TO_HIT_DEFAULT_THRESHOLD - TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE;
        } else if (weaponSkillDifference >= 1) {
            toHitThreshold = TO_HIT_DEFAULT_THRESHOLD - TO_HIT_MINIMAL_SKILL_DIFFERENCE;
        } else if (weaponSkillDifference >= -3) {
            toHitThreshold = TO_HIT_DEFAULT_THRESHOLD;
        } else if (weaponSkillDifference >= -7) {
            toHitThreshold = TO_HIT_DEFAULT_THRESHOLD + TO_HIT_MINIMAL_SKILL_DIFFERENCE;
        } else {
            toHitThreshold = TO_HIT_DEFAULT_THRESHOLD + TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE;
        }

        if (attackerToHitBonus != 0) {
            // A positive to hit bonus decreases the to hit threshold, making it easier to hit
            // A negative to hit bonus increases the to hit threshold, making it harder to hit
            toHitThreshold -= attackerToHitBonus;
        }

        toHitThreshold = (toHitThreshold > 6 ? TO_HIT_MAXIMUM_THRESHOLD : toHitThreshold);
        toHitThreshold = (toHitThreshold < 2 ? TO_HIT_MINIMUM_THRESHOLD : toHitThreshold);

        return toHitThreshold;
    }
}
