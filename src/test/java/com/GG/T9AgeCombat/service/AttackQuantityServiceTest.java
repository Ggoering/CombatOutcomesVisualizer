package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AttackQuantityServiceTest {
    private AttackQuantityService subject;

    @BeforeEach
    void setUp() {
        subject = new AttackQuantityService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void determineAttackQuantity() {
        Unit swordmaster = Unit.builder().name("Sword Master").M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).Count(30).AS(5).width(5).build();
        Unit blorcs = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(25).AS(4).width(10).build();

        Integer attackQuantitySM = subject.determineAttackQuantity(swordmaster, swordmaster, blorcs);
        assertEquals(15, attackQuantitySM);

        Integer attackQuantityBO = subject.determineAttackQuantity(blorcs, blorcs, swordmaster);
        assertEquals(17, attackQuantityBO);
    }

    @Test
    void determineModelsNotInBaseContact() {
        Unit swordmaster = Unit.builder().name("Sword Master").M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).Count(30).AS(5).width(5).build();
        Unit swordmasterWide = Unit.builder().name("Sword Master").M(5).OWS(6).DWS(6).S(5).T(3).I(6).W(1).A(2).Ld(8).baseSize(20).Count(30).AS(5).width(10).build();
        Unit blorcs = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(25).AS(4).width(10).build();

        Integer attackerActualWidth = subject.determineActualWidth(blorcs);
        Integer defenderActualWidth = subject.determineActualWidth(swordmaster);
        Integer defenderActualWidthWide = subject.determineActualWidth(swordmasterWide);
        Integer BONotInB2B = subject.determineModelsNotInBaseContact(attackerActualWidth, blorcs, defenderActualWidth);
        Integer BONotInB2BWideSM = subject.determineModelsNotInBaseContact(attackerActualWidth, swordmasterWide, defenderActualWidthWide);
        Integer SMNotInB2B = subject.determineModelsNotInBaseContact(defenderActualWidth, blorcs, attackerActualWidth);

        assertEquals(4, BONotInB2B);
        assertEquals(0, BONotInB2BWideSM);
        assertEquals(0, SMNotInB2B);
    }

    @Test
    void determineSupportingAttacks() {
        Unit blorcs = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(25).AS(4).width(10).build();
        Integer BONotInB2B = 4;

        Integer supportAttacks = subject.determineSupportingAttacks(blorcs, BONotInB2B);

        assertEquals(11, supportAttacks);
    }

    @Test
    void determineFrontRankAttacks() {
    }

    @Test
    void determineActualWidth() {
    }
}