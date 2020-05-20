package com.GG.T9AgeCombat.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

@Value
@Builder
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PropertyEntity {
    @Id
    long id;
    String name;
    Integer value;
    String modificationValue;
    String limitationValue;
    String timingValue;
}
