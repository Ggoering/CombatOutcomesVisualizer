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

    Integer rollToWound(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerS = attacker.getS();
        Integer defenderT = defender.getT();
        Integer toWoundThreshold = this.determineToWoundThreshold(attackerS, defenderT);
        List<Integer> dice = diceRollingService.roll(quantity);

        return filterOutFailedToWound(dice, toWoundThreshold);
    }

    Integer determineToWoundThreshold(Integer attackerS, Integer defenderT) {
            return 4 - (attackerS - defenderT);
    }

    Integer filterOutFailedToWound(List<Integer> resultList, Integer toHitThreshold) {
        return (int)resultList.stream().filter(a ->  (a != 1 && a >= toHitThreshold) || a == 6).count();
    }
}
