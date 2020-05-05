package com.GG.T9AgeCombat.models;


import com.GG.T9AgeCombat.enums.LimitationEnum;
import com.GG.T9AgeCombat.enums.ModificationEnum;
import com.GG.T9AgeCombat.enums.SpecialRuleEnum;
import com.GG.T9AgeCombat.enums.TimingEnum;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialRule {
    private final SpecialRuleEnum name;
    private final TimingEnum timing;
    private final ModificationEnum modification;
    private final Integer value;
    private final LimitationEnum limitation;
}
