package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CombatAggregationServiceTest {
    private CombatAggregationService subject;

    @BeforeEach
    void setUp() {
        DiceRollingService roll = new DiceRollingService();
        ToHitService hit = new ToHitService(roll);
        ToWoundService wound = new ToWoundService(roll);
        ArmorSaveService as = new ArmorSaveService(roll);
        AttackQuantityService aq = new AttackQuantityService();
        CombatResolutionService cr = new CombatResolutionService(roll);
        CombatCalculationService calc = new CombatCalculationService(aq, hit, wound, as, cr);
        subject = new CombatAggregationService(calc);
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