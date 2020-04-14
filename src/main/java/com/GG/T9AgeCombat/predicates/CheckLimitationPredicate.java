package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.Limitation;
import org.springframework.stereotype.Component;

@Component
public class CheckLimitationPredicate {
    private CheckLimitationPredicate() {
    }

    public static boolean validateSpecialRuleLimitation(Limitation limitation, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return isFirstRound;
            default:
                return false;
        }
    }
}
