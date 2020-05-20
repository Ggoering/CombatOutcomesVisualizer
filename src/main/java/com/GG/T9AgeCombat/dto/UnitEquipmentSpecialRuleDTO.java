package com.GG.T9AgeCombat.dto;

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
public class UnitEquipmentSpecialRuleDTO implements Serializable {
    @Id
    int id;
    String name;
    String equipmentClassification;
    String equipmentType;
    String equipmentCategory;
    boolean isDefault;
    String specialRuleName;
    Integer specialRuleValue;
    String modificationValue;
    String limitationValue;
    String timingValue;
}
