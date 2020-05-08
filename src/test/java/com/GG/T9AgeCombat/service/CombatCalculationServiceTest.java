package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.entities.Limitation;
import com.GG.T9AgeCombat.entities.Modification;
import com.GG.T9AgeCombat.entities.SpecialRuleEntity;
import com.GG.T9AgeCombat.entities.Timing;
import com.GG.T9AgeCombat.enums.*;
import com.GG.T9AgeCombat.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CombatCalculationServiceTest {
    @Mock
    private DiceRollingService mockDiceRollingService;
    @Mock
    private ToHitService mockToHitService;
    @Mock
    private ToWoundService mockToWoundService;
    @Mock
    private ArmorSaveService mockArmorSaveService;
    @Mock
    private WardSaveService mockWardSaveService;

    @Test
    @DisplayName("Units Have Same Initiative")
    void validateInitiative() {
        // Arrange
        List<Round> expectedResult = new ArrayList<>();
        Round expectedRound = Round.builder().combatScoreDifferential(null).primaryWoundsDealt(4).secondaryWoundsDealt(2)
                .flee(false).caught(null).winner("Swordmaster").wipedOut(true).build();
        expectedResult.add(expectedRound);
        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armor(5).modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(2).armor(5).modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).build();

        // Attacker is unit1
        when(mockToHitService.rollToHit(unit1, unit2, 4)).thenReturn(4);
        when(mockToWoundService.rollToWound(unit1, unit2, 4)).thenReturn(4);
        when(mockArmorSaveService.rollArmorSaves(unit1, unit2, 4)).thenReturn(4);

        // Attacker is unit2
        when(mockToHitService.rollToHit(unit2, unit1, 2)).thenReturn(2);
        when(mockToWoundService.rollToWound(unit2, unit1, 2)).thenReturn(2);
        when(mockArmorSaveService.rollArmorSaves(unit2, unit1, 2)).thenReturn(2);

        // Act
        List<Round> actualRound = getCombatCalculationService().fight(unit1, unit2, false, new ArrayList<>());

        // Assert
        assertThat(actualRound).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Mounts Have Same Initiative")
    void validateInitiativeMounts() {
        // Arrange
        List<Round> expectedResult = new ArrayList<>();
        Round expectedRound = Round.builder().primaryWoundsDealt(2).secondaryWoundsDealt(1)
                .flee(true).caught(false).winner("Swordmaster").wipedOut(false).build();
        expectedResult.add(expectedRound);
        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6)
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armor(5)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).isMounted(true)
                .mountAttacks(1).mountInitiative(6).mountStrength(5).mountOffensiveWeaponSkill(5).build();
        Unit unit1Mount = Unit.builder().isMount(true).offensiveWeaponSkill(5).strength(5).initiative(6)
                .attacks(1).basesize(25).modelCount(5).modelsPerRank(5).selection(1).build();
        Unit unit1Wounded = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6)
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(4).armor(5)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).isMounted(true)
                .mountAttacks(1).mountInitiative(6).mountStrength(5).mountOffensiveWeaponSkill(5).build();

        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(3).armor(5)
                .modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).isMounted(true)
                .mountAttacks(1).mountInitiative(6).mountStrength(5).mountOffensiveWeaponSkill(4).build();
        Unit unit2Mount = Unit.builder().isMount(true).offensiveWeaponSkill(4).strength(5).initiative(6)
                .attacks(1).basesize(25).modelCount(3).modelsPerRank(5).selection(2).build();
        Unit unit2Wounded = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(2).armor(5)
                .modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).isMounted(true)
                .mountAttacks(1).mountInitiative(6).mountStrength(5).mountOffensiveWeaponSkill(4).build();
        Unit unit2CriticalWound = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(1).armor(5)
                .modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).isMounted(true)
                .mountAttacks(1).mountInitiative(6).mountStrength(5).mountOffensiveWeaponSkill(4).build();

        // Unit1 mount
        when(mockToHitService.rollToHit(unit1Mount, unit2, 5)).thenReturn(1);
        when(mockToWoundService.rollToWound(unit1Mount, unit2, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(unit1Mount, unit2, 1)).thenReturn(1);

        // Unit2 mount
        when(mockToHitService.rollToHit(unit2Mount, unit1, 3)).thenReturn(1);
        when(mockToWoundService.rollToWound(unit2Mount, unit1, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(unit2Mount, unit1, 1)).thenReturn(1);

        // Unit1 riders
        when(mockToHitService.rollToHit(unit1Wounded, unit2Wounded, 4)).thenReturn(1);
        when(mockToWoundService.rollToWound(unit1Wounded, unit2Wounded, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(unit1Wounded, unit2Wounded, 1)).thenReturn(1);

        // Unit2 riders
        when(mockToHitService.rollToHit(unit2CriticalWound, unit1Wounded, 1)).thenReturn(0);
        when(mockToWoundService.rollToWound(unit2CriticalWound, unit1Wounded, 0)).thenReturn(0);

        when(mockDiceRollingService.rollWithSum(2)).thenReturn(7);
        when(mockDiceRollingService.rollWithSumTakeHighest(3, 2)).thenReturn(9);

        // Act
        List<Round> actualRound = getCombatCalculationService().fight(unit1, unit2, false, new ArrayList<>());

        // Assert
        assertThat(actualRound).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Validate Ward Saves")
    void validateWardSaves() {
        // Arrange
        List<Round> expectedResult = new ArrayList<>();
        Round expectedRound = Round.builder().combatScoreDifferential(null).primaryWoundsDealt(0).secondaryWoundsDealt(1)
                .flee(true).caught(false).winner("Black Orc").wipedOut(false).build();
        expectedResult.add(expectedRound);
        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5)
                .toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armor(5).modelsPerRank(5).selection(1)
                .standardBearer(1).wardSave(4).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5)
                .toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(2).armor(5).modelsPerRank(5).selection(2)
                .standardBearer(1).hasMusician(true).wardSave(6).build();

        // Attacker is unit1
        when(mockToHitService.rollToHit(unit1, unit2, 4)).thenReturn(4);
        when(mockToWoundService.rollToWound(unit1, unit2, 4)).thenReturn(4);
        when(mockArmorSaveService.rollArmorSaves(unit1, unit2, 4)).thenReturn(4);
        when(mockWardSaveService.rollWardSaves(unit1, 2)).thenReturn(1);

        // Attacker is unit2
        when(mockToHitService.rollToHit(unit2, unit1, 2)).thenReturn(2);
        when(mockToWoundService.rollToWound(unit2, unit1, 2)).thenReturn(2);
        when(mockArmorSaveService.rollArmorSaves(unit2, unit1, 2)).thenReturn(2);
        when(mockWardSaveService.rollWardSaves(unit2, 4)).thenReturn(0);

        when(mockDiceRollingService.rollWithSum(2)).thenReturn(12);

        // Act
        List<Round> actualRound = getCombatCalculationService().fight(unit1, unit2, false, new ArrayList<>());

        // Assert
        assertThat(actualRound).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Applies special rules")
    void applySpecialRules() {
        SpecialRule specialRuleHatred = SpecialRule.builder()
                .limitation(LimitationEnum.FIRST_ROUND)
                .modification(ModificationEnum.RE_ROLL_TO_HIT)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.HATRED)
                .value(6)
                .build();

        SpecialRule specialRuleHorde = SpecialRule.builder()
                .limitation(LimitationEnum.EIGHT_WIDE)
                .modification(ModificationEnum.EXTRA_RANKS)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.HORDE)
                .value(1)
                .build();

        SpecialRule specialRuleSwordSworn = SpecialRule.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.TO_HIT)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.SWORD_SWORN)
                .value(1)
                .build();

        SpecialRule specialRuleLightningReflexesGreatWeapon = SpecialRule.builder()
                .limitation(LimitationEnum.LIGHTNING_REFLEXES)
                .modification(ModificationEnum.INITIATIVE)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.LIGHTNING_REFLEXES_GREAT_WEAPON)
                .value(0)
                .build();

        SpecialRule specialRuleGreatWeaponStrikeLast = SpecialRule.builder()
                .limitation(LimitationEnum.LIGHTNING_REFLEXES)
                .modification(ModificationEnum.INITIATIVE)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.STRIKES_LAST)
                .value(-10)
                .build();


            SpecialRule specialRuleGreatWeapon = SpecialRule.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.STRENGTH_AND_ARMOR_PENETRATION)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.GREAT_WEAPON)
                .value(2)
                .build();

            SpecialRule specialRuleBornToFight = SpecialRule.builder()
                .limitation(LimitationEnum.FIRST_ROUND)
                .modification(ModificationEnum.STRENGTH)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.BORN_TO_FIGHT)
                .value(1)
                .build();

            SpecialRule specialRuleHeavyArmor = SpecialRule.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.HEAVY_ARMOR)
                .value(1)
                .build();

            SpecialRule specialRulePlateArmor = SpecialRule.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRuleEnum.HEAVY_ARMOR)
                .value(1)
                .build();

        List<SpecialRule> armorSpecialRulesBlackOrc = new ArrayList<>();
        armorSpecialRulesBlackOrc.add(specialRulePlateArmor);

        List<SpecialRule> armorSpecialRulesSwordMaster = new ArrayList<>();
        armorSpecialRulesSwordMaster.add(specialRuleHeavyArmor);

        List<SpecialRule> greatWeaponSpecialRulesBlackOrc = new ArrayList<>();
        greatWeaponSpecialRulesBlackOrc.add(specialRuleGreatWeapon);
        greatWeaponSpecialRulesBlackOrc.add(specialRuleGreatWeaponStrikeLast);

        List<SpecialRule> greatWeaponSpecialRulesSwordMaster = new ArrayList<>();
        greatWeaponSpecialRulesSwordMaster.add(specialRuleGreatWeapon);
        greatWeaponSpecialRulesSwordMaster.add(specialRuleGreatWeaponStrikeLast);

        Equipment blackOrcGreatWeapon = Equipment.builder()
                .name(EquipmentEnum.GREAT_WEAPON)
                .type(EquipmentTypeEnum.TWO_HANDED)
                .specialRules(greatWeaponSpecialRulesBlackOrc)
              .build();

        Equipment blackOrcPlate = Equipment.builder()
                .name(EquipmentEnum.PLATE)
                .type(EquipmentTypeEnum.ARMOR)
                .specialRules(armorSpecialRulesBlackOrc)
                .build();

        Equipment swordMasterGreatWeapon = Equipment.builder()
                .name(EquipmentEnum.GREAT_WEAPON)
                .type(EquipmentTypeEnum.TWO_HANDED)
                .specialRules(greatWeaponSpecialRulesSwordMaster)
              .build();

        Equipment swordMasterHeavyArmor = Equipment.builder()
                .name(EquipmentEnum.HEAVY)
                .type(EquipmentTypeEnum.ARMOR)
                .specialRules(armorSpecialRulesSwordMaster)
                .build();

        List<Equipment> equipmentBlackOrc = new ArrayList<>();
        equipmentBlackOrc.add(blackOrcGreatWeapon);
        equipmentBlackOrc.add(blackOrcPlate);

        List<Equipment> equipmentSwordMaster = new ArrayList<>();
        equipmentSwordMaster.add(swordMasterGreatWeapon);
        equipmentSwordMaster.add(swordMasterHeavyArmor);

        List<SpecialRule> unitSpecialRulesBlackOrc = new ArrayList<>();
        unitSpecialRulesBlackOrc.add(specialRuleBornToFight);
        unitSpecialRulesBlackOrc.add(specialRuleHorde);
        unitSpecialRulesBlackOrc.add(specialRuleHatred);

        List<SpecialRule> unitSpecialRulesSwordMaster = new ArrayList<>();
        unitSpecialRulesSwordMaster.add(specialRuleLightningReflexesGreatWeapon);
        unitSpecialRulesSwordMaster.add(specialRuleSwordSworn);
        unitSpecialRulesSwordMaster.add(specialRuleHorde);



        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).height(UnitHeightEnum.STANDARD).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(30).armor(5).modelsPerRank(10).selection(1).standardBearer(1).reRollToHitLessThan(0).toHitBonus(0).extraRanks(0).hasMusician(true).specialRuleList(unitSpecialRulesSwordMaster).equipmentList(equipmentSwordMaster).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).height(UnitHeightEnum.STANDARD).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(4).toughness(4).initiative(3).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(12).armor(5).modelsPerRank(5).selection(2).standardBearer(1).reRollToHitLessThan(0).toHitBonus(0).extraRanks(0).hasMusician(true).specialRuleList(unitSpecialRulesBlackOrc).equipmentList(equipmentBlackOrc).build();

        Integer expectedResultLightningReflexesApplied = 1;
        Integer expectedResultSwordMasterHordeApplied = 1;
        Integer expectedResultBlackOrcHordeApplied = 0;
        Integer expectedResultBornToFightApplied = 5;
        Integer expectedResultHatredApplied = 6;

        getCombatCalculationService().applySpecialRules(unit1, true);
        getCombatCalculationService().applySpecialRules(unit2, true);

        assertThat(unit1.getToHitBonus()).isEqualTo(expectedResultLightningReflexesApplied);
        assertThat(unit1.getExtraRanks()).isEqualTo(expectedResultSwordMasterHordeApplied);

        assertThat(unit2.getExtraRanks()).isEqualTo(expectedResultBlackOrcHordeApplied);
        assertThat(unit2.getStrength()).isEqualTo(expectedResultBornToFightApplied);
        assertThat(unit2.getReRollToHitLessThan()).isEqualTo(expectedResultHatredApplied);
    }

    private CombatCalculationService getCombatCalculationService() {
        return new CombatCalculationService(new AttackQuantityService(), mockToHitService, mockToWoundService,
                mockArmorSaveService, mockWardSaveService, new CombatResolutionService(mockDiceRollingService), new SpecialRuleRoutingService());
    }
}
