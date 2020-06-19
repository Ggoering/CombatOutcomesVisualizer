package com.GG.T9AgeCombat.equipment;

import com.GG.T9AgeCombat.enums.*;
import com.GG.T9AgeCombat.models.*;
import com.GG.T9AgeCombat.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GenericWeaponsSpecialRulesTest {
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
    @DisplayName("Applies offensive profile great weapon special rules")
    void applyGreatWeaponSpecialRules() {
//values match weapon special rule seeds
        SpecialRuleProperty specialRulePropertyGreatWeaponStrength = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.STRENGTH)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.STRENGTH_2)
                .value(2)
                .build();

        SpecialRuleProperty specialRulePropertyGreatWeaponAP = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR_PENETRATION)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.ARMOR_PENETRATION_2)
                .value(2)
                .build();

        SpecialRuleProperty specialRulePropertyGreatWeaponStrikeLast = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.AGILITY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.STRIKES_LAST)
                .value(-99)
                .build();

        SpecialRuleProperty specialRulePropertyGreatWeaponTwoHanded = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.TWO_HANDED)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.TWO_HANDED)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldParry = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.HAND_WEAPON_AND_INFANTRY)
                .modification(ModificationEnum.PARRY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.PARRY)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.LIGHT_ARMOR)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldMeleeArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.TWO_HANDED)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ROLL_ARMOR_SAVE)
                .name(SpecialRulePropertyEnum.MELEE_SHIELD)
                .value(-1)
                .build();

        List<SpecialRuleProperty> offensiveProfileSpecialRulesProperty = new ArrayList<>();
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyGreatWeaponStrength);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyGreatWeaponAP);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyGreatWeaponStrikeLast);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyGreatWeaponTwoHanded);

        List<SpecialRuleProperty> shieldSpecialRulesProperty = new ArrayList<>();
        shieldSpecialRulesProperty.add(specialRulePropertyShieldParry);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldArmorSave);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldMeleeArmorSave);


        Equipment blackOrcGreatWeapon = Equipment.builder()
                .name("Great Weapon")
                .classification(EquipmentClassificationEnum.MUNDANE)
                .type(EquipmentTypeEnum.CLOSE_COMBAT_WEAPON)
                .specialRuleProperties(offensiveProfileSpecialRulesProperty)
                .build();

        Equipment blackOrcShield = Equipment.builder()
                .name("Shield")
                .specialRuleProperties(shieldSpecialRulesProperty)
                .build();


        List<Equipment> equipmentBlackOrc = new ArrayList<>();

        List<Equipment> offensiveEquipmentBlackOrc = new ArrayList<>();
        offensiveEquipmentBlackOrc.add(blackOrcGreatWeapon);
        offensiveEquipmentBlackOrc.add(blackOrcShield);

        Integer expectedResultStrengthApplied = 6;
        Integer expectedResultArmorPenetrationApplied = 3;
        Integer expectedResultStrikesLastApplied= 0;
        Integer expectedResultShieldApplied = 1;
        Integer expectedResultMeleeShieldApplied = 0;

        OffensiveProfile blackorcOffensiveProfile = OffensiveProfile.builder()
                .name("Black Orc")
                .offensiveWeaponSkill(5)
                .armorPenetration(1)
                .strength(4)
                .agility(3)
                .attacks(1)
                .selection(2)
                .equipmentList(offensiveEquipmentBlackOrc)
                .build();

        List<OffensiveProfile> blackorcOffensiveProfileList = new ArrayList<>();
        blackorcOffensiveProfileList.add(blackorcOffensiveProfile);

        Unit unit = Unit.builder().name("Black Orc").advance(4).defensiveWeaponSkill(5)
                .toughness(4).wounds(1).leadership(8).basesize(25).modelCount(5).armor(0)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .equipmentList(equipmentBlackOrc)
                .offensiveProfileList(blackorcOffensiveProfileList).build();

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, LimitationEnum.NONE, true);
        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, null, false);

        assertThat(unit.isParry()).isFalse();
        assertThat(unit.isHasTwoHanded()).isTrue();
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultShieldApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualStrength()).isEqualTo(expectedResultStrengthApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualArmorPenetration()).isEqualTo(expectedResultArmorPenetrationApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualAgility()).isEqualTo(expectedResultStrikesLastApplied);

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ROLL_ARMOR_SAVE, null, false);
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultMeleeShieldApplied);
    }

    @Test
    @DisplayName("Applies offensive profile spear special rules")
    void applySpearSpecialRules() {
//values match weapon special rule seeds
        SpecialRuleProperty specialRulePropertySpearExtraRanks = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.EXTRA_RANKS)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.EXTRA_RANKS_1)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertySpearPermanentAP = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR_PENETRATION)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.ARMOR_PENETRATION_1)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertySpearTempAP = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.FIRST_ROUND_NOT_CHARGING)
                .modification(ModificationEnum.ARMOR_PENETRATION)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.SPEAR_ARMOR_PENETRATION)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertySpearTempAgility = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.FIRST_ROUND_NOT_CHARGING)
                .modification(ModificationEnum.AGILITY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.SPEAR_AGILITY)
                .value(2)
                .build();

        SpecialRuleProperty specialRulePropertyShieldParry = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.HAND_WEAPON_AND_INFANTRY)
                .modification(ModificationEnum.PARRY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.PARRY)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.LIGHT_ARMOR)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldMeleeArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.TWO_HANDED)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ROLL_ARMOR_SAVE)
                .name(SpecialRulePropertyEnum.MELEE_SHIELD)
                .value(-1)
                .build();

        List<SpecialRuleProperty> offensiveProfileSpecialRulesProperty = new ArrayList<>();
        offensiveProfileSpecialRulesProperty.add(specialRulePropertySpearExtraRanks);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertySpearPermanentAP);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertySpearTempAP);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertySpearTempAgility);

        List<SpecialRuleProperty> shieldSpecialRulesProperty = new ArrayList<>();
        shieldSpecialRulesProperty.add(specialRulePropertyShieldParry);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldArmorSave);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldMeleeArmorSave);


        Equipment staunchSpear = Equipment.builder()
                .name("Spear")
                .classification(EquipmentClassificationEnum.MUNDANE)
                .type(EquipmentTypeEnum.CLOSE_COMBAT_WEAPON)
                .specialRuleProperties(offensiveProfileSpecialRulesProperty)
                .build();

        Equipment shield = Equipment.builder()
                .name("Shield")
                .specialRuleProperties(shieldSpecialRulesProperty)
                .build();


        List<Equipment> equipmentStaunchSpearman = new ArrayList<>();

        List<Equipment> offensiveEquipmentStaunchSpearman = new ArrayList<>();
        offensiveEquipmentStaunchSpearman.add(staunchSpear);
        offensiveEquipmentStaunchSpearman.add(shield);

        Integer expectedResultExtraRanksApplied = 1;
        Integer expectedResultArmorPenetrationApplied = 1;
        Integer expectedResultTempAPApplied= 2;
        Integer expectedResultTempAgilityApplied= 7;
        Integer expectedResultShieldApplied = 1;

        OffensiveProfile staunchSpearmanOffensiveProfile = OffensiveProfile.builder()
                .name("Staunch Spearman")
                .offensiveWeaponSkill(4)
                .armorPenetration(0)
                .strength(3)
                .agility(5)
                .attacks(1)
                .selection(2)
                .equipmentList(offensiveEquipmentStaunchSpearman)
                .build();

        List<OffensiveProfile> staunchSpearmanOffensiveProfileList = new ArrayList<>();
        staunchSpearmanOffensiveProfileList.add(staunchSpearmanOffensiveProfile);

        Unit unit = Unit.builder().name("StaunchLineOfSpears").advance(4).defensiveWeaponSkill(5)
                .toughness(4).wounds(1).leadership(8).basesize(25).modelCount(5).armor(0)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .isCharging(false)
                .equipmentList(equipmentStaunchSpearman)
                .offensiveProfileList(staunchSpearmanOffensiveProfileList).build();

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, LimitationEnum.NONE, true);

        assertThat(unit.isParry()).isFalse();
        assertThat(unit.isHasTwoHanded()).isFalse();
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultShieldApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualArmorPenetration()).isEqualTo(expectedResultArmorPenetrationApplied);
        assertThat(unit.getExtraRanks()).isEqualTo(expectedResultExtraRanksApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualAgility()).isEqualTo(5);

        getCombatCalculationService().applySpecialRules(unit, true, TimingEnum.ALL, null, false);
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultShieldApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualAgility()).isEqualTo(expectedResultTempAgilityApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualArmorPenetration()).isEqualTo(expectedResultTempAPApplied);
    }

    @Test
    @DisplayName("Applies offensive profile halberd special rules")
    void applyHalberdSpecialRules() {
//values match weapon special rule seeds
        SpecialRuleProperty specialRulePropertyHalberdStrength = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.STRENGTH)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.STRENGTH_1)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyHalberdAP = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR_PENETRATION)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.ARMOR_PENETRATION_1)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyHalberdTwoHanded = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.TWO_HANDED)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.TWO_HANDED)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldParry = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.HAND_WEAPON_AND_INFANTRY)
                .modification(ModificationEnum.PARRY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.PARRY)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.LIGHT_ARMOR)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldMeleeArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.TWO_HANDED)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ROLL_ARMOR_SAVE)
                .name(SpecialRulePropertyEnum.MELEE_SHIELD)
                .value(-1)
                .build();

        List<SpecialRuleProperty> offensiveProfileSpecialRulesProperty = new ArrayList<>();
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyHalberdStrength);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyHalberdAP);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyHalberdTwoHanded);

        List<SpecialRuleProperty> shieldSpecialRulesProperty = new ArrayList<>();
        shieldSpecialRulesProperty.add(specialRulePropertyShieldParry);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldArmorSave);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldMeleeArmorSave);


        Equipment StateTrooperHalberd = Equipment.builder()
                .name("Halberd")
                .classification(EquipmentClassificationEnum.MUNDANE)
                .type(EquipmentTypeEnum.CLOSE_COMBAT_WEAPON)
                .specialRuleProperties(offensiveProfileSpecialRulesProperty)
                .build();

        Equipment StateTrooperShield = Equipment.builder()
                .name("Shield")
                .specialRuleProperties(shieldSpecialRulesProperty)
                .build();


        List<Equipment> equipmentStateTrooper = new ArrayList<>();

        List<Equipment> offensiveEquipmentStateTrooper = new ArrayList<>();
        offensiveEquipmentStateTrooper.add(StateTrooperHalberd);
        offensiveEquipmentStateTrooper.add(StateTrooperShield);

        Integer expectedResultStrengthApplied = 4;
        Integer expectedResultArmorPenetrationApplied = 1;
        Integer expectedResultStrikesLastApplied= 0;
        Integer expectedResultShieldApplied = 2;
        Integer expectedResultMeleeShieldApplied = 1;

        OffensiveProfile stateTrooperOffensiveProfile = OffensiveProfile.builder()
                .name("State Trooper")
                .offensiveWeaponSkill(3)
                .armorPenetration(0)
                .strength(3)
                .agility(3)
                .attacks(1)
                .selection(2)
                .equipmentList(offensiveEquipmentStateTrooper)
                .build();

        List<OffensiveProfile> stateTrooperOffensiveProfileList = new ArrayList<>();
        stateTrooperOffensiveProfileList.add(stateTrooperOffensiveProfile);

        Unit unit = Unit.builder().name("State Trooper").advance(4).defensiveWeaponSkill(3)
                .toughness(3).wounds(1).leadership(7).basesize(25).modelCount(5).armor(1)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .equipmentList(equipmentStateTrooper)
                .offensiveProfileList(stateTrooperOffensiveProfileList).build();

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, LimitationEnum.NONE, true);
        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, null, false);

        assertThat(unit.isParry()).isFalse();
        assertThat(unit.isHasTwoHanded()).isTrue();
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultShieldApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualStrength()).isEqualTo(expectedResultStrengthApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualArmorPenetration()).isEqualTo(expectedResultArmorPenetrationApplied);

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ROLL_ARMOR_SAVE, null, false);
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultMeleeShieldApplied);
    }

    @Test
    @DisplayName("Applies offensive profile paired weapons special rules")
    void applyPairedWeaponsSpecialRules() {
//values match weapon special rule seeds
        SpecialRuleProperty specialRulePropertyPairedWeaponsOWS = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.OFFENSIVE_WEAPON_SKILL)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.OFFENSIVE_WEAPON_SKILL_1)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyPairedWeaponsAttack = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ATTACKS)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.EXTRA_ATTACK_1)
                .value(1)
                .build();


        SpecialRuleProperty specialRulePropertyPairedWeaponsIgnoreParry = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.IGNORE_PARRY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.IGNORE_PARRY)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyPairedWeaponsTwoHanded = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.TWO_HANDED)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.TWO_HANDED)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldParry = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.HAND_WEAPON_AND_INFANTRY)
                .modification(ModificationEnum.PARRY)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.PARRY)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.NONE)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ALL)
                .name(SpecialRulePropertyEnum.LIGHT_ARMOR)
                .value(1)
                .build();

        SpecialRuleProperty specialRulePropertyShieldMeleeArmorSave = SpecialRuleProperty.builder()
                .limitation(LimitationEnum.TWO_HANDED)
                .modification(ModificationEnum.ARMOR)
                .timing(TimingEnum.ROLL_ARMOR_SAVE)
                .name(SpecialRulePropertyEnum.MELEE_SHIELD)
                .value(-1)
                .build();

        List<SpecialRuleProperty> offensiveProfileSpecialRulesProperty = new ArrayList<>();
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyPairedWeaponsAttack);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyPairedWeaponsOWS);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyPairedWeaponsIgnoreParry);
        offensiveProfileSpecialRulesProperty.add(specialRulePropertyPairedWeaponsTwoHanded);

        List<SpecialRuleProperty> shieldSpecialRulesProperty = new ArrayList<>();
        shieldSpecialRulesProperty.add(specialRulePropertyShieldParry);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldArmorSave);
        shieldSpecialRulesProperty.add(specialRulePropertyShieldMeleeArmorSave);


        Equipment StateTrooperPairedWeapons = Equipment.builder()
                .name("PairedWeapons")
                .classification(EquipmentClassificationEnum.MUNDANE)
                .type(EquipmentTypeEnum.CLOSE_COMBAT_WEAPON)
                .specialRuleProperties(offensiveProfileSpecialRulesProperty)
                .build();

        Equipment StateTrooperShield = Equipment.builder()
                .name("Shield")
                .specialRuleProperties(shieldSpecialRulesProperty)
                .build();


        List<Equipment> equipmentStateTrooper = new ArrayList<>();

        List<Equipment> offensiveEquipmentStateTrooper = new ArrayList<>();
        offensiveEquipmentStateTrooper.add(StateTrooperPairedWeapons);
        offensiveEquipmentStateTrooper.add(StateTrooperShield);

        Integer expectedResultOWSApplied = 4;
        Integer expectedResultAttacksApplied = 2;
        Integer expectedResultShieldApplied = 2;
        Integer expectedResultMeleeShieldApplied = 1;

        OffensiveProfile stateTrooperOffensiveProfile = OffensiveProfile.builder()
                .name("State Trooper")
                .offensiveWeaponSkill(3)
                .armorPenetration(0)
                .strength(3)
                .agility(3)
                .attacks(1)
                .selection(2)
                .hasIgnoreParry(false)
                .equipmentList(offensiveEquipmentStateTrooper)
                .build();

        List<OffensiveProfile> stateTrooperOffensiveProfileList = new ArrayList<>();
        stateTrooperOffensiveProfileList.add(stateTrooperOffensiveProfile);

        Unit unit = Unit.builder().name("State Trooper").advance(4).defensiveWeaponSkill(3)
                .toughness(3).wounds(1).leadership(7).basesize(25).modelCount(5).armor(1)
                .modelsPerRank(5).selection(1).standardBearer(1).hasMusician(true).selection(1)
                .equipmentList(equipmentStateTrooper)
                .offensiveProfileList(stateTrooperOffensiveProfileList).build();

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, LimitationEnum.NONE, true);
        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ALL, null, false);

        assertThat(unit.isParry()).isFalse();
        assertThat(unit.getOffensiveProfileList().get(0).isHasIgnoreParry()).isTrue();
        assertThat(unit.isHasTwoHanded()).isTrue();
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultShieldApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualOffensiveWeaponSkill()).isEqualTo(expectedResultOWSApplied);
        assertThat(unit.getOffensiveProfileList().get(0).getActualAttacks()).isEqualTo(expectedResultAttacksApplied);

        getCombatCalculationService().applySpecialRules(unit, false, TimingEnum.ROLL_ARMOR_SAVE, null, false);
        assertThat(unit.getActualArmor()).isEqualTo(expectedResultMeleeShieldApplied);
    }

    private CombatCalculationService getCombatCalculationService() {
        return new CombatCalculationService(new AttackQuantityService(), mockToHitService, mockToWoundService,
                mockArmorSaveService, mockWardSaveService, new CombatResolutionService(mockDiceRollingService), new SpecialRuleRoutingService(mockDiceRollingService));
    }
}
