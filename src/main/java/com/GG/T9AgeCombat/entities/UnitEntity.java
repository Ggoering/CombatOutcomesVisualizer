package com.GG.T9AgeCombat.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@Entity
@JsonInclude(NON_NULL)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UnitEntity {
    @Id
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
    int strength;
    int armorPenetration;
    int mountInitiative;
    int mountOffensiveWeaponSkill;
    int mountAttacks;
    int mountStrength;
    int mountArmorPenetration;
    int basesize;
    Boolean canHaveMusician;
    Boolean canHaveStandard;
    boolean isMounted;
    int equipmentPointLimit;
    String unitHeight;
    String faction;
    String unitType;
}