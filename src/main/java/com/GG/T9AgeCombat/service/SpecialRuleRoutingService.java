package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.predicates.CheckLimitationPredicate;
import org.springframework.stereotype.Service;

@Service
public class SpecialRuleRoutingService {
    boolean routeTemporaryLimitationToPredicate(LimitationEnum limitation, Unit unit, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return CheckLimitationPredicate.checkFirstRound(isFirstRound);
            case EIGHT_WIDE:
                return CheckLimitationPredicate.checkHorde(unit.getModelsPerRank());
            default:
                return false;
        }
    }
}