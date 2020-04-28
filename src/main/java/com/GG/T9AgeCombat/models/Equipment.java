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
@Table(name = "equipment", schema = "public", catalog = "T9AgeCombat")
public class Equipment {
    @Id
    long id;
    String name;
    @ManyToOne
    @JoinColumn(name = "equipment_type_id", referencedColumnName = "id", nullable = false)
    EquipmentType equipmentTypeByEquipmentTypeId;
    @OneToMany(mappedBy = "equipmentByEquipmentId")
    Collection<EquipmentSpecialRule> equipmentSpecialRulesById;
    @OneToMany(mappedBy = "equipmentByEquipmentId")
    Collection<UnitEquipment> unitEquipmentsById;
}
