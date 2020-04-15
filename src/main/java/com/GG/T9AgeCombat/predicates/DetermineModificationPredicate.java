package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Component;

import static com.GG.T9AgeCombat.enums.ModificationEnum.*;

@Component
public class DetermineModificationPredicate {
    private DetermineModificationPredicate() {
    }

    public static void applyBonus(Unit unit, String modification, int value) {
        if (modification.equals(STRENGTH.toString())) {
            unit.updateStrength(value);
        } else if (modification.equals(RE_ROLL_TO_HIT.toString())) {
            unit.updateReRollToHit(value);
        } else if (modification.equals(TO_HIT.toString())) {
            unit.updateToHitBonus(value);
        } else if (modification.equals(EXTRA_RANKS.toString())) {
            unit.updateExtraRank(value);
        }
    }
}
