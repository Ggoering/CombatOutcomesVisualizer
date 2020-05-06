package com.GG.T9AgeCombat.service;

import com.GG.T9AgeCombat.models.Round;
import com.GG.T9AgeCombat.models.Unit;
import org.springframework.stereotype.Service;

@Service
public class CombatResolutionService {
    private static final int LEADERSHIP_TEST_AUTO_PASS = 2;
    private static final int LEADERSHIP_TEST_DEFAULT_DIE_COUNT = 2;
    private static final int FLEE_AND_PURSUIT_DEFAULT_DIE_COUNT = 2;
    private static final int FLEE_AND_PURSUIT_MOUNT_DIE_COUNT = 3;
    private static final int FLEE_AND_PURSUIT_MOUNT_TAKE_HIGHEST_COUNT = 2;
    DiceRollingService diceRollingService;

    public CombatResolutionService(DiceRollingService diceRollingService) {
        this.diceRollingService = diceRollingService;
    }

    Round calculateCombatResult(Unit primary, Unit secondary, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, boolean isFirstRound) {
        if (primary.getModelCount() <= 0) {
            return Round.builder().primaryWoundsDealt(primaryWoundsDealt).secondaryWoundsDealt(secondaryWoundsDealt)
                    .winner(secondary.getName()).wipedOut(true).flee(false).build();
        }

        if (secondary.getModelCount() <= 0) {
            return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt)
                    .winner(primary.getName()).wipedOut(true).flee(false).build();
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
                return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(false)
                        .wipedOut(false).build();
            }
        }

        boolean isSteadfast;
        boolean flees;

        // Primary unit won combat
        if (combatResolutionDifference > 0) {
            isSteadfast = secondaryUnitRankBonus > primaryUnitRankBonus;
            flees = breakTest(secondary, combatResolutionDifference, isSteadfast);

            return createRound(primary, secondary, primaryWoundsDealt, secondaryWoundsDealt, flees);
        } else {
            isSteadfast = secondaryUnitRankBonus < primaryUnitRankBonus;
            flees = breakTest(primary, Math.abs(combatResolutionDifference), isSteadfast);

            return createRound(secondary, primary, primaryWoundsDealt, secondaryWoundsDealt, flees);
        }
    }

    Round createRound(Unit winner, Unit loser, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, boolean unitFlees) {
        boolean isCaught = false;

        if (unitFlees) {
            int fleeDistance = (loser.getActualMovement() != 0 ?
                    diceRollingService.rollWithSumTakeHighest(FLEE_AND_PURSUIT_MOUNT_DIE_COUNT, FLEE_AND_PURSUIT_MOUNT_TAKE_HIGHEST_COUNT) :
                    diceRollingService.rollWithSum(FLEE_AND_PURSUIT_DEFAULT_DIE_COUNT));
            int pursuitDistance = (winner.getActualMovement() != 0 ?
                    diceRollingService.rollWithSumTakeHighest(FLEE_AND_PURSUIT_MOUNT_DIE_COUNT, FLEE_AND_PURSUIT_MOUNT_TAKE_HIGHEST_COUNT) :
                    diceRollingService.rollWithSum(FLEE_AND_PURSUIT_DEFAULT_DIE_COUNT));
            isCaught = fleeDistance > pursuitDistance;
        }

        return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(unitFlees)
                .caught(isCaught).wipedOut(false).winner(winner.getName()).build();
    }

    boolean breakTest(Unit unit, Integer differential, boolean steadfast) {
        int leadership = unit.getActualLeadership();
        int leadershipCheck = diceRollingService.rollWithSum(LEADERSHIP_TEST_DEFAULT_DIE_COUNT);

        if (!steadfast) {
            leadership = leadership - differential;
        }

        return leadershipCheck != LEADERSHIP_TEST_AUTO_PASS || leadershipCheck > leadership;
    }
}
