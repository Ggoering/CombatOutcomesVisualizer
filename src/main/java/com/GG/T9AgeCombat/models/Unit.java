package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.Equipment;
import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.enums.SpecialRule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Value
@JsonInclude(NON_NULL)
public class Unit implements Comparable<Unit> {
    public static final Integer DEFAULT_REROLL_LESS_THAN = 0;
    public static final Integer DEFAULT_REROLL_GREATER_THAN = 99;
    Identification name;
    Integer movement;
    Integer offensiveWeaponSkill;
    Integer defensiveWeaponSkill;
    @NonFinal
    Integer strength;
    Integer toughness;
    Integer initiative;
    Integer wounds;
    @NonFinal
    int pendingWounds;
    Integer attacks;
    Integer leadership;
    Integer baseSize;
    @NonFinal
    int modelCount;
    Integer armorSave;
    Integer wardSave;
    Integer mountWeaponSkill;
    Integer mountStrength;
    Integer mountAttacks;
    Integer mountWounds;
    Integer mountInitiative;
    Integer mountMovement;
    Integer width;
    Integer selection;
    Integer standardBearer;
    boolean hasMusician;
    Integer musician;
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
    List<SpecialRule> specialRuleList;
    List<Equipment> equipmentList;

    public void setPendingWounds(int pendingWounds) {
        this.pendingWounds += pendingWounds;
    }

    public int compareTo(Unit compareUnits) {
        return initiative - compareUnits.getInitiative();
    }

    public void applyPendingWounds() {
        modelCount = (pendingWounds < modelCount ? modelCount - pendingWounds : 0);
        pendingWounds = 0;
    }

    public void updateStrength(Integer strength) {
        this.strength += strength;
    }

    public int getRankBonus() {
        return modelCount / width;
    }

    public int getActualWidth() {
        return modelCount >= width ? width * baseSize : modelCount * baseSize;
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

    public Unit createCopy() {
        return Unit.builder()
                .name(name)
                .movement(movement)
                .offensiveWeaponSkill(offensiveWeaponSkill)
                .defensiveWeaponSkill(defensiveWeaponSkill)
                .strength(strength)
                .toughness(toughness)
                .initiative(initiative)
                .wounds(wounds)
                .attacks(attacks)
                .leadership(leadership)
                .baseSize(baseSize)
                .modelCount(modelCount)
                .armorSave(armorSave)
                .wardSave(wardSave)
                .mountWeaponSkill(mountWeaponSkill)
                .mountStrength(mountStrength)
                .mountAttacks(mountAttacks)
                .mountWounds(mountWounds)
                .mountInitiative(mountInitiative)
                .mountMovement(mountMovement)
                .width(width)
                .selection(selection)
                .standardBearer(standardBearer)
                .hasMusician(hasMusician)
                .specialRuleList(specialRuleList)
                .equipmentList(equipmentList)
                .build();
    }
}
