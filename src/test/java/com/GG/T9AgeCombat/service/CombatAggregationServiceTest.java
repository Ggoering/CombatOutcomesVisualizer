package com.GG.T9AgeCombat.service;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CombatAggregationServiceTest {
    private CombatAggregationService subject;

    @BeforeEach
    void setUp() {
        DiceRollingService diceRollingService = new DiceRollingService();
        ToHitService toHitService = new ToHitService(diceRollingService);
        ToWoundService toWoundService = new ToWoundService(diceRollingService);
        ArmorSaveService armorSaveService = new ArmorSaveService(diceRollingService);
        AttackQuantityService attackQuantityService = new AttackQuantityService();
        CombatResolutionService combatResolutionService = new CombatResolutionService(diceRollingService);
        CombatCalculationService combatCalculationService = new CombatCalculationService(attackQuantityService, toHitService, toWoundService, armorSaveService, combatResolutionService);
        subject = new CombatAggregationService(combatCalculationService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCombatResults() {
    }

    @Test
    void getDataAggregation() {
        subject.getDataAggregation();
    }
}