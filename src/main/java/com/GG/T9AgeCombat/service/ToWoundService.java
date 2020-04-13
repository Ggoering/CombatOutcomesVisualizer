package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ToWoundService {
    public static final int TO_WOUND_AUTO_FAIL = 1;
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

        return removeFailedToWoundRolls(dice, toWoundThreshold);
    }

    Integer determineToWoundThreshold(Integer attackerStrength, Integer defenderToughness) {
        return TO_WOUND_DEFAULT_THRESHOLD - (attackerStrength - defenderToughness);
    }

    Integer removeFailedToWoundRolls(List<Integer> resultList, Integer toWoundThreshold) {
        return (int) resultList.stream().filter(a -> (a != TO_WOUND_AUTO_FAIL && a >= toWoundThreshold) || a == TO_WOUND_AUTO_SUCCESS).count();
    }
}
