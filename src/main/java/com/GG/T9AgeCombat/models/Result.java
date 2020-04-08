package com.GG.T9AgeCombat.models;
import com.GG.T9AgeCombat.enums.Identification;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Value
@JsonInclude(NON_NULL)
public class Result {
    List<Round> roundResults;
    Integer endingRound;
    Identification winner;
}
