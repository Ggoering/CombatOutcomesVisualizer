package com.GG.T9AgeCombat.predicates;

import com.GG.T9AgeCombat.enums.EquipmentCategoryEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import com.GG.T9AgeCombat.enums.SpecialRulePropertyEnum;
import com.GG.T9AgeCombat.enums.UnitTypeEnum;
import com.GG.T9AgeCombat.models.Equipment;
import com.GG.T9AgeCombat.models.SpecialRuleProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.GG.T9AgeCombat.common.Constants.HORDE_MODELS_PER_RANK;
import static java.util.stream.Collectors.toList;

@Component
public class CheckLimitationPredicate {

    public CheckLimitationPredicate() {
    }

    public static boolean checkFirstRound(Boolean isFirstRound) {
        return isFirstRound;
    }

    public static boolean checkFirstRoundNotCharging(Boolean isFirstRound, boolean isCharging) {
        return isFirstRound && !isCharging;
    }

    public static boolean passDisciplineTest(int leadership, Integer roll) {
        return roll <= leadership;
    }

    public static boolean checkHorde(Integer unitWidth) {
        return unitWidth >= HORDE_MODELS_PER_RANK;
    }

    public static boolean checkLightningReflexes(List<SpecialRuleProperty> unitSpecialRules) {
        return unitSpecialRules.stream().map(a -> a.getName()).collect(toList()).contains(SpecialRulePropertyEnum.LIGHTNING_REFLEXES);
    }

    public static boolean checkHandWeaponAndInfantry(List<Equipment> unitEquipment, UnitTypeEnum unitType) {
        List<EquipmentCategoryEnum> closeCombatWeapons = unitEquipment.stream()
                .filter(e -> e.getType().equals(EquipmentTypeEnum.CLOSE_COMBAT_WEAPON))
                .map(a -> a.getCategory()).collect(toList());

        boolean handWeaponOnly = closeCombatWeapons.size() == 1 && closeCombatWeapons.get(0).equals(EquipmentCategoryEnum.HAND_WEAPON);
        return handWeaponOnly && unitType.equals(UnitTypeEnum.INFANTRY);
    }

    public static boolean checkGreatWeapon(List<Equipment> unitEquipment) {
        return unitEquipment.stream().map(a -> a.getCategory()).collect(toList()).contains(EquipmentCategoryEnum.GREAT_WEAPON);
    }


    public static boolean checkTwoHanded(boolean hasTwoHanded) {
        return hasTwoHanded;
    }

}
