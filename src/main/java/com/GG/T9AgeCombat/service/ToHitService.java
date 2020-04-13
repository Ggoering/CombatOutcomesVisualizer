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

    List<Integer> rollAttacks(Integer quantity) {
        return diceRollingService.roll(quantity);
    }

    Integer rollToHit(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerWS = attacker.getOffensiveWeaponSkill();
        Integer defenderWS = defender.getDefensiveWeaponSkill();
        List<Integer> resultList = this.rollAttacks(quantity);
        Integer toHitThreshold = this.determineToHitThreshold(attackerWS, defenderWS);

        return removeFailedToHitRolls(resultList, toHitThreshold);
    }

    Integer determineToHitThreshold(Integer attackerWS, Integer defenderWS) {
        if (attackerWS.equals(defenderWS)) {
            return TO_HIT_DEFAULT_THRESHOLD;
        } else if (attackerWS > defenderWS) {
            return attackerWS - defenderWS > TO_HIT_ATTACKER_SKILL_THRESHOLD ? TO_HIT_DEFAULT_THRESHOLD - TO_HIT_SUBSTANTIAL_SKILL_DIFFERENCE : TO_HIT_DEFAULT_THRESHOLD - TO_HIT_MINIMAL_SKILL_DIFFERENCE;
        } else {
            return attackerWS - defenderWS < TO_HIT_DEFENDER_SKILL_THRESHOLD ? TO_HIT_DEFAULT_THRESHOLD + TO_HIT_MINIMAL_SKILL_DIFFERENCE : TO_HIT_DEFAULT_THRESHOLD;
        }
    }

    Integer removeFailedToHitRolls(List<Integer> resultList, Integer toHitThreshold) {
        return (int) resultList.stream().filter(a -> a >= toHitThreshold).count();
    }
}
