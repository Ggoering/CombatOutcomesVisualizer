package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.entities.SpecialRuleEntity;
import com.GG.T9AgeCombat.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.stream.Collectors.toList;

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
    @NonFinal
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

    Integer wardSave;
    Integer standardBearer;
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
    Integer toHitBonus;
    @NonFinal
    Integer extraRanks;

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
        woundTracker = pendingWounds % wounds;
        int modelsKilled = (pendingWounds - woundTracker) / wounds;
        modelCount = (modelsKilled > modelCount ? 0 : modelCount - modelsKilled);

        if (modelCount == 0) {
            woundTracker = 0;
        }
    }

    public void updateStrength(Integer strength) {
        this.strength += strength;
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
}
