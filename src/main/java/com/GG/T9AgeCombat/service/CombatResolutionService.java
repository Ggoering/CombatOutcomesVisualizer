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

        Integer primaryRankBonus = this.calculateRankBonus(primary);
        Integer secondaryRankBonus = this.calculateRankBonus(secondary);

        Integer primarySum = primaryRankBonus + primaryWoundsDealt + primary.getStandardBearer();
        Integer secondarySum = secondaryRankBonus + secondaryWoundsDealt + secondary.getStandardBearer();

        if (isFirstRound) {
            primarySum++;
        }

        int differential = primarySum - secondarySum;

        if (differential == 0) {
            primarySum = primarySum + primary.getMusician();
            secondarySum = secondarySum + secondary.getMusician();

            differential = primarySum - secondarySum;

            if (differential == 0) {
                return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(false).wipedOut(false).build();
            }
        }

        boolean steadfast;
        boolean flee;

        if (differential > 0) {
            steadfast = secondaryRankBonus > primaryRankBonus;
            flee = this.breakTest(secondary, differential, steadfast);

            return getRound(primary, primaryWoundsDealt, secondaryWoundsDealt, flee);
        } else {
            steadfast = secondaryRankBonus < primaryRankBonus;
            flee = this.breakTest(primary, Math.abs(differential), steadfast);

            return getRound(secondary, primaryWoundsDealt, secondaryWoundsDealt, flee);
        }
    }

    private Round getRound(Unit primary, Integer primaryWoundsDealt, Integer secondaryWoundsDealt, boolean flee) {
        boolean caught = false;

        if (flee) {
            int fleeDistance = diceRollingService.rollWithSum(2);
            int pursuitDistance = diceRollingService.rollWithSum(2);
            caught = fleeDistance > pursuitDistance;
        }

        return Round.builder().secondaryWoundsDealt(secondaryWoundsDealt).primaryWoundsDealt(primaryWoundsDealt).flee(flee).caught(caught).wipedOut(false).winner(primary.getName()).build();
    }

    Integer calculateRankBonus(Unit unit) {
        return unit.getModelCount() / unit.getWidth();
    }

    Boolean breakTest(Unit unit, Integer differential, boolean steadfast) {
        Integer leadership = unit.getLeadership();
        int leadershipCheck = diceRollingService.rollWithSum(2);

        if (!steadfast) {
            leadership = leadership - differential;
        }

        return leadershipCheck > leadership;
    }
}
