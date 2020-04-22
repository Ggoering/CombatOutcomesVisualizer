package com.GG.T9AgeCombat.controllers;

import com.GG.T9AgeCombat.service.CombatAggregationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/results")
public class ResultsController {
    public final CombatAggregationService combatAggregationService;

    public ResultsController(CombatAggregationService combatAggregationService) {
        this.combatAggregationService = combatAggregationService;
        combatAggregationService.getDataAggregation();
    }

    @PostMapping
    public void getResult(@Valid @RequestBody Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId, Integer secondaryCount, Integer secondaryWidth) {
        combatAggregationService.getDataAggregation();
    }
}
