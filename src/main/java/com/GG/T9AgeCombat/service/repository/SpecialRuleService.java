package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.entities.EquipmentDTO;
import com.GG.T9AgeCombat.entities.SpecialRuleEntity;
import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.SpecialRuleEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.SpecialRule;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SpecialRuleService {
    public List<SpecialRule> specialRuleFromDTO(List<SpecialRuleEntity> specialRuleDTOList) {
        return specialRuleDTOList.stream().map(dto ->
                SpecialRule.builder()
                        .name(SpecialRuleEnum.valueOf(dto.getName()))
                        .limitation(LimitationEnum.valueOf(dto.getLimitationValue()))
                        .modification(ModificationEnum.valueOf(dto.getModificationValue()))
                        .timing(TimingEnum.valueOf(dto.getTimingValue()))
                        .value(dto.getValue())
                        .build()
        ).collect(toList());
    }

    public SpecialRule specialRuleFromDTO(EquipmentDTO entity) {
        return SpecialRule.builder()
                .name(SpecialRuleEnum.valueOf(entity.getSpecialRuleName()))
                .limitation(LimitationEnum.valueOf(entity.getLimitationValue()))
                .modification(ModificationEnum.valueOf(entity.getModificationValue()))
                .timing(TimingEnum.valueOf(entity.getTimingValue()))
                .value(entity.getSpecialRuleValue())
                .build();
    }
}
