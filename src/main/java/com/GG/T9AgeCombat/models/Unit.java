package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.common.Constants;
import com.GG.T9AgeCombat.enums.UnitHeightEnum;
import com.GG.T9AgeCombat.enums.UnitTypeEnum;
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
public class Unit {
    public static final int SINGLE_WOUND_MODEL = 1;

    long id;
    String faction;
    UnitHeightEnum height;
    UnitTypeEnum type;
    String name;
    int advance;
    int march;
    int leadership;
    int wounds;
    @NonFinal
    int defensiveWeaponSkill;
    @NonFinal
    int toughness;
    @NonFinal
    int armor;
    int basesize;
    boolean canHaveMusician;
    @NonFinal
    boolean hasMusician;
    boolean canHaveStandard;
    @NonFinal
    int standardBearer;
    @NonFinal
    int pointCost;
    int extraModelPointCost;
    int defaultModelCount;
    int maximumModelCount;
    @NonFinal
    int selection;
    @NonFinal
    int modelCount;
    @NonFinal
    int modelsPerRank;
    @NonFinal
    int pendingWounds;
    @NonFinal
    int woundTracker;
    @NonFinal
    boolean hasReRollLeadership;
    @NonFinal
    @Builder.Default
    int reRollArmorSaveLessThan = Constants.DEFAULT_REROLL_LESS_THAN;
    @NonFinal
    @Builder.Default
    int reRollArmorSaveGreaterThan = Constants.DEFAULT_REROLL_GREATER_THAN;
    @NonFinal
    int extraRanks;
    @NonFinal
    @Builder.Default
    List<SpecialRuleProperty> specialRulePropertyList = new ArrayList<>();
    @Builder.Default
    List<Equipment> equipmentList = new ArrayList<>();
    @Builder.Default
    List<OffensiveProfile> offensiveProfileList = new ArrayList<>();
    @Builder.Default
    @NonFinal
    boolean isCharging = false;
    @Builder.Default
    @NonFinal
    boolean hasTwoHanded = false;
    @Builder.Default
    @NonFinal
    boolean parry = false;
    // Stat modifiers
    @NonFinal
    int advanceModifier;
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
    int wardSave;

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

    public void updateExtraRank(Integer extraRank) {
        this.extraRanks = this.extraRanks + extraRank;
    }

    public void setSelection(int selection) {
        this.selection = selection;

        for (OffensiveProfile offensiveProfile : offensiveProfileList) {
            offensiveProfile.setSelection(selection);
        }
    }

    public void updateArmor(int armor) {
        this.armor += armor;
    }

    public void updateParry (boolean parry) {this.parry = parry; }

    public void updateTwoHanded (boolean twoHanded) {this.hasTwoHanded = twoHanded; }

    public void updateAdvanceModifier(int advanceModifier) {
        this.advanceModifier = advanceModifier;
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

    public void setWardSave(int wardSave) {
        this.wardSave = wardSave;
    }

    public void resetStatModifiers() {
        advanceModifier = 0;
        leadershipModifier = 0;
        woundsModifier = 0;
        defensiveWeaponSkillModifier = 0;
        toughnessModifier = 0;
        armorModifier = 0;

        for (OffensiveProfile offensiveProfile : offensiveProfileList) {
            offensiveProfile.resetStatModifiers();
        }
    }

    public int getActualAdvance() {
        return advance + advanceModifier;
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

    public void addEquipmentSpecialRules() {
        for (Equipment equipment : equipmentList) {
            if (equipment.isEquipped()) {
                specialRulePropertyList.addAll(equipment.getSpecialRuleProperties());
            }
        }

        for (OffensiveProfile offensiveProfile : offensiveProfileList) {
            offensiveProfile.addEquipmentSpecialRules();
        }
    }
}
