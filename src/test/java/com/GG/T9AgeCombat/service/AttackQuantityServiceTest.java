package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.GG.T9AgeCombat.models.OffensiveProfile;
import com.GG.T9AgeCombat.models.Unit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        UnitHeightEnum unitHeight = UnitHeightEnum.STANDARD;

        OffensiveProfile swordmasterOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(2)
                .build();

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(2)
                .attacks(1)
                .build();

        List<OffensiveProfile> swordmasterOffensiveProfileList = new ArrayList<>();
        swordmasterOffensiveProfileList.add(swordmasterOffensiveProfile);

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);

        Unit swordmaster = Unit.builder().name("Swordmaster").height(unitHeight).advance(5).defensiveWeaponSkill(6).toughness(3)
                .wounds(1).leadership(8).basesize(20).modelCount(30).armor(5).modelsPerRank(5)
                .offensiveProfileList(swordmasterOffensiveProfileList).build();
        Unit blorcs = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1)
                .leadership(8).basesize(25).modelCount(25).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();

        Integer attackQuantitySM = subject.determineAttackQuantity(swordmasterOffensiveProfile, swordmaster, blorcs);
        assertEquals(15, attackQuantitySM);

        Integer attackQuantityBO = subject.determineAttackQuantity(blackorcOffensiveProfile, blorcs, swordmaster);
        assertEquals(17, attackQuantityBO);
    }

    @Test
    void determineModelsNotInBaseContact() {
        Unit swordmaster = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6).toughness(3).wounds(1)
                .leadership(8).basesize(20).modelCount(30).armor(5).modelsPerRank(5).build();
        Unit swordmasterMedium = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6).toughness(3).wounds(1)
                .leadership(8).basesize(20).modelCount(30).armor(5).modelsPerRank(7).build();
        Unit swordmasterWide = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6).toughness(3).wounds(1)
                .leadership(8).basesize(20).modelCount(30).armor(5).modelsPerRank(10).build();
        Unit blorcs = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1)
                .leadership(8).basesize(25).modelCount(25).armor(4).modelsPerRank(10).build();

        int blorcsActualWidth = blorcs.getWidth();
        int SMActualWidth = swordmaster.getWidth();
        int SMActualWidthWide = swordmasterWide.getWidth();
        int SMActualWidthMed = swordmasterMedium.getWidth();

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
        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(2)
                .attacks(1)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);
        
        UnitHeightEnum unitHeight = UnitHeightEnum.STANDARD;
        Unit blorcs = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(25).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs2 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(11).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs3 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(10).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs4 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(30).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs5 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(30).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs6 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(3).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs7 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(9).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs8 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(55).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit blorcs9 = Unit.builder().name("Black Orc").height(unitHeight).advance(4).defensiveWeaponSkill(5).toughness(4)
                .wounds(1).leadership(8).basesize(25).modelCount(0).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        int BONotInB2B1 = 4;
        int BONotInB2B2 = 0;

        Integer supportAttacks = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs, BONotInB2B1);
        Integer supportAttacks2 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs2, BONotInB2B1);
        Integer supportAttacks3 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs3, BONotInB2B1);
        Integer supportAttacks4 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs4, BONotInB2B1);
        Integer supportAttacks5 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs5, BONotInB2B2);
        Integer supportAttacks6 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs6, BONotInB2B2);
        Integer supportAttacks7 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs7, BONotInB2B2);
        Integer supportAttacks8 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs8, BONotInB2B2);
        Integer supportAttacks9 = subject.determineSupportingAttacks(blackorcOffensiveProfile, blorcs9, BONotInB2B2);

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
        OffensiveProfile blackorcOffensiveProfile2Atk = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(2)
                .attacks(2)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfile2AtkList = new ArrayList<>();
        blackorcOffensiveProfile2AtkList.add(blackorcOffensiveProfile2Atk);

        OffensiveProfile blackorcOffensiveProfile1Atk = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(2)
                .attacks(1)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfile1AtkList = new ArrayList<>();
        blackorcOffensiveProfile1AtkList.add(blackorcOffensiveProfile2Atk);

        OffensiveProfile blackorcOffensiveProfile7Atk = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(2)
                .attacks(7)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfile7AtkList = new ArrayList<>();
        blackorcOffensiveProfile7AtkList.add(blackorcOffensiveProfile2Atk);

        Unit blorcs = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1).leadership(8)
                .basesize(25).modelCount(10).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfile2AtkList).build();
        Unit blorcs2 = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1).leadership(8)
                .basesize(25).modelCount(11).armor(4).modelsPerRank(10)
                .offensiveProfileList(blackorcOffensiveProfile1AtkList).build();
        Unit blorcs3 = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1).leadership(8)
                .basesize(25).modelCount(1).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfile2AtkList).build();
        Unit blorcs4 = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1).leadership(8)
                .basesize(25).modelCount(0).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfile7AtkList).build();
        Unit blorcs5 = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5).toughness(4).wounds(1).leadership(8)
                .basesize(25).modelCount(0).armor(4).modelsPerRank(5)
                .offensiveProfileList(blackorcOffensiveProfile7AtkList).build();

        int BONotInB2B1 = 4;
        int BONotInB2B2 = 0;

        Integer attacks = subject.determineFrontRankAttacks(blackorcOffensiveProfile2Atk, blorcs, BONotInB2B1);
        Integer attacks2 = subject.determineFrontRankAttacks(blackorcOffensiveProfile1Atk, blorcs2, BONotInB2B1);
        Integer attacks3 = subject.determineFrontRankAttacks(blackorcOffensiveProfile2Atk, blorcs3, BONotInB2B1);
        Integer attacks4 = subject.determineFrontRankAttacks(blackorcOffensiveProfile7Atk, blorcs4, BONotInB2B1);
        Integer attacks5 = subject.determineFrontRankAttacks(blackorcOffensiveProfile7Atk, blorcs5, BONotInB2B2);

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
    @DisplayName("Supporting Attack Count - Standard Height")
    void supportingAttackCountStandardHeight() {
        UnitHeightEnum unitHeight = UnitHeightEnum.STANDARD;

        int noAttacks = subject.getSupportingAttacksPerModel(unitHeight, 0);
        int equalAttacks = subject.getSupportingAttacksPerModel(unitHeight, 1);
        int greaterAttacks = subject.getSupportingAttacksPerModel(unitHeight, 2);

        assertThat(noAttacks).isEqualTo(0);
        assertThat(equalAttacks).isEqualTo(1);
        assertThat(greaterAttacks).isEqualTo(1);
    }

    @Test
    @DisplayName("Supporting Attack Count - Large Height")
    void supportingAttackCountLargeHeight() {
        UnitHeightEnum unitHeight = UnitHeightEnum.LARGE;

        int noAttacks = subject.getSupportingAttacksPerModel(unitHeight, 0);
        int lessAttacks = subject.getSupportingAttacksPerModel(unitHeight, 2);
        int equalAttacks = subject.getSupportingAttacksPerModel(unitHeight, 3);
        int greaterAttacks = subject.getSupportingAttacksPerModel(unitHeight, 4);

        assertThat(noAttacks).isEqualTo(0);
        assertThat(lessAttacks).isEqualTo(2);
        assertThat(equalAttacks).isEqualTo(3);
        assertThat(greaterAttacks).isEqualTo(3);
    }

    @Test
    @DisplayName("Supporting Attack Count - Gigantic Height")
    void supportingAttackCountGiganticHeight() {
        UnitHeightEnum unitHeight = UnitHeightEnum.GIGANTIC;

        int noAttacks = subject.getSupportingAttacksPerModel(unitHeight, 0);
        int lessAttacks = subject.getSupportingAttacksPerModel(unitHeight, 4);
        int equalAttacks = subject.getSupportingAttacksPerModel(unitHeight, 5);
        int greaterAttacks = subject.getSupportingAttacksPerModel(unitHeight, 6);

        assertThat(noAttacks).isEqualTo(0);
        assertThat(lessAttacks).isEqualTo(4);
        assertThat(equalAttacks).isEqualTo(5);
        assertThat(greaterAttacks).isEqualTo(5);
    }

    @Test
    void determineActualWidth() {
    }
}