package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.entities.EquipmentDTO;
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
    SpecialRuleService specialRuleService;

    EquipmentService(SpecialRuleService specialRuleService) {
        this.specialRuleService = specialRuleService;
    }

    List<Equipment> buildEquipmentFromDTO(List<EquipmentDTO> equipmentList) {
        Map<EquipmentClassification, List<EquipmentDTO>> equipmentSpecialRuleListMap = equipmentList.stream()
                .collect(groupingBy(EquipmentDTO::toEquipmentId));
        Set<EquipmentClassification> equipmentIds = equipmentSpecialRuleListMap.keySet();
        return equipmentIds.stream().map(eqId ->
                Equipment.builder()
                        .name(eqId.getName())
                        .type(eqId.getType())
                        .specialRules(equipmentSpecialRuleListMap.get(eqId).stream()
                                .map(filteredEqDTO -> specialRuleService.specialRuleFromDTO(filteredEqDTO))
                                .collect(toList()))
                        .build()
        ).collect(toList());
    }
}
