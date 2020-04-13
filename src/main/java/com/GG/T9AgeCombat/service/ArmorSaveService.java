package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ArmorSaveService {
    private static final int ARMOR_PENETRATION_MODIFIER = 3;
    private static final int ARMOR_SAVE_AUTO_FAIL = 1;
    DiceRollingService diceRollingService;

    public ArmorSaveService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Integer rollArmorSaves(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerArmorPenetration = this.calculateArmorPenetration(attacker);
        Integer defenderArmorSave = defender.getArmorSave();
        Integer armorSaveThreshold = defenderArmorSave + attackerArmorPenetration;
        List<Integer> armorSaveRolls = diceRollingService.roll(quantity);

        Integer successfulSaves = removeFailedArmorSaveRolls(armorSaveRolls, armorSaveThreshold);

        return quantity - successfulSaves;
    }

    Integer calculateArmorPenetration(Unit attacker) {
        Integer attackerStrength = attacker.getStrength();
        int attackerArmorPenetration = attackerStrength - ARMOR_PENETRATION_MODIFIER;

        return Math.max(attackerArmorPenetration, 0);
    }

    Integer removeFailedArmorSaveRolls(List<Integer> armorSaveRolls, Integer armorSaveThreshold) {
        return (int) armorSaveRolls.stream().filter(a -> a != ARMOR_SAVE_AUTO_FAIL && a >= armorSaveThreshold).count();
    }
}
