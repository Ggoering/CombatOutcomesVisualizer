package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.Modification;
import com.GG.T9AgeCombat.enums.SpecialRule;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Component;

@Component
public class DetermineModificationPredicate {
    public static void applyBonus(Unit unit, Modification modification, Integer value) {
        switch (modification) {
            case STRENGTH: {
                unit.updateStrength(value);
            }
        }
    }

}
