package com.GG.T9AgeCombat.entities;

import com.GG.T9AgeCombat.enums.EquipmentEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import com.GG.T9AgeCombat.models.EquipmentClassification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EquipmentDTO implements Serializable {
    @Id
    long id;
    String name;
    String equipmentType;
    @Id
    String specialRuleName;
    int specialRuleValue;
    String modificationValue;
    String limitationValue;
    String timingValue;

    public EquipmentClassification toEquipmentId() {
        return EquipmentClassification.builder()
                .id(id)
                .name(EquipmentEnum.valueOf(name))
                .type(EquipmentTypeEnum.valueOf(equipmentType))
                .build();
    }
}
