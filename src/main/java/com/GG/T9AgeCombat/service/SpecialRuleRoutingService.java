package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Limitation;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.predicates.CheckLimitationPredicate;
import org.springframework.stereotype.Service;


@Service
public class SpecialRuleRoutingService {
    Boolean routeLimitationToPredicate(Limitation limitation, Unit unit, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return CheckLimitationPredicate.checkFirstRound((Boolean) isFirstRound);
            case TEN_WIDE:
                return CheckLimitationPredicate.checkHorde((Integer) unit.getWidth());
            case NONE:
                return true;
            default:
                return false;
        }
    }
}
