package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToHitService {
    DiceRollingService diceRollingService;
    public ToHitService(DiceRollingService diceRollingService){
        this.diceRollingService = diceRollingService;
    }

    List<Integer> rollAttacks(Integer quantity) {
        return diceRollingService.roll(quantity);
    }

    Integer rollToHit(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerWS = attacker.getOWS();
        Integer defenderWS = defender.getDWS();
        List<Integer> resultList = this.rollAttacks(quantity);
        Integer toHitThreshold = this.determineToHitThreshold(attackerWS, defenderWS);

        return filterOutMissedAttacks(resultList, toHitThreshold);
    }

    Integer determineToHitThreshold(Integer attackerWS, Integer defenderWS) {
        if(attackerWS == defenderWS) {
            return 4;
        } else if(attackerWS > defenderWS) {
            return attackerWS - defenderWS > 3 ? 2 : 3;
        } else {
            return attackerWS - defenderWS < -3 ? 5: 4;
        }
    }

    Integer filterOutMissedAttacks(List<Integer> resultList, Integer toHitThreshold) {
        return (int)resultList.stream().filter(a -> a >= toHitThreshold).count();
    }
}
