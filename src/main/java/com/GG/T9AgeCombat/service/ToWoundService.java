package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ToWoundService {
    public static final int MINIMUM_TO_WOUND_SUCCESS = 2;
    public static final int TO_WOUND_AUTO_SUCCESS = 6;
    public static final int TO_WOUND_DEFAULT_THRESHOLD = 4;
    DiceRollingService diceRollingService;

    public ToWoundService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Integer rollToWound(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerStrength = attacker.getStrength();
        Integer defenderToughness = defender.getToughness();
        Integer toWoundThreshold = this.determineToWoundThreshold(attackerStrength, defenderToughness);
        List<Integer> dice = diceRollingService.roll(quantity);


        return diceRollingService.getFinalWithReRolls(dice, toWoundThreshold, attacker.getReRollToWoundLessThan(), attacker.getReRollToWoundGreaterThan());
    }

    Integer determineToWoundThreshold(Integer attackerStrength, Integer defenderToughness) {
        Integer preliminaryToWoundThreshold = TO_WOUND_DEFAULT_THRESHOLD - (attackerStrength - defenderToughness);

        return  preliminaryToWoundThreshold < MINIMUM_TO_WOUND_SUCCESS ?
                MINIMUM_TO_WOUND_SUCCESS : preliminaryToWoundThreshold > TO_WOUND_AUTO_SUCCESS ?
                TO_WOUND_AUTO_SUCCESS : preliminaryToWoundThreshold;
    }
}
