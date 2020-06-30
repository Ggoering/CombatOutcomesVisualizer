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
                if(isPermanent) {
                    offensiveProfile.updateAttacks((int) value);
                } else {
                    offensiveProfile.updateAttacksModifier((int) value);
                }
                break;
            case ARMOR:
                if(isPermanent) {
                    unitProfile.updateArmor((int) value);
                } else {
                    unitProfile.updateArmorModifier((int) value);
                }
                break;
            case OFFENSIVE_WEAPON_SKILL:
                if(isPermanent) {
                    offensiveProfile.updateOffensiveWeaponSkill((int) value);
                } else {
                    offensiveProfile.updateOffensiveWeaponSkillModifier((int) value);
                }
                break;
            case STRENGTH:
                if(isPermanent) {
                    offensiveProfile.updateStrength((int) value);
                } else {
                    offensiveProfile.updateStrengthModifier((int) value);
                }
                break;
            case ARMOR_PENETRATION:
                if(isPermanent) {
                    offensiveProfile.updateArmorPenetration((int) value);
                } else {
                    offensiveProfile.updateArmorPenetrationModifier((int) value);
                }
                break;
            case TO_HIT:
                if(isPermanent) {
                    offensiveProfile.updateToHitBonus((int) value);
                } else {
                    offensiveProfile.updateToHitBonusModifier((int) value);
                }
                break;
            case MAGICAL_ATTACKS:
                    offensiveProfile.setHasMagicalAttacks((boolean) value);
                break;
            case MOUNTED:
                    offensiveProfile.setMount(true);
                break;
            case AGILITY:
                if(isPermanent) {
                    offensiveProfile.updateAgility((int) value);
                } else {
                    offensiveProfile.updateAgilityModifier((int) value);
                }
                break;
            case RE_ROLL_TO_HIT:
                if(isPermanent) {
                    offensiveProfile.setReRollToHit((int) value);
                } else {
                    offensiveProfile.setToHitReRollLessThanModifier((int) value);
                }
                break;
            case RE_ROLL_TO_WOUND:
                if(isPermanent) {
                    offensiveProfile.setReRollToWound((int) value);
                } else {
                    offensiveProfile.setToWoundReRollLessThanModifier((int) value);
                }
                break;
            case TWO_HANDED:
                    unitProfile.updateTwoHanded(((int)value != 0));
                break;
            case EXTRA_RANKS:
                if(isPermanent) {
                    unitProfile.updateExtraRank((int) value);
                } else {
                    unitProfile.updateExtraRankModifier((int) value);
                }
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
                if(isPermanent) {
                    unit.updateArmor((int) value);
                } else {
                    unit.updateArmorModifier((int) value);
                }
                break;
            case WARD_SAVE:
                if(isPermanent) {
                    unit.updateAegisSave((int) value);
                } else {
                    unit.updateAegisSaveModifier((int) value);
                }
                break;
            case EXTRA_RANKS:
                if(isPermanent) {
                    unit.updateExtraRank((int) value);
                } else {
                    unit.updateExtraRankModifier((int) value);
                }
                break;
            case PARRY:
                unit.updateParry(((int) value > 0));
                break;
            default:
                break;
        }
    }
}