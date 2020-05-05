package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.predicates.CheckLimitationPredicate;
import org.springframework.stereotype.Service;


@Service
public class SpecialRuleRoutingService {
    Boolean routeLimitationToPredicate(LimitationEnum limitation, Unit unit, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return CheckLimitationPredicate.checkFirstRound((Boolean) isFirstRound);
            case EIGHT_WIDE:
                return CheckLimitationPredicate.checkHorde((Integer) unit.getModelsPerRank());
            case NONE:
                return true;
            default:
                return false;
        }
    }
}