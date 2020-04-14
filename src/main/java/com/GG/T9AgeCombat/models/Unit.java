package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.Equipment;
import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.enums.SpecialRules;
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
    Integer attacks;
    Integer leadership;
    Integer baseSize;
    @NonFinal
    Integer modelCount;
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
    Integer musician;
    List<SpecialRules> specialRulesList;
    List<Equipment> equipmentList;

    public int compareTo(Unit compareUnits) {
        return this.initiative - compareUnits.getInitiative();
    }

    public void updateCount(Integer wounds) {
        this.modelCount = wounds >= this.getModelCount() ? 0 : this.getModelCount() - wounds;
        Integer newCount = wounds >= this.getModelCount() ? 0 : this.getModelCount() - wounds;
        this.modelCount = newCount;

    } public void updateStrength(Integer strength) {
        Integer newStrength = strength + this.strength;
        this.strength = newStrength;
    }
}
