package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
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

    @Test
    @DisplayName("Units Have Same Initiative")
    void validateInitiative() {
        // Arrange
        List<Round> expectedResult = new ArrayList<>();
        Round expectedRound = Round.builder().combatScoreDifferential(null).primaryWoundsDealt(4).secondaryWoundsDealt(2)
                .flee(false).caught(null).winner(Identification.SWORD_MASTER).wipedOut(true).build();
        expectedResult.add(expectedRound);
        Unit unit1 = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).baseSize(25).modelCount(5).armorSave(5).width(5).selection(1).standardBearer(1).hasMusician(true).build();
        Unit unit2 = Unit.builder().name(Identification.BLACK_ORC).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).strength(5).toughness(3).initiative(6).wounds(1).attacks(1).leadership(8).baseSize(25).modelCount(2).armorSave(5).width(5).selection(2).standardBearer(1).hasMusician(true).build();

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
                .flee(true).caught(false).winner(Identification.SWORD_MASTER).wipedOut(false).build();
        expectedResult.add(expectedRound);
        Unit unit1 = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6)
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).baseSize(25).modelCount(5).armorSave(5)
                .width(5).selection(1).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(5).mountWounds(1).build();
        Unit unit1Mount = Unit.builder().name(Identification.MOUNT).offensiveWeaponSkill(5).strength(5).initiative(6)
                .attacks(1).baseSize(25).modelCount(5).width(5).selection(1).build();
        Unit unit1Wounded = Unit.builder().name(Identification.SWORD_MASTER).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6)
                .strength(5).toughness(3).initiative(4).wounds(1).attacks(1).leadership(8).baseSize(25).modelCount(4).armorSave(5)
                .width(5).selection(1).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(5).mountWounds(1).build();

        Unit unit2 = Unit.builder().name(Identification.BLACK_ORC).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).baseSize(25).modelCount(3).armorSave(5)
                .width(5).selection(2).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(4).mountWounds(1).build();
        Unit unit2Mount = Unit.builder().name(Identification.MOUNT).offensiveWeaponSkill(4).strength(5).initiative(6)
                .attacks(1).baseSize(25).modelCount(3).width(5).selection(2).build();
        Unit unit2Wounded = Unit.builder().name(Identification.BLACK_ORC).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).baseSize(25).modelCount(2).armorSave(5)
                .width(5).selection(2).standardBearer(1).hasMusician(true)
                .mountAttacks(1).mountInitiative(6).mountMovement(9).mountStrength(5).mountWeaponSkill(4).mountWounds(1).build();
        Unit unit2CriticalWound = Unit.builder().name(Identification.BLACK_ORC).movement(5).offensiveWeaponSkill(6).defensiveWeaponSkill(6).
                strength(5).toughness(3).initiative(3).wounds(1).attacks(1).leadership(3).baseSize(25).modelCount(1).armorSave(5)
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

        // Act
        List<Round> actualRound = getCombatCalculationService().fight(unit1, unit2, false, new ArrayList<>());

        // Assert
        assertThat(actualRound).isEqualTo(expectedResult);
    }

    private CombatCalculationService getCombatCalculationService() {
        return new CombatCalculationService(new AttackQuantityService(), mockToHitService, mockToWoundService,
                mockArmorSaveService, new CombatResolutionService(mockDiceRollingService));
    }
}
