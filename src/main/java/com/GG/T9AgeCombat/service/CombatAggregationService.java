package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.enums.SpecialRules;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombatAggregationService {
    @Autowired
    private final CombatCalculationService combatCalculationService;


    public CombatAggregationService(CombatCalculationService combatCalculationService) {
        this.combatCalculationService = combatCalculationService;
    }
    List<Result> getCombatResults(Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId, Integer secondaryCount, Integer secondaryWidth) {

        List<Result> resultList = new ArrayList<Result>();


        for (int i = 0; i < 10000; i++) {
            Unit swordmaster = Unit.builder().name(Identification.SWORD_MASTER).M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).count(30).AS(5).width(5).selection(1).standardBearer(1).musician(1).build();
            List<SpecialRules> specialRulesList2 = new ArrayList<SpecialRules>();
            specialRulesList2.add(SpecialRules.BORN_TO_FIGHT);
            Unit blorcs = Unit.builder().name(Identification.BLACK_ORC).M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(2).Ld(8).baseSize(25).count(25).AS(4).width(5).selection(2).standardBearer(1).musician(1).specialRulesList(specialRulesList2).build();
            Result result = combatCalculationService.combat(swordmaster, blorcs);

            resultList.add(result);
        }

        return resultList;
    }

    public void getDataAggregation() {
        List<Result> resultList = this.getCombatResults(1, 1, 1, 1, 1, 1);

        long smCount = resultList.stream().filter(a -> a.getWinner().equals(Identification.SWORD_MASTER)).count();
        long boCount = resultList.stream().filter(a -> a.getWinner().equals(Identification.BLACK_ORC)).count();
        System.out.print(smCount + " Swordmasters");
        System.out.print(boCount + " BO");
    }




}
