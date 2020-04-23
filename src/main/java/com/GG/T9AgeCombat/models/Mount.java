package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.util.Collection;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Mount {
    @Id
    long id;
    Integer movement;
    Integer weaponSkill;
    Integer strength;
    Integer toughness;
    Integer initiative;
    Integer wounds;
    Integer attacks;
    Integer leadership;
    Integer armorSave;
    Integer wardSave;
    int basesize;
    String name;
    @ManyToOne
    @JoinColumn(name = "unit_type_id", referencedColumnName = "id", nullable = false)
    UnitType unitTypeByUnitTypeId;
    @OneToMany(mappedBy = "mountByMountId")
    Collection<MountSpecialRule> mountSpecialRulesById;
    @OneToMany(mappedBy = "mountByMountId")
    Collection<UnitMount> unitMountsById;
}
