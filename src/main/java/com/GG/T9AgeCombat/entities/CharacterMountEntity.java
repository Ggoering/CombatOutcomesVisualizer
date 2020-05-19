package com.GG.T9AgeCombat.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CharacterMountEntity {
    @Id
    long id;
    Integer movement;
    Integer leadership;
    int wounds;
    Integer defensiveWeaponSkill;
    Integer toughness;
    Integer armor;
    int initiative;
    Integer offensiveWeaponSkill;
    Integer attacks;
    Integer strength;
    Integer armorPenetration;
    int basesize;
    String name;
}
