package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class WardSaveService {
    private static final int WARD_SAVE_AUTO_FAIL = 1;
    DiceRollingService diceRollingService;

    public WardSaveService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Integer rollWardSaves(Unit unit, Integer quantity) {
        return quantity - removeFailedWardSaveRolls(diceRollingService.roll(quantity), unit.getWardSave());
    }

    int removeFailedWardSaveRolls(List<Integer> wardSaveRolls, Integer wardSaveThreshold) {
        return (int) wardSaveRolls.stream().filter(wardSave -> wardSave != WARD_SAVE_AUTO_FAIL && wardSave >= wardSaveThreshold).count();
    }
}
