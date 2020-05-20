package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.*;
import com.GG.T9AgeCombat.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

        OffensiveProfile swordmasterOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .selection(1)
                .build();

        List<OffensiveProfile> swordmasterOffensiveProfileList = new ArrayList<>();
        swordmasterOffensiveProfileList.add(swordmasterOffensiveProfile);

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .selection(2)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);
        
        Unit unit1 = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6).toughness(3).wounds(1).leadership(8)
                .basesize(25).modelCount(5).armor(5).modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true)
                .offensiveProfileList(swordmasterOffensiveProfileList).selection(1).build();
        Unit unit2 = Unit.builder().name("Black Orc").advance(5).defensiveWeaponSkill(6).toughness(3).wounds(1).leadership(8)
                .basesize(25).modelCount(2).armor(5).modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true)
                .offensiveProfileList(blackorcOffensiveProfileList).selection(2).build();

        // Attacker is unit1
        when(mockToHitService.rollToHit(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);
        when(mockToWoundService.rollToWound(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);
        when(mockArmorSaveService.rollArmorSaves(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);

        // Attacker is unit2
        when(mockToHitService.rollToHit(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);
        when(mockToWoundService.rollToWound(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);
        when(mockArmorSaveService.rollArmorSaves(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);

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

        OffensiveProfile swordmasterOffensiveProfile = OffensiveProfile.builder()
                .name("Swordmaster")
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(4)
                .attacks(1)
                .selection(1)
                .build();

        OffensiveProfile swordmasterMountOffensiveProfile = OffensiveProfile.builder()
                .name("Swordmaster Mount")
                .offensiveWeaponSkill(5)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .isMount(true)
                .selection(1)
                .build();

        List<OffensiveProfile> swordmasterOffensiveProfileList = new ArrayList<>();
        swordmasterOffensiveProfileList.add(swordmasterOffensiveProfile);
        swordmasterOffensiveProfileList.add(swordmasterMountOffensiveProfile);

        Unit unit1 = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(8).basesize(25).modelCount(5).armor(5)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .offensiveProfileList(swordmasterOffensiveProfileList).build();
        Unit unit1Wounded = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(8).basesize(25).modelCount(4).armor(5)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .offensiveProfileList(swordmasterOffensiveProfileList).build();

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .name("Black Orc")
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(3)
                .attacks(1)
                .selection(2)
                .build();

        OffensiveProfile blackorcMountOffensiveProfile = OffensiveProfile.builder()
                .name("Black Orc Mount")
                .offensiveWeaponSkill(4)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .isMount(true)
                .selection(2)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);
        blackorcOffensiveProfileList.add(blackorcMountOffensiveProfile);

        Unit unit2 = Unit.builder().name("Black Orc").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(3).basesize(25).modelCount(3).armor(5)
                .modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).selection(2)
                .offensiveProfileList(blackorcOffensiveProfileList).build();
        Unit unit2Wounded = Unit.builder().name("Black Orc").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(3).basesize(25).modelCount(2).armor(5)
                .modelsPerRank(5).selection(2).standardBearer(1).hasMusician(true).selection(2)
                .offensiveProfileList(blackorcOffensiveProfileList).build();

        // Unit1 mount
        when(mockToHitService.rollToHit(swordmasterMountOffensiveProfile, unit2, 5)).thenReturn(1);
        when(mockToWoundService.rollToWound(swordmasterMountOffensiveProfile, unit2, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(swordmasterMountOffensiveProfile, unit2, 1)).thenReturn(1);

        // Unit2 mount
        when(mockToHitService.rollToHit(blackorcMountOffensiveProfile, unit1, 3)).thenReturn(1);
        when(mockToWoundService.rollToWound(blackorcMountOffensiveProfile, unit1, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(blackorcMountOffensiveProfile, unit1, 1)).thenReturn(1);

        // Unit1 riders
        when(mockToHitService.rollToHit(swordmasterOffensiveProfile, unit2Wounded, 4)).thenReturn(1);
        when(mockToWoundService.rollToWound(swordmasterOffensiveProfile, unit2Wounded, 1)).thenReturn(1);
        when(mockArmorSaveService.rollArmorSaves(swordmasterOffensiveProfile, unit2Wounded, 1)).thenReturn(1);

        // Unit2 riders
        when(mockToHitService.rollToHit(blackorcOffensiveProfile, unit1Wounded, 1)).thenReturn(0);
        when(mockToWoundService.rollToWound(blackorcOffensiveProfile, unit1Wounded, 0)).thenReturn(0);

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

        OffensiveProfile swordmasterOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .selection(1)
                .build();

        List<OffensiveProfile> swordmasterOffensiveProfileList = new ArrayList<>();
        swordmasterOffensiveProfileList.add(swordmasterOffensiveProfile);

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(1)
                .selection(2)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);

        Unit unit1 = Unit.builder().name("Swordmaster").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(8).basesize(25).modelCount(5).armor(5).modelsPerRank(5).selection(1)
                .standardBearer(1).wardSave(4).offensiveProfileList(swordmasterOffensiveProfileList).selection(1).build();
        Unit unit2 = Unit.builder().name("Black Orc").advance(5).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(8).basesize(25).modelCount(2).armor(5).modelsPerRank(5).selection(2)
                .standardBearer(1).hasMusician(true).wardSave(6).offensiveProfileList(blackorcOffensiveProfileList).selection(2).build();

        // Attacker is unit1
        when(mockToHitService.rollToHit(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);
        when(mockToWoundService.rollToWound(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);
        when(mockArmorSaveService.rollArmorSaves(swordmasterOffensiveProfile, unit2, 4)).thenReturn(4);
        when(mockWardSaveService.rollWardSaves(unit1, 2)).thenReturn(1);

        // Attacker is unit2
        when(mockToHitService.rollToHit(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);
        when(mockToWoundService.rollToWound(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);
        when(mockArmorSaveService.rollArmorSaves(blackorcOffensiveProfile, unit1, 2)).thenReturn(2);
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
        SpecialRuleProperty specialRulePropertyHatred = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.FIRST_ROUND)
                .modification(ModificationEnum.RE_ROLL_TO_HIT)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.HATRED)
                .value(6)
                .build();

        SpecialRuleProperty specialRulePropertyHorde = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.EIGHT_WIDE)
                .modification(ModificationEnum.EXTRA_RANKS)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.HORDE)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertySwordSworn = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.TO_HIT)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.SWORD_SWORN)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyLightningReflexesGreatWeapon = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.LIGHTNING_REFLEXES)
                .modification(ModificationEnum.INITIATIVE)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.LIGHTNING_REFLEXES_GREAT_WEAPON)
                .value(0)
                .build();

        SpecialRuleProperty specialRulePropertyGreatWeaponStrikeLast = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.LIGHTNING_REFLEXES)
                .modification(ModificationEnum.INITIATIVE)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.STRIKES_LAST)
                .value(-10)
                .build();

        SpecialRuleProperty specialRulePropertyGreatWeapon = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.STRENGTH)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.GREAT_WEAPON_STRENGTH)
                .value(2)
                .build();

        SpecialRuleProperty specialRulePropertyBornToFight = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.FIRST_ROUND)
                .modification(ModificationEnum.STRENGTH)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.BORN_TO_FIGHT)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyHeavyArmor = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.HEAVY_ARMOR)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyPlateArmor = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.HEAVY_ARMOR)
                .value(1)
                .build();

        List<SpecialRuleProperty> armorSpecialRulesBlackOrcProperty = new ArrayList<>();
        armorSpecialRulesBlackOrcProperty.add(specialRulePropertyPlateArmor);

        List<SpecialRuleProperty> armorSpecialRulesSwordMasterProperty = new ArrayList<>();
        armorSpecialRulesSwordMasterProperty.add(specialRulePropertyHeavyArmor);

        List<SpecialRuleProperty> greatWeaponSpecialRulesBlackOrcProperty = new ArrayList<>();
        greatWeaponSpecialRulesBlackOrcProperty.add(specialRulePropertyGreatWeapon);
        greatWeaponSpecialRulesBlackOrcProperty.add(specialRulePropertyGreatWeaponStrikeLast);

        List<SpecialRuleProperty> greatWeaponSpecialRulesSwordMasterProperty = new ArrayList<>();
        greatWeaponSpecialRulesSwordMasterProperty.add(specialRulePropertyGreatWeapon);
        greatWeaponSpecialRulesSwordMasterProperty.add(specialRulePropertyGreatWeaponStrikeLast);

        Equipment blackOrcGreatWeapon = Equipment.builder()
                .name("Great Weapon")
                .specialRuleProperties(greatWeaponSpecialRulesBlackOrcProperty)
                .build();

        Equipment blackOrcPlate = Equipment.builder()
                .name("Plate Armor")
                .specialRuleProperties(armorSpecialRulesBlackOrcProperty)
                .build();

        Equipment swordMasterGreatWeapon = Equipment.builder()
                .name("Great Weapon")
                .specialRuleProperties(greatWeaponSpecialRulesSwordMasterProperty)
                .build();

        Equipment swordMasterHeavyArmor = Equipment.builder()
                .name("Heavy Armor")
                .specialRuleProperties(armorSpecialRulesSwordMasterProperty)
                .build();

        List<Equipment> equipmentBlackOrc = new ArrayList<>();
        equipmentBlackOrc.add(blackOrcPlate);

        List<Equipment> offensiveEquipmentBlackOrc = new ArrayList<>();
        offensiveEquipmentBlackOrc.add(blackOrcGreatWeapon);

        List<Equipment> equipmentSwordMaster = new ArrayList<>();
        equipmentSwordMaster.add(swordMasterHeavyArmor);

        List<Equipment> offensiveEquipmentSwordMaster = new ArrayList<>();
        offensiveEquipmentSwordMaster.add(swordMasterGreatWeapon);

        List<SpecialRuleProperty> unitSpecialRulesBlackOrcProperty = new ArrayList<>();
        unitSpecialRulesBlackOrcProperty.add(specialRulePropertyHorde);

        List<SpecialRuleProperty> unitSpecialRulesSwordMasterProperty = new ArrayList<>();
        unitSpecialRulesSwordMasterProperty.add(specialRulePropertyHorde);

        List<SpecialRuleProperty> offensiveSpecialRulesBlackOrcProperty = new ArrayList<>();
        offensiveSpecialRulesBlackOrcProperty.add(specialRulePropertyBornToFight);
        offensiveSpecialRulesBlackOrcProperty.add(specialRulePropertyHatred);

        List<SpecialRuleProperty> offensiveSpecialRulesSwordMasterProperty = new ArrayList<>();
        offensiveSpecialRulesSwordMasterProperty.add(specialRulePropertyLightningReflexesGreatWeapon);
        offensiveSpecialRulesSwordMasterProperty.add(specialRulePropertySwordSworn);

        OffensiveProfile swordmasterOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(6)
                .strength(5)
                .initiative(6)
                .attacks(2)
                .specialRulePropertyList(offensiveSpecialRulesSwordMasterProperty)
                .equipmentList(offensiveEquipmentSwordMaster)
                .selection(1)
                .build();

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .offensiveWeaponSkill(5)
                .strength(4)
                .initiative(3)
                .attacks(1)
                .specialRulePropertyList(offensiveSpecialRulesBlackOrcProperty)
                .equipmentList(offensiveEquipmentBlackOrc)
                .selection(2)
                .build();

        List<OffensiveProfile> swordmasterOffensiveProfileList = new ArrayList<>();
        swordmasterOffensiveProfileList.add(swordmasterOffensiveProfile);

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);

        Unit unit1 = Unit.builder().name("Swordmaster").advance(5).height(UnitHeightEnum.STANDARD).defensiveWeaponSkill(6)
                .toughness(3).wounds(1).leadership(8).basesize(25).modelCount(30).armor(5).modelsPerRank(10).selection(1)
                .standardBearer(1).extraRanks(0).hasMusician(true)
                .specialRulePropertyList(unitSpecialRulesSwordMasterProperty)
                .equipmentList(equipmentSwordMaster)
                .offensiveProfileList(swordmasterOffensiveProfileList).selection(1).build();
        Unit unit2 = Unit.builder().name("Black Orc").advance(5).height(UnitHeightEnum.STANDARD).defensiveWeaponSkill(6)
                .toughness(4).wounds(1).leadership(8).basesize(25).modelCount(12).armor(5).modelsPerRank(5).selection(2)
                .standardBearer(1).extraRanks(0).hasMusician(true)
                .specialRulePropertyList(unitSpecialRulesBlackOrcProperty)
                .equipmentList(equipmentBlackOrc)
                .offensiveProfileList(blackorcOffensiveProfileList).selection(2).build();

        Integer expectedResultLightningReflexesApplied = 1;
        Integer expectedResultSwordMasterHordeApplied = 1;
        Integer expectedResultBlackOrcHordeApplied = 0;
        Integer expectedResultBornToFightApplied = 5;
        Integer expectedResultHatredApplied = 6;

        getCombatCalculationService().applyPermanentSpecialRules(unit1);
        getCombatCalculationService().applyPermanentSpecialRules(unit2);
        getCombatCalculationService().applyTemporarySpecialRules(unit1, true);
        getCombatCalculationService().applyTemporarySpecialRules(unit2, true);

        assertThat(unit1.getOffensiveProfileList().get(0).getActualToHitBonus()).isEqualTo(expectedResultLightningReflexesApplied);
        assertThat(unit1.getExtraRanks()).isEqualTo(expectedResultSwordMasterHordeApplied);

        assertThat(unit2.getExtraRanks()).isEqualTo(expectedResultBlackOrcHordeApplied);
        assertThat(unit2.getOffensiveProfileList().get(0).getActualStrength()).isEqualTo(expectedResultBornToFightApplied);
        assertThat(unit2.getOffensiveProfileList().get(0).getReRollToHitLessThan()).isEqualTo(expectedResultHatredApplied);
    }

    private CombatCalculationService getCombatCalculationService() {
        return new CombatCalculationService(new AttackQuantityService(), mockToHitService, mockToWoundService,
                mockArmorSaveService, mockWardSaveService, new CombatResolutionService(mockDiceRollingService), new SpecialRuleRoutingService());
    }
}
