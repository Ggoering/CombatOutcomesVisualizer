package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ToWoundService {
    DiceRollingService diceRollingService;
    public ToWoundService(DiceRollingService diceRollingService){
        this.diceRollingService = diceRollingService;
    }

    List<Integer> rollToWound(Integer quantity) {
        return diceRollingService.roll(quantity);
    }

    Integer rollToWound(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerS = attacker.getS();
        Integer defenderS = defender.getS();
        Integer toWoundThreshold = this.determineToWoundThreshold(attackerS, defenderS);
        List<Integer> resultList = this.rollToWound(quantity);

        return filterOutFailedToWound(resultList, toWoundThreshold);
    }

    Integer determineToWoundThreshold(Integer attackerS, Integer defenderS) {
            return 4 - (attackerS - defenderS);
    }

    Integer filterOutFailedToWound(List<Integer> resultList, Integer toHitThreshold) {
        return (int)resultList.stream().filter(a ->  a != 1 || a == 6 || a >= toHitThreshold ).count();
    }
}
