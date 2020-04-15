package com.GG.T9AgeCombat.predicates;

import org.springframework.stereotype.Component;

@Component
public class CheckLimitationPredicate {
    private CheckLimitationPredicate() {
    }
    public static boolean checkFirstRound(Boolean isFirstRound) {
        return isFirstRound;
    }

    public static boolean checkHorde(Integer unitWidth) {
         return unitWidth >= 10;
    }
}
