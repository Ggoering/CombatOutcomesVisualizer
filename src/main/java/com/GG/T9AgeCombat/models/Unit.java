package com.GG.T9AgeCombat.models;

import com.GG.T9AgeCombat.enums.Equipment;
import com.GG.T9AgeCombat.enums.Identification;
import com.GG.T9AgeCombat.enums.SpecialRules;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Value
@JsonInclude(NON_NULL)
public class Unit implements Comparable<Unit> {
    Identification name;
    Integer M;
    Integer OWS;
    Integer DWS;
    Integer S;
    Integer T;
    Integer I;
    Integer W;
    Integer A;
    Integer Ld;
    Integer baseSize;
    @NonFinal
    Integer count;
    Integer AS;
    Integer WS;
    Integer mountWS;
    Integer mountS;
    Integer mountA;
    Integer mountW;
    Integer mountI;
    Integer mountM;
    Integer width;
    Integer selection;
    Integer standardBearer;
    Integer musician;
    List<SpecialRules> specialRulesList;
    List<Equipment> equipmentList;

    public int compareTo(Unit compareUnits) {
        Integer compareInitiative = compareUnits.getI();

        return this.I - compareInitiative;
    }

    public void updateCount(Integer wounds) {
        Integer newCount = wounds >= this.getCount() ? 0 : this.getCount() - wounds;
        this.count = newCount;
    }
}
