package com.GG.T9AgeCombat.service.repository;

import com.GG.T9AgeCombat.dto.UnitEquipmentSpecialRuleDTO;
import com.GG.T9AgeCombat.entities.PropertyEntity;
import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.SpecialRulePropertyEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import com.GG.T9AgeCombat.models.SpecialRuleProperty;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SpecialRulePropertyService {
    public List<SpecialRuleProperty> convertToSpecialRuleProperty(List<PropertyEntity> propertyEntityList) {
        return propertyEntityList.stream().map(dto ->
                SpecialRuleProperty.builder()
                        .name(SpecialRulePropertyEnum.valueOf(dto.getName()))
                        .limitation(LimitationEnum.valueOf(dto.getLimitationValue()))
                        .modification(ModificationEnum.valueOf(dto.getModificationValue()))
                        .timing(TimingEnum.valueOf(dto.getTimingValue()))
                        .value(dto.getValue())
                        .build()
        ).collect(toList());
    }

    public SpecialRuleProperty convertToSpecialRuleProperty(UnitEquipmentSpecialRuleDTO entity) {
        return SpecialRuleProperty.builder()
                .name(SpecialRulePropertyEnum.valueOf(entity.getSpecialRuleName()))
                .limitation(LimitationEnum.valueOf(entity.getLimitationValue()))
                .modification(ModificationEnum.valueOf(entity.getModificationValue()))
                .timing(TimingEnum.valueOf(entity.getTimingValue()))
                .value(entity.getSpecialRuleValue())
                .build();
    }
}
