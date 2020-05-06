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
//@Table(name = "equipment_special_rule", schema = "public", catalog = "T9AgeCombat")
public class EquipmentSpecialRule {
    @Id
    long id;
//    @ManyToOne
//    @JoinColumn(name = "equipment_id", referencedColumnName = "id", nullable = false)
//    Equipment equipmentByEquipmentId;
//    @ManyToOne
//    @JoinColumn(name = "special_rule_id", referencedColumnName = "id", nullable = false)
//    SpecialRuleEntity specialRuleBySpecialRuleId;
}
