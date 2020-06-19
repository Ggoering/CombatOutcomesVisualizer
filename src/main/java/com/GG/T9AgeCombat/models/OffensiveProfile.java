package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.common.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonInclude(NON_NULL)
public class OffensiveProfile {
    String name;
    @NonFinal
    int agility;
    @NonFinal
    int offensiveWeaponSkill;
    @NonFinal
    int attacks;
    @NonFinal
    int strength;
    @NonFinal
    int armorPenetration;
    @NonFinal
    @Builder.Default
    List<SpecialRuleProperty> specialRulePropertyList = new ArrayList<>();
    @Builder.Default
    List<Equipment> equipmentList = new ArrayList<>();

    @NonFinal
    int agilityModifier;
    @NonFinal
    int offensiveWeaponSkillModifier;
    @NonFinal
    int attacksModifier;
    @NonFinal
    int strengthModifier;
    @NonFinal
    int armorPenetrationModifier;
    @NonFinal
    int toHitBonusModifier;
    @NonFinal
    @Builder.Default
    int reRollToHitLessThan = Constants.DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    int reRollToHitGreaterThan = Constants.DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    @Builder.Default
    int reRollToWoundLessThan = Constants.DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    int reRollToWoundGreaterThan = Constants.DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    boolean hasReRollLeadership;
    @NonFinal
    int toHitBonus;
    @NonFinal
    @Builder.Default
    boolean isMount = false;
    @NonFinal
    int selection;
    @NonFinal
    boolean hasMagicalAttacks;
    @NonFinal
    boolean hasIgnoreParry;

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setAttacks(int attacks) {
        this.attacks = attacks;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void setHasMagicalAttacks(boolean hasMagicalAttacks) {
        this.hasMagicalAttacks = hasMagicalAttacks;
    }

    public void setMount(boolean mount) {
        isMount = mount;
    }

    public void updateAttacks(int attacks) {
        this.attacks += attacks;
    }

    public void updateOffensiveWeaponSkill(int offensiveWeaponSkill) {
        this.offensiveWeaponSkill += offensiveWeaponSkill;
    }

    public void updateStrength(int strength) {
        this.strength += strength;
    }

    public void updateIgnoreParry(boolean ignoreParry) {this.hasIgnoreParry = ignoreParry; }

    public void updateArmorPenetration(int armorPenetration) {
        this.armorPenetration += armorPenetration;
    }

    public void updateReRollToHit(int reRollToHit) {
        this.reRollToHitLessThan += reRollToHit;
    }

    public void updateToHitBonus(int toHitBonus) {
        this.toHitBonus += toHitBonus;
    }

    public void updateAgilityModifier(int agilityModifier) {
        this.agilityModifier += agilityModifier;
    }

    public void updateOffensiveWeaponSkillModifier(int offensiveWeaponSkillModifier) {
        this.offensiveWeaponSkillModifier += offensiveWeaponSkillModifier;
    }

    public void updateAttacksModifier(int attacksModifier) {
        this.attacksModifier += attacksModifier;
    }

    public void updateStrengthModifier(int strengthModifier) {
        this.strengthModifier += strengthModifier;
    }

    public void updateArmorPenetrationModifier(int armorPenetrationModifier) {
        this.armorPenetrationModifier += armorPenetrationModifier;
    }

    public void updateToHitBonusModifier(int toHitBonusModifier) {
        this.toHitBonusModifier += toHitBonusModifier;
    }

    public void resetStatModifiers() {
        agilityModifier = 0;
        offensiveWeaponSkillModifier = 0;
        attacksModifier = 0;
        strengthModifier = 0;
        armorPenetrationModifier = 0;
        toHitBonusModifier = 0;
    }

    public int getActualAgility() {
        return agility + agilityModifier > 0 ? agility + agilityModifier : 0;
    }

    public int getActualOffensiveWeaponSkill() {
        return offensiveWeaponSkill + offensiveWeaponSkillModifier;
    }

    public int getActualAttacks() {
        return attacks + attacksModifier;
    }

    public int getActualStrength() {
        return strength + strengthModifier;
    }

    public int getActualArmorPenetration() {
        return armorPenetration + armorPenetrationModifier;
    }

    public int getActualToHitBonus() {
        return toHitBonus + toHitBonusModifier;
    }

    public void addEquipmentSpecialRules() {
        for (Equipment equipment : equipmentList) {
            if (equipment.isEquipped()) {
                specialRulePropertyList.addAll(equipment.getSpecialRuleProperties());
            }
        }
    }
}
