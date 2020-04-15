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
