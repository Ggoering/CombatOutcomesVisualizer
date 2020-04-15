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

    int rollArmorSaves(Unit attacker, Unit defender, int quantity) {
        int armorSaveThreshold = defender.getArmorSave() + calculateArmorPenetration(attacker);
        List<Integer> armorSaveRolls = diceRollingService.roll(quantity);

        return quantity - removeFailedArmorSaveRolls(armorSaveRolls, armorSaveThreshold);
    }

    Integer calculateArmorPenetration(Unit attacker) {
        return Math.max(attacker.getStrength() - ARMOR_PENETRATION_MODIFIER, 0);
    }

    Integer removeFailedArmorSaveRolls(List<Integer> armorSaveRolls, int armorSaveThreshold) {
        return (int) armorSaveRolls.stream().filter(armorSave -> armorSave != ARMOR_SAVE_AUTO_FAIL && armorSave >= armorSaveThreshold).count();
    }
}
