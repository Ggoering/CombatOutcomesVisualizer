package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.EquipmentCategoryEnum;
import com.GG.T9AgeCombat.enums.EquipmentClassificationEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class Equipment {
    int id;
    String name;
    EquipmentClassificationEnum classification;
    EquipmentTypeEnum type;
    EquipmentCategoryEnum category;
    @NonFinal
    boolean isEquipped;
    @NonFinal
    @Builder.Default
    List<SpecialRuleProperty> specialRuleProperties = new ArrayList<>();

    public void setSpecialRuleProperties(List<SpecialRuleProperty> specialRuleProperties) {
        this.specialRuleProperties = specialRuleProperties;
    }
}

