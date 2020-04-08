package com.GG.T9AgeCombat.models;
import com.GG.T9AgeCombat.enums.Identification;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Value
@JsonInclude(NON_NULL)
public class Round {
    Integer combatScoreDifferential;
    Integer primaryWoundsDealt;
    Integer secondaryWoundsDealt;
    Boolean flee;
    Boolean caught;
    Identification winner;
    Boolean wipedOut;
}
