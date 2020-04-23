package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorSaveService {
    private static final int ARMOR_PENETRATION_MODIFIER = 3;
    private static final int ARMOR_SAVE_AUTO_FAIL = 1;
    private static final int BEST_ARMOR_SAVE = 2;
    DiceRollingService diceRollingService;

    public ArmorSaveService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    int rollArmorSaves(Unit attacker, Unit defender, int quantity) {
        List<Integer> armorSaveRolls = diceRollingService.roll(quantity);
        Integer defenderArmorSave = defender.getArmorSave();
        Integer armorPenetration = calculateArmorPenetration(attacker);

        int armorSaveThreshold = determineArmorSaveThreshold(defenderArmorSave, armorPenetration);
        return quantity - diceRollingService.getFinalWithReRolls(armorSaveRolls, armorSaveThreshold, defender.getReRollArmorSaveLessThan(), defender.getReRollArmorSaveGreaterThan());
    }

    Integer calculateArmorPenetration(Unit attacker) {
        return Math.max(attacker.getStrength() - ARMOR_PENETRATION_MODIFIER, 0);
    }

    Integer determineArmorSaveThreshold(Integer armorSave, Integer armorPenetration) {
        Integer armorSaveThreshold = armorSave + armorPenetration;

        return armorSaveThreshold <= ARMOR_SAVE_AUTO_FAIL ? BEST_ARMOR_SAVE : armorSaveThreshold;
    }
}
