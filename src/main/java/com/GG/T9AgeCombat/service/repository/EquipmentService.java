package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.dto.UnitEquipmentSpecialRuleDTO;
import com.GG.T9AgeCombat.models.Equipment;
import com.GG.T9AgeCombat.models.EquipmentClassification;
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
        Map<EquipmentClassification, List<UnitEquipmentSpecialRuleDTO>> equipmentSpecialRuleListMap = equipmentList.stream()
                .collect(groupingBy(UnitEquipmentSpecialRuleDTO::toEquipmentId));
        Set<EquipmentClassification> equipmentIds = equipmentSpecialRuleListMap.keySet();
        return equipmentIds.stream().map(eqId ->
                Equipment.builder()
                        .name(eqId.getName())
                        .type(eqId.getType())
                        .specialRuleProperties(equipmentSpecialRuleListMap.get(eqId).stream()
                                .map(filteredEqDTO -> specialRulePropertyService.convertToSpecialRuleProperty(filteredEqDTO))
                                .collect(toList()))
                        .build()
        ).collect(toList());
    }
}
