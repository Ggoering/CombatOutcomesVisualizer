package com.GG.T9AgeCombat.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Value
@JsonInclude(NON_NULL)
public class Unit implements Comparable<Unit>{
    String name;
    Integer M;
    @NonFinal
    Integer OWS;
    Integer DWS;
    @NonFinal
    Integer S;
    Integer T;
    Integer I;
    Integer W;
    @NonFinal
    Integer A;
    Integer Ld;
    Integer baseSize;
    @NonFinal
    Integer Count;
    @NonFinal
    Integer AS;
    Integer WS;
    Integer mountWS;
    Integer mountS;
    Integer mountA;
    Integer mountW;
    Integer mountI;
    Integer mountM;
    Integer width;

    public int compareTo(Unit compareUnits) {
        Integer compareInitiative = compareUnits.getI();

        return this.I - compareInitiative;
    }
}
