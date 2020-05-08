package com.GG.T9AgeCombat.predicates;

import org.springframework.stereotype.Component;

import static com.GG.T9AgeCombat.common.Constants.HORDE_MODELS_PER_RANK;

@Component
public class CheckLimitationPredicate {
    private CheckLimitationPredicate() {
    }

    public static boolean checkFirstRound(Boolean isFirstRound) {
        return isFirstRound;
    }

    public static boolean checkHorde(Integer unitWidth) {
        return unitWidth >= HORDE_MODELS_PER_RANK;
    }
}
