package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Component;

@Component
public class DetermineModificationPredicate {
    private DetermineModificationPredicate() {
    }

    public static void applyPermanentBonus(OffensiveProfile offensiveProfile, ModificationEnum modification, Object value) {
        switch (modification) {
            case ATTACKS:
                offensiveProfile.updateAttacks((int) value);
                break;
            case OFFENSIVE_WEAPON_SKILL:
                offensiveProfile.updateOffensiveWeaponSkill((int) value);
                break;
            case STRENGTH:
                offensiveProfile.updateStrength((int) value);
                break;
            case ARMOR_PENETRATION:
                offensiveProfile.updateArmorPenetration((int) value);
                break;
            case TO_HIT:
                offensiveProfile.updateToHitBonus((int) value);
                break;
            case MAGICAL_ATTACKS:
                offensiveProfile.setHasMagicalAttacks((boolean) value);
                break;
            case MOUNTED:
                offensiveProfile.setMount(true);
                break;
            default:
                break;
        }
    }

    public static void applyPermanentBonus(Unit unit, ModificationEnum modification, Object value) {
        switch (modification) {
            case ARMOR:
                unit.updateArmor((int) value);
                break;
            case WARD_SAVE:
                unit.setWardSave((int) value);
                break;
            default:
                break;
        }
    }

    public static void applyTemporaryBonus(OffensiveProfile offensiveProfile, ModificationEnum modification, Object value) {
        switch (modification) {
            case INITIATIVE:
                offensiveProfile.updateInitiativeModifier((int) value);
                break;
            case STRENGTH:
                offensiveProfile.updateStrengthModifier((int) value);
                break;
            case RE_ROLL_TO_HIT:
                offensiveProfile.updateReRollToHit((int) value);
                break;
            case TO_HIT:
                offensiveProfile.updateToHitBonusModifier((int) value);
                break;
            default:
                break;
        }
    }

    public static void applyTemporaryBonus(Unit unit, ModificationEnum modification, Object value) {
        switch (modification) {
            case EXTRA_RANKS:
                unit.updateExtraRank((int) value);
                break;
            default:
                break;
        }
    }
}