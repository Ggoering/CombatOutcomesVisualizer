package com.GG.T9AgeCombat.controllers;

import com.GG.T9AgeCombat.models.CombatResultResponse;
import com.GG.T9AgeCombat.models.Result;
import com.GG.T9AgeCombat.models.Unit;
import com.GG.T9AgeCombat.service.CombatResultService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultsController {
    private final CombatResultService combatResultService;

    public ResultsController(CombatResultService combatResultService
    ) {
        this.combatResultService = combatResultService;

    }

    @PostMapping
    public List<Result> getResult(@Valid @RequestBody Integer primaryId, Integer primaryCount, Integer primaryWidth, Integer secondaryId, Integer secondaryCount, Integer secondaryWidth) {
        return combatResultService.getResult(primaryId, primaryCount, primaryWidth, secondaryId, secondaryCount, secondaryWidth);
    }
}
