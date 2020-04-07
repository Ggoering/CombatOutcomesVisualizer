package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ArmorSaveService {
    DiceRollingService diceRollingService;
    public ArmorSaveService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Integer rollArmorSaves(Unit attacker, Unit defender, Integer quantity) {
        Integer attackerAP = this.determineAP(attacker);
        Integer defenderAS = defender.getAS();
        Integer finalAS = defenderAS + attackerAP;
        List<Integer> ASRolls = diceRollingService.roll(quantity);

        Integer successfulSaves = filterOutMissedSaves(ASRolls,finalAS);
        Integer woundsToApply = quantity - successfulSaves;

        return woundsToApply;
    }

    Integer determineAP(Unit attacker) {
        Integer attackerS = attacker.getS();
        Integer ap = attackerS - 3;

        return ap >= 0 ?  ap : 0;
    }

    Integer filterOutMissedSaves(List<Integer> ASRolls, Integer armorSave) {
        return (int)ASRolls.stream().filter(a -> a != 1 && a >= armorSave).count();
    }
}
