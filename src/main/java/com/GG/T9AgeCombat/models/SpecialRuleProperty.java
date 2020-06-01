package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.SpecialRulePropertyEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialRuleProperty {
    SpecialRulePropertyEnum name;
    TimingEnum timing;
    ModificationEnum modification;
    LimitationEnum limitation;
    Integer value;
}
