package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.predicates.CheckLimitationPredicate;
import org.springframework.stereotype.Service;

import static com.GG.T9AgeCombat.enums.LimitationEnum.*;


@Service
public class SpecialRuleRoutingService {
    Boolean routeLimitationToPredicate(String limitation, Unit unit, boolean isFirstRound) {
        if (limitation.equals(FIRST_ROUND.toString())) {
            return CheckLimitationPredicate.checkFirstRound(isFirstRound);
        } else if (limitation.equals(TEN_WIDE.toString())) {
            return CheckLimitationPredicate.checkHorde(unit.getWidth());
        } else {
            return limitation.equals(NONE.toString());
        }
    }
}
