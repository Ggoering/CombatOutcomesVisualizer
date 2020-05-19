package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.*;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.service.repository.UnitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
        LimitationEnum limitation = LimitationEnum.FIRST_ROUND;
        ModificationEnum modification = ModificationEnum.STRENGTH;
        TimingEnum timing = TimingEnum.ALL;

        SpecialRuleProperty specialRuleProperty = SpecialRuleProperty.builder().limitation(limitation).modification(modification)
                .timing(timing).name(SpecialRulePropertyEnum.BORN_TO_FIGHT).value(1).build();

        List<SpecialRuleProperty> unitSpecialRuleProperties = new ArrayList<>();
        unitSpecialRuleProperties.add(specialRuleProperty);

        UnitHeightEnum unitHeight = UnitHeightEnum.STANDARD;

        when(mockUnitService.retrieveUnit(1)).thenReturn(
                Unit.builder().name("Swordmaster").height(unitHeight).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armor(5).modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).build());
        when(mockUnitService.retrieveUnit(2)).thenReturn(
                Unit.builder().name("Black Orc").height(unitHeight).movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(2).leadership(8).basesize(25).modelCount(25).armor(4).modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).specialRulePropertyList(unitSpecialRuleProperties).build());

        subject.getDataAggregation();
    }
}