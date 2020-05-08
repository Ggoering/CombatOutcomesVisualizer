package com.GG.T9AgeCombat.entities;

import com.GG.T9AgeCombat.models.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
//@Table(name = "unit_equipment", schema = "public", catalog = "T9AgeCombat")
public class UnitEquipment {
    @Id
    long id;
//    @ManyToOne
//    Equipment equipmentByEquipmentId;
}
