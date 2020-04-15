package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.service.repository.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombatAggregationService {
    public static final int MAXIMUM_ROUND_COUNT = 10000;
    @Autowired
    private final CombatCalculationService combatCalculationService;
    private UnitService unitService;

    public CombatAggregationService(CombatCalculationService combatCalculationService, UnitService unitService) {
        this.combatCalculationService = combatCalculationService;
        this.unitService = unitService;
    }

    List<Result> getCombatResults(Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId, Integer secondaryCount, Integer secondaryWidth) {
        List<Result> resultList = new ArrayList<>();

        for (int i = 0; i < MAXIMUM_ROUND_COUNT; i++) {
            Unit swordmasters = unitService.retrieveUnit(1);
            Unit blackOrcs = unitService.retrieveUnit(2);

            swordmasters.setSelection(1);
            blackOrcs.setSelection(2);

            Result result = combatCalculationService.combat(swordmasters, blackOrcs);

            resultList.add(result);
        }

        return resultList;
    }

    public void getDataAggregation() {
        List<Result> resultList = getCombatResults(1, 1, 1, 1, 1, 1);

        long swordmasterCount = resultList.stream().filter(a -> a.getWinner().equals("Swordmaster")).count();
        long blackOrcCount = resultList.stream().filter(a -> a.getWinner().equals("Black Orc")).count();
        System.out.print(swordmasterCount + " Swordmaster");
        System.out.print(blackOrcCount + " Black Orc");
    }
}
