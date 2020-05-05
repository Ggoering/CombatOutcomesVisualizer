package com.GG.T9AgeCombat.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SpecialRuleEntity {
    @Id
    long id;
    String name;
    int value;
    String modificationValue;
    String limitationValue;
    String timingValue;
}
