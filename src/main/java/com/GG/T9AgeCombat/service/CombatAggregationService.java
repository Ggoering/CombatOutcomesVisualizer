package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.service.repository.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombatAggregationService {
    public static final int MAXIMUM_ROUND_COUNT = 10000;
    @Autowired
    private final CombatCalculationService combatCalculationService;
    private final UnitService unitService;
    private static final Logger logger = LoggerFactory.getLogger(CombatAggregationService.class);

    public CombatAggregationService(CombatCalculationService combatCalculationService, UnitService unitService) {
        this.combatCalculationService = combatCalculationService;
        this.unitService = unitService;
    }

    List<Result> getCombatResults(Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer primaryEquipmentSet,
                                  Integer secondaryId, Integer secondaryCount, Integer secondaryWidth, Integer secondaryEquipmentSet) {
        List<Result> resultList = new ArrayList<>();

        for (int i = 0; i < MAXIMUM_ROUND_COUNT; i++) {
            Unit primary = unitService.retrieveUnit(1, 1);
            Unit secondary = unitService.retrieveUnit(3, 1);
//            Unit third = unitService.retrieveUnit(2, 1);
//            Unit fourth = unitService.retrieveUnit(4, 1);

            primary.addEquipmentSpecialRules();
            secondary.addEquipmentSpecialRules();
//            third.addEquipmentSpecialRules();
//            fourth.addEquipmentSpecialRules();

            primary.setSelection(1);
            secondary.setSelection(2);

            Result result = combatCalculationService.combat(primary, secondary);

            resultList.add(result);
        }

        return resultList;
    }

    public void getDataAggregation() {
        List<Result> resultList = getCombatResults(1, 1, 1, 1, 1, 1, 1, 1);

        long swordmasterCount = resultList.stream().filter(a -> a.getWinner().equals("Swordmaster")).count();
        long blackOrcCount = resultList.stream().filter(a -> a.getWinner().equals("Black Orc")).count();
        logger.info("{} Swordmaster", swordmasterCount);
        logger.info("{} Black Orc", blackOrcCount);
    }
}
