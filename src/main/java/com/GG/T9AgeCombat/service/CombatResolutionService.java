package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class CombatResolutionService {
    DiceRollingService diceRollingService;

    public CombatResolutionService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Round determineResult(Unit primary, Unit secondary, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, boolean isFirstRound) {
        if (primary.getModelCount() <= 0) {
            return Round.builder().primaryWoundsDealt(primaryWoundsDealt).secondaryWoundsDealt(secondaryWoundsDealt).winner(secondary.getName()).wipedOut(true).flee(false).build();
        }

        if (secondary.getModelCount() <= 0) {
            return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).winner(primary.getName()).wipedOut(true).flee(false).build();
        }

        int primaryUnitRankBonus = primary.getRankBonus();
        int secondaryUnitRankBonus = secondary.getRankBonus();
        int primaryUnitCombatResolutionSum = primaryUnitRankBonus + primaryWoundsDealt + primary.getStandardBearer();
        int secondaryUnitCombatResolutionSum = secondaryUnitRankBonus + secondaryWoundsDealt + secondary.getStandardBearer();

        if (isFirstRound) {
            primaryUnitCombatResolutionSum++;
        }

        int combatResolutionDifference = primaryUnitCombatResolutionSum - secondaryUnitCombatResolutionSum;

        if (combatResolutionDifference == 0) {
            if (primary.isHasMusician()) {
                primaryUnitCombatResolutionSum++;
            }

            if (secondary.isHasMusician()) {
                secondaryUnitCombatResolutionSum++;
            }

            combatResolutionDifference = primaryUnitCombatResolutionSum - secondaryUnitCombatResolutionSum;

            if (combatResolutionDifference == 0) {
                return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(false).wipedOut(false).build();
            }
        }

        boolean isSteadfast;
        boolean flees;

        // Primary unit won combat
        if (combatResolutionDifference > 0) {
            isSteadfast = secondaryUnitRankBonus > primaryUnitRankBonus;
            flees = breakTest(secondary, combatResolutionDifference, isSteadfast);

            return getRound(primary, primaryWoundsDealt, secondaryWoundsDealt, flees);
        } else {
            isSteadfast = secondaryUnitRankBonus < primaryUnitRankBonus;
            flees = breakTest(primary, Math.abs(combatResolutionDifference), isSteadfast);

            return getRound(secondary, primaryWoundsDealt, secondaryWoundsDealt, flees);
        }
    }

    private Round getRound(Unit primary, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, boolean unitFlees) {
        boolean isCaught = false;

        if (unitFlees) {
            int fleeDistance = diceRollingService.rollWithSum(2);
            int pursuitDistance = diceRollingService.rollWithSum(2);
            isCaught = fleeDistance > pursuitDistance;
        }

        return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(unitFlees).caught(isCaught).wipedOut(false).winner(primary.getName()).build();
    }

    boolean breakTest(Unit unit, Integer differential, boolean steadfast) {
        Integer leadership = unit.getLeadership();
        int leadershipCheck = diceRollingService.rollWithSum(2);

        if (!steadfast) {
            leadership = leadership - differential;
        }

        return leadershipCheck != 2 || leadershipCheck > leadership;
    }
}
