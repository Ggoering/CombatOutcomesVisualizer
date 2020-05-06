package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@JsonInclude(NON_NULL)
public class Unit {
    public static final int SINGLE_WOUND_MODEL = 1;
    public static final Integer DEFAULT_REROLL_LESS_THAN = 0;
    public static final Integer DEFAULT_REROLL_GREATER_THAN = 99;

    long id;
    String name;
    int movement;
    int leadership;
    int wounds;
    int defensiveWeaponSkill;
    int toughness;
    int armor;
    int initiative;
    int offensiveWeaponSkill;
    int attacks;
    @NonFinal
    int strength;
    int armorPenetration;
    int mountInitiative;
    int mountOffensiveWeaponSkill;
    int mountAttacks;
    int mountStrength;
    int mountArmorPenetration;
    int basesize;
    @NonFinal
    boolean isMounted;
    Boolean canHaveMusician;
    Boolean canHaveStandard;
    int equipmentPointLimit;
    UnitHeightEnum height;
    List<SpecialRule> specialRuleList;
    List<Equipment> equipmentList;

    int standardBearer;
    boolean hasMusician;
    boolean isMount;
    @NonFinal
    Integer selection;
    @NonFinal
    int modelCount;
    @NonFinal
    int modelsPerRank;
    @NonFinal
    int pendingWounds;
    @NonFinal
    int woundTracker;
    @NonFinal
    @Builder.Default
    Integer reRollToHitLessThan = DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    Integer reRollToHitGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    @Builder.Default
    Integer reRollToWoundLessThan = DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    Integer reRollToWoundGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    @Builder.Default
    Integer reRollArmorSaveLessThan = DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    Integer reRollArmorSaveGreaterThan = DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    Boolean hasReRollLeadership;
    @NonFinal
    int toHitBonus;
    @NonFinal
    Integer extraRanks;

    // Stat modifiers
    @NonFinal
    int movementModifier;
    @NonFinal
    int leadershipModifier;
    @NonFinal
    int woundsModifier;
    @NonFinal
    int defensiveWeaponSkillModifier;
    @NonFinal
    int toughnessModifier;
    @NonFinal
    int armorModifier;
    @NonFinal
    int initiativeModifier;
    @NonFinal
    int offensiveWeaponSkillModifier;
    @NonFinal
    int attacksModifier;
    @NonFinal
    int strengthModifier;
    @NonFinal
    int armorPenetrationModifier;
    @NonFinal
    int wardSave;
    @NonFinal
    int toHitBonusModifier;

    public int getRankBonus() {
        return modelCount / modelsPerRank;
    }

    public int getWidth() {
        return modelCount >= modelsPerRank ? modelsPerRank * basesize : modelCount * basesize;
    }

    public void setPendingWounds(int pendingWounds) {
        this.pendingWounds += pendingWounds;
    }

    public void applyPendingWounds() {
        if (wounds == SINGLE_WOUND_MODEL) {
            modelCount = (pendingWounds < modelCount ? modelCount - pendingWounds : 0);
        } else { // Unit has multiple wounds
            // Have wounds from previous combat
            if (woundTracker > 0) {
                // Check to see if there are enough pending wounds to kill the wounded model
                if (woundTracker + pendingWounds >= wounds) {
                    // Subtract from the pending wounds the minimum number to kill the wounded model
                    pendingWounds -= wounds - woundTracker;
                    modelCount--;
                    adjustModelCountFromPendingWounds();
                } else {
                    woundTracker += pendingWounds;
                }
            } else {
                adjustModelCountFromPendingWounds();
            }
        }

        pendingWounds = 0;
    }

    private void adjustModelCountFromPendingWounds() {
        woundTracker = pendingWounds % (wounds + woundsModifier);
        int modelsKilled = (pendingWounds - woundTracker) / (wounds + woundsModifier);
        modelCount = (modelsKilled > modelCount ? 0 : modelCount - modelsKilled);

        if (modelCount == 0) {
            woundTracker = 0;
        }
    }

    public void updateReRollToHit(Integer reRollToHit) {
        this.reRollToHitLessThan = reRollToHit;
    }

    public void updateToHitBonus(Integer toHitBonus) {
        this.toHitBonus = this.toHitBonus + toHitBonus;
    }

    public void updateExtraRank(Integer extraRank) {
        this.extraRanks = this.extraRanks + extraRank;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public void updateStrength(int strength) {
        this.strength += strength;
    }

    public void updateMovementModifier(int movementModifier) {
        this.movementModifier += movementModifier;
    }

    public void updateLeadershipModifier(int leadershipModifier) {
        this.leadershipModifier += leadershipModifier;
    }

    public void updateWoundsModifier(int woundsModifier) {
        this.woundsModifier += woundsModifier;
    }

    public void updateDefensiveWeaponSkillModifier(int defensiveWeaponSkillModifier) {
        this.defensiveWeaponSkillModifier += defensiveWeaponSkillModifier;
    }

    public void updateToughnessModifier(int toughnessModifier) {
        this.toughnessModifier += toughnessModifier;
    }

    public void updateArmorModifier(int armorModifier) {
        this.armorModifier += armorModifier;
    }

    public void updateInitiativeModifier(int initiativeModifier) {
        this.initiativeModifier += initiativeModifier;
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

    public void setWardSave(int wardSave) {
        this.wardSave = wardSave;
    }

    public void resetStatModifiers() {
        movementModifier = 0;
        leadershipModifier = 0;
        woundsModifier = 0;
        defensiveWeaponSkillModifier = 0;
        toughnessModifier = 0;
        armorModifier = 0;
        initiativeModifier = 0;
        offensiveWeaponSkillModifier = 0;
        attacksModifier = 0;
        strengthModifier = 0;
        armorPenetrationModifier = 0;
        toHitBonusModifier = 0;
    }

    public int getActualMovement() {
        return movement + movementModifier;
    }

    public int getActualLeadership() {
        return leadership + leadershipModifier;
    }

    public int getActualWounds() {
        return wounds + woundsModifier;
    }

    public int getActualDefensiveWeaponSkill() {
        return defensiveWeaponSkill + defensiveWeaponSkillModifier;
    }

    public int getActualToughness() {
        return toughness + toughnessModifier;
    }

    public int getActualArmor() {
        return armor + armorModifier;
    }

    public int getActualInitiative() {
        return initiative + initiativeModifier;
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
}
