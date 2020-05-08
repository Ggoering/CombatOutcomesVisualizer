package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.EquipmentEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class EquipmentClassification {
    long id;
    EquipmentEnum name;
    EquipmentTypeEnum type;
}

