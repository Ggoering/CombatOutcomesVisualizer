package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.CombatResultResponse;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CombatResultService {
    private final CombatCalculationService combatCalculationService;


    public CombatResultService(CombatCalculationService combatCalculationService) {
        this.combatCalculationService = combatCalculationService;
    }
    public List<Result> getResult(Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId,  Integer secondaryCount, Integer secondaryWidth) {

        List<Result> resultList = Collections.emptyList();

        Unit swordmaster = Unit.builder().name("Sword Master").M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).Count(30).AS(5).width(5).build();

        Unit blorcs = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(25).AS(4).width(5).build();

        for (int i = 0; i < 10000; i++) {
            Result result = combatCalculationService.combat(swordmaster, blorcs);

            resultList.add(result);
        }

        return resultList;
    }

}
