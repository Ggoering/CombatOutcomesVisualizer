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
        Unit blorcs2 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(11).AS(4).width(10).build();
        Unit blorcs3 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(10).AS(4).width(10).build();
        Unit blorcs4 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(30).AS(4).width(10).build();
        Unit blorcs5 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(30).AS(4).width(10).build();
        Unit blorcs6 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(3).AS(4).width(10).build();
        Unit blorcs7 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(9).AS(4).width(5).build();
        Unit blorcs8 = Unit.builder().name("Black Orcs").M(4).OWS(5).DWS(5).S(4).T(4).I(2).W(1).A(1).Ld(8).baseSize(25).Count(55).AS(4).width(5).build();
        Integer BONotInB2B1 = 4;
        Integer BONotInB2B2 = 0;

        Integer supportAttacks = subject.determineSupportingAttacks(blorcs, BONotInB2B1);
        Integer supportAttacks2 = subject.determineSupportingAttacks(blorcs2, BONotInB2B1);
        Integer supportAttacks3 = subject.determineSupportingAttacks(blorcs3, BONotInB2B1);
        Integer supportAttacks4 = subject.determineSupportingAttacks(blorcs4, BONotInB2B1);
        Integer supportAttacks5 = subject.determineSupportingAttacks(blorcs5, BONotInB2B2);
        Integer supportAttacks6 = subject.determineSupportingAttacks(blorcs6, BONotInB2B2);
        Integer supportAttacks7 = subject.determineSupportingAttacks(blorcs7, BONotInB2B2);
        Integer supportAttacks8 = subject.determineSupportingAttacks(blorcs8, BONotInB2B2);

        assertEquals(11, supportAttacks);
        assertEquals(1, supportAttacks2);
        assertEquals(0, supportAttacks3);
        assertEquals(12, supportAttacks4);
        assertEquals(20, supportAttacks5);
        assertEquals(0, supportAttacks6);
        assertEquals(4, supportAttacks7);
        assertEquals(5, supportAttacks8);
    }

    @Test
    void determineFrontRankAttacks() {
    }

    @Test
    void determineActualWidth() {
    }
}