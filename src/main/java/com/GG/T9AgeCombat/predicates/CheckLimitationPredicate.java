package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.Limitation;
import org.springframework.stereotype.Component;

@Component
public class CheckLimitationPredicate {
    public static boolean checkFirstRound(Limitation limitation, Integer currentRound) {
        if(limitation == Limitation.FIRST_ROUND && currentRound == 1) {
            return true;
        } else {
            return false;
        }
    }
}
