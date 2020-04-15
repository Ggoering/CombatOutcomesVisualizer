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
        Unit swordmaster = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(5).build();
        Unit blorcs = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(25).armorSave(4).width(10).build();

        Integer attackQuantitySM = subject.determineAttackQuantity(swordmaster, blorcs);
        assertEquals(15, attackQuantitySM);

        Integer attackQuantityBO = subject.determineAttackQuantity(blorcs, swordmaster);
        assertEquals(17, attackQuantityBO);
    }

    @Test
    void determineModelsNotInBaseContact() {
        Unit swordmaster = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(5).build();
        Unit swordmasterMedium = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(7).build();
        Unit swordmasterWide = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(2).leadership(8).basesize(20).modelCount(30).armorSave(5).width(10).build();
        Unit blorcs = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(25).armorSave(4).width(10).build();

        Integer blorcsActualWidth = blorcs.getActualWidth();
        Integer SMActualWidth = swordmaster.getActualWidth();
        Integer SMActualWidthWide = swordmasterWide.getActualWidth();
        Integer SMActualWidthMed = swordmasterMedium.getActualWidth();

        Integer BONotInB2B = subject.determineModelsNotInBaseContact(blorcsActualWidth, blorcs, SMActualWidth);
        Integer BONotInB2BWideSM = subject.determineModelsNotInBaseContact(blorcsActualWidth, blorcs, SMActualWidthWide);
        Integer SMNotInB2B = subject.determineModelsNotInBaseContact(SMActualWidth, swordmaster, blorcsActualWidth);
        Integer SMMedNotInB2B = subject.determineModelsNotInBaseContact(SMActualWidthMed, swordmasterMedium, SMActualWidth);

        assertEquals(4, BONotInB2B);
        assertEquals(0, BONotInB2BWideSM);
        assertEquals(0, SMNotInB2B);
        assertEquals(0, SMMedNotInB2B);
    }

    @Test
    void determineSupportingAttacks() {
        Unit blorcs = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(25).armorSave(4).width(10).build();
        Unit blorcs2 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(11).armorSave(4).width(10).build();
        Unit blorcs3 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(10).armorSave(4).width(10).build();
        Unit blorcs4 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(30).armorSave(4).width(10).build();
        Unit blorcs5 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(30).armorSave(4).width(10).build();
        Unit blorcs6 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(3).armorSave(4).width(10).build();
        Unit blorcs7 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(9).armorSave(4).width(5).build();
        Unit blorcs8 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(55).armorSave(4).width(5).build();
        Unit blorcs9 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(0).armorSave(4).width(5).build();
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
        Integer supportAttacks9 = subject.determineSupportingAttacks(blorcs9, BONotInB2B2);

        assertEquals(11, supportAttacks);
        assertEquals(1, supportAttacks2);
        assertEquals(0, supportAttacks3);
        assertEquals(12, supportAttacks4);
        assertEquals(20, supportAttacks5);
        assertEquals(0, supportAttacks6);
        assertEquals(4, supportAttacks7);
        assertEquals(5, supportAttacks8);
        assertEquals(0, supportAttacks9);
    }

    @Test
    void determineFrontRankAttacks() {
        Unit blorcs = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(2).leadership(8).basesize(25).modelCount(10).armorSave(4).width(10).build();
        Unit blorcs2 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(11).armorSave(4).width(10).build();
        Unit blorcs3 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(2).leadership(8).basesize(25).modelCount(1).armorSave(4).width(5).build();
        Unit blorcs4 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(7).leadership(8).basesize(25).modelCount(0).armorSave(4).width(5).build();
        Unit blorcs5 = Unit.builder().name("Black Orc").movement(4).offensiveWeaponSkill(5).defensiveWeaponSkill(5).strength(4).toughness(4).initiative(2).wounds(1).attacks(7).leadership(8).basesize(25).modelCount(0).armorSave(4).width(5).build();

        Integer BONotInB2B1 = 4;
        Integer BONotInB2B2 = 0;

        Integer attacks = subject.determineFrontRankAttacks(blorcs, BONotInB2B1);
        Integer attacks2 = subject.determineFrontRankAttacks(blorcs2, BONotInB2B1);
        Integer attacks3 = subject.determineFrontRankAttacks(blorcs3, BONotInB2B1);
        Integer attacks4 = subject.determineFrontRankAttacks(blorcs4, BONotInB2B1);
        Integer attacks5 = subject.determineFrontRankAttacks(blorcs5, BONotInB2B2);

        assertEquals(12, attacks);
        assertEquals(6, attacks2);
        assertEquals(2, attacks3);
        assertEquals(0, attacks4);
        assertEquals(0, attacks5);
    }

    @Test
    void determineBackRankAttacks() {
        Integer attacks = subject.determineBackRankSupports(4, 2, 25, 10);
        Integer attacks2 = subject.determineBackRankSupports(4, 1, 25, 10);
        Integer attacks3 = subject.determineBackRankSupports(4, 2, 30, 10);
        Integer attacks4 = subject.determineBackRankSupports(0, 1, 29, 5);
        Integer attacks5 = subject.determineBackRankSupports(0, 1, 5, 10);
        Integer attacks6 = subject.determineBackRankSupports(0, 1, 7, 5);
        Integer attacks7 = subject.determineBackRankSupports(4, 2, 22, 10);

        assertEquals(5, attacks);
        assertEquals(6, attacks2);
        assertEquals(6, attacks3);
        assertEquals(5, attacks4);
        assertEquals(0, attacks5);
        assertEquals(2, attacks6);
        assertEquals(2, attacks7);
    }

    @Test
    void determineMidRankAttacks() {
        Integer attacks = subject.determineMidRankSupports(4, 1, 5, 25, 10);
        Integer attacks2 = subject.determineMidRankSupports(4, 1, 2, 23, 10);
        Integer attacks3 = subject.determineMidRankSupports(0, 1, 10, 30, 10);
        Integer attacks4 = subject.determineMidRankSupports(0, 4, 10, 40, 10);
        Integer attacks5 = subject.determineMidRankSupports(0, 1, 10, 40, 10);
        Integer attacks6 = subject.determineMidRankSupports(4, 2, 5, 25, 10);

        assertEquals(6, attacks);
        assertEquals(6, attacks2);
        assertEquals(10, attacks3);
        assertEquals(20, attacks4);
        assertEquals(10, attacks5);
        assertEquals(6, attacks6);
    }

    @Test
    void determineActualWidth() {
    }
}