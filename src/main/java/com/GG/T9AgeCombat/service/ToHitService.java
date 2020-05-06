package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToHitService {
    private static final int TO_HIT_DEFAULT_THRESHOLD = 4;
    private static final int TO_HIT_MINIMAL_SKILL_DIFFERENCE = 1;
    private static final int TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE = 2;
    private static final int TO_HIT_ATTACKER_SKILL_THRESHOLD = 3;
    private static final int TO_HIT_DEFENDER_SKILL_THRESHOLD = -3;
    DiceRollingService diceRollingService;

    public ToHitService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    int rollToHit(Unit attacker, Unit defender, int quantity) {
        int toHitThreshold = determineToHitThreshold(attacker.getActualOffensiveWeaponSkill(), defender.getActualDefensiveWeaponSkill());
        List<Integer> resultList = diceRollingService.roll(quantity);

        return diceRollingService.getFinalWithReRolls(resultList, toHitThreshold, attacker.getReRollToHitLessThan(), attacker.getReRollToHitGreaterThan());
    }

    int determineToHitThreshold(int attackerOWS, int defenderDWS) {
        if (attackerOWS == defenderDWS) {
            return TO_HIT_DEFAULT_THRESHOLD;
        } else if (attackerOWS > defenderDWS) {
            return attackerOWS - defenderDWS > TO_HIT_ATTACKER_SKILL_THRESHOLD ? TO_HIT_DEFAULT_THRESHOLD - TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE : TO_HIT_DEFAULT_THRESHOLD - TO_HIT_MINIMAL_SKILL_DIFFERENCE;
        } else {
            return attackerOWS - defenderDWS < TO_HIT_DEFENDER_SKILL_THRESHOLD ? TO_HIT_DEFAULT_THRESHOLD + TO_HIT_MINIMAL_SKILL_DIFFERENCE : TO_HIT_DEFAULT_THRESHOLD;
        }
    }
}
