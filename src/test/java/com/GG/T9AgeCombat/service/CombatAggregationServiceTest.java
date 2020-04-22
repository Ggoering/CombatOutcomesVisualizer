package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.SpecialRuleEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.service.repository.UnitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CombatAggregationServiceTest {
    private CombatAggregationService subject;
    @Mock
    private UnitService mockUnitService;

    @BeforeEach
    void setUp() {
        DiceRollingService diceRollingService = new DiceRollingService();
        ToHitService toHitService = new ToHitService(diceRollingService);
        ToWoundService toWoundService = new ToWoundService(diceRollingService);
        ArmorSaveService armorSaveService = new ArmorSaveService(diceRollingService);
        WardSaveService wardSaveService = new WardSaveService(diceRollingService);
        AttackQuantityService attackQuantityService = new AttackQuantityService();
        SpecialRuleRoutingService specialRuleRoutingService = new SpecialRuleRoutingService();
        CombatResolutionService combatResolutionService = new CombatResolutionService(diceRollingService);
        CombatCalculationService combatCalculationService = new CombatCalculationService(attackQuantityService, toHitService, toWoundService,
                armorSaveService, wardSaveService, combatResolutionService, specialRuleRoutingService);

        subject = new CombatAggregationService(combatCalculationService, mockUnitService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCombatResults() {
    }

    @Test
    void getDataAggregation() {
        Limitation limitation = Limitation.builder().value(LimitationEnum.FIRST_ROUND.toString()).build();
        Modification modification = Modification.builder().value(ModificationEnum.STRENGTH.toString()).build();
        Timing timing = Timing.builder().value(TimingEnum.ALL.toString()).build();

        SpecialRule specialRule = SpecialRule.builder().limitationByLimitationId(limitation).modificationByModificationId(modification)
                .timingByTimingId(timing).name(SpecialRuleEnum.BORN_TO_FIGHT.toString()).value(1).build();
        UnitSpecialRule unitSpecialRule = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRule).build();

        Collection<UnitSpecialRule> unitSpecialRules = new ArrayList<>();
        unitSpecialRules.add(unitSpecialRule);

        when(mockUnitService.retrieveUnit(1)).thenReturn(
                Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(5).selection(1).standardBearer(1).hasMusician(true).build());
        when(mockUnitService.retrieveUnit(2)).thenReturn(
                Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(2).leadership(8).basesize(25).modelCount(25).armorSave(4).width(5).selection(2).standardBearer(1).hasMusician(true).unitSpecialRulesById(unitSpecialRules).build());

        subject.getDataAggregation();
    }
}