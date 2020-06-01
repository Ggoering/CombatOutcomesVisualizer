package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.OffensiveProfile;
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

    int rollToWound(OffensiveProfile offensiveProfile, Unit defender, int quantity) {
        int toWoundThreshold = this.determineToWoundThreshold(offensiveProfile.getActualStrength(), defender.getActualToughness());
        List<Integer> resultList = diceRollingService.roll(quantity);

        return diceRollingService.getFinalWithReRolls(resultList, toWoundThreshold, offensiveProfile.getReRollToWoundLessThan(), offensiveProfile.getReRollToWoundGreaterThan());
    }

    int determineToWoundThreshold(int attackerStrength, int defenderToughness) {
        int toWoundThreshold = TO_WOUND_DEFAULT_THRESHOLD - (attackerStrength - defenderToughness);

        return toWoundThreshold < MINIMUM_TO_WOUND_SUCCESS ?
                MINIMUM_TO_WOUND_SUCCESS : Math.min(toWoundThreshold, TO_WOUND_AUTO_SUCCESS);
    }
}
