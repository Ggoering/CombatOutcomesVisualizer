package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.dto.UnitEquipmentSpecialRuleDTO;
import com.GG.T9AgeCombat.enums.EquipmentCategoryEnum;
import com.GG.T9AgeCombat.enums.EquipmentClassificationEnum;
import com.GG.T9AgeCombat.enums.EquipmentTypeEnum;
import com.GG.T9AgeCombat.models.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class EquipmentService {
    SpecialRulePropertyService specialRulePropertyService;

    EquipmentService(SpecialRulePropertyService specialRulePropertyService) {
        this.specialRulePropertyService = specialRulePropertyService;
    }

    List<Equipment> buildEquipmentFromDTO(List<UnitEquipmentSpecialRuleDTO> equipmentList) {
        Map<Integer, List<UnitEquipmentSpecialRuleDTO>> equipmentSpecialRuleListMap = equipmentList.stream()
                .collect(groupingBy(UnitEquipmentSpecialRuleDTO::getId));
        Set<Integer> equipmentIds = equipmentSpecialRuleListMap.keySet();

        return equipmentIds.stream().map(eqId ->
                equipmentSpecialRuleListMap.get(eqId).get(0).getSpecialRuleName() == null ?
                        Equipment.builder()
                                .id(eqId)
                                .name(equipmentSpecialRuleListMap.get(eqId).get(0).getName())
                                .classification(EquipmentClassificationEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentClassification()))
                                .type(EquipmentTypeEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentType()))
                                .category(EquipmentCategoryEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentCategory()))
                                .isEquipped(equipmentSpecialRuleListMap.get(eqId).get(0).isDefault())
                                .build()
                        : Equipment.builder()
                        .id(eqId)
                        .name(equipmentSpecialRuleListMap.get(eqId).get(0).getName())
                        .classification(EquipmentClassificationEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentClassification()))
                        .type(EquipmentTypeEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentType()))
                        .category(EquipmentCategoryEnum.valueOf(equipmentSpecialRuleListMap.get(eqId).get(0).getEquipmentCategory()))
                        .isEquipped(equipmentSpecialRuleListMap.get(eqId).get(0).isDefault())
                        .specialRuleProperties(equipmentSpecialRuleListMap.get(eqId).stream()
                                .map(filteredEqDTO -> specialRulePropertyService.convertToSpecialRuleProperty(filteredEqDTO))
                                .collect(toList()))
                        .build()
        ).collect(toList());
    }
}
