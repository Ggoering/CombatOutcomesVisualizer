package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Component;

@Component
public class DetermineModificationPredicate {
    private DetermineModificationPredicate() {
    }

    public static void applyBonus(OffensiveProfile offensiveProfile, ModificationEnum modification, Object value, Unit unitProfile, boolean isPermanent) {
        switch (modification) {
            case ATTACKS:
                offensiveProfile.updateAttacks((int) value);
                break;
            case ARMOR:
                unitProfile.updateArmor((int) value);
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
            case AGILITY:
                offensiveProfile.updateAgilityModifier((int) value);
                break;
            case RE_ROLL_TO_HIT:
                offensiveProfile.updateReRollToHit((int) value);;
                break;
            case TWO_HANDED:
                unitProfile.updateTwoHanded(((int)value != 0));
                break;
            case EXTRA_RANKS:
                unitProfile.updateExtraRank((int) value);
                break;
            case IGNORE_PARRY:
                offensiveProfile.updateIgnoreParry(((int)value != 0));
                break;
            default:
                break;
        }
    }

    public static void applyBonus(Unit unit, ModificationEnum modification, Object value, boolean isPermanent) {
        switch (modification) {
            case ARMOR:
                unit.updateArmor((int) value);
                break;
            case WARD_SAVE:
                unit.setWardSave((int) value);
                break;
            case EXTRA_RANKS:
                unit.updateExtraRank((int) value);
                break;
            case PARRY:
                unit.updateParry(((int) value > 0));
                break;
            default:
                break;
        }
    }
}