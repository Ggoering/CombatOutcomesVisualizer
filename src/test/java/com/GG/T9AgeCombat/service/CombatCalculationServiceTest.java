package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.enums.SpecialRuleEnum;
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
        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armorSave(5).width(5).selection(1).standardBearer(1).hasMusician(true).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(2).armorSave(5).width(5).selection(2).standardBearer(1).hasMusician(true).build();

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
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armorSave(5)
                .width(5).selection(1).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(5).mountWounds(1).build();
        Unit unit1Mount = Unit.builder().isMount(true).offensiveWeaponSkill(5).strength(5).initiative(6)
                .attacks(1).basesize(25).modelCount(5).width(5).selection(1).build();
        Unit unit1Wounded = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6)
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(4).armorSave(5)
                .width(5).selection(1).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(5).mountWounds(1).build();

        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(3).armorSave(5)
                .width(5).selection(2).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(4).mountWounds(1).build();
        Unit unit2Mount = Unit.builder().isMount(true).offensiveWeaponSkill(4).strength(5).initiative(6)
                .attacks(1).basesize(25).modelCount(3).width(5).selection(2).build();
        Unit unit2Wounded = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(2).armorSave(5)
                .width(5).selection(2).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(4).mountWounds(1).build();
        Unit unit2CriticalWound = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).basesize(25).modelCount(1).armorSave(5)
                .width(5).selection(2).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(4).mountWounds(1).build();

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
                .toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(5).armorSave(5).width(5).selection(1)
                .standardBearer(1).wardSave(4).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5)
                .toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(2).armorSave(5).width(5).selection(2)
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
        Limitation limitationFirstRound = Limitation.builder().value(LimitationEnum.FIRST_ROUND.toString()).build();
        Modification modificationStrength = Modification.builder().value(ModificationEnum.STRENGTH.toString()).build();
        Timing timingAll = Timing.builder().value(TimingEnum.ALL.toString()).build();
        SpecialRule specialRuleBorToFight = SpecialRule.builder().limitationByLimitationId(limitationFirstRound)
                .modificationByModificationId(modificationStrength).timingByTimingId(timingAll)
                .name(SpecialRuleEnum.BORN_TO_FIGHT.toString()).value(1).build();

        Limitation limitationNone = Limitation.builder().value(LimitationEnum.NONE.toString()).build();
        Modification modificationToHit = Modification.builder().value(ModificationEnum.TO_HIT.toString()).build();
        Timing timingRoleToHit = Timing.builder().value(TimingEnum.ROLL_TO_HIT.toString()).build();
        SpecialRule specialRuleLightningReflexes = SpecialRule.builder().limitationByLimitationId(limitationNone)
                .modificationByModificationId(modificationToHit).timingByTimingId(timingRoleToHit)
                .name(SpecialRuleEnum.LIGHTNING_REFLEXES.toString()).value(1).build();

        Limitation limitationTenWide = Limitation.builder().value(LimitationEnum.TEN_WIDE.toString()).build();
        Modification modificationExtraRanks = Modification.builder().value(ModificationEnum.EXTRA_RANKS.toString()).build();
        Timing timingDetermineAttackQuantity = Timing.builder().value(TimingEnum.DETERMINE_ATTACK_QUANTITY.toString()).build();
        SpecialRule specialRuleHorde = SpecialRule.builder().limitationByLimitationId(limitationTenWide)
                .modificationByModificationId(modificationExtraRanks).timingByTimingId(timingDetermineAttackQuantity)
                .name(SpecialRuleEnum.HORDE.toString()).value(1).build();

        Modification modificationReRollToHit = Modification.builder().value(ModificationEnum.RE_ROLL_TO_HIT.toString()).build();
        Timing timingDetermineRollToHit = Timing.builder().value(TimingEnum.ROLL_TO_HIT.toString()).build();
        SpecialRule specialRuleHatred = SpecialRule.builder().limitationByLimitationId(limitationFirstRound)
                .modificationByModificationId(modificationReRollToHit).timingByTimingId(timingDetermineRollToHit)
                .name(SpecialRuleEnum.HATRED.toString()).value(6).build();

        UnitSpecialRule swordMasterLightningReflexes = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRuleLightningReflexes).build();
        UnitSpecialRule swordMasterHorde = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRuleHorde).build();
        Collection<UnitSpecialRule> swordMasterSpecialRuleList = new ArrayList<>();
        swordMasterSpecialRuleList.add(swordMasterLightningReflexes);
        swordMasterSpecialRuleList.add(swordMasterHorde);

        UnitSpecialRule blackOrcBornToFight = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRuleBorToFight).build();
        UnitSpecialRule blackOrcHorde = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRuleHorde).build();
        UnitSpecialRule blackOrcHatred = UnitSpecialRule.builder().specialRuleBySpecialRuleId(specialRuleHatred).build();
        Collection<UnitSpecialRule> unitSpecialRulesBlackOrc = new ArrayList<>();
        unitSpecialRulesBlackOrc.add(blackOrcBornToFight);
        unitSpecialRulesBlackOrc.add(blackOrcHorde);
        unitSpecialRulesBlackOrc.add(blackOrcHatred);

        Unit unit1 = Unit.builder().name("Swordmaster").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(30).armorSave(5).width(10).selection(1).standardBearer(1).reRollToHitLessThan(0).toHitBonus(0).extraRanks(0).hasMusician(true).unitSpecialRulesById(swordMasterSpecialRuleList).build();
        Unit unit2 = Unit.builder().name("Black Orc").movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(4).toughness(4).initiative(3).wounds(1).attacks(1).leadership(8).basesize(25).modelCount(12).armorSave(5).width(5).selection(2).standardBearer(1).reRollToHitLessThan(0).toHitBonus(0).extraRanks(0).hasMusician(true).unitSpecialRulesById(unitSpecialRulesBlackOrc).build();

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
