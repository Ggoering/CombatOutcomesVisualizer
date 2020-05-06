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
//@Table(name = "character_mount", schema = "public", catalog = "T9AgeCombat")
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
//    @ManyToOne
//    @JoinColumn(name = "unit_type_id", referencedColumnName = "id", nullable = false)
//    UnitType unitTypeByUnitTypeId;
//    @OneToMany(mappedBy = "characterMountByCharacterMountId")
//    Collection<CharacterMountSpecialRule> characterMountSpecialRulesById;
//    @OneToMany(mappedBy = "characterMountByCharacterMountId")
//    Collection<UnitCharacterMount> unitCharacterMountsById;
}
