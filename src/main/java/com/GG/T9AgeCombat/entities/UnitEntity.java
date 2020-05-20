package com.GG.T9AgeCombat.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Value
@Builder
@Entity
@JsonInclude(NON_NULL)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UnitEntity {
    @Id
    long id;
    String faction;
    String unitHeight;
    String unitType;
    String name;
    int advance;
    int march;
    int leadership;
    int basesize;
    boolean canHaveMusician;
    boolean canHaveStandard;
    int pointCost;
    int extraModelPointCost;
    int defaultModelCount;
    int maximumModelCount;
}
