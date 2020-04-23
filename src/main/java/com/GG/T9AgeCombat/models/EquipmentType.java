package com.GG.T9AgeCombat.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "equipment_type", schema = "public", catalog = "T9AgeCombat")
public class EquipmentType {
    @Id
    long id;
    String type;
    Integer limit;
    @OneToMany(mappedBy = "equipmentTypeByEquipmentTypeId")
    Collection<Equipment> equipmentById;
}
