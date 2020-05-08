package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Component;

@Component
public class DetermineModificationPredicate {
    private DetermineModificationPredicate() {
    }

    public static void applyBonus(Unit unit, ModificationEnum modification, Object value) {
        switch (modification) {
            case STRENGTH: {
                unit.updateStrength((Integer) value);
                break;
            }
            case RE_ROLL_TO_HIT: {
                unit.updateReRollToHit((Integer) value);
                break;
            }
            case TO_HIT: {
                unit.updateToHitBonus((Integer) value);
                break;
            }
            case EXTRA_RANKS: {
                unit.updateExtraRank((Integer) value);
                break;
            }
            default:
                break;
        }
    }
}