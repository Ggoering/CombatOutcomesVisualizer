package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.SpecialRuleProperty;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.predicates.CheckLimitationPredicate;
import org.springframework.stereotype.Service;

@Service
public class SpecialRuleRoutingService {
    DiceRollingService diceRollingService;
    public SpecialRuleRoutingService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    boolean routeTemporaryLimitationToPredicate(LimitationEnum limitation, Unit unit, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return CheckLimitationPredicate.checkFirstRound(isFirstRound);
            case EIGHT_WIDE:
                return CheckLimitationPredicate.checkHorde(unit.getModelsPerRank());
            case NOT_LIGHTNING_REFLEXES:
                return !CheckLimitationPredicate.checkLightningReflexes(unit.getSpecialRulePropertyList());
            case NOT_GREAT_WEAPON:
                return !CheckLimitationPredicate.checkGreatWeapon(unit.getEquipmentList());
            case HAND_WEAPON_AND_INFANTRY:
                return CheckLimitationPredicate.checkHandWeaponAndInfantry(unit.getEquipmentList(), unit.getType());
            case FIRST_ROUND_NOT_CHARGING:
                return CheckLimitationPredicate.checkFirstRoundNotCharging(isFirstRound, unit.isCharging());
            case TWO_HANDED:
                return CheckLimitationPredicate.checkTwoHanded(unit.isHasTwoHanded());
            case PASS_DISCIPLINE_TEST:
                Integer roll = diceRollingService.rollWithSum(2);
                return CheckLimitationPredicate.passDisciplineTest(unit.getActualLeadership(), roll);
            default:
                return false;
        }
    }

    boolean routeTemporaryLimitationToPredicate(LimitationEnum limitation, OffensiveProfile unit, boolean isFirstRound) {
        switch (limitation) {
            case FIRST_ROUND:
                return CheckLimitationPredicate.checkFirstRound(isFirstRound);
            case NOT_LIGHTNING_REFLEXES:
                return !CheckLimitationPredicate.checkLightningReflexes(unit.getSpecialRulePropertyList());
            case NOT_GREAT_WEAPON:
                return !CheckLimitationPredicate.checkGreatWeapon(unit.getEquipmentList());
            default:
                return false;
        }
    }

    boolean checkLimitation(LimitationEnum limitation, TimingEnum timing, SpecialRuleProperty specialRuleProperty, Unit unit, boolean isFirstRound) {
        if(limitation == LimitationEnum.NONE) {
            return specialRuleProperty.getLimitation() == LimitationEnum.NONE && timing == specialRuleProperty.getTiming();
        } else {
            return this.routeTemporaryLimitationToPredicate(specialRuleProperty.getLimitation(), unit, isFirstRound)
                    && timing == specialRuleProperty.getTiming();
        }
    }

    boolean checkLimitation(LimitationEnum limitation, TimingEnum timing, SpecialRuleProperty specialRuleProperty, OffensiveProfile unit, boolean isFirstRound) {
        if(limitation == LimitationEnum.NONE) {
            return specialRuleProperty.getLimitation() == LimitationEnum.NONE && timing == specialRuleProperty.getTiming();
        } else {
            return this.routeTemporaryLimitationToPredicate(specialRuleProperty.getLimitation(), unit, isFirstRound)
                    && timing == specialRuleProperty.getTiming();
        }
    }

}