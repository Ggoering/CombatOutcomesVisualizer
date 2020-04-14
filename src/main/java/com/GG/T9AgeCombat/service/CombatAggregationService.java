package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.enums.SpecialRule;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CombatAggregationService {
    public static final int MAXIMUM_ROUND_COUNT = 10000;
    @Autowired
    private final CombatCalculationService combatCalculationService;

    public CombatAggregationService(CombatCalculationService combatCalculationService) {
        this.combatCalculationService = combatCalculationService;
    }

    List<Result> getCombatResults(Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId, Integer secondaryCount, Integer secondaryWidth) {
        List<Result> resultList = new ArrayList<>();

        for (int i = 0; i < MAXIMUM_ROUND_COUNT; i++) {
            Unit swordmasters = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).baseSize(20).modelCount(30).armorSave(5).width(5).selection(1).standardBearer(1).hasMusician(true).build();
            List<SpecialRule> specialRuleList = new ArrayList<>();
            specialRuleList.add(SpecialRule.BORN_TO_FIGHT);
            Unit blackOrcs = Unit.builder().name(Identification.BLACK_ORC).movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(2).leadership(8).baseSize(25).modelCount(25).armorSave(4).width(5).selection(2).standardBearer(1).hasMusician(true).specialRuleList(specialRuleList).build();
            Result result = combatCalculationService.combat(swordmasters, blackOrcs);

            resultList.add(result);
        }

        return resultList;
    }

    public void getDataAggregation() {
        List<Result> resultList = getCombatResults(1, 1, 1, 1, 1, 1);

        long swordmasterCount = resultList.stream().filter(a -> a.getWinner().equals(Identification.SWORD_MASTER)).count();
        long blackOrcCount = resultList.stream().filter(a -> a.getWinner().equals(Identification.BLACK_ORC)).count();
        System.out.print(swordmasterCount + " Swordmasters");
        System.out.print(blackOrcCount + " Black Orcs");
    }
}
