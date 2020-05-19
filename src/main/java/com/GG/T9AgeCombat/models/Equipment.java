package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.EquipmentEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

@Value
@Builder
public class Equipment {
    EquipmentEnum name;
    EquipmentTypeEnum type;
    @NonFinal
    boolean isEquipped;
    List<SpecialRuleProperty> specialRuleProperties;
}

