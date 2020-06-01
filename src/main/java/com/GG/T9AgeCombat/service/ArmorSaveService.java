package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmorSaveService {
    private static final int ARMOR_SAVE_DEFAULT_THRESHOLD = 7;
    private static final int ARMOR_SAVE_AUTO_FAIL = 1;
    private static final int BEST_ARMOR_SAVE = 2;
    DiceRollingService diceRollingService;

    public ArmorSaveService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    int rollArmorSaves(OffensiveProfile offensiveProfile, Unit defender, int quantity) {
        int armorSaveThreshold = calculateArmorSaveThreshold(defender.getActualArmor(), offensiveProfile.getActualArmorPenetration());

        // Only roll armor saves if there is a chance to save
        if (armorSaveThreshold < ARMOR_SAVE_DEFAULT_THRESHOLD) {
            List<Integer> armorSaveRolls = diceRollingService.roll(quantity);
            return quantity - diceRollingService.getFinalWithReRolls(armorSaveRolls, armorSaveThreshold,
                    defender.getReRollArmorSaveLessThan(), defender.getReRollArmorSaveGreaterThan());
        }

        return quantity;
    }

    int calculateArmorSaveThreshold(int armor, int armorPenetration) {
        // Page 20
        // 7 - (Armour of the defender) + (Armour Penetration of the attack)
        int armorSaveThreshold = ARMOR_SAVE_DEFAULT_THRESHOLD - armor + armorPenetration;

        return armorSaveThreshold <= ARMOR_SAVE_AUTO_FAIL ? BEST_ARMOR_SAVE : armorSaveThreshold;
    }
}
